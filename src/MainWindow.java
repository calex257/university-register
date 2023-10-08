import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private static MainWindow mw;
    private User logged_user;
    private ScoreVisitor scoreVisitor;

    public ScoreVisitor getScoreVisitor() {
        return scoreVisitor;
    }

    public void setScoreVisitor(ScoreVisitor sc) {
        scoreVisitor = sc;
    }

    JPanel main_panel;

    private MainWindow() {
        super("Cea mai jmek aplicatie");
        main_panel = new JPanel(new GridLayout(1, 2));
        main_panel.add(new LoginScreen());
        add(main_panel);
        setMinimumSize(new Dimension(400,400));
        //show();
    }
    public static MainWindow getMainWindowInstance() {
        if(mw == null) {
            mw = new MainWindow();
        }
        return mw;
    }
    public User getLogged_user() {
        return logged_user;
    }
    public void showWindow(){
        show();
    }

    public void setLogged_user(User logged_user) {
        this.logged_user = logged_user;
        if(logged_user instanceof Teacher ||
        logged_user instanceof Assistant) {
            if(main_panel.getComponentCount() == 1) {
                main_panel.add(new StaffScreen(logged_user));
            } else {
                main_panel.remove(1);
                main_panel.add(new StaffScreen(logged_user));
            }
        } else if(logged_user instanceof Student) {
            if(main_panel.getComponentCount() == 1) {
                main_panel.add(new StudentScreen((Student) logged_user));
            } else {
                main_panel.remove(1);
                main_panel.add(new StudentScreen((Student) logged_user));
            }
        } else if(logged_user instanceof Parent) {
            if(main_panel.getComponentCount() == 1) {
                main_panel.add(new ParentScreen((Parent) logged_user));
            } else {
                main_panel.remove(1);
                main_panel.add(new ParentScreen((Parent) logged_user));
            }
        }
    }
}
