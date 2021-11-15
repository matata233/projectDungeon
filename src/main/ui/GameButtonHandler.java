package ui;

import model.Command;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameButtonHandler implements ActionListener {
    private Game game;

    public GameButtonHandler(Game game) {
        this.game = game;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String choice = e.getActionCommand();
        switch (choice) {
            case Command.MOVE_NORTH:
                this.game.movePlayer(Command.MOVE_NORTH);
                break;
            case Command.MOVE_SOUTH:
                this.game.movePlayer(Command.MOVE_SOUTH);
                break;
            case Command.MOVE_WEST:
                this.game.movePlayer(Command.MOVE_WEST);
                break;
            case Command.MOVE_EAST:
                this.game.movePlayer(Command.MOVE_EAST);
                break;
            default:
                actionPerformed(e, choice);
        }
    }

    //cont'
    public void actionPerformed(ActionEvent e, String choice) {
        switch (choice) {
            case Command.CHAR_STATUS:
                this.game.showCharStatus();
                break;
            case Command.INVENTORY_LIST:
                this.game.showInventory();
                break;
            case Command.HELP:
                this.game.showCommandList();
                break;
            case Command.SAVE:
                this.game.saveGame();
                break;
            case Command.LOAD:
                this.game.loadGame();
                break;
            case Command.EXIT:
                System.exit(0);
                break;
        }
    }
}
