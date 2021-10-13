package model;

/*
 * Represents a command from user input.
 */
public class Command {
    public static final String COMMAND_LIST
            = "w - move north\n"
            + "s - move south\n"
            + "a - move west\n"
            + "d - move east\n"
            + "/ch - view your character status\n"
            + "/i - view your inventory\n"
            + "/loc - check your current location"
            + "/eq - equip a weapon/armor\n"
            + "/use - use a consumable item";

    private String command;
    private PlayableCharacter playableCharacter;

    // Constructs a command
    // EFFECTS: Store the user input, and a pointer to the player's character
    public Command(String userInput, PlayableCharacter pc) {
        this.command = userInput;
        this.playableCharacter = pc;
    }

    // Check if the user input is a move command
    // EFFECTS: Return true if it is 'w', 's', 'd', 's'
    //          Otherwise false
    public boolean isMoveCommand() {
        if (this.command.length() == 1 && this.command.charAt(0) == 'w'
                || this.command.charAt(0) == 's'
                || this.command.charAt(0) == 'd'
                || this.command.charAt(0) == 'a') {
            return true;
        }
        return false;
    }

    // Initiate a move command
    // MODIFIES: this
    // EFFECTS: Make the player move and return correspond message.
    public String moveCommand() {
        return this.playableCharacter.move(this.command);
    }

    // Initiate an equip command
    // MODIFIES: this
    // EFFECTS: Make the player equip an item and return correspond message.
    public String eqCommand() {
        for (Item item : this.playableCharacter.inventory) {
            if (item.getName().equalsIgnoreCase(this.command)) {
                return this.playableCharacter.equip(item);
            }
        }
        return this.command + " not found...";
    }

    // Initiate a use command
    // MODIFIES: this
    // EFFECTS: Make the player use an item and return correspond message.
    public String useCommand() {
        for (Item item : this.playableCharacter.inventory) {
            if (item.getName().equalsIgnoreCase(this.command)) {
                return this.playableCharacter.use(item);
            }
        }
        return this.command + " not found...";
    }

    // Initiate a command that display the current status of the player
    // EFFECTS: Return the current status of the player
    public String chCommand() {
        String msg = "";
        msg += "Name:" + this.playableCharacter.name + "\n";
        msg += "Attack: " + this.playableCharacter.attack + "\n";
        msg += "Defence: " + this.playableCharacter.defence + "\n";
        msg += "Current HP " + "/" + " Max HP: "
                + this.playableCharacter.hitPoint + "/"
                + PlayableCharacter.MAX_HITPOINT + "\n";
        msg += "Equipped gear: ";
        if (this.playableCharacter.mainHand.isEmpty()) {
            msg += "none";
        } else {
            Weapon weapon = (Weapon) this.playableCharacter.mainHand.get(0);
            msg += "\n" + weapon.name
                    + " (Bonus attack: +" + weapon.attack;
        }
        return msg;
    }

    // Initiate a command that display all the current item from the player's backpack
    // EFFECTS: Return a list of item
    public String itemListCommand() {
        String msg = "";
        for (Item item : this.playableCharacter.inventory) {
            msg += item.name + " x1 \n";
        }
        if (msg.isEmpty()) {
            msg = "You don't have any item in your backpack...";
        }
        return msg;
    }

    // Initiate a command that display the player's current location in [X,Y].
    // EFFECTS: Return a string of the current location
    public String locCommand() {
        return "You are at [" + this.playableCharacter.posX
                + ", " + this.playableCharacter.posY + "]";
    }

    public String getCommand() {
        return this.command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
