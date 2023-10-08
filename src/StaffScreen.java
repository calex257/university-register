import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.ComponentSampleModel;
import java.util.ArrayList;
import java.util.HashMap;

public class StaffScreen extends JPanel {
    User usr;
    JButton btn;
    JScrollPane scroll;
    JScrollPane scroll2;
    JPanel lists_panel;
    JList<String> courses;
    DefaultListModel<Grade> grade_model;

    JList<Grade> grades;
    ScoreVisitor sc;
    public StaffScreen(User usr) {
        this.usr = usr;
        this.sc = MainWindow.getMainWindowInstance().getScoreVisitor();

        setLayout(new GridLayout(2,1));
        btn = new JButton("Validate");
        btn.addMouseListener(new MouseInputListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if(grades.isSelectionEmpty() || courses.isSelectionEmpty()) {
                    return;
                }
                int cr = courses.getMinSelectionIndex();
                for(Grade gr: grades.getSelectedValuesList()) {
                    sc.validateGrade(gr, courses.getSelectedValue());
                }
                grade_model.removeRange(grades.getMinSelectionIndex(),
                        grades.getMaxSelectionIndex());
                courses.updateUI();
                grades.updateUI();
                courses.setSelectedIndex(cr);
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseDragged(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseMoved(MouseEvent mouseEvent) {

            }
        });
        lists_panel = new JPanel(new GridLayout(1, 2));
        add(lists_panel);
        add(btn);
        updateUI();
        if(usr instanceof Teacher) {
            handle_teacher((Teacher) usr);
        } else {
            handle_assistant((Assistant) usr);
        }
    }
    private void handle_teacher(Teacher teacher) {
        DefaultListModel<String> course_model = new DefaultListModel<>();
        for(Course c:Catalog.getCatalogInstance().getCursuri()){
            if(c.getTitular().equals(teacher)) {
                course_model.addElement(c.getNume());
            }
        }
        courses = new JList<>(course_model);
        grades = new JList<>();
        scroll = new JScrollPane(courses);
        scroll2 = new JScrollPane(grades);
        lists_panel.add(scroll);
        lists_panel.add(scroll2);

        updateUI();
        courses.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if(courses.isSelectionEmpty()){
                    return;
                }
                grade_model = new DefaultListModel<>();
                ArrayList<Grade> arr = sc.getUnvalidatedGrades(teacher,Catalog.getCatalogInstance()
                        .getCourseByName(courses.getSelectedValue()));
                System.out.println(arr);
                grade_model.addAll(arr);
                grades.setModel(grade_model);
                grades.updateUI();
            }
        });
    }

    private void handle_assistant(Assistant assistant) {
        DefaultListModel<String> course_model = new DefaultListModel<>();
        for(Course c:Catalog.getCatalogInstance().getCursuri()){
            for (Assistant ast: c.getAsistenti()){
                if(ast.equals(assistant)) {
                    course_model.addElement(c.getNume());
                }
            }
        }
        courses = new JList<>(course_model);
        grades = new JList<>();
        scroll = new JScrollPane(courses);
        scroll2 = new JScrollPane(grades);
        lists_panel.add(scroll);
        lists_panel.add(scroll2);
        updateUI();
        courses.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if(courses.isSelectionEmpty()){
                    return;
                }
                grade_model = new DefaultListModel<>();
                ArrayList<Grade> arr = sc.getUnvalidatedGrades(assistant,Catalog.getCatalogInstance()
                        .getCourseByName(courses.getSelectedValue()));
                System.out.println(arr);
                grade_model.addAll(arr);
                grades.setModel(grade_model);
                grades.updateUI();
            }
        });
    }
}
