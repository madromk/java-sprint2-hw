package tasks;

import manager.Statuses;
import manager.TypeOfTask;

public class Task extends BaseTask {

    public Task(String name, String description, Statuses status) {
        super(name, description, status);
    }

    public Task(String name, String description, Statuses status, TypeOfTask type) {
        super(name, description, status, type);
    }
}
