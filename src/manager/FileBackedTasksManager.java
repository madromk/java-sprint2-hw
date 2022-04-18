package manager;

import tasks.BaseTask;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {

    private final String fileName;

    public FileBackedTasksManager(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public int setTask(Task task, LocalDateTime dateTime, Duration duration) {
        int id = super.setTask(task, dateTime,duration);
        save();
        return id;
    }

    @Override
    public int setEpic(Epic epic) {
        int epicId = super.setEpic(epic);
        save();
        return epicId;
    }

    @Override
    public int setSubTask(SubTask subtask, int idEpic, LocalDateTime dateTime, Duration duration) {
        int subTaskId = super.setSubTask(subtask, idEpic, dateTime, duration);
        save();
        return subTaskId;
    }

    public String getFileName() {
        return fileName;
    }

    public static void historyToString(FileBackedTasksManager fileBackedTasksManager) {
        List<BaseTask> allHistory = fileBackedTasksManager.history();
        StringBuilder historyString = new StringBuilder();
        for (BaseTask baseTask : allHistory) {
            int id = baseTask.getId();
            String str = String.valueOf(id);
            historyString.append(str).append(", ");
        }
        String historyStr = historyString.toString();
        try {
            FileWriter fileWrite = new FileWriter(fileBackedTasksManager.getFileName(), true);
            fileWrite.write("\n");
            fileWrite.write(historyStr);
            fileWrite.close();
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка записи в файл");
        }
    }

    public TaskManager loadFromFile(TaskManager inMemoryTaskManager, String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String task = String.valueOf(TypeOfTask.TASK);
            String epic = String.valueOf(TypeOfTask.EPIC);
            String subTask = String.valueOf(TypeOfTask.SUBTASK);
            String type = "type";
            while (br.ready()) {
                String line = br.readLine();
                //переменная isTask вернёт true если в строке будет присутствоваться тип задачи(Task, SubTask или Epic)
                boolean isTasks = line.contains(task) || line.contains(epic) || line.contains(subTask);
                boolean isType = line.contains(type);
                if (isTasks) {
                    addTaskFromString(line, inMemoryTaskManager);
                }
                if (!(isTasks) && !(line.isEmpty()) && !(isType)) {
                    List<Integer> listHistory = historyFromString(line);
                    saveHistoryFromFileToMemory(listHistory, inMemoryTaskManager);
                }
            }
        } catch (IOException e) {
            e.getMessage();
//            throw new ManagerSaveException("Ошибка чтения файла");
        }
        return inMemoryTaskManager;
    }

    public void save() {
        String fileName = getFileName();
        HashMap<Integer, Task> allTasks = allTasksHashMap();
        HashMap<Integer, Epic> allEpics = allEpicsHashMap();
        try (FileWriter fileWrite = new FileWriter(fileName)) {
            String str = "id,type,name,status,description,startTime,duration(days),endTime,epic";
            fileWrite.write(str + "\n");
            for (Task task : allTasks.values()) {
                String dataTask = task.toString();
                fileWrite.write(dataTask + "\n");
            }
            for (Epic epic : allEpics.values()) {
                String dataTask = epic.toString();
                fileWrite.write(dataTask + "\n");
                HashMap<Integer, SubTask> subtasks = epic.getAllSubTask();
                if (!(subtasks.isEmpty())) {
                    for (SubTask subTask : subtasks.values()) {
                        String dataSubTask = subTask.toString();
                        fileWrite.write(dataSubTask + "\n");
                    }
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка чтения файла");
        }
    }


    public void addTaskFromString(String value, TaskManager inMemoryTaskManager) {
        String[] split = value.split(", ");
        String typeTask = String.valueOf(TypeOfTask.TASK);
        String typeEpic = String.valueOf(TypeOfTask.EPIC);
        String typeSubTask = String.valueOf(TypeOfTask.SUBTASK);
        if (split[1].equals(typeTask)) {
            int id = Integer.parseInt(split[0]);
            String name = split[2];
            Statuses status = Statuses.valueOf(split[3]);
            String description = split[4];
            String dateStart = split[5];
            long durationDays = Long.parseLong(split[6]);
            Task task = new Task(name, description, status, TypeOfTask.TASK);
            task.setStartDate(LocalDateTime.parse(dateStart));
            task.setDuration(Duration.ofDays(durationDays));
            inMemoryTaskManager.setTaskFromFile(id, task);
        }
        if (split[1].equals(typeEpic)) {
            int id = Integer.parseInt(split[0]);
            String name = split[2];
            String description = split[4];
            Epic epic = new Epic(name, description, TypeOfTask.EPIC);
            inMemoryTaskManager.setEpicFromFile(id, epic);
        }
        if (split[1].equals(typeSubTask)) {
            int id = Integer.parseInt(split[0]);
            String name = split[2];
            Statuses status = Statuses.valueOf(split[3]);
            String description = split[4];
            String dateStart = split[5];
            long durationDays = Long.parseLong(split[6]);
            int epicId = Integer.parseInt(split[8]);
            SubTask subTask = new SubTask(name, description, status, TypeOfTask.SUBTASK);
            subTask.setStartDate(LocalDateTime.parse(dateStart));
            subTask.setDuration(Duration.ofDays(durationDays));
            inMemoryTaskManager.setSubTaskFromFile(id, subTask, epicId);
        }
    }

    public static String toString(List<BaseTask> history) {
        List<BaseTask> allHistory = history;
        StringBuilder historyStr = new StringBuilder();
        for (BaseTask baseTask : allHistory) {
            int id = baseTask.getId();
            String str = String.valueOf(id);
            historyStr.append(str).append(", ");
        }
        return historyStr.toString();
    }

    public static List<Integer> historyFromString(String value) {
        String[] split = value.split(", ");
        List<Integer> idFromHistory = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            int id = Integer.parseInt(split[i]);
            idFromHistory.add(id);
        }
        return idFromHistory;
    }

    public static void saveHistoryFromFileToMemory(List<Integer> history,
                                                   TaskManager inMemoryTaskManager) {
        HashMap<Integer, Task> tasks = inMemoryTaskManager.allTasksHashMap();
        HashMap<Integer, Epic> epics = inMemoryTaskManager.allEpicsHashMap();
        HashMap<Integer, SubTask> subTasks = inMemoryTaskManager.allSubTasksHashMap();
        for (Integer id : history) {
            if (tasks.containsKey(id)) {
                inMemoryTaskManager.getTaskOnIdAndSaveInHistory(id);
            }
            if (epics.containsKey(id)) {
                inMemoryTaskManager.getEpicOnIdAndSaveInHistory(id);
            }
            if (subTasks.containsKey(id)) {
                inMemoryTaskManager.getSubTaskOnId(id);
            }
        }
    }
}
