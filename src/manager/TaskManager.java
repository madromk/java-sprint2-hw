package manager;

import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.util.List;


public interface TaskManager {
    void getAllTask();

    void getAllEpic();

    void getAllSubTask();

    void removeAllTask();

    void removeAllEpic();

    void removeAllSubTask();

    Task getTaskOnId(int idTask);

    Epic getEpicOnId(int idEpic);

    SubTask getSubTaskById(int idSubTask, int idEpic);

    void setTask(int taskId, Task task);

    void setEpic(Epic epic, int epicId);

    void setSubTask(SubTask subtask, int idSubTask, int idEpic);

    void updateTask(int id, Task task);

    void updateEpic(int id, Epic epic);

    void updateSubTask(int idEpic, int idSubTask, SubTask subTask);

    void removeTask(int id);

    void removeEpic(int id);

    void removeSubTask(int idSubTask, int idEpic);

    void getSubTaskInEpic(int epicId);

    void updateTaskStatus(Task task, Statuses status);

    void updateSubTaskStatus(SubTask subTask, Statuses status);

    int setTaskId();

    int setEpicId();

    List History();

}