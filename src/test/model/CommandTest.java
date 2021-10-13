package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandTest {
    Command command;
    PlayableCharacter playableCharacter;

    @BeforeEach
    void setUp() {
        this.playableCharacter = new PlayableCharacter("test");
        this.command = new Command("to be add", playableCharacter);
    }

    @Test
    void isMoveCommand() {
        this.command.setCommand("w");
        assertTrue(this.command.isMoveCommand());
        this.command.setCommand("s");
        assertTrue(this.command.isMoveCommand());
        this.command.setCommand("a");
        assertTrue(this.command.isMoveCommand());
        this.command.setCommand("d");
        assertTrue(this.command.isMoveCommand());
        //case: invalid input
        this.command.setCommand("wsad");
        assertFalse(this.command.isMoveCommand());

    }

    @Test
    void moveCommand() {
        this.playableCharacter.posX = 1;
        this.playableCharacter.posY = 1;
        String msg = "You move onward.";
        //case w
        this.command.setCommand("w");
        assertEquals(msg, this.command.moveCommand());
        assertEquals(1, this.playableCharacter.posX);
        assertEquals(0, this.playableCharacter.posY);

        //case s
        this.command.setCommand("s");
        assertEquals(msg, this.command.moveCommand());
        assertEquals(1, this.playableCharacter.posX);
        assertEquals(1, this.playableCharacter.posY);

        //case a
        this.command.setCommand("a");
        assertEquals(msg, this.command.moveCommand());
        assertEquals(0, this.playableCharacter.posX);
        assertEquals(1, this.playableCharacter.posY);

        //case d
        this.command.setCommand("d");
        assertEquals(msg, this.command.moveCommand());
        assertEquals(1, this.playableCharacter.posX);
        assertEquals(1, this.playableCharacter.posY);

    }

    @Test
    void eqCommand() {
        this.playableCharacter.add(new Potion("Potion"));
        this.playableCharacter.add(new Weapon("rusted sword", 1, 0));
        this.command.setCommand("rusted sword");
        assertEquals(this.playableCharacter.inventory.get(1).effect(),
                this.command.eqCommand());
        this.command.setCommand("potion");
        assertEquals("Huh? What am I doing...? Trying to equip the "
                        + this.playableCharacter.inventory.get(0).getName() + " ?",
                this.command.eqCommand());
        this.command.setCommand("great axe");
        assertEquals(this.command.getCommand() + " not found...",
                this.command.eqCommand());
    }

    @Test
    void useCommand() {
        this.playableCharacter.add(new Potion("Potion"));
        this.playableCharacter.add(new Weapon("rusted sword", 1, 0));
        assertEquals(2, this.playableCharacter.inventory.size());
        //case: drink a consumable successfully
        this.command.setCommand("potion");
        assertEquals(this.playableCharacter.inventory.get(0).effect(),
                this.command.useCommand());
        assertEquals(1, this.playableCharacter.inventory.size());
        //case: unable to eat a non-consumable
        this.command.setCommand("rusted sword");
        assertEquals("Huh? What am I doing...? Trying to eat the "
                        + this.playableCharacter.inventory.get(0).getName() + " ?",
                this.command.useCommand());
        assertEquals(1, this.playableCharacter.inventory.size());
        //case: unable to eat an item that doesn't exist
        this.command.setCommand("mushroom");
        assertEquals(this.command.getCommand() + " not found...",
                this.command.useCommand());
        assertEquals(1, this.playableCharacter.inventory.size());
    }

    @Test
    void chCommand() {
        String msg = "";
        msg += "Name:" + this.playableCharacter.name + "\n";
        msg += "Attack: " + this.playableCharacter.attack + "\n";
        msg += "Defence: " + this.playableCharacter.defence + "\n";
        msg += "Current HP " + "/" + " Max HP: "
                + this.playableCharacter.hitPoint + "/"
                + PlayableCharacter.MAX_HITPOINT + "\n";
        msg += "Equipped gear: ";
        //case: empty main hand
        String testMsg = "none";
        this.command.setCommand("/ch");
        assertEquals(msg + testMsg, this.command.chCommand());
        //case: have a weapon in main hand
        Item item = new Weapon("rusted sword", 1, 0);
        this.playableCharacter.add(item);
        this.playableCharacter.equip(item);
        Weapon weapon = (Weapon) this.playableCharacter.mainHand.get(0);
        testMsg = "\n" + weapon.name
                + " (Bonus attack: +" + weapon.attack + ")";
        assertEquals(msg + testMsg, this.command.chCommand());
    }

    @Test
    void itemListCommand() {
        //case: empty bag
        String testMsg1 = "You don't have any item in your backpack...";
        assertEquals(testMsg1, this.command.itemListCommand());
        //case: 2 items in the bag
        String testMsg2 = "";
        this.playableCharacter.add(new Potion("Potion"));
        this.playableCharacter.add(new Weapon("rusted sword", 1, 0));
        for (Item item : this.playableCharacter.inventory) {
            testMsg2 += item.name + " x1 \n";
        }
        assertEquals(testMsg2, this.command.itemListCommand());
    }

    @Test
    void locCommand() {
        assertEquals("You are at [" + this.playableCharacter.posX
                + ", " + this.playableCharacter.posY + "]", this.command.locCommand());
    }
}