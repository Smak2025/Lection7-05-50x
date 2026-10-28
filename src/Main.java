import ru.gr0550x.lec7.application.TaskService;
import ru.gr0550x.lec7.infrastructure.InMemoryTaskRepository;
import ru.gr0550x.lec7.ui.TaskWindow;

void main() {
    TaskService service = new TaskService(new InMemoryTaskRepository());
    new TaskWindow(service).setVisible(true);
}