package Items;

import Interfaces.Usable;
import Interfaces.Guardable;
import org.json.simple.JSONObject;

public class Llave extends Item implements Guardable, Usable {

    public Llave(int code, String nombre, String description) {
        super(code, nombre, description);
    }

    public static Llave leerJson(JSONObject obj) {
        int rCode = (int) (long) obj.get("llaveCode");
        String rTitle = (String) obj.get("llaveName");
        String rDescription = (String) obj.get("llaveDescription");
        Llave ll = new Llave(rCode, rTitle, rDescription);
        return ll;
    }

}
