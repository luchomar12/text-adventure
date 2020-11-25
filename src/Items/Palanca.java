package Items;

import textadventure.Exit;
import textadventure.Game;
import static textadventure.Game.in;
import Interfaces.Interactuable;
import textadventure.Player;
import textadventure.Room;
import Interfaces.Usable;

public class Palanca extends Item implements Interactuable{
    
    private Exit unlock;

    public Palanca(String itemName, String itemDescription, Room itemRoom, Exit unlock) {
        super(itemName, itemDescription, itemRoom);
        this.unlock = unlock;
    }

    @Override
    public void interact() {
        if(this.unlock.isOpened()){
            System.out.println("Esta palanda ya fue accionada");
        }else{
            System.out.println("¿Mover palanca? (si/no)");
            String entry = in.nextLine();
            if(entry.equalsIgnoreCase("si")){
                System.out.println("Has accionado la palanca. El ruido de algo destrabándose te sobresalta.");
                for(Exit e : Game.p.getPlayerRoom().getExits()){
                    if(this.unlock.equals(e)){
                        e.setIsOpened(true);
                        this.itemRoom.removeItem(this);
                        break;
                    }
                }
            }else{
                System.out.println("No has hecho nada...");
            }
        }
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }
    
}
