package textadventure;

import Items.Exit;
import Interfaces.Storable;
import Interfaces.Interactuable;
import Items.Item;
import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Room {

    private int roomCode;
    private String title;
    private String description;
    private List<Integer> intExits = new ArrayList<>();
    private List<Integer> intItems = new ArrayList<>();
    private Set<Item> items = new HashSet<>(); //creo conjunto de todos los items de la habitación
    private Set<Exit> exits = new HashSet<>(); //salidas

    
    
    
    public List<Integer> getIntegerExits(){
        return this.intExits;
    }
    
    public void addIntegerExits(int e){
        this.intExits.add(e);
    }
    
    public List<Integer> getIntegerItems(){
        return this.intItems;
    }
    
    public void addIntegerItems(int i){
        this.intItems.add(i);
    }
    
    
    
    
    
    
    //CONSTRUCTOR
    public Room(int code, String title, String description) {
        this.roomCode = code;
        this.title = title;
        this.description = description;
    }

    @Override
    public String toString() {
        return this.roomCode + "-" + this.exits;
    }

    //CODE
    public int getRoomCode() {
        return this.roomCode;
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
        Set<Interactuable> interactuableItems = new HashSet<>();
        for (Item item : items) {
            if (item instanceof Interactuable) { //si es interactuable lo agrego
                interactuableItems.add((Interactuable) item);
            }
            if (item instanceof Storable) { //pero si es storable no
                interactuableItems.remove(item);
            }
        }
        return interactuableItems;
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

    public void fin() {

    }

    @Override
    public int hashCode() {
        return this.roomCode;
    }

    @Override
    public boolean equals(Object obj) {
        Room r = (Room) obj;
        return this.hashCode() == r.hashCode();
    }
}
