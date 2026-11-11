import ru.gr0550x.lec7.application.*;
import ru.gr0550x.lec7.infrastructure.InMemoryTaskRepository;
import ru.gr0550x.lec7.ui.TaskWindow;

void main() {
    TaskService service = new TaskService(new InMemoryTaskRepository());
    List<TaskReportGenerator> reports = new ArrayList<>();
    reports.add(new ShortTaskReport());
    reports.add(new DetailedTaskReport());
    CommandHistory history = new CommandHistory();
    new TaskWindow(service, history, reports).setVisible(true);
}