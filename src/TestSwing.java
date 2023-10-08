import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;

public class TestSwing extends JFrame implements ActionListener {
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

    public TestSwing(String name) {
        JPanel list_panel = new JPanel(new GridLayout(1,2));
        HashSet<Student> students = new HashSet<>();
        for(Course c : Catalog.getCatalogInstance().getCursuri()){
            for(Group g : c.getGrupe().values()) {
                students.addAll(g);
            }
        }
        DefaultListModel<Student> stud_data= new DefaultListModel<Student>();
        stud_data.addAll(students);
        JList list = new JList(stud_data);
        JScrollPane scroll = new JScrollPane(list);
        LoginScreen login = new LoginScreen();
        list_panel.add(login);
        JList course_list = new JList();
        JScrollPane scroll2 = new JScrollPane(course_list);
        JPanel course_panel = new JPanel(new GridLayout(2, 1));
        JPanel info_panel = new JPanel();
        info_panel.setLayout(new BoxLayout(info_panel, BoxLayout.PAGE_AXIS));
        course_panel.add(course_list);
        course_panel.add(info_panel);
        JLabel course_name = new JLabel();
        JLabel course_teacher = new JLabel();
        JList<String> course_grades = new JList<>();
        JScrollPane cg_pane = new JScrollPane(course_grades);
        JList<String> course_assistants = new JList<>();
        JScrollPane ca_pane = new JScrollPane(course_assistants);
        JPanel lists_panel = new JPanel(new GridLayout(1,2));
        info_panel.add(course_name);
        info_panel.add(course_teacher);
        lists_panel.add(cg_pane);
        lists_panel.add(ca_pane);
        info_panel.add(lists_panel);
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if(list.isSelectionEmpty()) {
                    return;
                }
                HashSet<String> arr = new HashSet<>();
                DefaultListModel<String> course_data =new DefaultListModel<>();
                for(Course c:Catalog.getCatalogInstance().getCursuri()) {
                    for(Group g : c.getGrupe().values()) {
                        if(g.contains(list.getSelectedValue())) {
                            arr.add(c.getNume());
                        }
                    }
                }
                course_data.addAll(arr);
                course_list.setModel(course_data);
                course_list.setSelectedIndex(0);
                course_panel.updateUI();
            }
        });
        course_list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if(course_list.isSelectionEmpty()){
                    return;
                }
                Course selected_course = Catalog.getCatalogInstance().getCourseByName((String) course_list.getSelectedValue());
                course_teacher.setText("Profesor titular: " + selected_course.getTitular());
                course_name.setText("Nume curs: " + selected_course.getNume());
                DefaultListModel<String> mod1 = new DefaultListModel<>();
                for(Assistant a:selected_course.getAsistenti()) {
                    mod1.addElement(a.toString());
                }
                course_assistants.setModel(mod1);
                DefaultListModel<String> mod2 = new DefaultListModel<>();
                for(Grade gr:selected_course.getAllStudentGrades().values()) {
                    if(gr.getStudent().equals(list.getSelectedValue())){
                        mod2.addElement(gr.toString());
                    }
                }
                course_grades.setModel(mod2);
                info_panel.updateUI();
            }
        });
        add(list_panel);
        course_panel.setMinimumSize(new Dimension(100,100));
        list_panel.add(course_panel);
        setSize(400, 400);
        show();
        System.out.println(students);
    }
    public static void main(String[] args) {
        Student studentA = (Student) UserFactory.getUser("Student", "A", "Popescu");
        Student studentB = (Student) UserFactory.getUser("Student", "B", "Ionescu");
        Student studentC = (Student) UserFactory.getUser("Student", "C", "Ionescu");
        Teacher teacher1 = (Teacher) UserFactory.getUser("Teacher", "Daniel", "Rosner");
        Parent mother = (Parent) UserFactory.getUser("Parent", "M_AC", "Ionescu");
        User teacher = UserFactory.getUser("Teacher", "Teacher", "Georgescu");
        Parent father = (Parent) UserFactory.getUser("Parent", "T_AC", "Ionescu");
        User assistant = UserFactory.getUser("Assistant", "Assistant", "Popescu");
        FullCourse course = (FullCourse) new FullCourse.FullCourseBuilder().titular((Teacher) teacher).
                asistent((Assistant) assistant).nume("POO").pct_credit(100).grupa(
                        "321CC", new Group("321CC", (Assistant) assistant)
                ).nota(new Grade(5d,6d, (Student) studentA, "POO"))
                .strategie(new BestTotalScore()).build();

        PartialCourse pc = (PartialCourse) new PartialCourse.PartialCourseBuilder() .titular(teacher1)
                .strategie(new BestPartialScore()).pct_credit(150).nume("SO").asistent(
                        new Assistant("Matei", "Barbu")
                ).build();
        System.out.println(pc);
        Group gr = new Group("311", null);
        Group gr2 = new Group("324", null);

        //Test adaugari
        pc.addGroup(gr);
        course.addGroup(gr2);
        pc.addGroup("322", null);
        course.addGroup("322", null);
        pc.addAssistant("311", pc.getAssistantByName("Matei", "Barbu"));
        pc.addStudent("311", (Student) studentA);
        pc.addStudent("322", (Student) studentC);
        pc.addStudent("311", (Student) studentB);
        course.addStudent("324", (Student) studentA);
        course.addStudent("324", (Student) studentC);
        course.addStudent("324", (Student) studentB);
        Catalog.getCatalogInstance().addCourse(pc);
        Catalog.getCatalogInstance().addCourse(course);
        System.out.println(pc.getAllStudents());
        System.out.println(pc);
        ArrayList<Student> studenti = new ArrayList<>(100);
        for(int i=0;i<20;i++) {
            Student s = (Student) UserFactory.getUser("Student",first_names[(int) Math.floor(Math.random()*10)], last_names[(int) Math.floor(Math.random()*10)]);
            pc.addStudent("311", s);
            studenti.add(s);
            if(i%5 == 0) {
                System.out.println(s.getFirstName() + " " + s.getLastName());
                s.setMother(father);
                s.setFather(father);
                Catalog.getCatalogInstance().addObserver(father);
            }
        }
        ScoreVisitor sc = new ScoreVisitor();
        for(int i=0;i<20;i++) {
            sc.addAssistantGrade(pc.getAssistantByName("Matei", "Barbu"),studenti.get(i), "SO", (((int)(((Math.random() * 5) + 1) * 100))/ 100d));
            sc.addTeacherGrade(pc.getTitular(), studenti.get(i), "SO",(((int)(((Math.random() * 5) + 1) * 100))/ 100d));
        }

        MainWindow.getMainWindowInstance().setScoreVisitor(sc);
//        pc.getTitular().accept(sc);
//        pc.getAssistantByName("Matei", "Barbu").accept(sc);
        System.out.println(pc.getNote());
        System.out.println(Catalog.getCatalogInstance());
//        TestSwing ts = new TestSwing("poop");
        MainWindow.getMainWindowInstance().showWindow();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
