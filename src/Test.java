import java.util.ArrayList;
import java.util.TreeSet;

public class Test {
    private static final String[] first_names = {
            "Brenden",
            "Autumn",
            "Alayna",
            "Katherine",
            "Ally",
            "Rishi",
            "Lawrence",
            "Reed",
            "Cassie",
            "Trey"
    };
    private static final String[] last_names = {
            "Floyd",
            "Richards",
            "Wheeler",
            "Frey",
            "Hartman",
            "Doyle",
            "Galvan",
            "Martinez",
            "Eaton",
            "Cervantes"
    };
    public static void main(String[] args) {
        //Test factory
        Student studentA = (Student) UserFactory.getUser("Student", "A", "Popescu");
        Student studentB = (Student) UserFactory.getUser("Student", "B", "Ionescu");
        Student studentC = (Student) UserFactory.getUser("Student", "C", "Ionescu");

        Parent mother = (Parent) UserFactory.getUser("Parent", "M_AC", "Ionescu");
        Parent father = (Parent) UserFactory.getUser("Parent", "T_AC", "Ionescu");

        User teacher = UserFactory.getUser("Teacher", "Teacher", "Georgescu");
        User assistant = UserFactory.getUser("Assistant", "Assistant", "Popescu");
        //Test Builder
        FullCourse course = (FullCourse) new FullCourse.FullCourseBuilder().titular((Teacher) teacher).
        asistent((Assistant) assistant).nume("POO").pct_credit(100).grupa(
                "321CC", new Group("321CC", (Assistant) assistant)
                ).nota(new Grade(5d,6d, (Student) studentA, "POO"))
                .strategie(new BestTotalScore()).build();

        System.out.println("Curs: "+ course);
        System.out.println("Best Student:" + course.getBestStudent());

        Teacher teacher1 = (Teacher) UserFactory.getUser("Teacher", "Daniel", "Rosner");
        //Test builder + singleton
        Catalog catalog = Catalog.getCatalogInstance();
        catalog.addCourse(course);
        PartialCourse pc = (PartialCourse) new PartialCourse.PartialCourseBuilder() .titular(teacher1)
                .strategie(new BestPartialScore()).pct_credit(150).nume("SO").asistent(
                        new Assistant("Matei", "Barbu")
                ).build();
        System.out.println(pc);
        Group gr = new Group("311", null);

        //Test adaugari
        pc.addGroup(gr);
        pc.addGroup("322", null);
        pc.addAssistant("311", pc.getAssistantByName("Matei", "Barbu"));
        pc.addStudent("311", (Student) studentA);
        pc.addStudent("322", (Student) studentC);
        pc.addStudent("311", (Student) studentB);
        Catalog.getCatalogInstance().addCourse(pc);
        System.out.println(pc.getAllStudents());
        System.out.println(pc);

        //Test observer
        ((Student) studentB).setMother((Parent) mother);
        ((Student) studentA).setMother((Parent) mother);
        ((Student) studentA).setFather((Parent) father);
        ((Student) studentB).setFather((Parent) father);
        ((Student) studentC).setFather((Parent) father);
        ((Student) studentC).setMother((Parent) mother);

        catalog.addObserver((Parent) mother);
        catalog.addObserver((Parent) father);
        catalog.notifyObservers(new Grade(2.5d,3d,(Student)studentC, "POO"));

        ArrayList<Student> studenti = new ArrayList<>(100);

        System.out.println("Parintii lor sunt observeri:");
        for(int i=0;i<20;i++) {
            Student s = (Student) UserFactory.getUser("Student",first_names[(int) Math.floor(Math.random()*10)], last_names[(int) Math.floor(Math.random()*10)]);
            pc.addStudent("311", s);
            studenti.add(s);
            if(i%5 == 0) {
                System.out.println(s.getFirstName() + " " + s.getLastName());
                s.setMother(father);
                s.setFather(father);
            }
        }

        //Test visitor
        ScoreVisitor sc = new ScoreVisitor();
        for(int i=0;i<20;i++) {
            sc.addAssistantGrade(pc.getAssistantByName("Matei", "Barbu"),studenti.get(i), "SO", (((int)(((Math.random() * 5) + 1) * 100))/ 100d));
            sc.addTeacherGrade(pc.getTitular(), studenti.get(i), "SO",(((int)(((Math.random() * 5) + 1) * 100))/ 100d));
        }

        pc.getTitular().accept(sc);
        System.out.println(pc.getNote());
        System.out.println("inainte de asistent");
        pc.getAssistantByName("Matei", "Barbu").accept(sc);
        System.out.println(pc.getNote());

        //Test strategy
        System.out.println("Cea mai mare nota in partial " + pc.getBestStudent());
        pc.setBest_score(new BestExamScore());
        System.out.println("Cea mai mare nota in examen " + pc.getBestStudent());
        pc.setBest_score(new BestTotalScore());
        System.out.println("Cea mai mare nota in total " + pc.getBestStudent());
        System.out.println(pc.getAllStudents());

        //Test getGrade
        System.out.println(pc.getGrade(pc.getAllStudents().get(5)));

        //Test memento
        pc.makeBackup();
        for(int i=0;i<20;i++) {
            sc.addTeacherGrade(pc.getTitular(), studenti.get(i), "SO", 100d);
        }
        sc.visit(pc.getTitular());
        System.out.println(pc.getNote());
        pc.undo();
        System.out.println(pc.getNote());
    }
}
