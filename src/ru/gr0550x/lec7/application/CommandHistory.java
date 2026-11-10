package ru.gr0550x.lec7.application;

import java.util.ArrayDeque;
import java.util.Deque;

public class CommandHistory {
    private final Deque<TaskCommand> history = new ArrayDeque<>();

    public void execute(TaskCommand command){
        command.execute();
        history.push(command);
    }

    public boolean undo(){
        if (history.isEmpty()) return false;
        history.pop().undo();
        return true;
    }
}
