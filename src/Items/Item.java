package Items;

import textadventure.*;

public abstract class Item{
    protected String itemName;
    protected String itemDescription;
    protected Room itemRoom;
    
    public Item(String itemName, String itemDescription, Room itemRoom){
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemRoom = itemRoom;
    }

    public Room getItemRoom() {
        return itemRoom;
    }

    public void setItemRoom(Room itemRoom) {
        this.itemRoom = itemRoom;
    }
        
    public String getItemName(){
        return this.itemName;
    }
            
    public String getItemDescription(){
        return this.itemDescription;
    }    
    
    @Override
    public String toString(){
        return this.itemName + ": " + this.itemDescription;
    }
    
    @Override
    public int hashCode(){
        return this.itemName.hashCode();
    }
    
    @Override
    public boolean equals(Object o){
        Item i = (Item) o;
        return this.itemName.hashCode() == i.hashCode();
    }
}
