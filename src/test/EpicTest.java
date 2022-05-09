package test;

import manager.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.HTTPTaskManager;
import server.KVServer;
import tasks.Epic;
import tasks.SubTask;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EpicTest {

    TaskManager inMemoryTaskManager = new InMemoryTaskManager();


    public Epic getEpicOne() {
        return new Epic("Учеба", "Пройти шестой спринт", TypeOfTask.EPIC);
    }
    public Epic getEpicTwo() {
        return new Epic("Компьютер", "Собрать игровой ПК", TypeOfTask.EPIC);
    }
    public SubTask getSubTaskOne() {
        return new SubTask("Покупка", "Купить железо", InMemoryTaskManager.STATUS_NEW);
    }
    public LocalDateTime startDateTimeOne() {
        return LocalDateTime.of(2022, 7, 7, 00, 00);
    }
    public Duration durationOne() {
        return Duration.ofDays(4);
    }
    public SubTask getSubTaskTwo() {
        return new SubTask("Сборка", "Собрать ПК и протестировать", InMemoryTaskManager.STATUS_NEW);
    }
    public LocalDateTime startDateTimeTwo() {
        return LocalDateTime.of(2022, 7, 16, 00, 00);
    }
    public Duration durationTwo() {
        return Duration.ofDays(6);
    }

//    @BeforeEach
//    public void startServer() {
//        KVServer kvServer = new KVServer();
//        kvServer.start();
//    }
//
//    @AfterEach
//    public void startStop() {
//        kvServer.stop();
//    }

    @Test
    public void shouldGiveStatusNewForEpicWithoutSub() {
        //Расчёт статуса. Пустой список подзадач. Статус эпика должен быть NEW
        Epic epicOne = getEpicOne();
        inMemoryTaskManager.setEpic(epicOne);
        Statuses EpicOneStatus = epicOne.getStatus();
        assertEquals(Statuses.NEW, EpicOneStatus);
    }
    @Test
    public void shouldGiveOtherStatusForEpic() {
        //Расчёт статуса. Все подзадачи со статусом NEW. Статус эпика должен быть NEW
        Epic epicTwo = getEpicTwo();

        SubTask subtaskOne = getSubTaskOne();
        LocalDateTime dateTimeStartSub1 = startDateTimeOne();
        Duration durationSub1 = durationOne();

        SubTask subtaskTwo = getSubTaskTwo();
        LocalDateTime dateTimeStartSub2 = startDateTimeTwo();
        Duration durationSub2 = durationTwo();

        int epicId = inMemoryTaskManager.setEpic(epicTwo);
        int subtaskId1 = inMemoryTaskManager.setSubTask(subtaskOne, epicId, dateTimeStartSub1, durationSub1);
        int subtaskId2 = inMemoryTaskManager.setSubTask(subtaskTwo, epicId, dateTimeStartSub2, durationSub2);

        Statuses EpicTwoStatusNew = epicTwo.getStatus();
        assertEquals(Statuses.NEW, EpicTwoStatusNew);

        //Меняем у обеих сабтасок статусы на DONE. Ожидаем статуса эпика - DONE
        inMemoryTaskManager.updateSubTaskStatus(subtaskId1, subtaskOne, Statuses.DONE);
        inMemoryTaskManager.updateSubTaskStatus(subtaskId2, subtaskTwo, Statuses.DONE);

        Statuses EpicTwoStatusDone = epicTwo.getStatus();
        assertEquals(Statuses.DONE, EpicTwoStatusDone);

        //Меняем у обеих сабтасок статусы на NEW и DONE. Ожидаем статуса эпика - IN_PROGRESS
        inMemoryTaskManager.updateSubTaskStatus(subtaskId1, subtaskOne, Statuses.NEW);
        inMemoryTaskManager.updateSubTaskStatus(subtaskId2, subtaskTwo, Statuses.DONE);

        Statuses EpicTwoStatusInProgress = epicTwo.getStatus();
        assertEquals(Statuses.IN_PROGRESS, EpicTwoStatusInProgress);

        //Меняем у обеих сабтасок статусы на IN_PROGRESS. Ожидаем статуса эпика IN_PROGRESS
        inMemoryTaskManager.updateSubTaskStatus(subtaskId1, subtaskOne, Statuses.IN_PROGRESS);
        inMemoryTaskManager.updateSubTaskStatus(subtaskId2, subtaskTwo, Statuses.IN_PROGRESS);

        Statuses EpicTwoStatusInProgressTwo = epicTwo.getStatus();
        assertEquals(Statuses.IN_PROGRESS, EpicTwoStatusInProgressTwo);

    }

    @Test
    public void shouldGiveDateStartAndDateEndForEpic() {
        Epic epicTwo = getEpicTwo();

        SubTask subtaskOne = getSubTaskOne();
        LocalDateTime dateTimeStartSub1 = startDateTimeOne();
        Duration durationSub1 = durationOne();

        SubTask subtaskTwo = getSubTaskTwo();
        LocalDateTime dateTimeStartSub2 = startDateTimeTwo();
        Duration durationSub2 = durationTwo();

        int epicId = inMemoryTaskManager.setEpic(epicTwo);
        inMemoryTaskManager.setSubTask(subtaskOne, epicId, dateTimeStartSub1, durationSub1);
        inMemoryTaskManager.setSubTask(subtaskTwo, epicId, dateTimeStartSub2, durationSub2);

        //Извлекаем из памяти добавленный эпик и проверяем даты старта, конца и продолжительности.
        Epic epic = inMemoryTaskManager.getEpicOnIdAndSaveInHistory(epicId);

        LocalDateTime epicStart = epic.getStartDate();
        assertEquals(dateTimeStartSub1, epicStart, "Старт эпика не совпадает с самой ранней сабтаской");

        Duration durationEpic = epic.getDuration();
        assertEquals(durationSub1.plus(durationSub2), durationEpic, "Продолжительность эпика " +
                                                                                "и сабтасок не совпадают");
        LocalDateTime epicEnd = epic.getEndTime();
        assertEquals(subtaskTwo.getEndTime(), epicEnd, "Завершение эпика не совпадает " +
                                                                    "с завершением поздней сабтаской");

    }
}