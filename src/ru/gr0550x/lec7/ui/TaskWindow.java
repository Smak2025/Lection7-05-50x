package ru.gr0550x.lec7.ui;

import ru.gr0550x.lec7.application.CommandHistory;
import ru.gr0550x.lec7.application.TaskReportGenerator;
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
    private final JButton undoButton = new JButton("Отменить");

    private final JButton shortReportButton = new JButton("Краткий отчёт");

    private final JButton detailedReportButton = new JButton("Подробный отчёт");
    private final DefaultListModel<Task> listModel = new DefaultListModel<>();
    private final JList<Task> taskList = new JList<>(listModel);
    private final JLabel statisticsLabel = new JLabel(" ");

    public TaskWindow(
            TaskService service,
            CommandHistory history,
            List<TaskReportGenerator> reportGenerators
    ) {
        this.formPresenter = new TaskFormPresenter(
                this, service, history
        );

        this.listPresenter = new TaskListPresenter(
                this, service, history, reportGenerators
        );

        setTitle("Список задач");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        buildLayout();
        setSize(520, 440);
        setLocationRelativeTo(null);

        // Подписка на изменения и первоначальный показ списка.
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

        // Первая строка: операции над задачами.
        JPanel taskButtons = new JPanel(new FlowLayout());
        taskButtons.add(saveButton);
        taskButtons.add(completeButton);
        taskButtons.add(undoButton);

        // Вторая строка: варианты отчёта.
        JPanel reportButtons = new JPanel(new FlowLayout());
        reportButtons.add(shortReportButton);
        reportButtons.add(detailedReportButton);

        // Две строки кнопок располагаются друг под другом.
        JPanel controls = new JPanel(new GridLayout(2, 1));
        controls.add(taskButtons);
        controls.add(reportButtons);

        JPanel top = new JPanel(new BorderLayout(5, 5));
        top.add(form, BorderLayout.CENTER);
        top.add(controls, BorderLayout.SOUTH);

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
            formPresenter.saveClicked();
        });
        completeButton.addActionListener(e -> listPresenter.completeSelected());
        filterBox.addActionListener(e -> listPresenter.refresh());

        undoButton.addActionListener(e -> {
            listPresenter.undoLast();
        });

        // Окно только сообщает презентеру о действии пользователя.
        shortReportButton.addActionListener(
                e -> listPresenter.showReport(0)
        );

        detailedReportButton.addActionListener(
                e -> listPresenter.showReport(1)
        );
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

    @Override
    public void showReport(String report) {
        JTextArea textArea = new JTextArea(report, 15, 45);

        // Пользователь просматривает отчёт, но не редактирует его.
        textArea.setEditable(false);

        JOptionPane.showMessageDialog(
                this,
                new JScrollPane(textArea),
                "Отчёт о задачах",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}