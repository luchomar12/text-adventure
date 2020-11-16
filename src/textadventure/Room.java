package textadventure;
public class Room {
    
    private int roomCode;
    private int nCode;
    private int sCode;
    private int eCode;
    private int wCode;
    private String nombre;
    private String description;
    private Item [] inventory;
    
    public Room(int room, int n, int s, int e, int w, String nombre, String description, Item[] inventory){
        this.roomCode = room;
        this.nCode = n;
        this.sCode = s;
        this.eCode = e;
        this.wCode = w;
        this.nombre = nombre;
        this.description = description;
        this.inventory = inventory;
    }
    
    public int getRoom(){
        return this.roomCode;
    }
    
    public int getNorth(){
        return this.nCode;
    }
    
    public int getSouth(){
        return this.sCode;
    }
    
    public int getEast(){
        return this.eCode;
    }
    
    public int getWest(){
        return this.wCode;
    }
    
    public String getNombre(){
        return this.nombre;
    }
    
    public String getDescription(){
        return this.description;
    }
    
    public Item [] getItems(){
        return this.inventory;
    }
}
