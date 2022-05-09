package tasks;

import manager.InMemoryTaskManager;
import manager.Statuses;
import manager.TypeOfTask;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;

public class Epic extends BaseTask {

    private HashMap<Integer, SubTask> allSubTask = new HashMap<>();

    public Epic(String name, String description, TypeOfTask type) {
        super(name, description, null);
        this.setType(type);
    }

    public Epic(String name, String description) {
        super(name, description, null);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public void setSubTask(int id, SubTask subTask) {
        allSubTask.put(id, subTask);
    }

    @Override
    public Statuses getStatus() {
        Statuses status = null;
        if(allSubTask.isEmpty()) {
            return Statuses.NEW;
        } else {
            for (SubTask subTask : allSubTask.values()) {
                Statuses SubTaskStatus = subTask.getStatus();
                if ((status == null || status.equals(InMemoryTaskManager.STATUS_NEW))
                        && (SubTaskStatus.equals(InMemoryTaskManager.STATUS_NEW))) {
                    status = InMemoryTaskManager.STATUS_NEW;
                } else if ((status == null || status.equals(InMemoryTaskManager.STATUS_DONE))
                        && (SubTaskStatus.equals(InMemoryTaskManager.STATUS_DONE))) {
                    status = InMemoryTaskManager.STATUS_DONE;
                } else {
                    status = InMemoryTaskManager.STATUS_IN_PROGRESS;
                }
            }
            return status;
        }
    }

    @Override
    public void setStatus(Statuses status) {
    }

    @Override
    public void setDuration(Duration duration) {
    }
    @Override
    public void setStartDate(LocalDateTime localDate) {
    }

    @Override
    public Duration getDuration() {
        Duration duration = Duration.ofDays(0);
        if(allSubTask.isEmpty()) {
            return Duration.ofDays(0);
        } else {
            for (SubTask subTask : allSubTask.values()) {
                duration = duration.plus(subTask.getDuration());
            }
        }
        return duration;
    }

    @Override
    public LocalDateTime getStartDate() {
        LocalDateTime dateDefault = null;
        if(allSubTask.isEmpty()) {
            return null;
        } else {
            for (SubTask subTask : allSubTask.values()) {
                LocalDateTime dateStart = subTask.getStartDate();
                if(dateDefault == null) {
                    dateDefault = dateStart;
                }
                if(dateStart.isBefore(dateDefault)) {
                    dateDefault = dateStart;
                }
            }
        }
        return dateDefault;
    }

    @Override
    public LocalDateTime getEndTime() {
        LocalDateTime dateDefault = LocalDateTime.of(1999, 12, 31, 00, 00);
        if(allSubTask.isEmpty()) {
            return null;
        } else {
            for (SubTask subTask : allSubTask.values()) {
                LocalDateTime dateEnd = subTask.getEndTime();
                if(dateEnd.isAfter(dateDefault)) {
                    dateDefault = dateEnd;
                }
                if(dateDefault.isAfter(dateEnd) || dateDefault.equals(dateEnd)) {
                    dateDefault = dateDefault;
                }
            }
        }
        return dateDefault;
    }

    @Override
    public String getDurationInDays() {
        Duration durationEpic = getDuration();
        return String.valueOf(durationEpic.toDays());
    }


    public HashMap<Integer, SubTask> getAllSubTask() {
        return allSubTask;
    }

    public void clearAllSubTask() {
        allSubTask.clear();
    }

    public SubTask getSubTaskOnId(int id) {
        return allSubTask.get(id);
    }

    public void removeSubTask(int idSubTask) {
        allSubTask.remove(idSubTask);
    }

}

