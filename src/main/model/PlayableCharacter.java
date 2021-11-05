package model;

import java.util.List;

/*
 * Represents a playable character.
 */
public class PlayableCharacter extends Character {
    public static final int MAX_HITPOINT = 8;

    // Constructs a playable character
    // EFFECTS: a playable character with given name
    public PlayableCharacter(String name) {
        super.name = name;
    }

    // Constructs a playable character with given data
    // EFFECTS: a playable character with given data
    public PlayableCharacter(String name, int atk, int def, int hp, int posX, int posY,
                             List<Item> inventory, List<Item> mainHand) {
        this.inventory = inventory;
        this.mainHand = mainHand;
        this.name = name;
        this.attack = atk;
        this.defence = def;
        this.hitPoint = hp;
        this.posX = posX;
        this.posY = posY;
    }

    // Hit a character (TBD)
    // MODIFIES: otherCharacter
    // EFFECTS: If this character successfully hit otherCharacter,
    //          reduce otherCharacter's hitPoint
    //          return "You hit [otherCharacter.name]. [otherCharacter.name] took x damage."
    //          otherwise return "You have missed."
//    public String hit(Character otherCharacter) {
//        return "";//stub
//    }

    // EFFECTS: Displace the environment based on the current location
    public String sense() {
        String msg = "";
        if (this.getHitPoint() == 1) {
            msg += "You feel really dizzy and darkness is upon you.\n";
        }
        if (this.posX == 0) {
            msg += "There is a wall on your west.\n";
        }
        if (this.posX == 4) {
            msg += "There is a wall on your east.\n";
        }
        if (this.posY == 0) {
            msg += "There is a wall on your north.\n";
        }
        if (this.posY == 4) {
            msg += "There is a wall on your south.\n";
        }
        return msg;
    }

    // Add an item to the inventory
    // MODIFIES: this
    // EFFECTS: Add the item to the Inventory and return "You found [amount] [item]."
    public String add(Item item) {
        String msg = "You found " + item.amount + " " + item.getName();
        this.inventory.add(item);
        return msg;
    }

    // Equip a gear
    // MODIFIES: this
    // EFFECTS: Equip the gear from the Inventory and return "You equipped the [item]."
    //          If the item is not a gear, return a message.
    public String equip(Item item) {
        String msg = item.effect();
        if (!(item instanceof Gear)) {
            msg = "Huh? What am I doing...? Trying to equip the " + item.getName() + " ?";
        } else {
            //if (item instanceof Weapon) {
            if (!this.mainHand.isEmpty()) {
                Item ogItem = this.mainHand.remove(0);
                this.inventory.add(ogItem);
                msg += "\nYou put your " + ogItem.name + " back to your backpack.";
                //    }
            }
            this.inventory.remove(item);
            this.mainHand.add(item);
        }
        return msg;
    }

    // Use a consumable item
    // MODIFIES: this
    // EFFECTS: Use a consumable item from the Inventory and return "You use the [item]."
    //          If the item is not a consumable, return a message.
    public String use(Item item) {
        String msg = item.effect();
        if (!(item instanceof Consumable)) {
            msg = "Huh? What am I doing...? Trying to eat the " + item.getName() + " ?";
        } else {
            this.inventory.remove(item);
            msg = item.effect();
            //if (item instanceof Potion) {
            this.setHitPoint(this.hitPoint += 3);
            //}
        }
        return msg;
    }

    // The character moves a single unit
    // MODIFIES: this
    // EFFECTS: The character moves a single unit to indicated direction.
    //          If not moving out of bound, return "You moved onward."
    //          Otherwise, return "You tried to walk  into a wall. What's wrong with me?"
    public String move(String userInput) {
        String msg = "You move onward.";
        switch (userInput) {
            case Command.MOVE_NORTH:
                this.posY -= 1;
                break;
            case Command.MOVE_SOUTH:
                this.posY += 1;
                break;
            case Command.MOVE_WEST:
                this.posX -= 1;
                break;
            case Command.MOVE_EAST:
                this.posX += 1;
                break;
            default:
                msg = "You seems unable to decide where to go. You take your time to think about it.";
        }
        if (isCollied()) {
            msg = "You tried to walk into a wall. What's wrong with me?";
        }
        return msg;
    }

    // Determines if this Character has collided with the boundary of the dungeon
    // MODIFIES: this
    // EFFECTS:  returns true if this character has collided with the wall and restore the boundary of posX or posY
    //           false otherwise
    protected boolean isCollied() {
        if (this.posX > 4) {
            this.posX = 4;
        } else if (this.posX < 0) {
            this.posX = 0;
        } else if (this.posY > 4) {
            this.posY = 4;
        } else if (this.posY < 0) {
            this.posY = 0;
        } else {
            return false;
        }
        return true;
    }

    // MODIFIES: this
    // EFFECTS:  Update current hp, if hp > maximum hp the player can have, set to maximum hp
    @Override
    public void setHitPoint(int hitPoint) {
        super.setHitPoint(hitPoint);
        if (this.hitPoint > MAX_HITPOINT) {
            this.hitPoint = MAX_HITPOINT;
        }
    }
}
