package ui;

import model.Command;

import javax.swing.*;
import java.awt.*;

// Represents a JFrame of the main game window
public class GameWindow {
    public static final Font TITLE_FONT = new Font("Times New Roman", Font.PLAIN, 50);
    public static final Font BASE_FONT = new Font("Times New Roman", Font.PLAIN, 20);
    public static final Font BUTTON_FONT = new Font("Times New Roman", Font.BOLD, 25);
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private GameGUI gameGUI;

    private JFrame window;
    private Container mainDisplay;

    private GameDisplayPanel titlePanel;
    private GameButtonPanel titleButtonPanel;

    private GameDisplayPanel mainTextPanel;
    private GameDisplayTextArea mainTextArea;
    private GameButtonPanel mainButtonPanel;

    private GameButtonPanel utilityButtonPanel;

    private TitleButtonHandler titleButtonHandler;
    private GameButtonHandler gameButtonHandler;

    // EFFECTS: Create a main game window with given GameGUI
    public GameWindow(GameGUI gameGUI) {
        this.gameGUI = gameGUI;
        this.window = new JFrame();
        this.window.setSize(this.WIDTH, this.HEIGHT);
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.getContentPane().setBackground(Color.BLACK);
        this.window.setLayout(null);
        this.window.setVisible(true);

        this.mainDisplay = this.window.getContentPane();
        this.titleButtonHandler = new TitleButtonHandler(this.gameGUI);
        this.gameButtonHandler = new GameButtonHandler(this.gameGUI);
        this.centreOnScreen();
    }

    // Source: https://docs.oracle.com/javase/tutorial/uiswing/components/frame.html
    // Centres frame on desktop
    // MODIFIES: this
    // EFFECTS:  Location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        this.window.setLocationRelativeTo(null);
    }

    // MODIFIES: this
    // EFFECTS:  Create the title screen
    public void createTitleScreen() {
        this.titlePanel = new GameDisplayPanel("DUNGEON ADVENTURE", this.TITLE_FONT);
        this.titleButtonPanel = new GameButtonPanel(Command.NEW_GAME, Command.LOAD, Command.EXIT, null);
        this.titleButtonPanel.getChoiceButton1().getButton().addActionListener(
                this.titleButtonHandler);
        this.titleButtonPanel.getChoiceButton2().getButton().addActionListener(
                this.titleButtonHandler);
        this.titleButtonPanel.getChoiceButton3().getButton().addActionListener(
                this.titleButtonHandler);
        this.titleButtonPanel.getChoiceButton4().getButton().setVisible(false);

        this.mainDisplay.add(this.titlePanel.getDisplayPanel());
        this.mainDisplay.add(this.titleButtonPanel.getButtonPanel());
    }

    // MODIFIES: this
    // EFFECTS:  Display the title screen
    public void displayTitleScreen(boolean isDisplay) {
        this.titlePanel.getDisplayPanel().setVisible(isDisplay);
        this.titleButtonPanel.getButtonPanel().setVisible(isDisplay);
    }

    // MODIFIES: this
    // EFFECTS:  Create the main game screen
    public void createGameScreen() {
        displayTitleScreen(false);
        this.mainTextPanel = new GameDisplayPanel("", this.BASE_FONT);
        this.mainTextArea = new GameDisplayTextArea("Welcome to Dungeon Adventure!\n");
        createMainButtonPanel();
        createUtilityButtonPanel();
        this.mainTextPanel.getDisplayPanel().add(this.mainTextArea.getDisplayScrollPane());
        this.mainDisplay.add(this.mainTextPanel.getDisplayPanel());
        this.mainDisplay.add(this.mainButtonPanel.getButtonPanel());
        this.mainDisplay.add(this.utilityButtonPanel.getButtonPanel());
    }

    // MODIFIES: this
    // EFFECTS:  Create a set of utility buttons in the main game screen
    private void createUtilityButtonPanel() {
        this.utilityButtonPanel = new GameButtonPanel(6, 800, 100);
        this.utilityButtonPanel.getGameButtonFromList(0).getButton().setText(Command.CHAR_STATUS);
        this.utilityButtonPanel.getGameButtonFromList(1).getButton().setText(Command.INVENTORY_LIST);
        this.utilityButtonPanel.getGameButtonFromList(2).getButton().setText(Command.SAVE);
        this.utilityButtonPanel.getGameButtonFromList(3).getButton().setText(Command.LOAD);
        this.utilityButtonPanel.getGameButtonFromList(4).getButton().setText(Command.EXIT);
        this.utilityButtonPanel.getGameButtonFromList(5).getButton().setText(Command.HELP);
        for (GameButton gameButton :
                this.utilityButtonPanel.getUtilityButtonList()) {
            gameButton.getButton().addActionListener(new GameButtonHandler(this.gameGUI));
        }
    }

    // MODIFIES: this
    // EFFECTS:  Create a set of four main movement buttons in the main game screen
    private void createMainButtonPanel() {
        this.mainButtonPanel = new GameButtonPanel(
                Command.MOVE_NORTH, Command.MOVE_SOUTH, Command.MOVE_WEST, Command.MOVE_EAST);
        this.mainButtonPanel.getChoiceButton1().getButton().addActionListener(
                this.gameButtonHandler);
        this.mainButtonPanel.getChoiceButton2().getButton().addActionListener(
                this.gameButtonHandler);
        this.mainButtonPanel.getChoiceButton3().getButton().addActionListener(
                this.gameButtonHandler);
        this.mainButtonPanel.getChoiceButton4().getButton().addActionListener(
                this.gameButtonHandler);
    }

    // MODIFIES: this
    // EFFECTS:  Display the main game screen
    public void displayGameScreen(boolean isDisplay) {
        this.mainButtonPanel.getButtonPanel().setVisible(isDisplay);
        this.mainTextPanel.getDisplayPanel().setVisible(isDisplay);
        this.utilityButtonPanel.getButtonPanel().setVisible(isDisplay);
        this.displayTitleScreen(!isDisplay);
    }

    public GameDisplayTextArea getMainTextArea() {
        return mainTextArea;
    }

    public JFrame getWindow() {
        return window;
    }
}
