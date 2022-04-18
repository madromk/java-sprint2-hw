package test;

import manager.*;
import org.junit.jupiter.api.Test;
import tasks.BaseTask;
import tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryHistoryManagerTest {

    Task taskOne = new Task("Покупка", "Купить подарок на ДР",
            InMemoryTaskManager.STATUS_NEW, TypeOfTask.TASK);
    LocalDateTime dateTimeStartTask1 = LocalDateTime.of(2022, 3, 17, 00, 00);
    Duration durationTask1 = Duration.ofDays(2);

    Task taskTwo = new Task("Поездка", "Сьездить к родителям",
            InMemoryTaskManager.STATUS_NEW, TypeOfTask.TASK);
    LocalDateTime dateTimeStartTask2 = LocalDateTime.of(2022, 4, 8, 00, 00);
    Duration durationTask2 = Duration.ofDays(4);

    HistoryManager inMemoryHistoryManager = new Managers().getDefaultHistory();
    TaskManager inMemoryTaskManager = new Managers().getDefault();

    @Test
    void emptyHistory() {
        List<BaseTask> history = inMemoryTaskManager.history();
        assertEquals(0, history.size(), "История не пустая");
    }

    @Test
    void addInHistoryAndRepeat() {
        int taskId = inMemoryTaskManager.setTask(taskOne, dateTimeStartTask1, durationTask1);
        int taskId2 = inMemoryTaskManager.setTask(taskTwo, dateTimeStartTask2, durationTask2);

        //Вызываем метод getTaskOnId(taskId) для заполения истории

        BaseTask task1 = inMemoryTaskManager.getTaskOnIdAndSaveInHistory(taskId);
        BaseTask task2 = inMemoryTaskManager.getTaskOnIdAndSaveInHistory(taskId2);
        List<BaseTask> history = inMemoryTaskManager.history();

        assertEquals(task1, history.get(0), "Задачи не совпадают.");
        assertEquals(task2, history.get(1), "Задачи не совпадают.");

        //Вызываю ещё раз task1. Ожидаем, что task1 удалится с индекса 0 и добавиться в конец списка
        inMemoryTaskManager.getTaskOnIdAndSaveInHistory(taskId);
        history = inMemoryTaskManager.history();
        assertEquals(task1, history.get(1), "Задачи не совпадают.");
        assertEquals(task2, history.get(0), "Задачи не совпадают.");

    }

    @Test
    void checkRemoveTask() {
        int taskId = inMemoryTaskManager.setTask(taskOne, dateTimeStartTask1, durationTask1);
        int taskId2 = inMemoryTaskManager.setTask(taskTwo, dateTimeStartTask2, durationTask2);

        BaseTask task1 = inMemoryTaskManager.getTaskOnIdAndSaveInHistory(taskId);
        BaseTask task2 = inMemoryTaskManager.getTaskOnIdAndSaveInHistory(taskId2);
        List<BaseTask> history = inMemoryTaskManager.history();
        assertEquals(2, history.size(), "Количество задач в истории не совпадают");

        //Удаляем задачу. Ожидается что задача удалится и из истории
        inMemoryTaskManager.removeTask(taskId);
        history = inMemoryTaskManager.history();
        assertEquals(1, history.size(), "Количество задач в истории не совпадают");
        assertEquals(task2, history.get(0), "Ошибка в получении задачи");
    }

}