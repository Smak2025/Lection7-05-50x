package ru.gr0550x.lec7.application;

import ru.gr0550x.lec7.domain.Task;

public class ShortTaskReport extends TaskReportGenerator{

    @Override
    protected TaskFormatter createFormatter() {
        return new ShortFormatter();
    }

    private static class ShortFormatter implements TaskFormatter{

        @Override
        public String format(Task task) {
            return "- " + task.getTitle();
        }
    }
}
