package tasks;

import manager.Statuses;
import manager.TypeOfTask;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;

public class BaseTask {
    private String name;
    private String description;
    private Integer id;
    private Statuses status;
    TypeOfTask type;
    private Duration duration;
    private LocalDateTime localStartDate;

    public BaseTask(String name, String description, Statuses status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public BaseTask(String name, String description, Statuses status, TypeOfTask type) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseTask baseTask = (BaseTask) o;
        if (!name.equals(baseTask.getName())) return false;
        if (!description.equals(baseTask.getDescription())) return false;

        return true;
    }

//    @Override
//    public int hashCode() {
//        return id.hashCode();
//    }

    @Override
    public String toString() {
        return getId() + ", " + getType() + ", " + getName() + ", "
                + getStatus() + ", " + getDescription() + ", "
                + getStartDate() + ", " + getDurationInDays() + ", "
                + getEndTime() + ", ";
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Statuses getStatus() {
        return status;
    }

    public void setId(int baseId) {
        this.id = baseId;
    }

    public void setStatus(Statuses status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }


    public TypeOfTask getType() {
        return type;
    }


    public void setType(TypeOfTask type) {
        this.type = type;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setStartDate(LocalDateTime localStartDate) {
        this.localStartDate = localStartDate;
    }

    public LocalDateTime getEndTime() {
        return localStartDate.plus(duration);
    }

    public Duration getDuration() {
        return duration;
    }

    public LocalDateTime getStartDate() {
        return localStartDate;
    }

    public String getDurationInDays() {
        return String.valueOf(duration.toDays());
    }

}
