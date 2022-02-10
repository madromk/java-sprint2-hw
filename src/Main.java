import manager.Manager;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;


public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();

        //Создаем первую простую задачу
        Task taskOne = new Task("Покупка", "Купить подарок на ДР", Manager.statusNew);
        int taskIdOne = manager.setTaskId();
        manager.setTask( taskIdOne, taskOne);
        System.out.println(manager.getTaskOnId(taskIdOne));
        System.out.println();

        //Создаем вторую простую задачу
        Task taskTwo = new Task("Ремонт", "Забарать интрумент", Manager.statusNew);
        int taskIdTwo = manager.setTaskId();
        manager.setTask(taskIdTwo, taskTwo);
        System.out.println(manager.getTaskOnId(taskIdTwo));
        System.out.println();

        //Создаем эпик с двумя подзадачами
        Epic epicOne = new Epic("Компьютер", "Собрать игровой ПК");
        int epicIdOne = manager.setEpicId();
        manager.setEpic(epicOne, epicIdOne);
        SubTask subtaskOne = new SubTask("Покупка", "Купить железо", Manager.statusNew);
        SubTask subtaskTwo = new SubTask("Сборка", "Собрать ПК и протестировать", Manager.statusNew);
        int idSubTaskOne = epicOne.setIdSubTask();
        int idSubTaskTwo = epicOne.setIdSubTask();
        manager.setSubTask(subtaskOne, idSubTaskOne, epicIdOne);
        manager.setSubTask(subtaskTwo, idSubTaskTwo, epicIdOne);
        System.out.println(epicOne);
        manager.getSubTaskInEpic(epicIdOne);
        System.out.println();

        // Создаем эпик с одной подзадачей
        Epic epicTwo = new Epic("Учеба", "Пройти второй спринт");
        int epicIdTwo = manager.setEpicId();
        manager.setEpic(epicTwo, epicIdTwo);
        SubTask subtaskThree = new SubTask("Проект", "Сдать задачу на проверку", Manager.statusNew);
        int idSubTaskThree = epicTwo.setIdSubTask();
        manager.setSubTask(subtaskThree, idSubTaskThree, epicIdTwo);
        System.out.println(epicTwo);
        manager.getSubTaskInEpic(epicIdTwo);
        System.out.println();

        //Меняем статус у первой задачи
        manager.updateTaskStatus(taskOne, Manager.statusDone);
        System.out.println(manager.getTaskOnId(taskIdOne));
        System.out.println();

        //Меняем статус у подзадачи первого эпика
        manager.updateSubTaskStatus(subtaskOne, Manager.statusInProgress);
        System.out.println(manager.getEpicOnId(epicIdOne));
        manager.getSubTaskInEpic(epicIdOne);
        System.out.println();

        //Удаляем первую задачу и выводим на печать все существующие задачи
        manager.removeTask(taskIdOne);
        manager.getAllTask();
        System.out.println();

        //Удаляем первый эпик и выводим на печать все существующие сабтаски
        manager.removeEpic(epicIdOne);
        manager.getAllEpic();
    }

}
