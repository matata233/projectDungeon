package ui;

import javax.swing.*;
import java.awt.*;

public class GameButtonPanel {
    private JPanel buttonPanel;
    private GameButton choiceButton1;
    private GameButton choiceButton2;
    private GameButton choiceButton3;
    private GameButton choiceButton4;

    public GameButtonPanel(
            String buttonName1, String buttonName2, String buttonName3, String buttonName4) {
        this.buttonPanel = new JPanel();
        this.buttonPanel.setBounds(300, 400, 200, 100);
        this.buttonPanel.setBackground(Color.BLUE);
        this.choiceButton1 = new GameButton(buttonName1);
        this.choiceButton2 = new GameButton(buttonName2);
        this.choiceButton3 = new GameButton(buttonName3);
        this.choiceButton4 = new GameButton(buttonName4);
        this.buttonPanel.add(choiceButton1.getButton());
        this.buttonPanel.add(choiceButton2.getButton());
        this.buttonPanel.add(choiceButton3.getButton());
        this.buttonPanel.add(choiceButton4.getButton());
        this.buttonPanel.setLayout(new GridLayout(4,1));
    }

    public JPanel getButtonPanel() {
        return buttonPanel;
    }

    public GameButton getChoiceButton1() {
        return choiceButton1;
    }

    public GameButton getChoiceButton2() {
        return choiceButton2;
    }

    public GameButton getChoiceButton3() {
        return choiceButton3;
    }

    public GameButton getChoiceButton4() {
        return choiceButton4;
    }
}
