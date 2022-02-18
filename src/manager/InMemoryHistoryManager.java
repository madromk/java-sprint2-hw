package manager;

import tasks.BaseTask;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private static final int MAX_SIZE_HISTORY = 10;

    private List<BaseTask> history = new ArrayList<>();

    @Override
    public void addInHistory(BaseTask task) {
        if(history.size() == MAX_SIZE_HISTORY) {
            history.remove(0);
        }
        history.add(task);
    }

    @Override
    public List getHistory() {
        return history;
    }
}
