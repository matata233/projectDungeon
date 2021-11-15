package ui;

import javax.swing.*;
import java.awt.*;

public class GameDisplayPanel {


    private JPanel displayPanel;
    private JLabel displayLabel;

    public GameDisplayPanel(String displayContent, Font font) {
        this.displayPanel = new JPanel();
        this.displayPanel.setBounds(100, 100, 600, 300);
        this.displayPanel.setBackground(Color.BLACK);

        this.displayLabel = new JLabel(displayContent);
        this.displayLabel.setForeground(Color.WHITE);
        this.displayLabel.setFont(font);
        this.displayPanel.add(this.displayLabel);
    }

    public JPanel getDisplayPanel() {
        return displayPanel;
    }
}
