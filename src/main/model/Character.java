package model;

import java.util.ArrayList;
import java.util.List;

/*
 * Represents an abstract class of character.
 */
public abstract class Character {
    protected List<Item> inventory;
    protected List<Item> mainHand;
    protected String name;
    protected int attack;
    protected int defence;
    protected int hitPoint;
    protected int posX;
    protected int posY;

    // EFFECTS: Create a character is positioned at coordinates (0, 0).
    //          Initialized with a name "nameless", ATK=1 DEF=0 HP=8 by default.
    Character() {
        this.inventory = new ArrayList<>();
        this.mainHand = new ArrayList<>();
        this.name = "nameless";
        this.attack = 1;
        this.defence = 0;
        this.hitPoint = 8;
        this.posX = 0;
        this.posY = 0;
    }

    // Hit a character (TBD)
    // MODIFIES: otherCharacter
    // EFFECTS: If this character successfully hit otherCharacter,
    //          reduce otherCharacter's hitPoint
    //          return "You hit [otherCharacter.name]. [otherCharacter.name] took x damage."
    //          otherwise return "You have missed."
//    public String hit(Character otherCharacter) {
//        return "";//stub
//    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public void setHitPoint(int hitPoint) {
        this.hitPoint = hitPoint;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;

    }

    public String getName() {
        return name;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefence() {
        return defence;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
}
