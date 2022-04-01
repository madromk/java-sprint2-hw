package manager;

import tasks.BaseTask;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {

    private static String fileName;

    public FileBackedTasksManager(String fileName) {
        this.fileName = fileName;
    }

    public static void main(String[] args) {

        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager("datafile.csv");

        //Создаем простую задачу
        Task taskOne = new Task("Покупка", "Купить подарок на ДР",
                InMemoryTaskManager.STATUS_NEW, TypeOfTask.TASK);
        int taskIdOne = fileBackedTasksManager.setTaskId();
        fileBackedTasksManager.setTask(taskIdOne, taskOne);

        //Создаем эпик с одной подзадачей
        Epic epicOne = new Epic("Компьютер", "Собрать игровой ПК1", TypeOfTask.EPIC);
        SubTask subtaskOne = new SubTask("Сборка", "Собрать ПК и протестировать",
                InMemoryTaskManager.STATUS_NEW, TypeOfTask.SUBTASK);
        int epicIdOne = fileBackedTasksManager.setEpicId();
        int idSubTaskOne = fileBackedTasksManager.setTaskId();
        fileBackedTasksManager.setEpic(epicOne, epicIdOne);
        fileBackedTasksManager.setSubTask(subtaskOne, idSubTaskOne, epicIdOne);

        //Вызываем задачи для истории просмотров
        fileBackedTasksManager.getTaskOnId(taskIdOne);
        fileBackedTasksManager.getEpicOnId(epicIdOne);

        //Преобразуем историю в строку и добавляем в файл
        historyToString(fileBackedTasksManager);

        //Читаем данные из файла и добавляем в память
        TaskManager inMemoryTaskManager = new Managers().getDefault();
        loadFromFile(fileBackedTasksManager, inMemoryTaskManager);

        //Получаем задачу по id из памяти
        System.out.println("Получаем задачу по id");
        Task task = inMemoryTaskManager.getTaskOnId(1);
        System.out.println(task);

        //Вывод на экран истории просмотров
        System.out.println();
        System.out.println("История");
        List<BaseTask> history = inMemoryTaskManager.history();
        System.out.println(history);
    }

    @Override
    public void setTask(int taskId, Task task) {
        super.setTask(taskId, task);
        save();
    }

    @Override
    public void setEpic(Epic epic, int epicId) {
        super.setEpic(epic, epicId);
        save();
    }

    @Override
    public void setSubTask(SubTask subtask, int idSubTask, int idEpic) {
        super.setSubTask(subtask, idSubTask, idEpic);
        save();
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
            FileWriter fileWrite = new FileWriter(getFileName(), true);
            fileWrite.write("\n");
            fileWrite.write(historyStr);
            fileWrite.close();
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка записи в файл");
        }
    }

    public static void loadFromFile(FileBackedTasksManager fileBackedTasksManager2, TaskManager inMemoryTaskManager) {
        try (BufferedReader br = new BufferedReader(new FileReader(getFileName()))) {
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
                    fileBackedTasksManager2.addTaskFromString(line, inMemoryTaskManager);
                }
                if (!(isTasks) && !(line.isEmpty()) && !(isType)) {
                    List<Integer> listHistory = historyFromString(line);
                    saveHistoryFromFileToMemory(listHistory, inMemoryTaskManager);
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка чтения файла");
        }
    }

    public void save() {
        String fileName = getFileName();
        HashMap<Integer, Task> allTasks = allTasksHashMap();
        HashMap<Integer, Epic> allEpics = allEpicsHashMap();
        try (FileWriter fileWrite = new FileWriter(fileName)) {
            String str = "id,type,name,status,description,epic";
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

    public static String getFileName() {
        return fileName;
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
            Task task = new Task(name, description, status, TypeOfTask.TASK);
            inMemoryTaskManager.setTask(id, task);
        }
        if (split[1].equals(typeEpic)) {
            int id = Integer.parseInt(split[0]);
            String name = split[2];
            String description = split[4];
            Epic epic = new Epic(name, description, TypeOfTask.EPIC);
            inMemoryTaskManager.setEpic(epic, id);
        }
        if (split[1].equals(typeSubTask)) {
            int id = Integer.parseInt(split[0]);
            String name = split[2];
            Statuses status = Statuses.valueOf(split[3]);
            String description = split[4];
            int epicId = Integer.parseInt(split[5]);
            SubTask subTask = new SubTask(name, description, status, TypeOfTask.SUBTASK);
            inMemoryTaskManager.setSubTask(subTask, id, epicId);
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
                inMemoryTaskManager.getTaskOnId(id);
            }
            if (epics.containsKey(id)) {
                inMemoryTaskManager.getEpicOnId(id);
            }
            if (subTasks.containsKey(id)) {
                inMemoryTaskManager.getSubTaskOnId(id);
            }
        }
    }
}
