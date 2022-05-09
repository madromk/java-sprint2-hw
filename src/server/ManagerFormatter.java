package server;

import server.HTTPTaskManager;
import server.KVServer;
import tasks.BaseTask;
import tasks.Epic;
import tasks.Task;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ManagerFormatter {

    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, Epic> epics;
    private final List<BaseTask> history;


    public ManagerFormatter(HashMap<Integer, Task> tasks, HashMap<Integer, Epic> epics, List<BaseTask> history) {
        this.tasks = tasks;
        this.epics = epics;
        this.history = history;
    }

    public HTTPTaskManager loadFromServer() {
        HTTPTaskManager httpTaskManager = null;
        httpTaskManager = new HTTPTaskManager(KVServer.getUrl());
        if(!tasks.isEmpty()) {
            for(Integer id : tasks.keySet()) {
                httpTaskManager.updateTask(id, tasks.get(id));
            }
        }
        if(!epics.isEmpty()) {
            for(Integer id : epics.keySet()) {
                httpTaskManager.updateEpic(id, epics.get(id));
            }
        }

        for(BaseTask baseTask : history) {
            int id = baseTask.getId();
            if(httpTaskManager.allTasksHashMap().containsKey(id)) {
                httpTaskManager.getTaskOnIdAndSaveInHistory(id);
            }
            if(httpTaskManager.allEpicsHashMap().containsKey(id)) {
                httpTaskManager.getEpicOnIdAndSaveInHistory(id);
            }
            if(httpTaskManager.allSubTasksHashMap().containsKey(id)) {
                httpTaskManager.getSubTaskOnIdAndSaveInHistory(id);
            }
        }

        return  httpTaskManager;
    }
}
