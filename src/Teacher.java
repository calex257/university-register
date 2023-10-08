public class Teacher extends User implements Element{
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public Teacher(String firstName, String lastName){
        super(firstName, lastName);
    }
}
