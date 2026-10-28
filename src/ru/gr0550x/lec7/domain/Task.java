package ru.gr0550x.lec7.domain;

import java.time.LocalDate;

public class Task {
    private final String title;
    private final LocalDate deadline;
    private boolean done;
    private boolean archived;
    private final Priority priority;

    public Task(String title, LocalDate deadline, Priority priority) {
        if (title == null || title.isBlank())
            throw new IllegalArgumentException("Название задачи не может быть пустым");
        if (deadline == null)
            throw new IllegalArgumentException("Дата обязательна");
        if (priority == null)
            throw new IllegalArgumentException("Приоритет обязателен");
        this.title = title.trim();
        this.deadline = deadline;
        this.priority = priority;
    }

    public String getTitle()      { return title; }
    public LocalDate getDeadline(){ return deadline; }
    public Priority getPriority() { return priority; }
    public boolean isDone()       { return done; }
    public boolean isArchived()   { return archived; }

    public void archive() { archived = true; }

    public void complete() {
        if (archived) throw new IllegalStateException("Архивную задачу нельзя отметить выполненной");
        done = true;
    }

    public boolean isOverdue() {
        return !done && deadline.isBefore(LocalDate.now());
    }
}