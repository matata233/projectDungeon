package ui;

import model.Command;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MoveChoiceHandler implements ActionListener {
    private Game game;

    public MoveChoiceHandler(Game game) {
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
        }
    }
}
