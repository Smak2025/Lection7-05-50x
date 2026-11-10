package ru.gr0550x.lec7.application;

import ru.gr0550x.lec7.domain.Task;

// ConcreteCreator: создаёт подробный форматировщик.
public class DetailedTaskReport extends TaskReportGenerator {

    @Override
    protected TaskFormatter createFormatter() {
        return new DetailedFormatter();
    }

    // ConcreteProduct: выводит несколько свойств задачи.
    private static class DetailedFormatter implements TaskFormatter {

        @Override
        public String format(Task task) {
            return "- " + task.getTitle()
                    + " | срок: " + task.getDeadline()
                    + " | приоритет: " + task.getPriority();
        }
    }
}