package Items;

import Interfaces.Abrible;
import Interfaces.Storable;
import Interfaces.Usable;
import Interfaces.Interactuable;
import textadventure.*;

public class Llave extends Item implements Usable, Storable{
    
    public Llave(String nombre, String description, Room itemRoom){
        super(nombre, description, itemRoom);
    }
    
    @Override
    public void use(Abrible obj) {
        obj.open(this);
    }

    @Override
    public void use(Interactuable obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
