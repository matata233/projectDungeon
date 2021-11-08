package ui;

import model.Command;
import model.Item;
import model.PlayableCharacter;
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
            this.pc = jsonReader.read();
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
    }

    public void useItem(Item item) {
        this.gameWindow.getMainTextArea().addMessageToDisplay(
                this.pc.use(item) + "\n");
    }

    public void equipItem(Item item) {
        this.gameWindow.getMainTextArea().addMessageToDisplay(
                this.pc.equip(item) + "\n");
    }

    public void showCharStatus() {
        this.charStatusWindow.displayScreen(true);
    }

    public void showInventory() {
        this.inventoryWindow.displayScreen(true);
    }

    /*
     * Play the game in console
     */
    public static void main(String[] args) {
        new Game().reset();
    }

    public PlayableCharacter getPc() {
        return pc;
    }
}


