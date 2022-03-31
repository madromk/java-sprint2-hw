package tasks;

import manager.Statuses;
import manager.TypeOfTask;

public class SubTask extends BaseTask {

    private int belongEpicId;

    public SubTask(String name, String description, Statuses status) {
        super(name, description, status);
    }

    public SubTask(String name, String description, Statuses status, TypeOfTask type) {
        super(name, description, status, type);
    }

    public void setBelongEpicId(int belongEpicId) {
        this.belongEpicId = belongEpicId;
    }

    public int getBelongEpicId() {
        return belongEpicId;
    }

    @Override
    public String toString() {
        return super.toString() + getBelongEpicId();
    }
}

