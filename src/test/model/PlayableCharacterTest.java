package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayableCharacterTest {
    PlayableCharacter pc;

    @BeforeEach
    void setUp() {
        this.pc = new PlayableCharacter("test");
    }

    @Test
    void sense() {
        //case: (0,0)
        String testMsg = "";
        testMsg += "There is a wall on your west.\n";
        testMsg += "There is a wall on your north.\n";
        assertEquals(testMsg, this.pc.sense());
        //case: (4,4)
        testMsg = "";
        testMsg += "There is a wall on your east.\n";
        testMsg += "There is a wall on your south.\n";
        this.pc.setPosX(4);
        this.pc.setPosY(4);
        assertEquals(testMsg, this.pc.sense());
        //case: (1,1)
        testMsg = "";
        this.pc.setPosX(1);
        this.pc.setPosY(1);
        assertEquals(testMsg, this.pc.sense());
        //case: pc.hp == 1
        testMsg += "You feel really dizzy and darkness is upon you.\n";
        this.pc.setHitPoint(1);
        assertEquals(testMsg, this.pc.sense());
    }

    @Test
    void add() {
        assertEquals(0, this.pc.inventory.size());
        Item item = new Weapon("Rusted Sword", 1, 0);
        String msg = "You found " + 1 + " " + "Rusted Sword";
        assertEquals(msg, this, pc.add(item));
        assertEquals(1, this.pc.inventory.size());
    }

    @Test
    void equip() {
        Item item1 = new Weapon("Rusted Sword", 1, 0);
        Item item2 = new Potion("Potion");
        Item item3 = new Weapon("Iron Sword", 2, 0);
        String failMsg = "Huh? What am I doing...? Trying to equip the " + item2.getName() + " ?";
        String passMsg = item1.effect();
        this.pc.add(item1);
        this.pc.add(item2);
        this.pc.add(item3);
        assertEquals(3, this.pc.inventory.size());
        assertEquals(0, this.pc.mainHand.size());
        //case: equip a consumable item
        assertEquals(failMsg, this.pc.equip(item2));
        assertEquals(3, this.pc.inventory.size());
        assertEquals(0, this.pc.mainHand.size());
        //case: equip a weapon when main hand is free
        assertEquals(passMsg, this.pc.equip(item1));
        assertEquals(2, this.pc.inventory.size());
        assertEquals(1, this.pc.mainHand.size());
        //case: equip a weapon when there is a weapon on pc's main hand
        passMsg = item3.effect();
        passMsg += "\nYou put your " + item3.name + " back to your backpack.";
        assertEquals(passMsg, this.pc.equip(item3));
        assertEquals(2, this.pc.inventory.size());
        assertEquals(1, this.pc.mainHand.size());
    }

    @Test
    void use() {
        Item item1 = new Weapon("Rusted Sword", 1, 0);
        Item item2 = new Potion("Potion");
        Item item3 = new Potion("Potion");
        String failMsg = "Huh? What am I doing...? Trying to eat the " + item2.getName() + " ?";
        String passMsg = item2.effect();
        this.pc.add(item1);
        this.pc.add(item2);
        this.pc.add(item3);
        assertEquals(3, this.pc.inventory.size());

        //case: consume a non-consumable item
        assertEquals(failMsg, this.pc.use(item1));
        assertEquals(3, this.pc.inventory.size());
        //case: consume a potion when hp is full or >= 5
        assertEquals(passMsg, this.pc.use(item2));
        assertEquals(2, this.pc.inventory.size());
        assertEquals(8, this.pc.getHitPoint());
        //case: consume a potion when hp i less than 5
        this.pc.setHitPoint(4);
        assertEquals(passMsg, this.pc.use(item3));
        assertEquals(1, this.pc.inventory.size());
        assertEquals(7, this.pc.getHitPoint());
    }

    @Test
    void move() {
        String msg = "You move onward.";
        String colliedMsg = "You tried to walk into a wall. What's wrong with me?";
        assertEquals(0, this.pc.getPosX());
        assertEquals(0, this.pc.getPosY());
        //case: move north
        assertEquals(colliedMsg, this.pc.move("w"));
        assertEquals(0, this.pc.getPosX());
        assertEquals(0, this.pc.getPosY());
        //case: move south
        assertEquals(msg, this.pc.move("s"));
        assertEquals(0, this.pc.getPosX());
        assertEquals(1, this.pc.getPosY());
        //case: move west
        assertEquals(colliedMsg, this.pc.move("a"));
        assertEquals(0, this.pc.getPosX());
        assertEquals(0, this.pc.getPosY());
        //case: move east
        assertEquals(msg, this.pc.move("d"));
        assertEquals(1, this.pc.getPosX());
        assertEquals(0, this.pc.getPosY());
        //case: no input
        msg = "You seems unable to decide where to go. You take your time to think about it.";
        assertEquals(msg, this.pc.move(""));
        assertEquals(1, this.pc.getPosX());
        assertEquals(0, this.pc.getPosY());
    }

    @Test
    void isCollied() {
        this.pc.posX = 5;
        assertTrue(this.pc.isCollied());
        this.pc.posY = 10;
        assertTrue(this.pc.isCollied());
        this.pc.posX = -1;
        assertTrue(this.pc.isCollied());
        this.pc.posY = -10;
        assertTrue(this.pc.isCollied());
        this.pc.posX = 2;
        this.pc.posY = 4;
        assertFalse(this.pc.isCollied());
    }
}