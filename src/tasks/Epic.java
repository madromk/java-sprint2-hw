package tasks;
import manager.Manager;

import java.util.HashMap;

public class Epic extends BaseTask {

    public Epic(String name, String description) {
        super(name, description, null);
    }

    private int idSubTask = 0;

    private HashMap<Integer, SubTask> allSubTask = new HashMap<>();


    @Override
    public String getStatus() {
        String status = "";
        for (SubTask subTask : allSubTask.values()) {
            String SubTaskStatus = subTask.getStatus();
            if ((status.isEmpty() || status.equals(Manager.statusNew)) && (SubTaskStatus.equals(Manager.statusNew))) {
                status = Manager.statusNew;
            } else if ((status.isEmpty() || status.equals(Manager.statusDone))
                    && (SubTaskStatus.equals(Manager.statusDone))) {
                status = Manager.statusDone;
            } else {
                status = Manager.statusInProgress;
            }
        }
        return status;
    }

    @Override
    public void setStatus(String status) {
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

    public int setIdSubTask() {
        return this.idSubTask++;
    }

    public SubTask getSubTaskOnId(int id) {
        return allSubTask.get(id);
    }

}

