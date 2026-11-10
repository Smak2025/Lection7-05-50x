package ru.gr0550x.lec7.ui;

import ru.gr0550x.lec7.application.*;
import ru.gr0550x.lec7.domain.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskListPresenter implements TaskChangeListener {
    private final TaskView view;
    private final TaskService service;
    private final CommandHistory history;

    // Генераторы передаются презентеру при создании.
    private final List<TaskReportGenerator> reportGenerators;

    public TaskListPresenter(
            TaskWindow view,
            TaskService service,
            CommandHistory history,
            List<TaskReportGenerator> reportGenerators
    ) {
        this.view = view;
        this.service = service;
        this.history = history;
        this.reportGenerators = reportGenerators;
    }

    public void start() {
        service.addChangeListener(this);
        refresh();
    }

    @Override
    public void tasksChanged(){
        refresh();
    }

    public void completeSelected() {
        Task task = view.getSelectedTask();
        if (task == null) {
            view.showError("Выберите задачу в списке");
            return;
        }
        if (task.isDone()){
            view.showError("Задача уже выполнена");
            return;
        }
        try {
            history.execute(new CompleteTaskCommand(service, task));
        } catch (IllegalStateException ex) {
            view.showError(ex.getMessage());
        }
    }

    public void undoLast(){
        if (!history.undo()){
            view.showError("Отменять нечего");
        }
    }

    public void refresh() {
        TaskFilter filter = view.getFilter();
        List<Task> result = new ArrayList<>();
        for (Task t : service.getAllTasks()) {
            if (filter.condition().test(t)) result.add(t);
        }
        view.showTasks(result);
        view.showStatistics(formatStatistics(service.getStatistics()));
    }

    private String formatStatistics(TaskStatistics s) {
        return "Всего: " + s.total() + ", выполнено: " + s.done() + ", просрочено: " + s.overdue();
    }

    public void showReport(int generatorNum){
        if (generatorNum < 0 || generatorNum >= reportGenerators.size()){
            view.showError("Выбран неподдерживаемый тип отчета");
            return;
        }
        showReport(reportGenerators.get(generatorNum));
    }

    // Общий сценарий получения и показа отчёта.
    private void showReport(TaskReportGenerator generator) {
        // Для отчёта получаем все задачи из сервиса.
        List<Task> tasks = service.getAllTasks();

        // Построение текста делегируем выбранному генератору.
        String report = generator.generate(tasks);

        // Готовый текст передаём представлению.
        view.showReport(report);
    }
}