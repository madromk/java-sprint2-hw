import java.util.ArrayList;

public class Epic extends BaseTask {

    private ArrayList<Integer> subTaskNumbers = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description, null);
    }



    @Override
    public String toString() {
        return super.toString();
    }

    public ArrayList<Integer> getSubTaskNumbers() {
        return subTaskNumbers;
    }

    public void setSubTaskNumbers(ArrayList<Integer> subTaskNumbers) {
        this.subTaskNumbers = subTaskNumbers;
    }
}

