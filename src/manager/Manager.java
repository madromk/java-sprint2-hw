import java.util.HashMap;

public class Manager {

    String statusNew = "NEW";
    String statusInProgress = "IN_PROGRESS";
    String statusDone = "DONE";

    HashMap<Integer, Task> allTask = new HashMap<>();
    HashMap<Integer, Epic> allEpic = new HashMap<>();
    HashMap<Integer, SubTask> allSubTask = new HashMap<>();

    int taskId = 0;
    int epicId = 0;
    int idSubTask = 0;

    public void getAllTask() {
        if (!(allTask.isEmpty())) {
            for (Task task : allTask.values()) {
                System.out.println("Название: " + task.getName()
                        + ". Описание: " + task.getDescription()
                        + ". Статус: " + task.getStatus());
            }
        } else {
            System.out.println("Список задач пуст");
        }
    }

    public void getAllEpic() {
        if (!(allEpic.isEmpty())) {
            for (Epic epic : allEpic.values()) {
                System.out.println("Название: " + epic.getName()
                        + ". Описание: " + epic.getDescription()
                        + ". Статус: " + epic.getStatus());
            }
        } else {
            System.out.println("Список задач пуст");
        }
    }

    public void getAllSubTask() {
        if (!(allSubTask.isEmpty())) {
            for (SubTask subTask : allSubTask.values()) {
                System.out.println("Название: " + subTask.getName()
                        + ". Описание: " + subTask.getDescription()
                        + ". Статус: " + subTask.getStatus());
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
        if (!(allSubTask.isEmpty())) {
            allSubTask.clear();
        }
    }

    public Task getTaskOnId (int id) {
        Task task = allTask.get(id);
        return task;
    }

    public Epic getEpicOnId (int id) {
        Epic epic = allEpic.get(id);
        return epic;
    }

    public SubTask getSubTaskOnId(int id) {
        SubTask subTask = allSubTask.get(id);
        return subTask;
    }

    public void createTask(Task task, int taskId) {
        task.setId(taskId); //Сохранили id в объекте
        allTask.put(taskId, task);
    }

    public void createEpic(Epic epic, int epicId) {
        epic.setId(epicId);
        allEpic.put(epicId, epic);
    }

    public void createSubTask(SubTask subtask, int idSubTask, int idEpic) {
        subtask.setId(idSubTask);
        allSubTask.put(idSubTask, subtask);
        Epic epic = allEpic.get(idEpic);
        epic.subTaskNumbers.add(idSubTask); //Добавляем id подзадачи в список конкретного эпика
    }

    public void updateTask(int id, Task task) {
        allTask.put(id, task);
    }

    public void updateEpic(int id, Epic epic) {
        allEpic.put(id, epic);
    }

    public void updateSubTask(int id, SubTask subTask) {
        allSubTask.put(id, subTask);
    }

    public void removeTask (int id) {
        allTask.remove(id);
    }

    public void removeEpic(int id) {
        allEpic.remove(id);
    }

    public void removeSubTask(int id) {
        allSubTask.remove(id);
    }

    public void getSubTaskInEpic(int epicId) {
        Epic epic = allEpic.get(epicId);
        if (!(epic.subTaskNumbers.isEmpty())) {
            System.out.println("Подзадачи:");
            for (Integer id : epic.subTaskNumbers) {
                SubTask subTask = allSubTask.get(id);
                System.out.println("Название: " + subTask.getName()
                        + ". Описание: " + subTask.getDescription()
                        + ". Статус: " + subTask.getStatus());
            }
        } else {
            System.out.println("Список подзадач пуст");
        }
    }

    public void updateTaskStatus(int id, String status) {
        Task task = allTask.get(id);
        task.setStatus(status);
        allTask.put(id, task);
    }

    public void updateEpicStatus(int idEpic) {
        Epic epic = allEpic.get(idEpic);
        String status = "";
        for (Integer id : epic.subTaskNumbers) {
            String SubTaskStatus = allSubTask.get(id).getStatus();
            if ((status.isEmpty() || status.equals(statusNew)) && (SubTaskStatus.equals(statusNew))) {
                status = statusNew;
            } else if ((status.isEmpty() || status.equals(statusDone)) && (SubTaskStatus.equals(statusDone))) {
                status = statusDone;
            } else {
                status = statusInProgress;
            }
        }
        epic.setStatus(status);
    }

    public void updateSubTaskStatus(int id, String status) {
        SubTask subTask = allSubTask.get(id);
        subTask.setStatus(status);
        allSubTask.put(id, subTask);
    }

    public int setTaskId() {
        return this.taskId++;
    }

    public int setEpicId() {
        return this.epicId++;
    }

    public int setIdSubTask() {
        return this.idSubTask++;
    }
}

