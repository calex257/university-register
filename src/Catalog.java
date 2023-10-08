import java.util.ArrayList;

public class Catalog implements Subject {
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Grade grade) {
        for(Observer obs: observers) {
            if(grade.getStudent().getFather() == obs ||
                    grade.getStudent().getMother() == obs){
                obs.update(new Notification(grade));
            }
        }
    }

    private ArrayList<Course> cursuri;

    public ArrayList<Course> getCursuri() {
        return cursuri;
    }

    private ArrayList<Observer> observers;
    private static Catalog c;

    public Course getCourseByName(String str) {
        for(Course curs : cursuri) {
            if(curs.getNume().equals(str)) {
                return curs;
            }
        }
        return null;
    }
    private Catalog() {
        cursuri = new ArrayList<Course>(100);
        observers = new ArrayList<Observer>(100);
    }

    public static Catalog getCatalogInstance() {
        if (c == null) {
            c = new Catalog();
            return c;
        }
        return c;
    }

    public void addCourse(Course course) {
        cursuri.add(course);
    }

    public void removeCourse(Course course) {
        if (cursuri.contains(course)) {
            cursuri.remove(course);
        }
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "cursuri=" + cursuri +
                ", observers=" + observers +
                '}';
    }
}
