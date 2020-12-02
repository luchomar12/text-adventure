package Items;

import Interfaces.Interactuable;
import Interfaces.Storable;

public class Nota extends Item implements Interactuable, Storable{

    private String nota;
    
    public Nota(int code, String itemName, String itemDescription, String nota) {
        super(code, itemName, itemDescription);
        this.nota = nota;
    }
    
    public String getNota(){
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
