import ru.gr0550x.lec7.application.CommandHistory;
import ru.gr0550x.lec7.application.TaskService;
import ru.gr0550x.lec7.infrastructure.InMemoryTaskRepository;
import ru.gr0550x.lec7.ui.TaskWindow;

void main() {
    TaskService service = new TaskService(new InMemoryTaskRepository());
    CommandHistory history = new CommandHistory();
    new TaskWindow(service, history).setVisible(true);
}