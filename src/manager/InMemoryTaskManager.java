package manager;

import tasks.BaseTask;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    public static Statuses STATUS_NEW = Statuses.NEW;
    public static Statuses STATUS_IN_PROGRESS = Statuses.IN_PROGRESS;
    public static Statuses STATUS_DONE = Statuses.DONE;

    private HashMap<Integer, Task> allTask = new HashMap<>();
    private HashMap<Integer, Epic> allEpic = new HashMap<>();

    Comparator<BaseTask> baseTaskComparator = new Comparator<BaseTask>() {
        @Override
        public int compare(BaseTask baseTask1, BaseTask baseTask2) {
            if(baseTask2.getStartDate() == null) {
                return 1;
            }
            if(baseTask1.getStartDate() == null) {
                return 1;
            }
            if(baseTask1.getStartDate().isBefore(baseTask2.getStartDate())) {
                return -1;
            } else if(baseTask1.getStartDate().isAfter(baseTask2.getStartDate())) {
                return 1;
            } else {
                return -1;
            }
        }
    };


    private Set<BaseTask> prioritizedTasks = new TreeSet<>(baseTaskComparator);
    private int id = 1;

    protected HistoryManager inMemoryHistoryManager = new Managers().getDefaultHistory();

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
    public Task getTaskOnIdAndSaveInHistory(int idTask) {
        inMemoryHistoryManager.addInHistory(allTask.get(idTask));
        return allTask.get(idTask);
    }

    @Override
    public Epic getEpicOnIdAndSaveInHistory(int idEpic) {
        inMemoryHistoryManager.addInHistory(allEpic.get(idEpic));
        return allEpic.get(idEpic);
    }
    @Override
    public Task getTaskOnId(int idTask) {
        return allTask.get(idTask);
    }

    @Override
    public Epic getEpicOnId(int idEpic) {
        return allEpic.get(idEpic);
    }

    @Override
    public SubTask getSubTaskOnId(int idSubTask) {
        SubTask subTask = null;
        for(Epic epic : allEpic.values()) {
            HashMap<Integer, SubTask> subTasks = epic.getAllSubTask();
            if(subTasks.containsKey(idSubTask)) {
                inMemoryHistoryManager.addInHistory(subTasks.get(idSubTask));
                subTask = subTasks.get(idSubTask);
            }
        }
        return subTask;
    }

    @Override
    public int setTask(Task task, LocalDateTime dateTime, Duration duration) {
        boolean isCheckDate = checkDate(dateTime);
        int id = -1;
        if(isCheckDate) {
            task.setStartDate(dateTime);
            task.setDuration(duration);
            id = setTaskId();
            task.setId(id);
            allTask.put(id, task);
            updatePrioritizedTasks();
        } else {
            printCannotCreateTaskBecauseOfTime();
        }
        return id;
    }

    @Override
    public void setTaskFromFile(int id, Task task) {
        task.setId(id);
        allTask.put(id, task);
    }

    @Override
    public int setEpic(Epic epic) {
        int epicId = setEpicId();
        epic.setId(epicId);
        allEpic.put(epicId, epic);
        updatePrioritizedTasks();
        return epicId;
    }

    @Override
    public void setEpicFromFile(int id, Epic epic) {
        epic.setId(id);
        allEpic.put(id, epic);
    }

    @Override
    public int setSubTask(SubTask subTask, int idEpic, LocalDateTime dateTime, Duration duration) {
        boolean isCheckDate = checkDate(dateTime);
        int id = -1;
        if(isCheckDate) {
            subTask.setStartDate(dateTime);
            subTask.setDuration(duration);
            id = setTaskId();
            subTask.setId(id);
            subTask.setBelongEpicId(idEpic);
            Epic epic = getEpicOnId(idEpic);
            epic.getAllSubTask().put(id, subTask);
            allEpic.put(idEpic, epic);
            updatePrioritizedTasks();
        } else {
            printCannotCreateTaskBecauseOfTime();
        }
        return id;
    }

    @Override
    public void setSubTaskFromFile(int subTaskId, SubTask subTask, int EpicId) {
        subTask.setId(subTaskId);
        Epic epic = allEpic.get(EpicId);
        epic.setSubTask(subTaskId, subTask);
        allEpic.put(EpicId, epic);
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
    public void removeTask(int idTask) {
        inMemoryHistoryManager.remove(idTask);
        allTask.remove(idTask);
    }

    @Override
    public void removeEpic(int idEpic) {
        inMemoryHistoryManager.remove(idEpic);
        allEpic.remove(idEpic);
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
    public void updateTaskStatus(int id, Task task, Statuses status) {
        task.setStatus(status);
        allTask.put(id, task);
    }

    @Override
    public void updateSubTaskStatus(int idSubTask, SubTask subTask, Statuses status) {
        subTask.setStatus(status);
        for(Epic epic : allEpic.values()) {
            if(epic.getAllSubTask().containsKey(idSubTask)) {
                epic.setSubTask(idSubTask, subTask);
            }
        }
    }

    @Override
    public int setTaskId() {
        return this.id++;
    }

    @Override
    public int setEpicId() {
        return this.id++;
    }

    @Override
    public List history() {
        return inMemoryHistoryManager.getHistory();
    }


    @Override
    public HashMap<Integer, Task> allTasksHashMap() {
        return allTask;
    }

    @Override
    public HashMap<Integer, Epic> allEpicsHashMap() {
        return allEpic;
    }

    @Override
    public HashMap<Integer, SubTask> allSubTasksHashMap() {
        HashMap<Integer, SubTask> allSubTask = new HashMap<>();
        for(Epic epic : allEpic.values()) {
            if(!(epic.getAllSubTask().isEmpty())) {
                allSubTask.putAll(epic.getAllSubTask());
            }
        }
        return allSubTask;
    }
    @Override
    public Set<BaseTask> getPrioritizedTasks() {
        return prioritizedTasks;
    }
    @Override
    public boolean checkDate(LocalDateTime dateTimeStart) {
        List<BaseTask> sortedTasks = new ArrayList<>(getPrioritizedTasks());
        int index = 1;
        int lastIndexSortedTasks = sortedTasks.size() - index;
        if (sortedTasks.isEmpty()) {
            return true;
        }
        BaseTask lastTask = sortedTasks.get(lastIndexSortedTasks);
        while(true) {
            if (lastTask.getEndTime() == null) {
                if(sortedTasks.size() == 1) {
                    return true;
                }
                index++;
                lastIndexSortedTasks = sortedTasks.size() - index;
                lastTask = sortedTasks.get(lastIndexSortedTasks);
            } else {
                break;
            }
        }
        if(lastTask.getEndTime().isBefore(dateTimeStart)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void printCannotCreateTaskBecauseOfTime() {
        System.out.println("Задача пересекается с другой задачей. Измените время.");
    }

    @Override
    public void updatePrioritizedTasks() {
        prioritizedTasks.clear();
        if(!allTask.isEmpty()) {
            for(Task task : allTask.values()) {
                prioritizedTasks.add((BaseTask)task);
            }
        }
        if(!allEpic.isEmpty()) {
            for(Epic epic : allEpic.values()) {
                prioritizedTasks.add((BaseTask)epic);
                if(!epic.getAllSubTask().isEmpty()){
                    for(SubTask subTask : epic.getAllSubTask().values()) {
                        prioritizedTasks.add((BaseTask)subTask);
                    }
                }
            }
        }

    }

    public HistoryManager getInMemoryHistoryManager() {
        return inMemoryHistoryManager;
    }

    public void setInMemoryHistoryManager(HistoryManager inMemoryHistoryManager) {
        this.inMemoryHistoryManager = inMemoryHistoryManager;
    }
}


