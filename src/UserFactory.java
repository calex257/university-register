public class UserFactory {
    public static User getUser(String type, String firstName, String lastName) {
        if(type.equals("Teacher")) {
            return new Teacher(firstName, lastName);
        }
        if(type.equals("Student")) {
            return new Student(firstName, lastName);
        }
        if(type.equals("Assistant")) {
            return new Assistant(firstName, lastName);
        }
        if(type.equals("Parent")) {
            return new Parent(firstName, lastName);
        }
        return null;
    }
}
