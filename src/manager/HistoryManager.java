package manager;

import tasks.BaseTask;

import java.util.List;

public interface HistoryManager {

    void addInHistory(BaseTask task);

    void remove(int id);

    List getHistory();
}
