package manager;

import server.HTTPTaskManager;
import server.KVServer;

import java.io.IOException;

public class Managers {

    public static HTTPTaskManager getDefault() {
        return new HTTPTaskManager(KVServer.getUrl());
    }

    public HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public FileBackedTasksManager getTasksManager(String file)  {
        return new FileBackedTasksManager(file);
    }

    public InMemoryTaskManager getInMemoryTaskManager()  {
        return new InMemoryTaskManager();
    }
}
