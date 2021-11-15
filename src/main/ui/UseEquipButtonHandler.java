package ui;

import model.Item;
import model.Potion;
import model.Weapon;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UseEquipButtonHandler implements ActionListener {
    GameGUI gameGUI;
    GameButton gameButton;
    GameDisplayList gameDisplayList;

    public UseEquipButtonHandler(GameGUI gameGUI, GameDisplayList gameDisplayList, GameButton gameButton) {
        this.gameGUI = gameGUI;
        this.gameDisplayList = gameDisplayList;
        this.gameButton = gameButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int index = this.gameDisplayList.getDisplayList().getSelectedIndex();
        int size = this.gameDisplayList.getListModel().getSize();
        if (size == 0) { //inventory is empty. disable equip/use button
            this.gameButton.getButton().setEnabled(false);
        } else { //Select an index.
            Object selectedItem = this.gameDisplayList.getDisplayList().getSelectedValue();
            if (selectedItem instanceof Potion) {
                this.gameGUI.useItem((Item) selectedItem);
                this.gameDisplayList.getListModel().remove(index);
            } else if (selectedItem instanceof Weapon) {
                //if main-hand already have a weapon, swap the weapon with the one to be equipped
                if (!this.gameGUI.getPc().getMainHand().isEmpty()) {
                    Item ogItem = this.gameGUI.getPc().getMainHand().get(0);
                    this.gameDisplayList.getListModel().addElement(ogItem);
                }
                this.gameGUI.equipItem((Item) selectedItem);
                this.gameDisplayList.getListModel().remove(index);
            }
        }
    }
}