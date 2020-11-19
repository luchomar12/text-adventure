package textadventure;

import java.util.*;

public class Room {
    
    private String title;
    private String description;
    private Set<Item> items = new HashSet<>(); //creo un conjunto de items que estarán en cada habitación
    private Set<Exit> exits = new HashSet<>();

    public Set<Item> getItems() {
        return items;
    }
    
    public Room(){}

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
    
    public void removeExit(int exit){
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
