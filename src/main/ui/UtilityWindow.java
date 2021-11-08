package ui;

import model.Command;
import model.Item;
import model.Potion;
import model.Weapon;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;

public class UtilityWindow {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 600;

    private Game game;
    private JFrame utilityWindow;
    private JPanel displayContent;
    private JScrollPane displayContentWithScroll;
    private JTextArea displayTextArea;
    private GameDisplayList displayList;
    private GameButton equipButton;
    private GameButton useButton;


    public UtilityWindow(Game game) {
        this.game = game;
        this.utilityWindow = new JFrame();
        this.utilityWindow.setSize(this.WIDTH, this.HEIGHT);
        this.utilityWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.utilityWindow.getContentPane().setBackground(Color.BLACK);
        this.utilityWindow.setLayout(null);
        this.utilityWindow.setVisible(false);
    }

    // Centres frame on desktop
    // modifies: this
    // effects:  location of frame is set so frame is centred on desktop
    public void leftCentreOnScreen() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        this.utilityWindow.setLocation(
                (screen.width - GameWindow.WIDTH) / 2 - this.utilityWindow.getWidth(),
                (screen.height - this.utilityWindow.getHeight()) / 2);
    }

    public void rightCentreOnScreen() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        this.utilityWindow.setLocation(
                (screen.width - GameWindow.WIDTH) / 2 + this.utilityWindow.getWidth() * 2,
                (screen.height - this.utilityWindow.getHeight()) / 2);
    }

    public void displayScreen(boolean isVisible) {
        this.utilityWindow.setVisible(isVisible);
    }

    public void createDisplay() {
        this.displayContent = new JPanel();
        this.displayContent.setBounds(0, 0, this.WIDTH, this.HEIGHT);
        this.displayContent.setBackground(Color.BLUE);

        this.displayTextArea = new JTextArea();
        this.displayTextArea.setBackground(Color.BLACK);
        this.displayTextArea.setForeground(Color.WHITE);
        this.displayTextArea.setFont(GameWindow.BASE_FONT);
        this.displayTextArea.setLineWrap(true);
        this.displayTextArea.setEditable(false);
        this.displayTextArea.setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));
        this.displayTextArea.setFont(GameWindow.BASE_FONT);
        this.displayContent.add(displayTextArea);
        this.utilityWindow.add(displayContent);
    }

    public void updateDisplayText(String msg) {
        this.displayTextArea.setText(msg);
    }

    public void createDisplayList() {
        this.displayList = new GameDisplayList();
//        this.displayList.addItemToList(new Weapon("test1", 1, 0));
//        this.displayList.addItemToList(new Weapon("test2", 1, 0));
//        this.displayList.addItemToList(new Potion("test potion"));
        this.displayContentWithScroll = new JScrollPane(this.displayList.getDisplayList());
        this.displayContentWithScroll.setBounds(0, 0, this.WIDTH, this.HEIGHT - 100);
        this.displayContentWithScroll.setBackground(Color.BLUE);

        this.displayContent = new JPanel();
        this.displayContent.setBounds(0, this.HEIGHT - 100, this.WIDTH, 100);
        this.displayContent.setBackground(Color.BLUE);

        this.equipButton = new GameButton(Command.EQUIP);
        this.useButton = new GameButton(Command.USE);
        this.equipButton.getButton().addActionListener(
                new UseEquipButtonHandler(this.game, this.displayList, this.equipButton));
        this.useButton.getButton().addActionListener(
                new UseEquipButtonHandler(this.game, this.displayList, this.useButton));

        this.displayList.getDisplayList().addListSelectionListener(
                new GameListSelectionHandler(this.displayList, useButton, equipButton));


        this.displayContent.add(this.equipButton.getButton());
        this.displayContent.add(this.useButton.getButton());

        this.utilityWindow.add(this.displayContentWithScroll);
        this.utilityWindow.add(this.displayContent);
        this.utilityWindow.setVisible(true);
    }
}
