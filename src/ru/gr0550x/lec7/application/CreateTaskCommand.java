package ru.gr0550x.lec7.application;

import ru.gr0550x.lec7.domain.Task;

public class CreateTaskCommand implements TaskCommand{
    private final TaskService service;
    private final Task task;

    public CreateTaskCommand(TaskService service, Task task){
        this.service = service;
        this.task = task;
    }

    @Override
    public void execute(){
        service.save(task);
    }

    @Override
    public void undo() {
        service.delete(task);
    }


}
