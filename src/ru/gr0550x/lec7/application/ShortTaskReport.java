package ru.gr0550x.lec7.application;

import ru.gr0550x.lec7.domain.Task;

// ConcreteCreator: создаёт краткий форматировщик.
public class ShortTaskReport extends TaskReportGenerator {

    @Override
    protected TaskFormatter createFormatter() {
        return new ShortFormatter();
    }

    // ConcreteProduct: выводит только название задачи.
    private static class ShortFormatter implements TaskFormatter {

        @Override
        public String format(Task task) {
            return "- " + task.getTitle();
        }
    }
}