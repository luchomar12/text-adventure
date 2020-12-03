package Items;

import textadventure.Game;

public abstract class Item {

    protected int itemCode;
    protected String itemName;
    protected String itemDescription;

    public Item(int itemCode, String itemName, String itemDescription) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
    }

    public String getItemName() {
        return this.itemName;
    }

    public String getItemDescription() {
        return this.itemDescription;
    }

    public int getItemCode() {
        return this.itemCode;
    }

    @Override
    public String toString() {
        return this.itemName + ": " + this.itemDescription;
    }

    @Override
    public int hashCode() {
        return this.itemCode;
    }

    @Override
    public boolean equals(Object o) {
        Item i = (Item) o;
        return this.itemCode == i.itemCode;
    }
}
