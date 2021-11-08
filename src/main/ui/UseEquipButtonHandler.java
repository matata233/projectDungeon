package ui;

import model.Command;
import model.Item;
import model.Potion;
import model.Weapon;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UseEquipButtonHandler implements ActionListener {
    Game game;
    GameButton gameButton;
    GameDisplayList gameDisplayList;

    public UseEquipButtonHandler(Game game, GameDisplayList gameDisplayList, GameButton gameButton) {
        this.game = game;
        this.gameDisplayList = gameDisplayList;
        this.gameButton = gameButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String choice = e.getActionCommand();
        int index = this.gameDisplayList.getDisplayList().getSelectedIndex();
        int size = this.gameDisplayList.getListModel().getSize();
        if (size == 0) { //Nobody's left, disable firing.
            this.gameButton.getButton().setEnabled(false);
        } else { //Select an index.
            Object selectedItem = this.gameDisplayList.getDisplayList().getSelectedValue();
            if (selectedItem instanceof Potion) {
                this.game.useItem((Item) selectedItem);
                this.gameDisplayList.getListModel().remove(index);
            } else if (selectedItem instanceof Weapon) {
                if (!this.game.getPc().getMainHand().isEmpty()) {
                    Item ogItem = this.game.getPc().getMainHand().get(0);
                    this.gameDisplayList.getListModel().addElement(ogItem);
                }
                this.game.equipItem((Item) selectedItem);
                this.gameDisplayList.getListModel().remove(index);
            }
        }
    }
}