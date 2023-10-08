import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class LoginScreen extends JPanel {
    private User user;

    public LoginScreen() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        JLabel first_name = new JLabel("First name");
        JTextField tf_first_name = new JTextField(JTextField.CENTER);
        JLabel last_name = new JLabel("Last name");
        JTextField tf_last_name = new JTextField();
        JLabel password = new JLabel("Password");
        JPasswordField tf_password = new JPasswordField();
        tf_first_name.setBorder(new EmptyBorder(10, 10, 10, 10));
        tf_first_name.setMaximumSize(new Dimension(400, 40));
        tf_last_name.setBorder(new EmptyBorder(10, 10, 10, 10));
        tf_last_name.setMaximumSize(new Dimension(400, 40));
        tf_password.setBorder(new EmptyBorder(10, 10, 10, 10));
        tf_password.setMaximumSize(new Dimension(400, 40));
        JButton login_button = new JButton("Log in");
        JLabel status = new JLabel();
        login_button.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String fn = tf_first_name.getText();
                String ln = tf_last_name.getText();
                String pw = tf_password.getPassword().toString();
                if (fn.equals("") || ln.equals("") || pw.equals("")) {
                    status.setText("Campuri necompletate");
                    return;
                }
                for (Course c : Catalog.getCatalogInstance().getCursuri()) {
                    //techer
                    if (c.getTitular().getFirstName().equals(fn) &&
                            c.getTitular().getLastName().equals(ln)) {
                        MainWindow.getMainWindowInstance().setLogged_user(c.getTitular());
                        updateUI();
                        return;
                    }
                    //astent
                    if (c.getAssistantByName(fn, ln) != null) {
                        MainWindow.getMainWindowInstance().setLogged_user(c.getAssistantByName(fn, ln));
                        updateUI();
                        return;
                    }
                    for (Student s : c.getAllStudents()) {
                        if (s.getLastName().equals(ln) &&
                                s.getFirstName().equals(fn)) {
                            MainWindow.getMainWindowInstance().setLogged_user(s);
                            updateUI();
                            System.out.println(s);
                            return;
                        } else if (s.getMother() != null && s.getMother().getLastName().equals(ln) &&
                                s.getMother().getFirstName().equals(fn)) {
                            MainWindow.getMainWindowInstance().setLogged_user(s.getMother());
                            updateUI();
                            return;
                        } else if (s.getFather() != null && s.getFather().getLastName().equals(ln) &&
                                s.getFather().getFirstName().equals(fn)) {
                            MainWindow.getMainWindowInstance().setLogged_user(s.getFather());
                            updateUI();
                            return;
                        }
                    }
                }
                status.setText("Utilizatorul " + fn + " " + ln + " nu a fost gasit");
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
        });
        add(first_name);
        add(tf_first_name);
        add(last_name);
        add(tf_last_name);
        add(password);
        add(tf_password);
        add(login_button);
        add(status);
    }
}
