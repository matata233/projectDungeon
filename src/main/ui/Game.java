package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

public class Game {
    public static final String JSON_STORE = "./data/pc.json";
    public static final Random RND = new Random();

    private GameWindow gameWindow;
    private UtilityWindow charStatusWindow;
    private UtilityWindow inventoryWindow;
    private PlayableCharacter pc;
    private String option;
    private boolean gameOver;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    public Game() {
        this.jsonWriter = new JsonWriter(JSON_STORE);
        this.jsonReader = new JsonReader(JSON_STORE);
        this.gameWindow = new GameWindow(this);
        this.gameWindow.createTitleScreen();
        this.gameWindow.createGameScreen();
        this.gameWindow.displayGameScreen(false);

        this.charStatusWindow = new UtilityWindow(this);
        this.charStatusWindow.leftCentreOnScreen();

        this.inventoryWindow = new UtilityWindow(this);
        this.inventoryWindow.rightCentreOnScreen();
    }

    public void reset() {
        this.gameWindow.displayTitleScreen(true);
    }

    public void startGame() {
        this.gameWindow.displayGameScreen(true);
        this.gameOver = false;
        createCharacter();
        runDungeon();
        this.charStatusWindow.displayScreen(true);
        this.inventoryWindow.displayScreen(true);
    }

    private void createCharacter() {
        String playerName;
        playerName = JOptionPane.showInputDialog(
                "Please enter your character Name");
        this.pc = new PlayableCharacter(playerName);
        this.charStatusWindow.createDisplay();
        this.charStatusWindow.updateDisplayText(
                new Command(Command.GET_LOCATION, this.pc).locCommand() + "\n\n"
                        + new Command(Command.CHAR_STATUS, this.pc).chCommand()
        );
        this.inventoryWindow.createDisplayList();
    }

    public void runDungeon() {
        this.gameWindow.getMainTextArea().addMessageToDisplay(
                "Dungeon description to be added\n");
        this.gameWindow.getMainTextArea().addMessageToDisplay(
                "\n");
        this.gameWindow.getMainTextArea().addMessageToDisplay(
                "You try to find a way out of here...\n");
        this.gameWindow.getMainTextArea().addMessageToDisplay(this.pc.sense());
        this.gameWindow.getMainTextArea().addMessageToDisplay(
                "[Tips: click HELP to get a detailed full list for most command buttons!]\n");

    }

