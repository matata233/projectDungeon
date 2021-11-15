package ui;

import javax.swing.*;
import java.awt.*;

public class GameDisplayList {
    private JList displayList;
    private DefaultListModel listModel;

    public GameDisplayList() {
        this.listModel = new DefaultListModel();
        this.displayList = new JList(this.listModel);
        this.displayList.setBackground(Color.BLACK);
        this.displayList.setForeground(Color.WHITE);
        this.displayList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.displayList.setSelectedIndex(0);
        this.displayList.setLayoutOrientation(JList.VERTICAL);
        this.displayList.setFont(GameWindow.BASE_FONT);
    }

    public JList getDisplayList() {
        return displayList;
    }

    public DefaultListModel getListModel() {
        return listModel;
    }
}
