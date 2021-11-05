package ui;

import model.Command;

import javax.swing.*;
import java.awt.*;

public class GameWindow {
    public static final Font TITLE_FONT = new Font("Times New Roman", Font.PLAIN, 50);
    public static final Font BASE_FONT = new Font("Times New Roman", Font.PLAIN, 20);
    public static final Font BUTTON_FONT = new Font("Times New Roman", Font.BOLD, 25);
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private Game game;

    private JFrame window;
    private Container mainDisplay;

    private GameDisplayPanel titlePanel;
    private GameButtonPanel titleButtonPanel;

    private GameDisplayPanel mainTextPanel;
    private GameButtonPanel mainButtonPanel;
    private GameDisplayTextArea mainTextArea;

    private TitleChoiceHandler titleChoiceHandler;
    private MoveChoiceHandler moveChoiceHandler;

    public GameWindow(Game game) {
        this.game = game;
        this.window = new JFrame();
        this.window.setSize(this.WIDTH, this.HEIGHT);
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.getContentPane().setBackground(Color.BLACK);
        this.window.setLayout(null);
        this.window.setVisible(true);

        this.mainDisplay = this.window.getContentPane();
        this.titleChoiceHandler = new TitleChoiceHandler(this.game);
        this.moveChoiceHandler = new MoveChoiceHandler(this.game);

    }

    public void createTitleScreen() {
        this.titlePanel = new GameDisplayPanel("DUNGEON ADVENTURE", this.TITLE_FONT);
        this.titleButtonPanel = new GameButtonPanel(Command.NEW_GAME, Command.LOAD, Command.EXIT, null);
        this.titleButtonPanel.getChoiceButton1().getButton().addActionListener(
                this.titleChoiceHandler);
        this.titleButtonPanel.getChoiceButton2().getButton().addActionListener(
                this.titleChoiceHandler);
        this.titleButtonPanel.getChoiceButton3().getButton().addActionListener(
                this.titleChoiceHandler);
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
                this.moveChoiceHandler);
        this.mainButtonPanel.getChoiceButton2().getButton().addActionListener(
                this.moveChoiceHandler);
        this.mainButtonPanel.getChoiceButton3().getButton().addActionListener(
                this.moveChoiceHandler);
        this.mainButtonPanel.getChoiceButton4().getButton().addActionListener(
                this.moveChoiceHandler);


        this.mainTextPanel.getDisplayPanel().add(this.mainTextArea.getDisplayScrollPane());
        this.mainDisplay.add(this.mainTextPanel.getDisplayPanel());
        this.mainDisplay.add(this.mainButtonPanel.getButtonPanel());
    }

    public void displayGameScreen(boolean isDisplay) {
        this.mainTextPanel.getDisplayPanel().setVisible(isDisplay);
        this.mainButtonPanel.getButtonPanel().setVisible(isDisplay);
        this.displayTitleScreen(false);
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
