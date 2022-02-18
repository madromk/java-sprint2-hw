package tasks;

import manager.Statuses;

public class SubTask extends BaseTask {

    private int belongEpicId;

    public SubTask(String name, String description, Statuses status) {
        super(name, description, status);
    }

    public void setBelongEpicId(int belongEpicId) {
        this.belongEpicId = belongEpicId;
    }

    public int getBelongEpicId() {
        return belongEpicId;
    }
}

