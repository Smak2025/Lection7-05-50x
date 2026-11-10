package ru.gr0550x.lec7.application;

import ru.gr0550x.lec7.domain.Task;

import java.util.List;

// Creator: содержит общий алгоритм построения отчёта.
public abstract class TaskReportGenerator {

    // Product: объект, который оформляет сведения об одной задаче.
    protected interface TaskFormatter {
        String format(Task task);
    }

    // Factory Method: подкласс определяет конкретный форматировщик.
    protected abstract TaskFormatter createFormatter();

    // Общий алгоритм построения отчёта.
    public String generate(List<Task> tasks) {
        TaskFormatter formatter = createFormatter();

        StringBuilder report = new StringBuilder();
        report.append("Отчёт о задачах\n\n");

        for (Task task : tasks) {
            // Оформление отдельной задачи делегируем продукту.
            report.append(formatter.format(task));
            report.append('\n');
        }

        report.append("\nВсего задач: ");
        report.append(tasks.size());

        return report.toString();
    }
}