package ui;

import model.Command;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents an event handler for game button in the main game window
public class GameButtonHandler implements ActionListener {
    private GameGUI gameGUI;

    // EFFECTS: Create a handler with given gameGUI
    public GameButtonHandler(GameGUI gameGUI) {
        this.gameGUI = gameGUI;
    }

    @Override
    // MODIFIES: this
    // EFFECTS: Performed actions regarding the name of the button,
    //          corresponding to different commands
    public void actionPerformed(ActionEvent e) {
        String choice = e.getActionCommand();
        switch (choice) {
            case Command.MOVE_NORTH:
                this.gameGUI.runDungeon(Command.MOVE_NORTH);
                break;
            case Command.MOVE_SOUTH:
                this.gameGUI.runDungeon(Command.MOVE_SOUTH);
                break;
            case Command.MOVE_WEST:
                this.gameGUI.runDungeon(Command.MOVE_WEST);
                break;
            case Command.MOVE_EAST:
                this.gameGUI.runDungeon(Command.MOVE_EAST);
                break;
            default:
                actionPerformed(e, choice);
        }
    }

    // A continuation of method actionPerformed(ActionEvent e) to meet checkstyle rule
    // MODIFIES: this
    // EFFECTS: Performed actions regarding the name of the button,
    //          corresponding to different commands
    public void actionPerformed(ActionEvent e, String choice) {
        switch (choice) {
            case Command.CHAR_STATUS:
                this.gameGUI.showCharStatus();
                break;
            case Command.INVENTORY_LIST:
                this.gameGUI.showInventory();
                break;
            case Command.HELP:
                this.gameGUI.showCommandList();
                break;
            case Command.SAVE:
                this.gameGUI.saveGame();
                break;
            case Command.LOAD:
                this.gameGUI.loadGame();
                break;
            case Command.EXIT:
                System.exit(0);
                break;
        }
    }
}
