package ui;

import model.Command;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents a button handler in the title screen of the main game window
public class TitleButtonHandler implements ActionListener {
    private GameGUI gameGUI;

    // EFFECTS: Create a button handler with given GameGUI
    public TitleButtonHandler(GameGUI gameGUI) {
        this.gameGUI = gameGUI;
    }

    @Override
    // MODIFIES: this
    // EFFECTS: Performed actions regarding the name of the button,
    //          corresponding to different commands
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
