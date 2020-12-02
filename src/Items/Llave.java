package Items;

import Interfaces.Usable;
import Interfaces.Guardable;

public class Llave extends Item implements Guardable, Usable {

    public Llave(int code, String nombre, String description) {
        super(code, nombre, description);
    }

}
