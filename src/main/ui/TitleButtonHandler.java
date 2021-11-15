package ui;

import model.Command;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TitleButtonHandler implements ActionListener {
    private GameGUI gameGUI;

    public TitleButtonHandler(GameGUI gameGUI) {
        this.gameGUI = gameGUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String choice = e.getActionCommand();
        switch (choice) {
            case Command.NEW_GAME:
                this.gameGUI.startGame();
                break;
            case Command.LOAD:
                this.gameGUI.loadGame();
                break;
            case Command.EXIT:
                System.exit(0);
        }
    }
}
