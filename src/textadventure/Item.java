package textadventure;
public class Item{
    private String itemName;
    private String itemDescription;
    private boolean interactuable;
    private String interaction;
    
    
    public Item(String itemName, String itemDescription, boolean interactuable){
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.interactuable = interactuable;
    }
        
    public String getItemName(){
        return this.itemName;
    }
            
    public String getItemDescription(){
        return this.itemDescription;
    }
    
    public void setInteractuable(boolean i){
        this.interactuable = i;
    }
    
    public boolean isInteractuable(){
        return this.interactuable;
    }
    
    public void setInteraction(String interaction){
        this.interaction = interaction;
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
