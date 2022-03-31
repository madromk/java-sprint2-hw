package tasks;

import manager.InMemoryTaskManager;
import manager.Statuses;
import manager.TypeOfTask;

import java.util.HashMap;

public class Epic extends BaseTask {

    private HashMap<Integer, SubTask> allSubTask = new HashMap<>();

    public Epic(String name, String description, TypeOfTask type) {
        super(name, description, null);
        this.type = type;
    }

    public Epic(String name, String description) {
        super(name, description, null);
    }

    @Override
    public Statuses getStatus() {
        Statuses status = null;
        for (SubTask subTask : allSubTask.values()) {
            Statuses SubTaskStatus = subTask.getStatus();
            if ((status == null || status.equals(InMemoryTaskManager.STATUS_NEW))
                    && (SubTaskStatus.equals(InMemoryTaskManager.STATUS_NEW))) {
                status = InMemoryTaskManager.STATUS_NEW;
            } else if ((status == null || status.equals(InMemoryTaskManager.STATUS_DONE))
                    && (SubTaskStatus.equals(InMemoryTaskManager.STATUS_DONE))) {
                status = InMemoryTaskManager.STATUS_DONE;
            } else {
                status = InMemoryTaskManager.STATUS_IN_PROGRESS;
            }
        }
        return status;
    }

    @Override
    public void setStatus(Statuses status) {
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public void setSubTask(int id, SubTask subTask) {
        allSubTask.put(id, subTask);
    }

    public HashMap<Integer, SubTask> getAllSubTask() {
        return allSubTask;
    }

    public void clearAllSubTask() {
        allSubTask.clear();
    }

    public void removeSubTask(int idSubTask) {
        allSubTask.remove(idSubTask);
    }

    public SubTask getSubTaskOnId(int id) {
        return allSubTask.get(id);
    }

}

