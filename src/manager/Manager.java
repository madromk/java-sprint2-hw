package manager;

import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.util.HashMap;

public class Manager {

    public static final String STATUS_NEW = "NEW";
    public static final String STATUS_IN_PROGRESS = "IN_PROGRESS";
    public static final String STATUS_DONE = "DONE";

    private HashMap<Integer, Task> allTask = new HashMap<>();
    private HashMap<Integer, Epic> allEpic = new HashMap<>();


    private int taskId = 0;
    private int epicId = 0;


    public void getAllTask() {
        if (!(allTask.isEmpty())) {
            for (Task task : allTask.values()) {
                System.out.println(task);
            }
        } else {
            System.out.println("Список задач пуст");
        }
    }

    public void getAllEpic() {
        if (!(allEpic.isEmpty())) {
            for (Epic epic : allEpic.values()) {
                System.out.println(epic);
            }
        } else {
            System.out.println("Список задач пуст");
        }
    }

    public void getAllSubTask() {
        if (!(allEpic.isEmpty())) {
            HashMap<Integer, SubTask> newHashMap = new HashMap<>();
            for (Epic epic : allEpic.values()) {
                for (int key : epic.getAllSubTask().keySet()) {
                    newHashMap.put(key, epic.getAllSubTask().get(key));
                }
            }
            for (SubTask subTask : newHashMap.values()) {
                System.out.println(subTask);
            }
        } else {
            System.out.println("Список задач пуст");
        }
    }

    public void removeAllTask() {
        if (!(allTask.isEmpty())) {
            allTask.clear();
        }
    }

    public void removeAllEpic() {
        if (!(allEpic.isEmpty())) {
            allEpic.clear();
        }
    }

    public void removeAllSubTask() {
        if (!(allEpic.isEmpty())) {
            for (Epic epic : allEpic.values()) {
                epic.clearAllSubTask();
            }
        }
    }

    public Task getTaskOnId(int idTask) {
        return allTask.get(idTask);
    }

    public Epic getEpicOnId(int idEpic) {
        return allEpic.get(idEpic);
    }

    public SubTask getSubTaskById(int idSubTask, int idEpic) {
        return allEpic.get(idEpic).getSubTaskOnId(idSubTask);
    }

    public void setTask(int taskId, Task task) {
        task.setId(taskId); //Сохранили id в объекте
        allTask.put(taskId, task);
    }

    public void setEpic(Epic epic, int epicId) {
        epic.setId(epicId);
        allEpic.put(epicId, epic);
    }

    public void setSubTask(SubTask subtask, int idSubTask, int idEpic) {
        subtask.setId(idSubTask);
        subtask.setBelongEpicId(idEpic); //Сохраняем id эпика в сабтаске
        allEpic.get(idEpic).setSubTask(idSubTask, subtask);
    }

    public void updateTask(int id, Task task) {
        allTask.put(id, task);
    }

    public void updateEpic(int id, Epic epic) {
        allEpic.put(id, epic);
    }

    public void updateSubTask(int idEpic, int idSubTask, SubTask subTask) {
        allEpic.get(idEpic).setSubTask(idSubTask, subTask);
    }

    public void removeTask(int id) {
        allTask.remove(id);
    }

    public void removeEpic(int id) {
        allEpic.remove(id);
    }

    public void removeSubTask(int idSubTask, int idEpic) {
        allEpic.get(idEpic).removeSubTask(idSubTask);
    }

    public void getSubTaskInEpic(int epicId) {
        if (!(allEpic.isEmpty())) {
            HashMap<Integer, SubTask> newHashMap = new HashMap<>();
            Epic epic = allEpic.get(epicId);
            System.out.println("Подзадачи:");
            for (int key : epic.getAllSubTask().keySet()) {
                newHashMap.put(key, epic.getAllSubTask().get(key));
            }
            for (SubTask subTask : newHashMap.values()) {
                System.out.println(subTask);
            }
        } else {
            System.out.println("Список подзадач пуст");
        }
    }

    public void updateTaskStatus(Task task, String status) {
        task.setStatus(status);
    }

    public void updateSubTaskStatus(SubTask subTask, String status) {
        subTask.setStatus(status);
    }

    public int setTaskId() {
        return this.taskId++;
    }

    public int setEpicId() {
        return this.epicId++;
    }
}


