import manager.InMemoryTaskManager;
import manager.Managers;
import manager.TaskManager;
import tasks.BaseTask;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.util.List;


public class Main {
    public static void main(String[] args) {
        TaskManager inMemoryTaskManager = new Managers().getDefault();

        //Создаем первую простую задачу
        Task taskOne = new Task("Покупка", "Купить подарок на ДР", InMemoryTaskManager.STATUS_NEW);
        int taskIdOne = inMemoryTaskManager.setTaskId();
        inMemoryTaskManager.setTask(taskIdOne, taskOne);

        //Создаем вторую простую задачу
        Task taskTwo = new Task("Ремонт", "Забрать интрумент", InMemoryTaskManager.STATUS_NEW);
        int taskIdTwo = inMemoryTaskManager.setTaskId();
        inMemoryTaskManager.setTask(taskIdTwo, taskTwo);

        //Создаем эпик с тремя подзадачами
        Epic epicOne = new Epic("Компьютер", "Собрать игровой ПК");
        int epicIdOne = inMemoryTaskManager.setEpicId();
        inMemoryTaskManager.setEpic(epicOne, epicIdOne);
        SubTask subtaskOne = new SubTask("Покупка", "Купить железо", InMemoryTaskManager.STATUS_NEW);
        SubTask subtaskTwo = new SubTask("Сборка", "Собрать ПК и протестировать",
                InMemoryTaskManager.STATUS_NEW);
        SubTask subtaskThree = new SubTask("Установка ОС", "Установить Windows",
                InMemoryTaskManager.STATUS_NEW);
        int idSubTaskOne = epicOne.setIdSubTask();
        int idSubTaskTwo = epicOne.setIdSubTask();
        int idSubTaskThree = epicOne.setIdSubTask();
        inMemoryTaskManager.setSubTask(subtaskOne, idSubTaskOne, epicIdOne);
        inMemoryTaskManager.setSubTask(subtaskTwo, idSubTaskTwo, epicIdOne);
        inMemoryTaskManager.setSubTask(subtaskThree, idSubTaskThree, epicIdOne);

        // Создаем эпик без подзадач
        Epic epicTwo = new Epic("Учеба", "Пройти второй спринт");
        int epicIdTwo = inMemoryTaskManager.setEpicId();
        inMemoryTaskManager.setEpic(epicTwo, epicIdTwo);

        //Вызываем задачи. При каждом вызове задачи - вызываем историю. Не должно быть повторов
        inMemoryTaskManager.getTaskOnId(taskIdOne);
        System.out.println("История просмотров:");
        List<BaseTask> history = inMemoryTaskManager.history();
        System.out.println(history);
        System.out.println();

        inMemoryTaskManager.getTaskOnId(taskIdTwo);
        System.out.println("История просмотров:");
        history = inMemoryTaskManager.history();
        System.out.println(history);
        System.out.println();

        inMemoryTaskManager.getEpicOnId(epicIdOne);
        System.out.println("История просмотров:");
        history = inMemoryTaskManager.history();
        System.out.println(history);
        System.out.println();

        inMemoryTaskManager.getTaskOnId(taskIdOne);
        System.out.println("История просмотров:");
        history = inMemoryTaskManager.history();
        System.out.println(history);
        System.out.println();

        //Удаляем первую простую задачу и удаляем эпик с подзадачами. Не должно остаться эпика и его подзадач
        inMemoryTaskManager.removeTask(taskIdOne);
        inMemoryTaskManager.removeEpic(epicIdOne);
        history = inMemoryTaskManager.history();
        System.out.println("История просмотров:");
        System.out.println(history);

    }
}
