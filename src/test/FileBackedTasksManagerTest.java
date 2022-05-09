package test;

import manager.FileBackedTasksManager;
import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.Test;
import tasks.Task;

import java.io.IOException;

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
        TaskManager inMemoryTaskManager = new Managers().getTasksManager(fileName);
        inMemoryTaskManager = fileBackedTasksManager2.load(inMemoryTaskManager, fileName);


        //Проверяем, что задачи которые мы прочитали из файла, совпадают с теми, которые мы положили в файл
        taskOne.setId(1); //Здесь заданим айди задачи, поскольку айди присваивается в момент сохранения задачи в табл.
        Task task1 = inMemoryTaskManager.getTaskOnId(1);
        assertEquals(taskOne, task1, "Задачи не совпадают task1");
        taskTwo.setId(2); //По аналогии с 42 строкой
        Task task2 = inMemoryTaskManager.getTaskOnId(2);
        assertEquals(taskTwo, task2, "Задачи не совпадают task2");

        //Выводим на печать историю
        System.out.println(inMemoryTaskManager.history());
    }

}