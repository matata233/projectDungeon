package ui;

import javax.swing.*;
import java.awt.*;

// Represents a button in the main game window
public class GameButton {
    private JButton button;

    // EFFECTS: Create a button with given button name
    public GameButton(String buttonName) {
        this.button = new JButton(buttonName);
        this.button.setBackground(Color.BLACK);
        this.button.setForeground(Color.WHITE);
        this.button.setFont(GameWindow.BUTTON_FONT);

    }

    public JButton getButton() {
        return button;
    }
}
