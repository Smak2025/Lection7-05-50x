import ru.gr0550x.lec7.application.*;
import ru.gr0550x.lec7.infrastructure.InMemoryTaskRepository;
import ru.gr0550x.lec7.ui.TaskWindow;

void main() {
    TaskService service = new TaskService(new InMemoryTaskRepository());
    CommandHistory history = new CommandHistory();
    TaskReportGenerator shortReport = new ShortTaskReport();
    TaskReportGenerator detailedReport = new DetailedTaskReport();

    // service и history — те же объекты, которые уже создаются при запуске.
    TaskWindow window = new TaskWindow(
            service,
            history,
            shortReport,
            detailedReport
    );

    window.setVisible(true);
}