package ru.gr0550x.lec7.ui;

import ru.gr0550x.lec7.domain.Task;

@FunctionalInterface
public interface TaskCondition {
    boolean test(Task task);
}