package ui;

import javax.swing.*;
import java.awt.*;

// Represents a message display panel in the main game window
public class GameDisplayPanel {
    private JPanel displayPanel;
    private JLabel displayLabel;

    // EFFECTS: Create a panel with given display content in string, and font type
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
