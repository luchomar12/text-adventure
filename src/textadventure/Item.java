package textadventure;
public class Item {
    private int itemCode;
    private int itemRoom;
    private String itemName;
    private String itemDescription;
    
    
    public Item(int itemCode, int itemRoom, String itemName, String itemDescription){
        this.itemCode = itemCode;
        this.itemRoom = itemRoom;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
    }
    
    public int getItemCode(){
        return this.itemCode;
    }
    
    public int getItemRoom(){
        return this.itemRoom;
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
    
    
}
