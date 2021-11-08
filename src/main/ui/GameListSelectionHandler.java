package ui;

import model.Potion;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class GameListSelectionHandler implements ListSelectionListener {
    GameDisplayList gameDisplayList;
    GameButton useButton;
    GameButton equipButton;

    public GameListSelectionHandler(GameDisplayList gameDisplayList, GameButton useButton, GameButton equipButton) {
        this.gameDisplayList = gameDisplayList;
        this.useButton = useButton;
        this.equipButton = equipButton;

    }

    @Override
    //This method is required by ListSelectionListener.
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            if (this.gameDisplayList.getDisplayList().getSelectedIndex() == -1) {
                this.equipButton.getButton().setEnabled(false);
                this.useButton.getButton().setEnabled(false);
            } else if (this.gameDisplayList.getDisplayList().getSelectedValue() instanceof Potion) {
                this.useButton.getButton().setEnabled(true);
                this.equipButton.getButton().setEnabled(false);
            } else {
                this.equipButton.getButton().setEnabled(true);
                this.useButton.getButton().setEnabled(false);
            }
        }
    }
}
