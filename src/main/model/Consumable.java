package model;

/*
 * Represents an abstract class of consumable item.
 */
public abstract class Consumable extends Item {
    // Create a consumable item with quantity 1 and given name
    Consumable(String name) {
        super(name, 1);
    }

    // Return a system message to player when using an item
    // EFFECTS: Return a system message to player
    protected String effect() {
        return "You used the " + this.name + "\n";
    }
}
