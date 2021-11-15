package ui;

import javax.swing.*;
import java.awt.*;

public class GameDisplayTextArea {
    private JTextArea displayTextArea;
    private JScrollPane displayScrollPane;

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

    public void addMessageToDisplay(String str) {
        this.displayTextArea.append(str);
        scrollDown();
    }

    public void clearDisplay() {
        this.displayTextArea.setText("");
    }

    //move to the bottom of text area
    private void scrollDown() {
        this.displayTextArea.setCaretPosition(this.displayTextArea.getText().length());
    }

    public JScrollPane getDisplayScrollPane() {
        return displayScrollPane;
    }
}
