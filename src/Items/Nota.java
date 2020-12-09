package Items;

import Interfaces.Interactuable;
import Interfaces.Guardable;
import org.json.simple.JSONObject;

public class Nota extends Item implements Interactuable, Guardable {

    private String nota;

    public Nota(int code, String itemName, String itemDescription, String nota) {
        super(code, itemName, itemDescription);
        this.nota = nota;
    }

    public static Nota leerJson(JSONObject obj) {
        int nCode = (int) (long) obj.get("notaCode");
        String nTitle = (String) obj.get("notaName");
        String nDescription = (String) obj.get("notaDescription");
        String nNota = (String) obj.get("nNota");
        Nota n = new Nota(nCode, nTitle, nDescription, nNota);
        return n;
    }

    public String getNota() {
        return this.nota;
    }

    @Override
    public void interact() {
        System.out.println(this.getNota());
    }

    @Override
    public boolean validateInteract(String input) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
