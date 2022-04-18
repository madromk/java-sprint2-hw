import manager.*;
import tasks.BaseTask;
import tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        TaskManager inMemoryTaskManager = new Managers().getDefault();

        Task taskOne = new Task("Покупка", "Купить подарок на ДР",
                InMemoryTaskManager.STATUS_NEW, TypeOfTask.TASK);
        LocalDateTime dateTimeStartTask1 = LocalDateTime.of(2022, 3, 17, 00, 00);
        Duration durationTask1 = Duration.ofDays(2);
        int taskId = inMemoryTaskManager.setTask(taskOne, dateTimeStartTask1, durationTask1);
        Task savedTask = inMemoryTaskManager.getTaskOnIdAndSaveInHistory(taskId);

        System.out.println("История просмотров 1:");
        List<BaseTask> history = inMemoryTaskManager.history();
        System.out.println(history);
        System.out.println();

        System.out.println("История просмотров 1.1:");
        Task savedTask2 = inMemoryTaskManager.getTaskOnIdAndSaveInHistory(taskId);
        history = inMemoryTaskManager.history();
        System.out.println(history);
        System.out.println();

        System.out.println("История просмотров 1.2:");
        inMemoryTaskManager.removeTask(taskId);
        history = inMemoryTaskManager.history();
        System.out.println(history);
        System.out.println();

//        Task taskTwo = new Task("Пробежка", "Бег",
//                InMemoryTaskManager.STATUS_NEW, TypeOfTask.TASK);
//        LocalDateTime dateTimeStartTask2 = LocalDateTime.of(2022, 4, 17, 00, 00);
//        Duration durationTask2 = Duration.ofDays(2);
//        int taskId2 = inMemoryTaskManager.setTask(taskTwo, dateTimeStartTask2, durationTask2);
//        Task savedTask2 = inMemoryTaskManager.getTaskOnId(taskId2);
//
//        System.out.println("История просмотров 2:");
//        history = inMemoryTaskManager.history();
//        System.out.println(history);
//        System.out.println();
//
//        System.out.println("История просмотров 3:");
//        inMemoryTaskManager.getTaskOnId(taskId);
//        history = inMemoryTaskManager.history();
//        System.out.println(history);
//        System.out.println();
//
//        System.out.println("История просмотров 4:");
//        inMemoryTaskManager.removeTask(taskId);
//        history = inMemoryTaskManager.history();
//        System.out.println(history);
//        System.out.println();

        //Создаем первую простую задачу
//        Task taskOne = new Task("Покупка", "Купить подарок на ДР", InMemoryTaskManager.STATUS_NEW);
//        int taskIdOne = inMemoryTaskManager.setTaskId();
//        inMemoryTaskManager.setTask(taskIdOne, taskOne);
//
//        //Создаем вторую простую задачу
//        Task taskTwo = new Task("Ремонт", "Забрать интрумент", InMemoryTaskManager.STATUS_NEW);
//        int taskIdTwo = inMemoryTaskManager.setTaskId();
//        inMemoryTaskManager.setTask(taskIdTwo, taskTwo);
//
//        //Создаем эпик с тремя подзадачами
//        Epic epicOne = new Epic("Компьютер", "Собрать игровой ПК");
//        int epicIdOne = inMemoryTaskManager.setEpicId();
//        inMemoryTaskManager.setEpic(epicOne, epicIdOne);
//        SubTask subtaskOne = new SubTask("Покупка", "Купить железо", InMemoryTaskManager.STATUS_NEW);
//        SubTask subtaskTwo = new SubTask("Сборка", "Собрать ПК и протестировать",
//                InMemoryTaskManager.STATUS_NEW);
//        SubTask subtaskThree = new SubTask("Установка ОС", "Установить Windows",
//                InMemoryTaskManager.STATUS_NEW);
//        int idSubTaskOne = inMemoryTaskManager.setTaskId();
//        int idSubTaskTwo = inMemoryTaskManager.setTaskId();
//        int idSubTaskThree = inMemoryTaskManager.setTaskId();
//        inMemoryTaskManager.setSubTask(subtaskOne, idSubTaskOne, epicIdOne);
//        inMemoryTaskManager.setSubTask(subtaskTwo, idSubTaskTwo, epicIdOne);
//        inMemoryTaskManager.setSubTask(subtaskThree, idSubTaskThree, epicIdOne);
//
//        // Создаем эпик без подзадач
//        Epic epicTwo = new Epic("Учеба", "Пройти второй спринт");
//        int epicIdTwo = inMemoryTaskManager.setEpicId();
//        inMemoryTaskManager.setEpic(epicTwo, epicIdTwo);
//        Statuses st = epicTwo.getStatus();
//        System.out.println(st);
//
//        //Вызываем задачи. При каждом вызове задачи - вызываем историю. Не должно быть повторов
//        inMemoryTaskManager.getTaskOnId(taskIdOne);
//        System.out.println("История просмотров 1:");
//        List<BaseTask> history = inMemoryTaskManager.history();
//        System.out.println(history);
//        System.out.println();
//
//        inMemoryTaskManager.getTaskOnId(taskIdTwo);
//        System.out.println("История просмотров 2:");
//        history = inMemoryTaskManager.history();
//        System.out.println(history);
//        System.out.println();
//
//        inMemoryTaskManager.getEpicOnId(epicIdOne);
//        System.out.println("История просмотров 3:");
//        history = inMemoryTaskManager.history();
//        System.out.println(history);
//        System.out.println();
//
//        inMemoryTaskManager.getTaskOnId(taskIdOne);
//        System.out.println("История просмотров 4:");
//        history = inMemoryTaskManager.history();
//        System.out.println(history);
//        System.out.println();
//
//        //Удаляем первую простую задачу и удаляем эпик с подзадачами. Не должно остаться эпика и его подзадач
//        inMemoryTaskManager.removeTask(taskIdOne);
//        inMemoryTaskManager.removeEpic(epicIdOne);
//        history = inMemoryTaskManager.history();
//        System.out.println("История просмотров 5:");
//        System.out.println(history);

    }
}
