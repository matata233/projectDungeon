package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/*
 * Represents the main game application.
 */
public class GameApp {
    private static final String JSON_STORE = "./data/pc.json";
    public static final int DUNGEON_SIZE = 5;
    public static final Random RND = new Random();

    private List<List> dungeon;
    //private int level; //tbd
    private PlayableCharacter pc;
    //private NonPlayableCharacter npc; //tbd
    private String option;
    private boolean gameOver;
    private final Scanner sc;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS:  Constructs a dungeon Game with a 5x5 dungeon
    public GameApp() {
        this.dungeon = new ArrayList<>(DUNGEON_SIZE);
        for (List<String> colDungeon : this.dungeon) {
            this.dungeon.add(new ArrayList<>(DUNGEON_SIZE));
        }
        //this.level = 1;
        this.gameOver = false;
        this.sc = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS:  Constructs the main menu of the game
    private void mainMenu() {
        System.out.println("Welcome to Dungeon adventure!");
        System.out.println("Please select one of the following option: ");
        System.out.println("1. Start a new game");
        System.out.println("2. Continue from previous game");
        System.out.println("0. Exit");
        System.out.println();
        System.out.print("> ");
    }

    // Sets / resets the game
    // REQUIRES: User input must be an integer
    // MODIFIES: this
    // EFFECTS:  Return to the main game menu, reset the game status to the beginning
    public void reset() {
        mainMenu();
        this.sc.reset();
        this.option = this.sc.next();
        switch (option) {
            case "1":
                this.gameOver = false;
                createCharacter();
                runDungeon();
                break;
            case "2":
                try {
                    loadGame();
                    runDungeon();
                } catch (IOException e) {
                    System.out.println("Unable to read from file: " + JSON_STORE);
                    System.out.println("Returning to the main menu..." + "\n");
                    reset();
                }
                break;
            default:
                this.sc.close();
                System.out.println("Thanks for playing!");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads previous character from file
    private void loadGame() throws IOException {
        this.pc = jsonReader.read();
        System.out.println("Loaded " + this.pc.getName() + " from " + JSON_STORE);
        System.out.println();
        runDungeon();
    }

    // EFFECTS: saves the game to file
    private void saveGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(this.pc);
            jsonWriter.close();
            System.out.println("Saved " + this.pc.getName() + " to " + JSON_STORE);
            System.out.println();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS:  Constructs a playable character with given name from user
    private void createCharacter() {
        System.out.println("Please enter your character's name ");
        Scanner sc = new Scanner(System.in);
        System.out.print("> ");
        this.pc = new PlayableCharacter(sc.nextLine());
    }

    // MODIFIES: this
    // EFFECTS:  Run the game
    private void runDungeon() {
        System.out.println("Dungeon description to be added");
        String userInput = "";
        while (!gameOver) {
            System.out.println();
            System.out.println("You try to find a way out of here...");
            System.out.println(this.pc.sense());
            System.out.println("[Tips: enter /help to get a full command list!]");
            System.out.print("> ");
            this.sc.reset();
            userInput = sc.next().toLowerCase();
            Command userCommand = new Command(userInput, this.pc);
            if (userCommand.isMoveCommand()) {
                System.out.println(userCommand.moveCommand());
                randomEvent();
                isOver();
            } else {
                executeCommand(userCommand);
            }
        }
        reset();
    }

    @SuppressWarnings("methodlength")
    // MODIFIES: this
    // EFFECTS:  Execute given command from user
    public void executeCommand(Command userCommand) {
        switch (userCommand.getCommand()) {
            case Command.HELP: //get a full command list
                System.out.println(Command.COMMAND_LIST);
                break;
            case Command.CHAR_STATUS:
                //print out the status of PC
                System.out.println(userCommand.chCommand());
                break;
            case Command.INVENTORY_LIST:
                //print out a list of pc's inventory
                System.out.println(userCommand.itemListCommand());
                break;
            case Command.GET_LOCATION:
                //print out the current location of pc
                System.out.println(userCommand.locCommand());
                break;
            case Command.EQUIP:
            case Command.USE:
                //equip an item
                //use an item
                System.out.println(itemCommand(userCommand));
                break;
            case Command.SAVE:
                //save the current game
                saveGame();
                break;
            case Command.LOAD:
                //load the previous game
                try {
                    loadGame();
                } catch (IOException e) {
                    System.out.println("Unable to read from file: " + JSON_STORE + "\n");
                }
                break;
            default:
                System.out.println("You do nothing...");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS:  A helper method for equip and use command with given item name from user
    private String itemCommand(Command command) {
        String msg = "";
        System.out.println("You try to use/equip...");
        System.out.println("[Tips: enter the consumable item/gear name you want to use/equip.]");
        System.out.println();
        System.out.print("> ");
        this.sc.reset();
        sc.nextLine();
        String userInput = sc.nextLine().toLowerCase();
        Command itemName = new Command(userInput, this.pc);
        if (command.getCommand().equalsIgnoreCase(Command.EQUIP)) {
            msg = itemName.eqCommand();
        } else if (command.getCommand().equalsIgnoreCase(Command.USE)) {
            msg = itemName.useCommand();
        } else {
            msg = "You can't decide what to do...";
        }
        return msg;
    }

    // EFFECTS:  Create random events while user moves
    private void randomEvent() {
        int eventNum = RND.nextInt(10);
        switch (eventNum) {
            case 0:
                System.out.println("You found a potion!");
                this.pc.add(new Potion("Potion"));
                break;
            case 1:
                System.out.println("You found a iron sword!");
                this.pc.add(new Weapon("Iron Sword", 2, 0));
                break;
            case 2:
                System.out.println("You found a rusted sword!");
                this.pc.add(new Weapon("Rusted Sword", 1, 0));
                break;
            case 3:
            case 4:
            case 5:
                System.out.println("The ground was so wet and you slip down! It hurt so bad.");
                this.pc.setHitPoint(this.pc.getHitPoint() - 1);
                break;
        }
    }

    // Is game over?
    // EFFECTS: returns true if game is over, false otherwise
    private boolean isOver() {
        if (this.pc.getPosX() == 2 && this.pc.getPosY() == 2) {
            gameOver = true;
            System.out.println("You found the exit!\n");
        } else if (this.pc.getHitPoint() <= 0) {
            gameOver = true;
            System.out.println("You become unconscious...\n");
        }
        return gameOver;
    }
}
