package ui;

import model.Command;

import javax.swing.*;
import java.awt.*;

public class GameWindow {
    public static final Font TITLE_FONT = new Font("Times New Roman", Font.PLAIN, 50);
    public static final Font BASE_FONT = new Font("Times New Roman", Font.PLAIN, 20);
    public static final Font BUTTON_FONT = new Font("Times New Roman", Font.BOLD, 25);
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private Game game;

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

    public GameWindow(Game game) {
        this.game = game;
        this.window = new JFrame();
        this.window.setSize(this.WIDTH, this.HEIGHT);
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.getContentPane().setBackground(Color.BLACK);
        this.window.setLayout(null);
        this.window.setVisible(true);

        this.mainDisplay = this.window.getContentPane();
        this.titleButtonHandler = new TitleButtonHandler(this.game);
        this.gameButtonHandler = new GameButtonHandler(this.game);
        this.centreOnScreen();
    }

    // Centres frame on desktop
    // modifies: this
    // effects:  location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        this.window.setLocation((screen.width - this.window.getWidth()) / 2, (screen.height - this.window.getHeight()) / 2);
    }

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

    public void displayTitleScreen(boolean isDisplay) {
        this.titlePanel.getDisplayPanel().setVisible(isDisplay);
        this.titleButtonPanel.getButtonPanel().setVisible(isDisplay);
    }

    public void createGameScreen() {
        displayTitleScreen(false);
        this.mainTextPanel = new GameDisplayPanel("", this.BASE_FONT);
        this.mainTextArea = new GameDisplayTextArea("Welcome to Dungeon Adventure!\n");

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

        this.utilityButtonPanel = new GameButtonPanel(6, 800, 100);
        this.utilityButtonPanel.getGameButtonFromList(0).getButton().setText(Command.CHAR_STATUS);
        this.utilityButtonPanel.getGameButtonFromList(1).getButton().setText(Command.INVENTORY_LIST);
//        this.utilityButtonPanel.getGameButtonFromList(2).getButton().setText(Command.GET_LOCATION);
//        this.utilityButtonPanel.getGameButtonFromList(3).getButton().setText(Command.USE);
//        this.utilityButtonPanel.getGameButtonFromList(4).getButton().setText(Command.EQUIP);
        this.utilityButtonPanel.getGameButtonFromList(2).getButton().setText(Command.SAVE);
        this.utilityButtonPanel.getGameButtonFromList(3).getButton().setText(Command.LOAD);
        this.utilityButtonPanel.getGameButtonFromList(4).getButton().setText(Command.EXIT);
        this.utilityButtonPanel.getGameButtonFromList(5).getButton().setText(Command.HELP);
        for (GameButton gameButton :
                this.utilityButtonPanel.getUtilityButtonList()) {
            gameButton.getButton().addActionListener(new GameButtonHandler(this.game));
        }
        this.mainTextPanel.getDisplayPanel().add(this.mainTextArea.getDisplayScrollPane());
        this.mainDisplay.add(this.mainTextPanel.getDisplayPanel());
        this.mainDisplay.add(this.mainButtonPanel.getButtonPanel());
        this.mainDisplay.add(this.utilityButtonPanel.getButtonPanel());
    }

    public void displayGameScreen(boolean isDisplay) {
        this.mainButtonPanel.getButtonPanel().setVisible(isDisplay);
        this.mainTextPanel.getDisplayPanel().setVisible(isDisplay);
        this.utilityButtonPanel.getButtonPanel().setVisible(isDisplay);
        this.displayTitleScreen(!isDisplay);
    }

    public GameDisplayTextArea getMainTextArea() {
        return mainTextArea;
    }

    public GameButtonPanel getTitleButtonPanel() {
        return titleButtonPanel;
    }

    public GameButtonPanel getMainButtonPanel() {
        return mainButtonPanel;
    }

    public JFrame getWindow() {
        return window;
    }
}
