public class BaseTask {
    private String name;
    private String description;
    private int id;
    private String status;

    public BaseTask(String name, String description, String status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public void setId(int baseId) {
        this.id = baseId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Название: " + name +
                ". Описание: " + description +
                ". Статус: " + status;
    }

}
