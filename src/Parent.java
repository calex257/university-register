import java.util.ArrayList;
import java.util.HashSet;

public class Parent extends User implements Observer{
    private HashSet<Notification> notifs;
    @Override
    public void update(Notification notification) {
        System.out.println("Parintele " + getFirstName() + " " + getLastName() + " a fost notificat" +notification);
        notifs.add(notification);
    }

    public Parent(String firstName, String lastName) {
        super(firstName, lastName);
        notifs = new HashSet<>(100);
    }

    public HashSet<Notification> getNotifs() {
        return notifs;
    }

    public void setNotifs(HashSet<Notification> notifs) {
        this.notifs = notifs;
    }
}
