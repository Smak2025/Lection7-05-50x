package ru.gr0550x.lec7.application;

import ru.gr0550x.lec7.domain.Task;

import java.util.List;

public abstract class TaskReportGenerator {

    //Product
    protected interface TaskFormatter{
        String format(Task task);
    }

    // Фабричный метод
    protected abstract TaskFormatter createFormatter();

    public String generate(List<Task> tasks){
        TaskFormatter formatter = createFormatter();
        StringBuilder report = new StringBuilder();
        report.append("Отчет о задачах\n\n");

        for (var task: tasks){
            report.append(formatter.format(task));
            report.append('\n');
        }

        report.append("\nВсего задач: ");
        report.append(tasks.size());

        return report.toString();
    }

}
