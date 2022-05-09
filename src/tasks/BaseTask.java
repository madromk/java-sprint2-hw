package tasks;

import manager.Statuses;
import manager.TypeOfTask;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class BaseTask {
    private String name;
    private String description;
    private int id;
    private Statuses status;
    private TypeOfTask type;
    private Duration durationInHours;
    private LocalDateTime startDate;

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
        return Objects.equals(id, baseTask.getId()) &&
                Objects.equals(name, baseTask.getName()) &&
                Objects.equals(description, baseTask.getDescription());
    }


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
        this.durationInHours = duration;
    }

    public void setStartDate(LocalDateTime localStartDate) {
        this.startDate = localStartDate;
    }

    public LocalDateTime getEndTime() {
        return startDate.plus(durationInHours);
    }

    public Duration getDuration() {
        return durationInHours;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public String getDurationInDays() {
        return String.valueOf(durationInHours.toDays());
    }

}
