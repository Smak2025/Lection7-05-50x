package ru.gr0550x.lec7.infrastructure;

import ru.gr0550x.lec7.domain.Task;
import ru.gr0550x.lec7.domain.TaskRepository;

import java.util.List;

// Декоратор: добавляет журналирование к любому TaskRepository.
public class LoggingTaskRepository implements TaskRepository {

    // Обёрнутый репозиторий.
    private final TaskRepository inner;

    public LoggingTaskRepository(TaskRepository inner) {
        this.inner = inner;
    }

    @Override
    public void save(Task task) {
        // Сначала выполняем основную операцию.
        inner.save(task);

        // Сообщение выводится после её успешного завершения.
        System.out.println(
                "Выполнено сохранение задачи: " + task.getTitle()
        );
    }

    @Override
    public void delete(Task task) {
        // Передаём удаление внутреннему репозиторию.
        inner.delete(task);

        // Интерфейс delete не сообщает, была ли задача найдена,
        // поэтому журналируем именно завершившийся вызов.
        System.out.println(
                "Выполнен вызов удаления задачи: " + task.getTitle()
        );
    }

    @Override
    public List<Task> findAll() {
        // Чтение не меняем: делегируем внутреннему объекту.
        return inner.findAll();
    }
}