    public void loadGame() {
        try {
            this.pc = this.jsonReader.read();
            this.charStatusWindow.createDisplay();
            this.charStatusWindow.updateDisplayText(
                    new Command(Command.GET_LOCATION, this.pc).locCommand() + "\n\n"
                            + new Command(Command.CHAR_STATUS, this.pc).chCommand()
            );
            this.inventoryWindow.createDisplayList();
            this.inventoryWindow.addToDisplayList(this.pc.getInventory());
            JOptionPane.showMessageDialog(this.gameWindow.getWindow(),
                    "Loaded " + this.pc.getName() + " from " + JSON_STORE + "\n");
            this.gameWindow.displayGameScreen(true);
            runDungeon();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this.gameWindow.getWindow(),
                    "Unable to read from file: " + JSON_STORE + "\n");
        }
    }

    // EFFECTS: saves the game to file
    public void saveGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(this.pc);
            jsonWriter.close();
            JOptionPane.showMessageDialog(this.gameWindow.getWindow(),
                    "Saved " + this.pc.getName() + " to " + JSON_STORE + "\n");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this.gameWindow.getWindow(),
                    "Unable to write from file: " + JSON_STORE + "\n");
        }
    }

    public void movePlayer(String moveCommand) {
        this.gameWindow.getMainTextArea().addMessageToDisplay(
                this.pc.move(moveCommand) + "\n");
        randomEvent();
        this.charStatusWindow.updateDisplayText(
                new Command(Command.GET_LOCATION, this.pc).locCommand() + "\n\n"
                        + new Command(Command.CHAR_STATUS, this.pc).chCommand()
        );
        if (isOver()) {
            this.gameWindow.displayGameScreen(false);
            this.inventoryWindow.displayScreen(false);
            this.charStatusWindow.displayScreen(false);
            this.gameWindow.displayTitleScreen(true);
        }
    }

    public void useItem(Item item) {
        this.gameWindow.getMainTextArea().addMessageToDisplay(
                this.pc.use(item) + "\n");
        this.charStatusWindow.updateDisplayText(
                new Command(Command.GET_LOCATION, this.pc).locCommand() + "\n\n"
                        + new Command(Command.CHAR_STATUS, this.pc).chCommand()
        );
    }

    public void equipItem(Item item) {
        this.gameWindow.getMainTextArea().addMessageToDisplay(
                this.pc.equip(item) + "\n");
        this.charStatusWindow.updateDisplayText(
                new Command(Command.GET_LOCATION, this.pc).locCommand() + "\n\n"
                        + new Command(Command.CHAR_STATUS, this.pc).chCommand()
        );
    }

    public void showCharStatus() {
        this.charStatusWindow.displayScreen(true);
    }

    public void showInventory() {
        this.inventoryWindow.displayScreen(true);
    }

    // EFFECTS:  Create random events while user moves
    private void randomEvent() {
        int eventNum = RND.nextInt(10);
        Item item;
        switch (eventNum) {
            case 0:
                JOptionPane.showMessageDialog(this.gameWindow.getWindow(),
                        "You found a potion!" + "\n");
                this.gameWindow.getMainTextArea().addMessageToDisplay("You found a potion!" + "\n");
                item = new Potion("Potion");
                this.pc.add(item);
                this.inventoryWindow.addToDisplayList(item);
                break;
            case 1:
                JOptionPane.showMessageDialog(this.gameWindow.getWindow(),
                        "You found a iron sword!" + "\n");
                this.gameWindow.getMainTextArea().addMessageToDisplay("You found a iron sword!" + "\n");
                item = new Weapon("Iron Sword", 2, 0);
                this.pc.add(item);
                this.inventoryWindow.addToDisplayList(item);
                break;
            case 2:
                JOptionPane.showMessageDialog(this.gameWindow.getWindow(),
                        "You found a rusted sword!" + "\n");
                this.gameWindow.getMainTextArea().addMessageToDisplay("You found a rusted sword!" + "\n");
                item = new Weapon("Rusted Sword", 1, 0);
                this.pc.add(item);
                this.inventoryWindow.addToDisplayList(item);
                break;
            case 3:
            case 4:
            case 5:
                JOptionPane.showMessageDialog(this.gameWindow.getWindow(),
                        "The ground was so wet and you slip down! It hurt so bad." + "\n");
                this.gameWindow.getMainTextArea().addMessageToDisplay(
                        "The ground was so wet and you slip down! It hurt so bad." + "\n");
                this.pc.setHitPoint(this.pc.getHitPoint() - 1);
                break;
        }
    }

    // Is game over?
    // EFFECTS: returns true if game is over, false otherwise
    private boolean isOver() {
        if (this.pc.getPosX() == 2 && this.pc.getPosY() == 2) {
            gameOver = true;
            JOptionPane.showMessageDialog(this.gameWindow.getWindow(), "You found the exit!\n\n" + "Congratulations!");
        } else if (this.pc.getHitPoint() <= 0) {
            gameOver = true;
            JOptionPane.showMessageDialog(this.gameWindow.getWindow(), "You become unconscious...\n");
        }
        return gameOver;
    }

    public PlayableCharacter getPc() {
        return pc;
    }

    /*
     * Play the game in console
     */
    public static void main(String[] args) {
        new Game().reset();
    }

    public void showCommandList() {
        JOptionPane.showMessageDialog(this.gameWindow.getWindow(),
                Command.COMMAND_LIST);
    }
}


