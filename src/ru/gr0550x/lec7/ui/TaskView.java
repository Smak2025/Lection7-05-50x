package ru.gr0550x.lec7.ui;

import ru.gr0550x.lec7.domain.Priority;
import ru.gr0550x.lec7.domain.Task;

import java.util.List;

public interface TaskView {
    String getTaskTitle();
    String getDeadlineText();
    Priority getPriority();
    boolean isArchived();
    TaskFilter getFilter();
    Task getSelectedTask();

    void clearForm();
    void showTasks(List<Task> tasks);
    void showStatistics(String text);
    void showError(String message);
}