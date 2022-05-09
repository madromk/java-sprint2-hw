package server;

import com.google.gson.Gson;
import manager.FileBackedTasksManager;

public class HTTPTaskManager extends FileBackedTasksManager {


    private final KVTaskClient kvTaskClient;
    private String key;

    public HTTPTaskManager(String url) {
        super();
        this.kvTaskClient = new KVTaskClient(url);
        this.key = "defaultKey";
    }

    @Override
    public void save() {
        Gson gson = new Gson();
        String json = gson.toJson(new ManagerFormatter(allTasksHashMap(), allEpicsHashMap(), history()));
        kvTaskClient.put(key, json);
    }


    public HTTPTaskManager load(String key) {
            String fromJson = kvTaskClient.load(key);
            Gson gson = new Gson();
            ManagerFormatter managerFormatter = gson.fromJson(fromJson, ManagerFormatter.class);
            return managerFormatter.loadFromServer();
    }

    public void setKey(String key) {
        this.key = key;
    }


}
