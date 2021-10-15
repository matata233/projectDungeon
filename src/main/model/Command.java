package model;

/*
 * Represents a command from user input.
 */
public class Command {
    public static final String MOVE_NORTH = "w";
    public static final String MOVE_SOUTH = "s";
    public static final String MOVE_WEST = "a";
    public static final String MOVE_EAST = "d";
    public static final String CHAR_STATUS = "/ch";
    public static final String INVENTORY_LIST = "/i";
    public static final String GET_LOCATION = "/loc";
    public static final String EQUIP = "/eq";
    public static final String USE = "/use";

    public static final String COMMAND_LIST
            = MOVE_NORTH + " - move north\n"
            + MOVE_SOUTH + " - move south\n"
            + MOVE_WEST + " - move west\n"
            + MOVE_EAST + " - move east\n"
            + CHAR_STATUS + " - view your character status\n"
            + INVENTORY_LIST + " - view your inventory\n"
            + GET_LOCATION + " - check your current location\n"
            + EQUIP + " - equip a weapon/armor\n"
            + USE + " - use a consumable item\n";

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
        if (this.command.length() == 1 && this.command.equalsIgnoreCase(MOVE_NORTH)
                || this.command.equalsIgnoreCase(MOVE_SOUTH)
                || this.command.equalsIgnoreCase(MOVE_WEST)
                || this.command.equalsIgnoreCase(MOVE_EAST)) {
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
                    + " (Bonus attack: +" + weapon.attack + ")";
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

    protected void setCommand(String command) {
        this.command = command;
    }
}
