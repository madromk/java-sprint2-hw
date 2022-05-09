package test;


import org.junit.jupiter.api.Test;

import java.io.IOException;


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