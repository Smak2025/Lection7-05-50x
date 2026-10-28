package ru.gr0550x.lec7.ui;

import ru.gr0550x.lec7.application.TaskService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class TaskFormPresenter {
    private final TaskView view;
    private final TaskService service;

    public TaskFormPresenter(TaskView view, TaskService service) {
        this.view = view;
        this.service = service;
    }

    // Возвращает true, если задача сохранена
    public boolean saveClicked() {
        try {
            LocalDate deadline = parseDeadline(view.getDeadlineText());
            service.createTask(view.getTaskTitle(), deadline, view.getPriority(), view.isArchived());
            view.clearForm();
            return true;
        } catch (IllegalArgumentException ex) {
            view.showError(ex.getMessage());
            return false;
        }
    }

    private LocalDate parseDeadline(String text) {
        if (text == null || text.isBlank())
            throw new IllegalArgumentException("Дата выполнения не может быть пустой");
        try {
            return LocalDate.parse(text.trim());
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Дата должна быть в формате yyyy-MM-dd");
        }
    }
}