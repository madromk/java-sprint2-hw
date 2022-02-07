public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();

        //Создаем первую простую задачу
        Task taskOne = new Task("Покупка", "Купить подарок на ДР", manager.statusNew);
        int taskIdOne = manager.setTaskId();
        manager.createTask(taskOne, taskIdOne);
        System.out.println(manager.allTask.get(taskIdOne));
        System.out.println();

        //Создаем вторую простую задачу
        Task taskTwo = new Task("Ремонт", "Забарать интрумент", manager.statusNew);
        int taskIdTwo = manager.setTaskId();
        manager.createTask(taskTwo, taskIdTwo);
        System.out.println(manager.allTask.get(taskIdTwo));
        System.out.println();

        //Создаем эпик с двумя подзадачами
        Epic epicOne = new Epic("Компьютер", "Собрать игровой ПК");
        int epicIdOne = manager.setEpicId();
        manager.createEpic(epicOne, epicIdOne);
        SubTask subtaskOne = new SubTask("Покупка", "Купить железо", manager.statusNew);
        SubTask subtaskTwo = new SubTask("Сборка", "Собрать ПК и протестировать", manager.statusNew);
        int idSubTaskOne = manager.setIdSubTask();
        int idSubTaskTwo = manager.setIdSubTask();
        manager.createSubTask(subtaskOne, idSubTaskOne, epicIdOne);
        manager.createSubTask(subtaskTwo, idSubTaskTwo, epicIdOne);
        manager.updateEpicStatus(epicIdOne);
        System.out.println(epicOne);
        manager.getSubTaskInEpic(epicIdOne);
        System.out.println();

//      Создаем эпик с одной подзадачей
        Epic epicTwo = new Epic("Учеба", "Пройти второй спринт");
        int epicIdTwo = manager.setEpicId();
        manager.createEpic(epicTwo, epicIdTwo);
        SubTask subtaskThree = new SubTask("Проект", "Сдать задачу на проверку", manager.statusNew);
        int idSubTaskThree = manager.setIdSubTask();
        manager.createSubTask(subtaskThree, idSubTaskThree, epicIdTwo);
        manager.updateEpicStatus(epicIdTwo);
        System.out.println(epicTwo);
        manager.getSubTaskInEpic(epicIdTwo);
        System.out.println();

        //Меняем статус у первой задачи
        manager.updateTaskStatus(taskIdOne, manager.statusDone);
        System.out.println(manager.allTask.get(taskIdOne));
        System.out.println();

        //Меняем статус у подзадачи первого эпика
        manager.updateSubTaskStatus(idSubTaskOne, manager.statusDone);
        manager.updateEpicStatus(epicIdOne);
        System.out.println(manager.allEpic.get(epicIdOne));
        manager.getSubTaskInEpic(epicIdOne);
        System.out.println();

        //Удаляем первую задачу
        manager.removeTask(taskIdOne);
        manager.getAllTask();
        System.out.println();

        //Удаляем первый эпик
        manager.removeEpic(epicIdOne);
        manager.getAllEpic();
    }

}
