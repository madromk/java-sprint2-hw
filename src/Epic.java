import java.util.ArrayList;

public class Epic extends BaseTask {

    ArrayList<Integer> subTaskNumbers = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description, null);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

