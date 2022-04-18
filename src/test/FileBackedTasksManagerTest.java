package test;

import manager.FileBackedTasksManager;
import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.Test;
import tasks.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileBackedTasksManagerTest extends TaskManagerTest{

    String fileName = "datafile.csv";

    FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(fileName);



    @Test
    void saveTaskAndHistoryInFile() {
        //Создаём две простые задачи и один эпик с подзадачей. Ожидание - создание файла с перечнем задач и истории
        int taskId1 = fileBackedTasksManager.setTask(taskOne, dateTimeStartTask1, durationTask1);
        int taskId2 = fileBackedTasksManager.setTask(taskTwo, dateTimeStartTask2, durationTask2);

        int epicId = fileBackedTasksManager.setEpic(epicOne);
        int subTaskId1 = fileBackedTasksManager.setSubTask(subtaskOne, epicId, dateTimeStartSub1, durationSub1);

        //Вызываем задачи 1 и 2 для истории
        fileBackedTasksManager.getTaskOnIdAndSaveInHistory(taskId1);
        fileBackedTasksManager.getTaskOnIdAndSaveInHistory(taskId2);
        //Преобразуем историю в строку и добавляем в файл
        FileBackedTasksManager.historyToString(fileBackedTasksManager);
    }
    @Test
    void loadFromFile() {
        FileBackedTasksManager fileBackedTasksManager2 = new FileBackedTasksManager(fileName);
        TaskManager inMemoryTaskManager = new Managers().getDefault();
        inMemoryTaskManager = fileBackedTasksManager2.loadFromFile(inMemoryTaskManager, fileName);


        //Проверяем, что задачи которые мы прочитали из файла, совпадают с теми, которые мы положили в файл
        Task taskFromFile = inMemoryTaskManager.getTaskOnId(1);
        assertEquals(taskOne, taskFromFile, "Задачи не совпадают");
        Task task2 = inMemoryTaskManager.getTaskOnId(2);
        assertEquals(taskTwo, task2, "Задачи не совпадают");

        //Выводим на печать историю
        System.out.println(inMemoryTaskManager.history());
    }

}