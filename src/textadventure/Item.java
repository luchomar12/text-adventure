package textadventure;
public class Item {
    private int itemCode;
    private String itemName;
    private String itemDescription;
    
    
    public Item(String itemName, String itemDescription){
        this.itemName = itemName;
        this.itemDescription = itemDescription;
    }
    
    public int getItemCode(){
        return this.itemCode;
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
        return this.itemCode;
    }
    
    @Override
    public boolean equals(Object o){
        Item i = (Item) o;
        return this.itemCode == i.itemCode;
    }
}
