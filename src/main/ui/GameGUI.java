package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import static javax.swing.JOptionPane.OK_OPTION;

// Represents the main game application with GUI components
public class GameGUI {
    public static final String JSON_STORE = "./data/pc.json";
    public static final Random RND = new Random();

    private GameWindow gameWindow;
    private UtilityWindow charStatusWindow;
    private UtilityWindow inventoryWindow;
    private PlayableCharacter pc;
    private boolean gameOver;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: Constructs a dungeon Game with a 5x5 dungeon
    public GameGUI() {
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

        this.charStatusWindow.createCharInfoDisplay();
        this.inventoryWindow.createInventoryDisplay();
    }

    // MODIFIES: this
    // EFFECTS:  Return to the main game menu, reset the game status to the beginning
    public void reset() {
        this.gameWindow.displayTitleScreen(true);
    }

    // MODIFIES: this
    // EFFECTS:  Update the display information for all utility display windows
    private void updateDisplayWindow() {
        this.charStatusWindow.updateDisplayText(
                new Command(Command.GET_LOCATION, this.pc).locCommand() + "\n\n"
                        + new Command(Command.CHAR_STATUS, this.pc).chCommand()
        );
        this.inventoryWindow.refreshInventoryDisplay();
    }

    // MODIFIES: this
    // EFFECTS:  Create a new game
    public void startGame() {
        this.gameWindow.displayGameScreen(true);
        this.gameOver = false;
        createCharacter();
        initializeDungeon();
    }

    // MODIFIES: this
    // EFFECTS:  Constructs a playable character with given name from user or a default name
    private void createCharacter() {
        String playerName;
        playerName = JOptionPane.showInputDialog(
                "Please enter your character Name");
        this.pc = new PlayableCharacter(playerName);
        this.inventoryWindow.clearInventoryDisplay();
    }

    // MODIFIES: this
    // EFFECTS:  Display welcome information once the game is started
    public void initializeDungeon() {
        this.gameWindow.getMainTextArea().clearDisplay();
        this.gameWindow.getMainTextArea().addMessageToDisplay(
                "Dungeon description to be added\n");
        this.gameWindow.getMainTextArea().addMessageToDisplay(
                "\n");
        this.gameWindow.getMainTextArea().addMessageToDisplay(
                "You try to find a way out of here...\n");
        this.gameWindow.getMainTextArea().addMessageToDisplay(this.pc.sense());
        this.gameWindow.getMainTextArea().addMessageToDisplay(
                "[Tips: click HELP to get a detailed full list for most command buttons!]\n");
        updateDisplayWindow();
        this.gameOver = false;
    }

