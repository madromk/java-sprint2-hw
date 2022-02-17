package manager;

import tasks.BaseTask;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;


public interface TaskManager {
    public void getAllTask();

    public void getAllEpic();

    public void getAllSubTask();

    public void removeAllTask();

    public void removeAllEpic();

    public void removeAllSubTask();

    public Task getTaskOnId(int idTask);

    public Epic getEpicOnId(int idEpic);

    public SubTask getSubTaskById(int idSubTask, int idEpic);

    public void setTask(int taskId, Task task);

    public void setEpic(Epic epic, int epicId);

    public void setSubTask(SubTask subtask, int idSubTask, int idEpic);

    public void updateTask(int id, Task task);

    public void updateEpic(int id, Epic epic);

    public void updateSubTask(int idEpic, int idSubTask, SubTask subTask);

    public void removeTask(int id);

    public void removeEpic(int id);

    public void removeSubTask(int idSubTask, int idEpic);

    public void getSubTaskInEpic(int epicId);

    public void updateTaskStatus(Task task, Statuses status);

    public void updateSubTaskStatus(SubTask subTask, Statuses status);

    public int setTaskId();

    public int setEpicId();

    public void printHistory();

}