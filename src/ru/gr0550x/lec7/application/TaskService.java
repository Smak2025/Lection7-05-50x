package ru.gr0550x.lec7.application;

import ru.gr0550x.lec7.domain.Priority;
import ru.gr0550x.lec7.domain.Task;
import ru.gr0550x.lec7.domain.TaskRepository;

import java.time.LocalDate;
import java.util.List;

public class TaskService {
    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public void createTask(String title, LocalDate deadline, Priority priority, boolean archived) {
        Task task = new Task(title, deadline, priority);
        if (archived) task.archive();
        repository.save(task);
    }

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public TaskStatistics getStatistics() {
        List<Task> tasks = repository.findAll();
        int done = 0;
        int overdue = 0;
        for (Task t : tasks) {
            if (t.isDone()) done++;
            if (t.isOverdue()) overdue++;
        }
        return new TaskStatistics(tasks.size(), done, overdue);
    }
}