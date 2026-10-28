package ru.gr0550x.lec7.infrastructure;

import ru.gr0550x.lec7.domain.Task;
import ru.gr0550x.lec7.domain.TaskRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTaskRepository implements TaskRepository {
    private final List<Task> tasks = new ArrayList<>();

    @Override
    public void save(Task task) { tasks.add(task); }

    @Override
    public List<Task> findAll() { return List.copyOf(tasks); }
}