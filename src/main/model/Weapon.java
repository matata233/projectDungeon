package model;

/*
 * Represents a weapon.
 */
public class Weapon extends Gear {
    // EFFECTS: Create a weapon with given name, attack, and defence
    public Weapon(String name, int attack, int defence) {
        super(name, attack, defence);
    }
}
