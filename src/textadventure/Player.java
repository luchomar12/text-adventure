package textadventure;
import Interfaces.Storable;
import Interfaces.Interactuable;
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
    
    public Set<Interactuable> getInteractuableInventory(){
        Set<Interactuable> interactuableInventory = new HashSet<>();
        for(Storable item : inventory){
            if(item instanceof Interactuable){
                interactuableInventory.add((Interactuable)item);
            }
        }
        return interactuableInventory;
    }
    
    //metodo para moverse
    public void moverPlayer(){
        boolean seMueve = false;
        while(!seMueve){
            System.out.println("Donde quieres ir? (n,s,e,w) o (b) para volver al menú.");            
            String direction = in.nextLine();
            if(direction.equals("b")){
                break;
            }
            Exit salida = this.playerRoom.isExit(direction);
            if(salida == null){
                System.out.println("No hay salida en esa dirección");
            }else if(salida.isOpened()){ //si la salida está aberta seteo nueva habitación y salgo del bucle.
                this.setPlayerRoom(salida.getLeadsTo());
                break;
            }else{
                System.out.println(salida.getClosedDescription());
                if(salida.getInteractuable() != null){
                    this.playerRoom.addItem((Item) salida.getInteractuable());
                    in.nextLine();
                }
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
    
    public void interactWith(String option){
        if(option.equalsIgnoreCase("i")){//si quiero interactuar con algún objeto de mi inventario
            if(this.getInteractuableInventory().isEmpty()){
                System.out.println("No tienes ningún objeto en el inventario con el que puedas interactuar");
            }else{
                System.out.println("¿Con qué objeto quieres interactuar?");
                for(Interactuable item : this.getInteractuableInventory()){
                    System.out.println("    -"+item);
                }
                System.out.println("");
                System.out.print("> ");
                String entry = in.nextLine();
                for(Interactuable item : this.getInteractuableInventory()){
                    Item i = (Item) item;
                    if(entry.equalsIgnoreCase(i.getItemName())){
                        item.interact();
                        break;
                    }else{
                        System.out.println("No es un item correcto");
                    }
                }
            }
        }else if(option.equalsIgnoreCase("h")){
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
                        break;
                    }else{
                        System.out.println("No hay -"+entry+"- para interactuar");
                    }
                }
            }
        }else{
            System.out.println("No es una opción válida");
        }
    }
    
    public void showInventory(){
        if(this.inventory.isEmpty()){
            System.out.println("Aún no tienes nada en el inventario");
            in.nextLine();
        }else{
            System.out.println("**INVENTARIO**");
            for(Storable item : inventory){
                System.out.println("    -"+item);
            }
            in.nextLine();
        }
    }
}
