import javax.swing.*;
import java.awt.*;

public class ParentScreen extends JPanel {
    Parent parent;
    JScrollPane scroll;
    JList<Notification> not_list;

    public ParentScreen(Parent parent) {
        this.parent = parent;
        not_list = new JList<>();
        DefaultListModel<Notification> def = new DefaultListModel<>();
        def.addAll(parent.getNotifs());
        not_list.setModel(def);
        scroll = new JScrollPane(not_list);
        setLayout(new GridLayout(1,1));
        add(scroll);
        updateUI();
    }
}
