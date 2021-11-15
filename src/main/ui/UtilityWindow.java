package ui;

import model.Command;
import model.Item;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UtilityWindow {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 600;

    private GameGUI gameGUI;
    private JFrame utilityWindow;
    private JPanel displayPanel;
    private JScrollPane displayPanelWithScroll;
    private JTextArea displayTextArea;
    private GameDisplayList displayList;
    private GameButton equipButton;
    private GameButton useButton;


    public UtilityWindow(GameGUI gameGUI) {
        this.gameGUI = gameGUI;
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

    public void createCharInfoDisplay() {
        createDisplayPanel(0, 0, this.WIDTH, this.HEIGHT);
        createDisplayTextArea();
        this.displayPanel.add(this.displayTextArea);
        this.utilityWindow.add(this.displayPanel);
    }

    private void createDisplayTextArea() {
        this.displayTextArea = new JTextArea();
        this.displayTextArea.setBackground(Color.BLACK);
        this.displayTextArea.setForeground(Color.WHITE);
        this.displayTextArea.setFont(GameWindow.BASE_FONT);
        this.displayTextArea.setLineWrap(true);
        this.displayTextArea.setEditable(false);
        this.displayTextArea.setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));
        this.displayTextArea.setFont(GameWindow.BASE_FONT);
    }

    private void createDisplayPanel(int x, int y, int width, int height) {
        this.displayPanel = new JPanel();
        this.displayPanel.setBounds(x, y, width, height);
        this.displayPanel.setBackground(Color.BLACK);
    }

    public void updateDisplayText(String msg) {
        this.displayTextArea.setText(msg);
    }

    public void createInventoryDisplay() {
        this.displayList = new GameDisplayList();
        createDisplayPanelWithScroll();
        createDisplayPanel(0, this.HEIGHT - 100, this.WIDTH, 100);
        this.equipButton = new GameButton(Command.EQUIP);
        this.useButton = new GameButton(Command.USE);
        addHandlersToInventoryDisplay();
        this.displayPanel.add(this.equipButton.getButton());
        this.displayPanel.add(this.useButton.getButton());

        this.utilityWindow.add(this.displayPanelWithScroll);
        this.utilityWindow.add(this.displayPanel);
    }

    private void addHandlersToInventoryDisplay() {
        this.equipButton.getButton().addActionListener(
                new UseEquipButtonHandler(this.gameGUI, this.displayList, this.equipButton));
        this.useButton.getButton().addActionListener(
                new UseEquipButtonHandler(this.gameGUI, this.displayList, this.useButton));

        this.displayList.getDisplayList().addListSelectionListener(
                new GameListSelectionHandler(this.displayList, useButton, equipButton));
    }

    private void createDisplayPanelWithScroll() {
        this.displayPanelWithScroll = new JScrollPane(this.displayList.getDisplayList());
        this.displayPanelWithScroll.setBounds(0, 0, this.WIDTH, this.HEIGHT - 100);
        this.displayPanelWithScroll.setBackground(Color.BLACK);
    }

    public void addToInventoryDisplay(Item item) {
        this.displayList.getListModel().addElement(item);
    }

    public void addToInventoryDisplay(List<Item> itemList) {
        for (Item item : itemList) {
            this.displayList.getListModel().addElement(item);
        }
    }

    public void refreshInventoryDisplay() {
        this.displayList.getDisplayList().ensureIndexIsVisible(this.displayList.getListModel().size());
    }

    public void clearInventoryDisplay() {
        this.displayList.getListModel().clear();
    }
}