    // MODIFIES: this
    // EFFECTS: Loads previous character from file
    public void loadGame() {
        try {
            this.pc = this.jsonReader.read();
            JOptionPane.showMessageDialog(this.gameWindow.getWindow(),
                    "Loaded " + this.pc.getName() + " from " + JSON_STORE + "\n");
            this.gameWindow.displayGameScreen(true);
            this.inventoryWindow.clearInventoryDisplay();
            this.inventoryWindow.addToInventoryDisplay(this.pc.getInventory());
            initializeDungeon();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this.gameWindow.getWindow(),
                    "Unable to read from file: " + JSON_STORE + "\n");
        }
    }

    // EFFECTS: Saves the game to file
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

    // MODIFIES: this
    // EFFECTS: display player movement and random event they encounter
    //          if game is over condition met, end game and return to the title screen
    public void runDungeon(String moveCommand) {
        this.gameWindow.getMainTextArea().addMessageToDisplay(
                this.pc.moveInGUI(moveCommand) + "\n");
        randomEvent();
        updateDisplayWindow();
        if (isOver()) {
            this.gameWindow.displayGameScreen(false);
            this.inventoryWindow.displayScreen(false);
            this.charStatusWindow.displayScreen(false);
            this.gameWindow.getMainTextArea().clearDisplay();
            this.gameWindow.displayTitleScreen(true);
        }
    }

    // MODIFIES: this
    // EFFECTS: display player using an item
    //          and update corresponding display in utility windows
    public void useItem(Item item) {
        this.gameWindow.getMainTextArea().addMessageToDisplay(
                this.pc.use(item) + "\n");
        updateDisplayWindow();
    }

    // MODIFIES: this
    // EFFECTS: display player equipping an item
    //          and update corresponding display in utility windows
    public void equipItem(Item item) {
        this.gameWindow.getMainTextArea().addMessageToDisplay(
                this.pc.equip(item) + "\n");
        updateDisplayWindow();
    }

    // MODIFIES: this
    // EFFECTS: update corresponding display and pop character info window
    public void showCharStatus() {
        updateDisplayWindow();
        this.charStatusWindow.displayScreen(true);
    }

    // MODIFIES: this
    // EFFECTS: update corresponding display and pop inventory info window
    public void showInventory() {
        updateDisplayWindow();
        this.inventoryWindow.displayScreen(true);
    }

    // EFFECTS: display a list of functions regarding all accessible buttons
    public void showCommandList() {
        JOptionPane.showMessageDialog(this.gameWindow.getWindow(),
                Command.COMMAND_LIST);
    }

    // MODIFIES: this
    // EFFECTS:  Create random events while user moves
    private void randomEvent() {
        int eventNum = RND.nextInt(10);
        String msg = "";
        switch (eventNum) {
            case 0:
                msg = randomEventFoundItem(new Potion("Potion"));
                break;
            case 1:
                msg = randomEventFoundItem(new Weapon("Iron Sword", 2, 0));
                break;
            case 2:
                msg = randomEventFoundItem(new Weapon("Rusted Sword", 1, 0));
                break;
            case 5:
                msg = "The ground was so wet and you slip down! It hurt so bad." + "\n";
                this.pc.setHitPoint(this.pc.getHitPoint() - 1);
                break;
            default:
                return;
        }
        JOptionPane.showMessageDialog(this.gameWindow.getWindow(), msg);
        this.gameWindow.getMainTextArea().addMessageToDisplay(msg);
    }

    // MODIFIES: this
    // EFFECTS: Display a text of looting an item and add the item to the inventory,
    //          update display inventory window
    private String randomEventFoundItem(Item item) {
        String msg = "You found a " + item.getName() + "!\n";
        this.pc.add(item);
        this.inventoryWindow.addToInventoryDisplay(item);
        return msg;
    }

    // Is game over?
    // EFFECTS: returns true if game is over, false otherwise
    private boolean isOver() {
        if (this.pc.getPosX() == 2 && this.pc.getPosY() == 2) {
            gameOver = true;
            JOptionPane.showMessageDialog(this.gameWindow.getWindow(), "You found the exit!\n\n" + "Congratulations!");
        } else if (this.pc.getHitPoint() <= 0) {
            gameOver = true;
            try {
                addPopUpImage();
            } catch (IOException e) {
                System.out.println("Unable to read image.");
            }
            JOptionPane.showMessageDialog(
                    this.gameWindow.getWindow(), "You become unconscious...\nReturn to the main menu...");
        }
        return gameOver;
    }

    // Source of the code: https://stackoverflow.com/questions/15258467/java-how-can-i-popup-a-dialog-box-as-only-an-image
    // Source of the image: https://www.ifanr.com/app/1084187
    // EFFECTS: Pop a game over an image when game is ended
    private void addPopUpImage() throws IOException {
        BufferedImage image = ImageIO.read(new File("./data/0-hp.png"));
        JLabel imageLabel = new JLabel(new ImageIcon(image));
        JOptionPane.showConfirmDialog(
                null, imageLabel, "Your hp has dropped to 0...\n", JOptionPane.WARNING_MESSAGE, OK_OPTION);
    }

    public PlayableCharacter getPc() {
        return pc;
    }

    /*
     * Play the game in GUI
     */
    public static void main(String[] args) {
        new GameGUI().reset();
    }


}


