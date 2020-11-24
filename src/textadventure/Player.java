package textadventure;
import java.util.*;
public class Player{
    
    public Scanner in = new Scanner(System.in);
    private static Player jugador;
    private Room playerRoom;
    private Set<Item> inventory = new HashSet<>();
    
    
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
        if(this.playerRoom.getItems().isEmpty()){
            System.out.println("No hay items para tomar");
        }else{
            System.out.println("¿Qué item queres tomar?");
            String entry = in.nextLine();
            for(Item i : this.playerRoom.getItems()){
                if(entry.equalsIgnoreCase(i.getItemName())){
                    System.out.println("Has tomado "+i.getItemName());
                    inventory.add(i);
                    this.playerRoom.removeItem(i);
                }else{
                    System.out.println("No hay ningún item '"+entry+"'");
                }
            }
        }
    }
    
    public void interactWith(){
        List<Item> interactuables = new ArrayList<>();
        for(Item item : this.playerRoom.getItems()){
            if(item.isInteractuable()){
                interactuables.add(item);
            }
        }
        if(interactuables.isEmpty()){
            System.out.println("No hay nada para interactuar");
        }else{
            System.out.println("¿Con qué quieres interactuar?");
            for(Item item : interactuables){
                System.out.println(item.getItemName());
            }
            String entry = in.nextLine();
            for(Item item : interactuables){
                if(entry.equals(item.getItemName())){
                    item.interact();
                }
            }
            
        }
        
    }
    
    public void showInventory(){
        if(this.inventory.isEmpty()){
            System.out.println("Aún no tienes nada en el inventario");
        }else{
            System.out.println("**INVENTARIO**");
            for(Item item : inventory){
                System.out.println("    -"+item);
            }
        }
    }
}
