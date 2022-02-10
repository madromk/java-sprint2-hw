package tasks;

import tasks.BaseTask;

public class SubTask extends BaseTask {

    private int belongEpicId;

    public SubTask(String name, String description, String status) {
        super(name, description, status);
    }

    public void setBelongEpicId(int belongEpicId) {
        this.belongEpicId = belongEpicId;
    }

    public int getBelongEpicId() {
        return belongEpicId;
    }
}

