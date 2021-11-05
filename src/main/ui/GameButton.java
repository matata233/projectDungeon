package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameButton {


    private JButton button;

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
