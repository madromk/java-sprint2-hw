package manager;

import tasks.BaseTask;
import tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public interface HistoryManager {

    void addInHistory(BaseTask task);

    void remove(int id);

    List getHistory();


}
