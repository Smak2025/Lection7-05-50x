package ru.gr0550x.lec7.ui;

import ru.gr0550x.lec7.application.TaskService;
import ru.gr0550x.lec7.domain.Priority;
import ru.gr0550x.lec7.domain.Task;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TaskWindow extends JFrame implements TaskView {

    private final TaskFormPresenter formPresenter;
    private final TaskListPresenter listPresenter;

    private final JTextField titleField = new JTextField(20);
    private final JTextField deadlineField = new JTextField(10);
    private final JComboBox<Priority> priorityBox = new JComboBox<>(Priority.values());
    private final JCheckBox archivedBox = new JCheckBox("В архив");
    private final JComboBox<TaskFilter> filterBox =
            new JComboBox<>(TaskFilters.standard().toArray(new TaskFilter[0]));
    private final JButton saveButton = new JButton("Добавить");
    private final JButton completeButton = new JButton("Выполнить");
    private final DefaultListModel<Task> listModel = new DefaultListModel<>();
    private final JList<Task> taskList = new JList<>(listModel);
    private final JLabel statisticsLabel = new JLabel(" ");

    public TaskWindow(TaskService service) {
        this.formPresenter = new TaskFormPresenter(this, service);
        this.listPresenter = new TaskListPresenter(this, service);

        setTitle("Список задач");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        buildLayout();
        setSize(520, 440);
        setLocationRelativeTo(null);
        listPresenter.start();
    }

    private void buildLayout() {
        JPanel form = new JPanel(new GridLayout(4, 2, 5, 5));
        form.add(new JLabel("Название:"));
        form.add(titleField);
        form.add(new JLabel("Дедлайн yyyy-MM-dd:"));
        form.add(deadlineField);
        form.add(new JLabel("Приоритет:"));
        form.add(priorityBox);
        form.add(new JLabel("Архивная:"));
        form.add(archivedBox);

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.add(saveButton);
        buttons.add(completeButton);

        JPanel top = new JPanel(new BorderLayout(5, 5));
        top.add(form, BorderLayout.CENTER);
        top.add(buttons, BorderLayout.SOUTH);

        JPanel bottom = new JPanel(new BorderLayout(5, 5));
        bottom.add(filterBox, BorderLayout.NORTH);
        bottom.add(new JScrollPane(taskList), BorderLayout.CENTER);
        bottom.add(statisticsLabel, BorderLayout.SOUTH);

        add(top, BorderLayout.NORTH);
        add(bottom, BorderLayout.CENTER);

        taskList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean selected,
                                                          boolean hasFocus) {
                super.getListCellRendererComponent(list, value, index, selected, hasFocus);
                Task task = (Task) value;
                String text = (task.isDone() ? "[x] " : "[ ] ") + task.getTitle()
                        + " | " + task.getDeadline() + " | " + task.getPriority();
                if (task.isOverdue()) {
                    text += " (просрочено)";
                }
                setText(text);
                setForeground(colorFor(task.getPriority()));
                return this;
            }
        });

        saveButton.addActionListener(e -> {
            if (formPresenter.saveClicked()) listPresenter.refresh();
        });
        completeButton.addActionListener(e -> listPresenter.completeSelected());
        filterBox.addActionListener(e -> listPresenter.refresh());
    }

    private Color colorFor(Priority priority) {
        return switch (priority) {
            case HIGH -> Color.RED;
            case MEDIUM -> Color.ORANGE;
            case LOW -> Color.GRAY;
        };
    }

    @Override public String getTaskTitle()    { return titleField.getText(); }
    @Override public String getDeadlineText() { return deadlineField.getText(); }
    @Override public Priority getPriority()   { return (Priority) priorityBox.getSelectedItem(); }
    @Override public boolean isArchived()     { return archivedBox.isSelected(); }
    @Override public TaskFilter getFilter()   { return (TaskFilter) filterBox.getSelectedItem(); }
    @Override public Task getSelectedTask()   { return taskList.getSelectedValue(); }

    @Override
    public void clearForm() {
        titleField.setText("");
        deadlineField.setText("");
        archivedBox.setSelected(false);
    }

    @Override
    public void showTasks(List<Task> tasks) {
        listModel.clear();
        for (Task t : tasks) listModel.addElement(t);
    }

    @Override
    public void showStatistics(String text) {
        statisticsLabel.setText(text);
    }

    @Override
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}