package Items;

import Interfaces.Storable;
import Interfaces.Usable;

public class Llave extends Item implements Storable, Usable {

    public Llave(int code, String nombre, String description) {
        super(code, nombre, description);
    }

}
