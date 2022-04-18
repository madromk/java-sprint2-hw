package manager;

import tasks.BaseTask;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


public interface TaskManager {
    void getAllTask();

    void getAllEpic();

    void getAllSubTask();

    void removeAllTask();

    void removeAllEpic();

    void removeAllSubTask();
    Task getTaskOnId(int idTask);
    public Epic getEpicOnId(int idEpic);

    Task getTaskOnIdAndSaveInHistory(int idTask);

    Epic getEpicOnIdAndSaveInHistory(int idEpic);

    SubTask getSubTaskOnId(int idSubTask);

    int setTask(Task task, LocalDateTime date, Duration duration);

    void setTaskFromFile(int id, Task task);

    int setEpic(Epic epic);
    void setEpicFromFile(int id, Epic epic);

    int setSubTask(SubTask subTask, int idEpic, LocalDateTime dateTime, Duration duration);
    void setSubTaskFromFile(int subTaskId, SubTask subTask, int EpicId);

    void updateTask(int id, Task task);

    void updateEpic(int id, Epic epic);

    void updateSubTask(int idEpic, int idSubTask, SubTask subTask);

    void removeTask(int id);

    void removeEpic(int id);

    void removeSubTask(int idSubTask, int idEpic);

    void getSubTaskInEpic(int epicId);

    void updateTaskStatus(int id, Task task, Statuses status);

    void updateSubTaskStatus(int idSubTask, SubTask subTask, Statuses status);

    int setTaskId();

    int setEpicId();

    List history();

    HashMap<Integer, Task> allTasksHashMap();

    HashMap<Integer, Epic> allEpicsHashMap();

    HashMap<Integer, SubTask> allSubTasksHashMap();
    Set<BaseTask> getPrioritizedTasks();
    void updatePrioritizedTasks();
    void printCannotCreateTaskBecauseOfTime();
    boolean checkDate(LocalDateTime dateTimeStart);

}