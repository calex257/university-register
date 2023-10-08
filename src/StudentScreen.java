import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.HashSet;

public class StudentScreen extends JPanel {
    Student student;
    JScrollPane scroll;
    JList courses_list;
    StudentInfoPanel pan;
    public StudentScreen(Student student) {
        this.student = student;
        courses_list = new JList();
        pan = new StudentInfoPanel(student);
        scroll = new JScrollPane(courses_list);
        setLayout(new GridLayout(2,1));
        HashSet<String> arr = new HashSet<>();
        DefaultListModel<String> courses = new DefaultListModel<>();
        for(Course c:Catalog.getCatalogInstance().getCursuri()) {
            for(Group g : c.getGrupe().values()) {
                if(g.contains((student))) {
                    System.out.println(c.getNume());
                    arr.add(c.getNume());
                }
            }
        }
        courses_list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if(courses_list.isSelectionEmpty()){
                    return;
                }
                pan.setCourse(Catalog.getCatalogInstance().getCourseByName((String) courses_list.getSelectedValue()));
            }
        });
        courses.addAll(arr);
        courses_list.setModel(courses);
        courses_list.setSelectedIndex(0);
        add(scroll);
        add(pan);
        updateUI();
    }
}
