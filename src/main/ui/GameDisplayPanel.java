package ui;

import javax.swing.*;
import java.awt.*;

public class GameDisplayPanel {


    private JPanel displayPanel;
    private JLabel dipsplayLabel;

    public GameDisplayPanel(String displayContent, Font font) {
        this.displayPanel = new JPanel();
        this.displayPanel.setBounds(100, 100, 600, 300);
        this.displayPanel.setBackground(Color.BLUE);

        this.dipsplayLabel = new JLabel(displayContent);
        this.dipsplayLabel.setForeground(Color.WHITE);
        this.dipsplayLabel.setFont(font);
        this.displayPanel.add(this.dipsplayLabel);
    }

    public JPanel getDisplayPanel() {
        return displayPanel;
    }
}
