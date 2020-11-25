package Items;

import java.util.*;
import Interfaces.Interactuable;
import textadventure.Room;
import Interfaces.Storable;

public class Nota extends Item implements Interactuable, Storable{

    static private Scanner in;
    public Nota(String itemName, String itemDescription, Room itemRoom) {
        super(itemName, itemDescription, itemRoom);
    }

    @Override
    public void interact() {
        System.out.println("\"La poca luz que entra en mi\nCasi a tientas te dejó\nSin cuenta lo contó\nY del pasillo se salió\"");
        System.out.println("...");
        in.nextLine();
        System.out.println("Están remarcados el primer y el tercer párrafo...");
    }
    
}
