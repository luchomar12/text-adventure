package textadventure;
import Items.Item;
import java.util.*;
public class Player{
    
    public Scanner in = new Scanner(System.in);
    public static Player jugador;
    private Room playerRoom;
    public Set<Storable> inventory = new HashSet<>();
    
    
    private Player(){}
    
    public static Player getInstance(){
        if(jugador == null){
            return new Player();
        }
        return jugador;
    }
    
    public Room getPlayerRoom(){
        return this.playerRoom;
    }
    
    public void setPlayerRoom(Room r){
        this.playerRoom = r;
    }
    
    //metodo para moverse moverse
    public void moverPlayer(){
        boolean seMueve = false;
        while(!seMueve){
            System.out.println("Donde quieres ir? (n,s,e,w) o (b) para volver al menú.");            
            String direction = in.nextLine();
            if(direction.equals("b")){
                break;
            }else if(this.playerRoom.getExit(direction, this)){ //da true si hay salida, false si no hay
                seMueve = true;//si hay salida salgo del bucle, sino vuelve a preguntar
            }else{
                System.out.println("No hay salida para esa dirección");    
            }                    
        }   
    }
    
    public void takeItem(){
        if(this.playerRoom.getStorableItems().isEmpty()){
            System.out.println("No hay items que puedas tomar");
        }else{
            System.out.println("Puedes tomar los siguientes items, ¿cuál quieres? ");
            for(Storable item : this.playerRoom.getStorableItems()){
                System.out.println("    -"+item);
            }
            String entry = in.nextLine();
            for(Storable item : this.playerRoom.getStorableItems()){
                Item it = (Item) item;
                if(entry.equalsIgnoreCase(it.getItemName())){
                    this.inventory.add((Storable) it);
                    this.playerRoom.removeItem(it);
                    System.out.println("Has tomado "+it.getItemName());
                }else{
                    System.out.println("No hay -"+entry+"- para tomar");
                }
            }
        }
    }
    
    public void removeItem(Storable item){
        this.inventory.remove(item);
    }
    
    public void interactWith(){
        if(this.playerRoom.getInteractuableItems().isEmpty()){
            System.out.println("No hay nada con lo que interactuar");
        }else{
            System.out.println("¿Con qué quieres interactuar?");
            for(Interactuable obj : this.playerRoom.getInteractuableItems()){
                System.out.println("    -"+obj);
            }
            String entry = in.nextLine();
            for(Interactuable item : this.playerRoom.getInteractuableItems()){
                Item it = (Item) item;
                if(entry.equalsIgnoreCase(it.getItemName())){
                    item.interact();
                    
                }else{
                    System.out.println("No hay -"+entry+"- para interactuar");
                }
            }
        }
    }
    
    public void showInventory(){
        if(this.inventory.isEmpty()){
            System.out.println("Aún no tienes nada en el inventario");
        }else{
            System.out.println("**INVENTARIO**");
            for(Storable item : inventory){
                System.out.println("    -"+item);
            }
        }
    }
}
