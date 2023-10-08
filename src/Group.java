import java.util.Collection;
import java.util.Comparator;
import java.util.TreeSet;

public class Group extends TreeSet<Student> implements Comparable {
    private String ID;
    private Assistant assistant;
    private Comparator<Student> comp;
    public Group(String ID, Assistant assistant, Comparator<Student> comp) {
        this.ID = ID;
        this.assistant = assistant;
        this.comp = comp;
    }
    public Group(String ID, Assistant assistant) {
        this(ID, assistant, null);
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Assistant getAssistant() {
        return assistant;
    }

    public void setAssistant(Assistant assistant) {
        this.assistant = assistant;
    }

    @Override
    public int compareTo(Object o) {
        Group gr = (Group) o;
        return (gr.ID.compareTo(ID));
    }

    @Override
    public String toString() {
        return super.toString() + "Group{" +
                "ID='" + ID + '\'' +
                ", assistant=" + assistant +
                ", comp=" + comp +
                '}';
    }
}
