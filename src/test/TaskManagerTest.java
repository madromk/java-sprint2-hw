package test;

import manager.*;
import org.junit.jupiter.api.Test;
import tasks.BaseTask;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class TaskManagerTest<T extends TaskManager>  {

    Task taskOne = new Task("Покупка", "Купить подарок на ДР",
            InMemoryTaskManager.STATUS_NEW, TypeOfTask.TASK);
    LocalDateTime dateTimeStartTask1 = LocalDateTime.of(2022, 3, 17, 00, 00);
    Duration durationTask1 = Duration.ofDays(2);
    Task taskTwo = new Task("Поездка", "Сьездить к родителям",
            InMemoryTaskManager.STATUS_NEW, TypeOfTask.TASK);
    LocalDateTime dateTimeStartTask2 = LocalDateTime.of(2022, 4, 8, 00, 00);
    Duration durationTask2 = Duration.ofDays(4);
    Epic epicOne = new Epic("Домашние дела", "Ремонт, уборка", TypeOfTask.EPIC);
    Epic epicTwo = new Epic("Ремонт", "Ремонт в ванной", TypeOfTask.EPIC);
    SubTask subtaskOne = new SubTask("Сборка", "Собрать ПК и протестировать",
            InMemoryTaskManager.STATUS_NEW, TypeOfTask.SUBTASK);
    LocalDateTime dateTimeStartSub1 = LocalDateTime.of(2022, 7, 7, 00, 00);
    Duration durationSub1 = Duration.ofDays(4);

    TaskManager inMemoryTaskManager = new Managers().getDefault();


    @Test
    void addNewTask() {

        int taskId = inMemoryTaskManager.setTask(taskOne, dateTimeStartTask1, durationTask1);
        Task savedTask = inMemoryTaskManager.getTaskOnIdAndSaveInHistory(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(taskOne, savedTask, "Задачи не совпадают.");
    }

    @Test
    void addNewEpicWithoutSubTask() {
        int epicId = inMemoryTaskManager.setEpic(epicOne);
        Epic savedEpic = inMemoryTaskManager.getEpicOnIdAndSaveInHistory(epicId);
        assertNotNull(savedEpic, "Задача не найдена.");
        assertEquals(epicOne, savedEpic, "Задачи не совпадают.");
    }
    @Test
    void addNewEpicWithSubTask() {

        int epicId = inMemoryTaskManager.setEpic(epicOne);
        int subTaskId1 = inMemoryTaskManager.setSubTask(subtaskOne, epicId, dateTimeStartSub1, durationSub1);

        Epic savedEpic = inMemoryTaskManager.getEpicOnIdAndSaveInHistory(epicId);
        assertNotNull(savedEpic, "Задача не найдена.");
        assertEquals(epicOne, savedEpic, "Задачи не совпадают.");

        SubTask savedSubTask = savedEpic.getSubTaskOnId(subTaskId1);
        assertNotNull(savedSubTask, "Задача не найдена.");
        assertEquals(subtaskOne, savedSubTask, "Задачи не совпадают.");

    }

    @Test
    void removeTaskAndEpic() {
        int taskId = inMemoryTaskManager.setTask(taskOne, dateTimeStartTask1, durationTask1);

        assertFalse(inMemoryTaskManager.allTasksHashMap().isEmpty());
        inMemoryTaskManager.removeTask(taskId);
        assertTrue(inMemoryTaskManager.allTasksHashMap().isEmpty());

        int epicId = inMemoryTaskManager.setEpic(epicOne);
        assertFalse(inMemoryTaskManager.allEpicsHashMap().isEmpty());
        inMemoryTaskManager.removeEpic(epicId);
        assertTrue(inMemoryTaskManager.allEpicsHashMap().isEmpty());

    }

    @Test
    void removeAllTasksAndEpics() {
        int taskId = inMemoryTaskManager.setTask(taskOne, dateTimeStartTask1, durationTask1);
        int taskId2 = inMemoryTaskManager.setTask(taskTwo, dateTimeStartTask2, durationTask2);
        //Проверяем, что размер хештаблицы равен 2 (по кол-ву добавленныз задач)
        assertEquals(2, inMemoryTaskManager.allTasksHashMap().size());
        inMemoryTaskManager.removeAllTask();
        //Проверяем, что хештаблица пустая
        assertTrue(inMemoryTaskManager.allTasksHashMap().isEmpty());

        int epicId = inMemoryTaskManager.setEpic(epicOne);
        int epicId2 = inMemoryTaskManager.setEpic(epicTwo);
        assertEquals(2, inMemoryTaskManager.allEpicsHashMap().size());
        inMemoryTaskManager.removeAllEpic();
        assertTrue(inMemoryTaskManager.allEpicsHashMap().isEmpty());

    }

    @Test
    void updateStatusTask() {
        Statuses DONE = Statuses.DONE;
        int taskId = inMemoryTaskManager.setTask(taskOne, dateTimeStartTask1, durationTask1);
        inMemoryTaskManager.updateTaskStatus(taskId, taskOne, DONE);
        Task task = inMemoryTaskManager.getTaskOnIdAndSaveInHistory(taskId);
        assertEquals(DONE, task.getStatus(), "Задачи не совпадают.");
    }

    @Test
    void checkPrioritizedTasks() {

        int taskId1 = inMemoryTaskManager.setTask(taskOne, dateTimeStartTask1, durationTask1); //Дата старта 22.3.17
        int epicId = inMemoryTaskManager.setEpic(epicOne); //без даты
        int taskId2 = inMemoryTaskManager.setTask(taskTwo, dateTimeStartTask2, durationTask2); // 22.4.8

        //Выгружаем приоритетные задачи в List для доступа к методам get(index);
        List<BaseTask> listPrioritizedTasks = new ArrayList<>(inMemoryTaskManager.getPrioritizedTasks());

        BaseTask task1 = (BaseTask)inMemoryTaskManager.getTaskOnId(taskId1);
        BaseTask task2 = (BaseTask)inMemoryTaskManager.getTaskOnId(taskId2);
        BaseTask epic1 = (BaseTask)inMemoryTaskManager.getEpicOnId(epicId);

        //На первом месте задача с самым ранним стартом 22.3.17
        assertEquals(task1, listPrioritizedTasks.get(0));

        //Второе место - дата старта 22.4.8
        assertEquals(task2, listPrioritizedTasks.get(1));
        //Поскольку эпик без даты, он будет в конце списка, т.е под индексом 2
        assertEquals(epic1, listPrioritizedTasks.get(2), "Задачи не совпадают.");
    }

}