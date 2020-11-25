package Items;

import Interfaces.Interactuable;
import textadventure.Room;

public class Tablero extends Item implements Interactuable{

    public Tablero(String itemName, String itemDescription, Room itemRoom) {
        super(itemName, itemDescription, itemRoom);
    }

    @Override
    public void interact() {
        System.out.println(this);
    }
    
}
