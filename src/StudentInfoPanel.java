import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

public class StudentInfoPanel extends JPanel {
    Student student;
    Course course;
    JLabel course_name, course_teacher, student_assistant;
    JPanel lists_panel;
    JList<String> course_grades;
    JList<Assistant> course_assistants;
    JScrollPane cg_pane, ca_pane;

    public StudentInfoPanel(Student student) {
        this.student = student;
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        course_name = new JLabel();
        course_teacher = new JLabel();
        student_assistant = new JLabel();
        lists_panel = new JPanel(new GridLayout(1, 2));
        course_grades = new JList<>();
        course_assistants = new JList<>();
        cg_pane = new JScrollPane(course_grades);
        ca_pane = new JScrollPane(course_assistants);
        lists_panel.add(cg_pane);
        lists_panel.add(course_assistants);
        add(course_name);
        add(course_teacher);
        add(student_assistant);
        add(lists_panel);
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
        course_teacher.setText("Profesorul titular: " + course.getTitular());
        course_name.setText("Numele cursului: " + course.getNume());
        for (Group gr : course.getGrupe().values()) {
            if (gr.contains(student)) {
                student_assistant.setText("Asistentul grupei: " + gr.getAssistant());
                break;
            }
        }
        DefaultListModel<Assistant> ast = new DefaultListModel<>();
        ast.addAll(course.getAsistenti());
        course_assistants.setModel(ast);
        DefaultListModel<String> mod2 = new DefaultListModel<>();
        for(Grade gr:course.getAllStudentGrades().values()) {
            if(gr.getStudent().equals(student)){
                mod2.addElement(gr.toString());
            }
        }
        course_grades.setModel(mod2);
        updateUI();
    }


}
