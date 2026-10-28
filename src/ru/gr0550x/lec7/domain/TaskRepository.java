package ru.gr0550x.lec7.domain;

import java.util.List;

public interface TaskRepository {
    void save(Task task);
    List<Task> findAll();
}