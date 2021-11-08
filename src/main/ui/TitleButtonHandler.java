package ui;

import model.Command;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class TitleButtonHandler implements ActionListener {
    private Game game;

    public TitleButtonHandler(Game game) {
        this.game = game;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String choice = e.getActionCommand();
        switch (choice) {
            case Command.NEW_GAME:
                this.game.startGame();
                break;
            case Command.LOAD:
                this.game.loadGame();
                break;
            case Command.EXIT:
                System.exit(0);
        }
    }
}
