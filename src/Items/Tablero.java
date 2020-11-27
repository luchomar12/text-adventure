package Items;

import Interfaces.Interactuable;
import static textadventure.Game.in;
import textadventure.Room;

public class Tablero extends Item implements Interactuable{
    
    private boolean isOn;
    private Exit exit; //exit en el que se encuentra
    private int password;

    public Tablero(String itemName, String itemDescription, Room itemRoom, boolean isOn) {
        super(itemName, itemDescription, itemRoom);
        this.isOn = isOn;
    }

    public void setOnDescription(String description){
        this.itemDescription = description;
    }
    
    public void setPassword(int pass){
        this.password = pass;
    }
        
    
    @Override
    public void interact() {
        if(this.isOn){
            System.out.println("");
            System.out.println("Puedes introducir números");
            System.out.println("");
            System.out.print("> ");
            int input = in.nextInt();
            if (input==this.password) {
                System.out.println("¡Contraseña correcto! Se abrió la puerta");
                exit.setIsOpened(true);
                this.itemRoom.removeItem(this);
            } else {
                System.out.println("La contraseña es incorrecta...");
            }
        }else{
            System.out.println("Tiene números pero no ocurre nada cuando los aprieto.");
            System.out.println("El led está apagado. Evidentemente no está activado.");
            System.out.println("Debe haber una manera de activarlo en alguna habitación...");
        }
    }
    
}
