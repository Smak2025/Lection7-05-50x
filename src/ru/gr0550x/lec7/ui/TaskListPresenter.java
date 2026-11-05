package ru.gr0550x.lec7.ui;

import ru.gr0550x.lec7.application.TaskChangeListener;
import ru.gr0550x.lec7.application.TaskService;
import ru.gr0550x.lec7.application.TaskStatistics;
import ru.gr0550x.lec7.domain.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskListPresenter implements TaskChangeListener {
    private final TaskView view;
    private final TaskService service;

    public TaskListPresenter(TaskView view, TaskService service) {
        this.view = view;
        this.service = service;
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
        try {
            service.complete(task);
        } catch (IllegalStateException ex) {
            view.showError(ex.getMessage());
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
}