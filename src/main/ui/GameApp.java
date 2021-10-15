package ui;

import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/*
 * Represents the main console base UI of the game.
 */
public class GameApp {
    public static final int DUNGEON_SIZE = 5;
    public static final Random RND = new Random();

    private List<List> dungeon;
    //private int level; //tbd
    private PlayableCharacter pc;
    //private NonPlayableCharacter npc; //tbd
    private String option;
    private boolean gameOver;
    private final Scanner sc;

    // EFFECTS:  Constructs a dungeon Game with a 5x5 dungeon
    public GameApp() {
        this.dungeon = new ArrayList<>(DUNGEON_SIZE);
        for (List<String> colDungeon : this.dungeon) {
            this.dungeon.add(new ArrayList<>(DUNGEON_SIZE));
        }
        //this.level = 1;
        this.gameOver = false;
        this.sc = new Scanner(System.in);
    }

    // EFFECTS:  Constructs the main menu of the game
    private void mainMenu() {
        System.out.println("Welcome to Dungeon adventure!");
        System.out.println("Please select one of the following option: ");
        System.out.println("1. Start a new game");
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
            default:
                this.sc.close();
                System.out.println("Thanks for playing!");
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

    // MODIFIES: this
    // EFFECTS:  Execute given command from user
    public void executeCommand(Command userCommand) {
        switch (userCommand.getCommand()) {
            case "/help": //get a full command list
                System.out.println(Command.COMMAND_LIST);
                break;
            case "/ch":
                //print out the status of PC
                System.out.println(userCommand.chCommand());
                break;
            case "/i":
                //print out a list of pc's inventory
                System.out.println(userCommand.itemListCommand());
                break;
            case "/loc":
                //print out the current location of pc
                System.out.println(userCommand.locCommand());
                break;
            case "/eq":
            case "/use":
                //equip an item
                //use an item
                System.out.println(itemCommand(userCommand));
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
        if (command.getCommand().charAt(1) == 'e') {
            msg = itemName.eqCommand();
        } else if (command.getCommand().charAt(1) == 'u') {
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
            System.out.println("You found the exit!");
        } else if (this.pc.getHitPoint() <= 0) {
            gameOver = true;
            System.out.println("You become unconscious...");
        }
        return gameOver;
    }
}
