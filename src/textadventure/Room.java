package textadventure;

import Items.Exit;
import Interfaces.Interactuable;
import Items.Item;
import java.util.*;
import Interfaces.Guardable;
import org.json.simple.JSONObject;

public class Room {

    private int roomCode;
    private String title;
    private String description;
    private Set<Item> items = new HashSet<>(); //creo conjunto de todos los items de la habitación
    private Set<Exit> exits = new HashSet<>(); //salidas

    //CONSTRUCTOR
    public Room(int code, String title, String description) {
        this.roomCode = code;
        this.title = title;
        this.description = description;
    }

    public static Room leerJson(JSONObject obj) {
        int rCode = (int) (long) obj.get("roomCode");
        String rTitle = (String) obj.get("roomTitle");
        String rDescription = (String) obj.get("roomDescription");
        Room r = new Room(rCode, rTitle, rDescription);//creo la room
        return r;
    }

    @Override
    public String toString() {
        return this.roomCode + "-" + this.title;
    }

    //CODE
    public int getRoomCode() {
        return this.roomCode;
    }

    public Set<Item> getAllItems() {
        return items;
    }

    public Set<Guardable> getStorableItems() {
        Set<Guardable> storables = new HashSet<>();
        for (Item item : items) {
            if (item instanceof Guardable) {
                storables.add((Guardable) item);
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
            if (item instanceof Guardable) { //pero si es storable no
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
