import manager.InMemoryTaskManager;
import manager.Managers;
import manager.TaskManager;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
        TaskManager inMemoryTaskManager = new Managers().getDefault();

        //Создаем первую простую задачу
        Task taskOne = new Task("Покупка", "Купить подарок на ДР", InMemoryTaskManager.STATUS_NEW);
        int taskIdOne = inMemoryTaskManager.setTaskId();
        inMemoryTaskManager.setTask(taskIdOne, taskOne);
        System.out.println(inMemoryTaskManager.getTaskOnId(taskIdOne));
        System.out.println();

        //Создаем вторую простую задачу
        Task taskTwo = new Task("Ремонт", "Забарать интрумент", InMemoryTaskManager.STATUS_NEW);
        int taskIdTwo = inMemoryTaskManager.setTaskId();
        inMemoryTaskManager.setTask(taskIdTwo, taskTwo);
        System.out.println(inMemoryTaskManager.getTaskOnId(taskIdTwo));
        System.out.println();

        //Создаем эпик с двумя подзадачами
        Epic epicOne = new Epic("Компьютер", "Собрать игровой ПК");
        int epicIdOne = inMemoryTaskManager.setEpicId();
        inMemoryTaskManager.setEpic(epicOne, epicIdOne);
        SubTask subtaskOne = new SubTask("Покупка", "Купить железо", InMemoryTaskManager.STATUS_NEW);
        SubTask subtaskTwo = new SubTask("Сборка", "Собрать ПК и протестировать",
                InMemoryTaskManager.STATUS_NEW);
        int idSubTaskOne = epicOne.setIdSubTask();
        int idSubTaskTwo = epicOne.setIdSubTask();
        inMemoryTaskManager.setSubTask(subtaskOne, idSubTaskOne, epicIdOne);
        inMemoryTaskManager.setSubTask(subtaskTwo, idSubTaskTwo, epicIdOne);
        System.out.println(epicOne);
        inMemoryTaskManager.getSubTaskInEpic(epicIdOne);
        System.out.println();

        // Создаем эпик с одной подзадачей
        Epic epicTwo = new Epic("Учеба", "Пройти второй спринт");
        int epicIdTwo = inMemoryTaskManager.setEpicId();
        inMemoryTaskManager.setEpic(epicTwo, epicIdTwo);
        SubTask subtaskThree = new SubTask("Проект", "Сдать задачу на проверку",
                InMemoryTaskManager.STATUS_NEW);
        int idSubTaskThree = epicTwo.setIdSubTask();
        inMemoryTaskManager.setSubTask(subtaskThree, idSubTaskThree, epicIdTwo);
        System.out.println(epicTwo);
        inMemoryTaskManager.getSubTaskInEpic(epicIdTwo);
        System.out.println();

        //Меняем статус у первой задачи
        inMemoryTaskManager.updateTaskStatus(taskOne, InMemoryTaskManager.STATUS_DONE);
        System.out.println(inMemoryTaskManager.getTaskOnId(taskIdOne));
        System.out.println();

        //Меняем статус у подзадачи первого эпика
        inMemoryTaskManager.updateSubTaskStatus(subtaskOne, InMemoryTaskManager.STATUS_IN_PROGRESS);
        System.out.println(inMemoryTaskManager.getEpicOnId(epicIdOne));
        inMemoryTaskManager.getSubTaskInEpic(epicIdOne);
        System.out.println();

        //Удаляем первую задачу и выводим на печать все существующие задачи
        inMemoryTaskManager.removeTask(taskIdOne);
        inMemoryTaskManager.getAllTask();
        System.out.println();

        //Удаляем первый эпик и выводим на печать все существующие сабтаски
        inMemoryTaskManager.removeEpic(epicIdOne);
        inMemoryTaskManager.getAllEpic();
        System.out.println();

        //Вызываем метод с печатью историей
        System.out.println("История просмотровЖ");
        inMemoryTaskManager.printHistory();
    }

}
