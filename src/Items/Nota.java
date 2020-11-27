package Items;

import java.util.*;
import Interfaces.Interactuable;
import textadventure.Room;
import Interfaces.Storable;

public class Nota extends Item implements Interactuable, Storable{

    public Scanner in = new Scanner(System.in);
    private String nota;
    
    public Nota(String itemName, String itemDescription, Room itemRoom) {
        super(itemName, itemDescription, itemRoom);
    }
    
    public void setNota(String nota){
        this.nota = nota;
    }
    
    public String getNota(){
        return this.nota;
    }

    @Override
    public void interact() {
        System.out.println(this.getNota());
    }
    
}
