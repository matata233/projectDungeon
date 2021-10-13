package model;

/*
 * Represents a potion.
 */
public class Potion extends Consumable {

    // EFFECTS: Create a potion with given name
    public Potion(String name) {
        super(name);
    }

    // EFFECTS: Create a system message of the effect of potion
    @Override
    protected String effect() {
        return super.effect() + "Your wound is healed. You feel much better.";
    }
}
