package Items;

import Interfaces.Interactuable;
import static textadventure.Game.in;
import textadventure.Room;

public class Tablero extends Item implements Interactuable{
    
    private boolean isOn;
    private Interactuable obj; //Objeto interactuable en el que se encuentra
    private String [] password;
    private String pista;

    public Tablero(String itemName, String itemDescription, Room itemRoom, boolean isOn) {
        super(itemName, itemDescription, itemRoom);
        this.isOn = isOn;
    }
    
    public void setIn(Interactuable obj){
        this.obj = obj;
    }

    public void setOnDescription(String description){
        this.itemDescription = description;
    }
    
    public void setPassword(String [] pass){
        this.password = pass;
    }
    
    public void setPista(String pista){
        this.pista = pista;
    }
    
    public String getPista(){
        return this.pista;
    }
    
    public void setIsOn(boolean isOn){
        this.isOn = isOn;
    }
    
    @Override
    public void interact() {
        if(this.isOn){
            System.out.println(this.getPista());
            in.nextLine();
            System.out.println("Es una pista para la contraseña...");
            in.nextLine();
            System.out.println("");
            if (validatePassword()) {
                System.out.println("¡Contraseña correcta!");
                if(obj instanceof Exit){
                    Exit e = (Exit) obj;
                    e.setIsOpened(true);
                    System.out.println("Parece que la salida se abrió...");
                }else if(obj instanceof Cofre){
                    Cofre c = (Cofre) obj;
                    c.setIsOpened(true);
                    System.out.println("Parece que se abrió...");
                }
                this.itemRoom.removeItem(this);
            } else {
                System.out.println("La contraseña no es correcta...");
            }
        }else{
            System.out.println("En la pantalla no ocurre nada cuando la aprieto.");
            System.out.println("El led está apagado. Evidentemente no está activado.");
            System.out.println("Debe haber una manera de activarlo en alguna habitación...");
        }
    }
    
    public boolean validatePassword(){
        int cont = 0;
        for(int i = 0; i<password.length;i++){
            System.out.println("Introduce el caracter "+(i+1));
            if(in.nextLine().equalsIgnoreCase(password[i])){
                cont++;
            }
        }
        return cont == password.length;
    }
    
}
