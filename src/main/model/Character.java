package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

/*
 * Represents an abstract class of character.
 */
public abstract class Character implements Writable {
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

    protected void setHitPoint(int hitPoint) {
        this.hitPoint = hitPoint;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setPosX(int posX) {
        this.posX = posX;
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

    public String getName() {
        return name;
    }

    public List<Item> getMainHand() {
        return mainHand;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", this.name);
        json.put("attack", this.attack);
        json.put("defence", this.defence);
        json.put("hitPoint", this.hitPoint);
        json.put("posX", this.posX);
        json.put("posY", this.posY);
        json.put("inventory", inventoryToJson());
        json.put("mainHand", mainHandToJson());
        return json;
    }

    // EFFECTS: returns items from inventory in this Character as a JSON array
    private JSONArray inventoryToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Item item : this.inventory) {
            jsonArray.put(item.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns items from mainHand in this Character as a JSON array
    private JSONArray mainHandToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Item item : this.mainHand) {
            jsonArray.put(item.toJson());
        }

        return jsonArray;
    }
}
