package model;

import org.json.JSONObject;
import persistence.Writable;

/*
 * Represents an abstract class of item.
 */
public abstract class Item implements Writable {
    protected String name;
    protected int amount;

    // EFFECTS: Create an item with given name and amount
    Item(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    // EFFECTS: Create a system message of the effect of the item
    protected abstract String effect();


    public String getName() {
        return name;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", this.name);
        json.put("amount", this.amount);
        return json;
    }

    @Override
    public String toString() {
        return this.name + " x1 \n";
    }
}
