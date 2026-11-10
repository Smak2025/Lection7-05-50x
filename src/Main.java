import ru.gr0550x.lec7.application.*;
import ru.gr0550x.lec7.domain.TaskRepository;
import ru.gr0550x.lec7.infrastructure.InMemoryTaskRepository;
import ru.gr0550x.lec7.infrastructure.LoggingTaskRepository;
import ru.gr0550x.lec7.ui.TaskWindow;

void main() {
    // Создаём обычный репозиторий.
    TaskRepository repository = new InMemoryTaskRepository();

    // Добавляем журналирование через обёртку.
    TaskRepository loggingRepository =
            new LoggingTaskRepository(repository);

    // Сервис по-прежнему получает TaskRepository.
    TaskService service = new TaskService(loggingRepository);
    CommandHistory history = new CommandHistory();
    List<TaskReportGenerator> reportGenerators = new ArrayList<>();
    reportGenerators.add(new ShortTaskReport());
    reportGenerators.add(new DetailedTaskReport());

    // service и history — те же объекты, которые уже создаются при запуске.
    TaskWindow window = new TaskWindow(
            service,
            history,
            reportGenerators
    );

    window.setVisible(true);
}