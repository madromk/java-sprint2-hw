package manager;

import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.util.List;

import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {

    public static Statuses STATUS_NEW = Statuses.NEW;
    public static Statuses STATUS_IN_PROGRESS = Statuses.IN_PROGRESS;
    public static Statuses STATUS_DONE = Statuses.DONE;

    private HashMap<Integer, Task> allTask = new HashMap<>();
    private HashMap<Integer, Epic> allEpic = new HashMap<>();


    private int taskId = 0;
    private int epicId = 0;

    private HistoryManager inMemoryHistoryManager = new Managers().getDefaultHistory();

    @Override
    public void getAllTask() {
        if (!(allTask.isEmpty())) {
            for (Task task : allTask.values()) {
                System.out.println(task);
            }
        } else {
            System.out.println("Список задач пуст");
        }
    }

    @Override
    public void getAllEpic() {
        if (!(allEpic.isEmpty())) {
            for (Epic epic : allEpic.values()) {
                System.out.println(epic);
            }
        } else {
            System.out.println("Список задач пуст");
        }
    }

    @Override
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

    @Override
    public void removeAllTask() {
        if (!(allTask.isEmpty())) {
            allTask.clear();
        }
    }

    @Override
    public void removeAllEpic() {
        if (!(allEpic.isEmpty())) {
            allEpic.clear();
        }
    }

    @Override
    public void removeAllSubTask() {
        if (!(allEpic.isEmpty())) {
            for (Epic epic : allEpic.values()) {
                epic.clearAllSubTask();
            }
        }
    }

    @Override
    public Task getTaskOnId(int idTask) {
        inMemoryHistoryManager.addInHistory(allTask.get(idTask));
        return allTask.get(idTask);
    }

    @Override
    public Epic getEpicOnId(int idEpic) {
        inMemoryHistoryManager.addInHistory(allEpic.get(idEpic));
        return allEpic.get(idEpic);
    }

    @Override
    public SubTask getSubTaskById(int idSubTask, int idEpic) {
        inMemoryHistoryManager.addInHistory(allEpic.get(idEpic).getSubTaskOnId(idSubTask));
        return allEpic.get(idEpic).getSubTaskOnId(idSubTask);
    }

    @Override
    public void setTask(int taskId, Task task) {
        task.setId(taskId); //Сохранили id в объекте
        allTask.put(taskId, task);
    }

    @Override
    public void setEpic(Epic epic, int epicId) {
        epic.setId(epicId);
        allEpic.put(epicId, epic);
    }

    @Override
    public void setSubTask(SubTask subtask, int idSubTask, int idEpic) {
        subtask.setId(idSubTask);
        subtask.setBelongEpicId(idEpic); //Сохраняем id эпика в сабтаске
        allEpic.get(idEpic).setSubTask(idSubTask, subtask);
    }

    @Override
    public void updateTask(int id, Task task) {
        allTask.put(id, task);
    }

    @Override
    public void updateEpic(int id, Epic epic) {
        allEpic.put(id, epic);
    }

    @Override
    public void updateSubTask(int idEpic, int idSubTask, SubTask subTask) {
        allEpic.get(idEpic).setSubTask(idSubTask, subTask);
    }

    @Override
    public void removeTask(int id) {
        allTask.remove(id);
    }

    @Override
    public void removeEpic(int id) {
        allEpic.remove(id);
    }

    @Override
    public void removeSubTask(int idSubTask, int idEpic) {
        allEpic.get(idEpic).removeSubTask(idSubTask);
    }

    @Override
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

    @Override
    public void updateTaskStatus(Task task, Statuses status) {
        task.setStatus(status);
    }

    @Override
    public void updateSubTaskStatus(SubTask subTask, Statuses status) {
        subTask.setStatus(status);
    }

    @Override
    public int setTaskId() {
        return this.taskId++;
    }

    @Override
    public int setEpicId() {
        return this.epicId++;
    }

    @Override
    public List History() {
        return inMemoryHistoryManager.getHistory();
    }


    public HistoryManager getInMemoryHistoryManager() {
        return inMemoryHistoryManager;
    }

    public void setInMemoryHistoryManager(HistoryManager inMemoryHistoryManager) {
        this.inMemoryHistoryManager = inMemoryHistoryManager;
    }
}


