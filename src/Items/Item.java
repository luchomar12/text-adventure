package Items;
public abstract class Item{
    protected String itemName;
    protected String itemDescription;    
    
    public Item(String itemName, String itemDescription){
        this.itemName = itemName;
        this.itemDescription = itemDescription;
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
