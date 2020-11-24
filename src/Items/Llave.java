package Items;

import textadventure.*;

public class Llave extends Item implements Usable, Storable{
    
    public Llave(String nombre, String description){
        super(nombre, description);
    }
    
    @Override
    public void use(Interactuable obj) {
        obj.open(this);
    }
}
