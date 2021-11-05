package ui;

import model.PlayableCharacter;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.io.IOException;
import java.util.Random;

public class Game {
    public static final String JSON_STORE = "./data/pc.json";
    public static final Random RND = new Random();

    private GameWindow gameWindow;
    private PlayableCharacter pc;
    private String option;
    private boolean gameOver;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    public Game() {
        this.gameWindow = new GameWindow(this);
        this.jsonWriter = new JsonWriter(JSON_STORE);
        this.jsonReader = new JsonReader(JSON_STORE);
        this.gameWindow.createTitleScreen();
        this.gameWindow.createGameScreen();
        this.gameWindow.displayGameScreen(false);
    }

    public void reset() {
        this.gameWindow.displayTitleScreen(true);
    }

    public void startGame() {
        this.gameWindow.displayGameScreen(true);
        this.gameOver = false;
        createCharacter();
        runDungeon();
    }

    private void createCharacter() {
        String playerName = null;
        playerName = JOptionPane.showInputDialog(
                "Please enter your character Name");
        this.pc = new PlayableCharacter(playerName);
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
                "[Tips: click HELP to get a detailed full list for all command buttons!]\n");

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

    public void movePlayer(String moveCommand) {
        this.gameWindow.getMainTextArea().addMessageToDisplay(
                this.pc.move(moveCommand) + "\n");
    }

    /*
     * Play the game in console
     */
    public static void main(String[] args) {
        new Game().reset();
    }
}


