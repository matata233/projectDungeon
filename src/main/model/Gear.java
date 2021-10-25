package model;

import org.json.JSONObject;

/*
 * Represents an abstract class of gear.
 */
public abstract class Gear extends Item {
    protected int attack;
    protected int defence;

    // EFFECTS: Create a gear with given name, atk and def with quantity 1
    Gear(String name, int attack, int defence) {
        super(name, 1);
        this.attack = attack;
        this.defence = defence;
    }

    // EFFECTS: Create a system message of when player equip a gear
    protected String effect() {
        return "You equipped the " + this.name + ".\n";
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = super.toJson();
        json.put("attack", this.attack);
        json.put("defence", this.defence);
        return json;
    }
}
