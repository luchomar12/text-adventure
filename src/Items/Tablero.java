package Items;

import Interfaces.Interactuable;
import java.util.List;
import textadventure.Game;
import static textadventure.Game.in;
import static textadventure.Game.p;

public class Tablero extends Item implements Interactuable {

    //ATRIBUTOS
    private boolean isOn; //si está o no encendido
    private Interactuable interactuable; //Objeto interactuable en el que se encuentra
    private List<String> password; //password
    private String passInfo; //información para ingresar el password
    //INTS
    private int intIn;

    //CONSTRUCTOR
    public Tablero(int code, String itemName, String itemDescription, boolean isOn, String tInfo, int tIn, List<String> pass) {
        super(code, itemName, itemDescription);
        this.isOn = isOn;
        this.passInfo = tInfo;
        this.intIn = tIn;
        this.password = pass;

    }

    //IsON
    public void setIsOn(boolean isOn) {
        this.isOn = isOn;
    }

    //OBJ
    public void setIn(Interactuable obj) {
        this.interactuable = obj;
    }

    public Interactuable getIn() {
        return this.interactuable;
    }

    //PASSWORD
    public void setPassInfo(String passInfo) {
        this.passInfo = passInfo;
    }

    public String getPassInfo() {
        return this.passInfo;
    }

    //Interface INTERACTUABLE
    @Override
    public void interact() {
        System.out.println(this.itemDescription); //Muestro descripcion del tablero
        if (this.isOn) { //si el Tablero está prendido
            System.out.println("");
            System.out.println("Hay información para la contraseña:");
            System.out.println(this.getPassInfo()); //muestro la info para la contraseña
            in.nextLine();
            if (validatePassword()) {
                System.out.println("¡Contraseña correcta!");
                p.getPlayerRoom().removeItem(this); // saco el tablero de la vista de la Room
                if (this.getIn() instanceof Exit) { //si está en una salida
                    Exit e = (Exit)this.getIn();
                    e.setIsOpened(true); //abro la salida
                    e.setInteractuable(null);//uso el interactuable (lo quita)
                    System.out.println("Parece que la salida se abrió...");
                    Game.p.setPlayerRoom(e.getLeadsTo()); //voy hacia la siguiente Room
                } else if (this.getIn() instanceof Cofre) { //si está en un cofre
                    Cofre c = (Cofre) this.getIn();
                    c.setInteractuable(null); //le quito el interactuable al Cofre
                    c.open(); //abro el cofre
                }
            } else {
                System.out.println("La contraseña no es correcta...");
            }
        } else { //Si el tablero está apagado
            System.out.println("En la pantalla no ocurre nada cuando la aprieto.");
            System.out.println("El led está apagado. Evidentemente no está activado.");
            System.out.println("Debe haber una manera de activarlo en alguna habitación...");
        }
    }

    public int getIntIn() {
        return intIn;
    }

    public boolean validatePassword() {
        int cont = 0;
        for (int i = 0; i < password.size(); i++) {
            System.out.println("Introduce el " + (i + 1) + "° caracter: ");
            if (in.nextLine().equalsIgnoreCase(password.get(i))) {
                cont++;
            }
        }
        return cont == password.size();
    }

    @Override
    public boolean validateInteract(String input) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
