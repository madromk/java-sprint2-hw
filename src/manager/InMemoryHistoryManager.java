package manager;

import tasks.BaseTask;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private List<BaseTask> history = new ArrayList<>();

    @Override
    public void addInHistory(BaseTask task) {
        if(history.size() <= 10) {
            history.add(task);
        } else {
            history.remove(0);
            history.add(task);
        }
    }

    @Override
    public List getHistory() {
        return history;
    }
}
