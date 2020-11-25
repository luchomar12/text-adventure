package Items;

import Interfaces.Interactuable;
import java.util.Scanner;
import textadventure.Exit;
import textadventure.Game;
import textadventure.Room;

public class PlacaMetalica extends Item implements Interactuable{

    static private Scanner in;
    private Llave opener;
    private Exit place;
    
    public PlacaMetalica(String itemName, String itemDescription, Room itemRoom, Llave opener) {
        super(itemName, itemDescription, itemRoom);
        this.opener = opener;
    }
    
    public void setPlace(Exit exit){
        this.place = exit;
    }

    @Override
    public void interact() {
        System.out.println("¿Qué quieres usar?");
        for(Interactuable item : Game.p.getInteractuableInventory()){
            System.out.println("    -"+item);
        }
        String entry = in.nextLine();
        for(Interactuable item : Game.p.getInteractuableInventory()){
            Item i = (Item) item;
            if(entry.equalsIgnoreCase(i.getItemName())){
                if(opener.equals((Llave) i)){
                    System.out.println("¡Has desatornillado la placa!");
                    this.place.setInteractuable(null);
                    break;
                }else{
                    System.out.println("No puedes usar eso aquí");
                }
            }else{
                System.out.println("No tienes un \""+entry+"\"");
                break;
            }
        }
        
        
    }
    
}
