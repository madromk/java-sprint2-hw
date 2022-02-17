package tasks;

import manager.Statuses;

public class BaseTask {
    private String name;
    private String description;
    private int id;
    Statuses status;

    public BaseTask(String name, String description, Statuses status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Statuses getStatus() {
        return status;
    }

    public void setId(int baseId) {
        this.id = baseId;
    }

    public void setStatus(Statuses status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Название: " + getName() +
                ". Описание: " + getDescription() +
                ". Статус: " + getStatus();
    }

    public int getId() {
        return id;
    }
}
