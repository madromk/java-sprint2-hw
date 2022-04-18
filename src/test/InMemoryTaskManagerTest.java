package test;

import manager.InMemoryTaskManager;
import manager.Statuses;
import org.junit.jupiter.api.Test;
import tasks.BaseTask;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import test.TaskManagerTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest extends TaskManagerTest {

    @Test
    @Override
    void addNewTask() {
        super.addNewTask();
    }

    @Test
    @Override
    void addNewEpicWithoutSubTask() {
        super.addNewEpicWithoutSubTask();
    }

    @Test
    @Override
    void addNewEpicWithSubTask() {
        super.addNewEpicWithSubTask();
    }

    @Test
    @Override
    void removeTaskAndEpic() {
        super.removeTaskAndEpic();

    }

    @Test
    @Override
    void removeAllTasksAndEpics() {
        super.removeTaskAndEpic();
    }

    @Test
    @Override
    void updateStatusTask() {
        super.updateStatusTask();
    }

    @Test
    @Override
    void checkPrioritizedTasks() {
        super.checkPrioritizedTasks();
    }

}