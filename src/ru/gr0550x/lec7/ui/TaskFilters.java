package ru.gr0550x.lec7.ui;

import ru.gr0550x.lec7.domain.Priority;

import java.util.List;

public final class TaskFilters {
    private TaskFilters() { }

    public static List<TaskFilter> standard() {
        return List.of(
                new TaskFilter("Все", task -> true),
                new TaskFilter("Активные", task -> !task.isDone()),
                new TaskFilter("Просроченные", task -> task.isOverdue()),
                new TaskFilter("Высокий приоритет", task -> task.getPriority() == Priority.HIGH)
        );
    }
}