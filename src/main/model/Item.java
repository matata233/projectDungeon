package model;

/*
 * Represents an abstract class of item.
 */
public abstract class Item {
    protected String name;
    protected int amount;

    // EFFECTS: Create an item with given name and amount
    Item(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    protected abstract String effect();


    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
