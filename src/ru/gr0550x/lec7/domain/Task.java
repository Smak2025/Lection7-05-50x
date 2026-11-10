package ru.gr0550x.lec7.domain;

import java.time.LocalDate;

public class Task {
    private final String title;
    private final LocalDate deadline;
    private boolean done;
    private boolean archived;
    private final Priority priority;

    // Необязательное описание задачи.
    private final String description;

    public Task(String title, LocalDate deadline, Priority priority){
        this(title, deadline, priority, "");
    }

    // Общий конструктор, содержащий проверки обязательных параметров.
    private Task(String title, LocalDate deadline, Priority priority,
                 String description) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException(
                    "Название задачи не может быть пустым");
        }

        if (deadline == null) {
            throw new IllegalArgumentException("Дата обязательна");
        }

        if (priority == null) {
            throw new IllegalArgumentException("Приоритет обязателен");
        }

        this.title = title.trim();
        this.deadline = deadline;
        this.priority = priority;

        // Если описание не указано, используем пустую строку.
        this.description = description == null ? "" : description.trim();
    }

    public String getTitle()      { return title; }
    public LocalDate getDeadline(){ return deadline; }
    public Priority getPriority() { return priority; }
    public boolean isDone()       { return done; }
    public boolean isArchived()   { return archived; }

    public String getDescription() { return description; }

    public void archive() { archived = true; }

    public void complete() {
        if (archived) throw new IllegalStateException("Архивную задачу нельзя отметить выполненной");
        done = true;
    }

    public void reopen(){
        done = false;
    }

    public boolean isOverdue() {
        return !done && deadline.isBefore(LocalDate.now());
    }

    // Создание строителя начинается с обязательных параметров.
    public static Builder builder(String title, LocalDate deadline,
                                  Priority priority) {
        return new Builder(title, deadline, priority);
    }

    // Вложенный класс, который собирает параметры будущей задачи.
    public static final class Builder {
        private final String title;
        private final LocalDate deadline;
        private final Priority priority;

        // Необязательные параметры имеют значения по умолчанию.
        private String description = "";
        private boolean archived = false;

        private Builder(String title, LocalDate deadline, Priority priority) {
            this.title = title;
            this.deadline = deadline;
            this.priority = priority;
        }

        public Builder description(String description) {
            this.description = description;
            return this; // Возвращаем строитель для цепочки вызовов.
        }

        public Builder archived(boolean archived) {
            this.archived = archived;
            return this;
        }

        public Task build() {
            // Проверки обязательных параметров выполняет конструктор Task.
            Task task = new Task(title, deadline, priority, description);

            // Используем существующий метод доменной модели.
            if (archived) {
                task.archive();
            }

            return task;
        }
    }
}