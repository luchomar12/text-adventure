package Items;

import Interfaces.Storable;
import Interfaces.Usable;
import textadventure.*;

public class Llave extends Item implements Storable, Usable{
    
    public Llave(String nombre, String description, Room itemRoom){
        super(nombre, description, itemRoom);
    }
    
}
