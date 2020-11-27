package textadventure;

import Items.Exit;
import Interfaces.Storable;
import Interfaces.Interactuable;
import Items.Item;
import java.util.*;

public class Room {

    private String title;
    private String description;
    private Set<Item> items = new HashSet<>(); //creo conjunto de todos los items de la habitación
    private Set<Interactuable> interactuableItems = new HashSet<>(); // items itneractuables
    private Set<Exit> exits = new HashSet<>(); //salidas
    private boolean dark; //si está o no iluminada la habitación

    public Room() {
    }

    public Set<Item> getAllItems() {
        return items;
    }

    public Set<Storable> getStorableItems() {
        Set<Storable> storables = new HashSet<>();
        for (Item item : items) {
            if (item instanceof Storable) {
                storables.add((Storable) item);
            }
        }
        return storables;
    }

    public Set<Interactuable> getInteractuableItems() {
        for (Item item : items) {
            if (item instanceof Interactuable) {
                interactuableItems.add((Interactuable) item);
            }
        }
        return interactuableItems;
    }
    
    public void addInteractuableItem(Interactuable item){
        this.interactuableItems.add(item);
    }
    
    public void removeInteractuableItem(Interactuable item){
        this.interactuableItems.remove(item);
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

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public void addExit(Exit exit) {
        exits.add(exit);
    }

    public void removeExit(Exit exit) {
        exits.remove(exit);
    }

    public Exit isExit(String direction) {
        for (Exit e : exits) {
            if (e.getDirection().equalsIgnoreCase(direction)) {
                return e;
            }
        }
        return null;
    }

    public Set<Exit> getExits() {
        return exits;
    }
    
    public void setDark(boolean dark){
        this.dark = dark;
    }
    
    public boolean isDark(){
        return this.dark;
    }

    @Override
    public int hashCode() {
        return this.title.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        Room r = (Room) obj;
        return this.hashCode() == r.hashCode();
    }
}
