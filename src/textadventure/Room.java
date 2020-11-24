package textadventure;

import Items.Item;
import java.util.*;

public class Room {
    
    private String title;
    private String description;
    private Set<Item> items = new HashSet<>(); //creo un conjunto de items que estarán en cada habitación
    private Set<Exit> exits = new HashSet<>();

    public Room(){}
    
    public Set<Item> getAllItems(){
        return items;
    }
    
    public Set<Storable> getStorableItems() {
        Set<Storable> storables = new HashSet<>();
        for(Item item : items){
            if(item instanceof Storable){
                storables.add((Storable) item);
            }
        }
        return storables;
    }
    
    public Set<Interactuable> getInteractuableItems(){
        Set<Interactuable> interacts = new HashSet<>();
        for(Item item : items){
            if(item instanceof Interactuable){
                interacts.add((Interactuable) item);
            }
        }
        return interacts;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void addItem(Item item){
        items.add(item);
    }
    
    public void removeItem(Item item){
        items.remove(item);
    }
    
    public void addExit(Exit exit){
        exits.add(exit);
    }
    
    public void removeExit(Exit exit){
        exits.remove(exit);
    }

    public boolean getExit(String direccion, Player p) {
        for(Exit i : exits){ //busco las salidas de esta room
            if(i.getDirection().equals(direccion)){ //si hay salidas que coincidan con la entrada que hago por teclado (String exit)
                p.setPlayerRoom(i.getLeadsTo()); //seteo el nuevo room
                return true;
            }   
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return this.title.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        Room r = (Room) obj;
        return this.hashCode()==r.hashCode();
    }
}
