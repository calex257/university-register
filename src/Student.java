public class Student extends User implements Comparable{
    private Parent mother, father;

    public Student(String firstName, String lastName){
        super(firstName, lastName);
    }

    public Parent getMother() {
        return mother;
    }

    public void setMother(Parent mother) {
        this.mother = mother;
    }

    public Parent getFather() {
        return father;
    }

    public void setFather(Parent father) {
        this.father = father;
    }

    @Override
    public int compareTo(Object o) {
        return getLastName().compareTo(((Student)o).getLastName());
    }
}
