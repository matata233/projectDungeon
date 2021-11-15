package ui;

import javax.swing.*;
import java.awt.*;

// Represents a JTextArea in the main game window
public class GameDisplayTextArea {
    private JTextArea displayTextArea;
    private JScrollPane displayScrollPane;

    // EFFECTS: Create a JTextArea within a scrollPane, with display content in string
    public GameDisplayTextArea(String displayContent) {
        this.displayTextArea = new JTextArea(displayContent);
        this.displayTextArea.setBackground(Color.BLACK);
        this.displayTextArea.setForeground(Color.WHITE);
        this.displayTextArea.setFont(GameWindow.BASE_FONT);
        this.displayTextArea.setLineWrap(true);
        this.displayTextArea.setEditable(false);
        this.displayScrollPane = new JScrollPane(displayTextArea);
        this.displayScrollPane.setPreferredSize(new Dimension(600, 300));
        this.displayScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }

    // MODIFIES: this
    // EFFECTS: Add display text to the JTextArea
    public void addMessageToDisplay(String str) {
        this.displayTextArea.append(str);
        scrollDown();
    }

    // MODIFIES: this
    // EFFECTS: Clear all existing text in the JTextArea
    public void clearDisplay() {
        this.displayTextArea.setText("");
    }

    // MODIFIES: this
    // EFFECTS: Move to the bottom of text area whenever a new text is added
    private void scrollDown() {
        this.displayTextArea.setCaretPosition(this.displayTextArea.getText().length());
    }

    public JScrollPane getDisplayScrollPane() {
        return displayScrollPane;
    }
}
