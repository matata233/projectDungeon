package model;

/*
 * Represents a command from user input.
 */
public class Command {
    public static final String HELP = "HELP";
    public static final String MOVE_NORTH = "MOVE North";
    public static final String MOVE_SOUTH = "MOVE South";
    public static final String MOVE_WEST = "MOVE West";
    public static final String MOVE_EAST = "MOVE East";
    public static final String CHAR_STATUS = "Player Status";
    public static final String INVENTORY_LIST = "Inventory";
    public static final String GET_LOCATION = "Location";
    public static final String EQUIP = "Equip";
    public static final String USE = "Use";
    public static final String SAVE = "SAVE";
    public static final String LOAD = "LOAD";
    public static final String NEW_GAME = "NEW GAME";
    public static final String EXIT = "EXIT";

    public static final String COMMAND_LIST
            = MOVE_NORTH + " - move north\n"
            + MOVE_SOUTH + " - move south\n"
            + MOVE_WEST + " - move west\n"
            + MOVE_EAST + " - move east\n"
            + CHAR_STATUS + " - view your character status\n"
            + INVENTORY_LIST + " - view your inventory\n"
            + GET_LOCATION + " - check your current location\n"
            + EQUIP + " - equip a weapon/armor\n"
            + USE + " - use a consumable item\n"
            + SAVE + " - save the current game\n"
            + LOAD + " - load the previous game\n";

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
        if (this.command.length() == 1 && this.command.equalsIgnoreCase("w")
                || this.command.equalsIgnoreCase("s")
                || this.command.equalsIgnoreCase("a")
                || this.command.equalsIgnoreCase("d")) {
            return true;
        }
        return false;
    }

    // Initiate a move command
    // MODIFIES: this
    // EFFECTS: Make the player move and return correspond message.
    public String moveCommandInConsole() {
        return this.playableCharacter.moveInConsole(this.command);
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
        msg += "Name:" + this.playableCharacter.name + "\n\n";
        msg += "Attack: " + this.playableCharacter.attack + "\n\n";
        msg += "Defence: " + this.playableCharacter.defence + "\n\n";
        msg += "Current HP " + "/" + " Max HP: "
                + this.playableCharacter.hitPoint + "/"
                + PlayableCharacter.MAX_HITPOINT + "\n\n";
        msg += "Equipped gear: ";
        if (this.playableCharacter.mainHand.isEmpty()) {
            msg += "none";
        } else {
            Weapon weapon = (Weapon) this.playableCharacter.mainHand.get(0);
            msg += "\n\n" + weapon.name
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
        return "Location: You are at [" + this.playableCharacter.posX
                + ", " + this.playableCharacter.posY + "]";
    }

    public String getCommand() {
        return this.command;
    }

    protected void setCommand(String command) {
        this.command = command;
    }
}
