package ru.gr0550x.lec7.ui;

public record TaskFilter(String name, TaskCondition condition) {
    @Override
    public String toString() { return name; }
}