package Items;

import Interfaces.Interactuable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import textadventure.Game;

public class Tablero extends Item implements Interactuable {

    //ATRIBUTOS
    private Game juego = Game.dameInstancia();
    private Scanner in = new Scanner(System.in);
    private boolean isOn; //si está o no encendido
    private Interactuable interactuable; //Objeto interactuable en el que se encuentra
    private List<String> password; //password
    private String passInfo; //información para ingresar el password
    private String offDescription;

    //CONSTRUCTOR
    public Tablero(int code, String itemName, String itemDescription, String offDescription, boolean isOn, String tInfo, List<String> pass) {
        super(code, itemName, itemDescription);
        this.isOn = isOn;
        this.passInfo = tInfo;
        this.password = pass;
        this.offDescription = offDescription;
    }

    public static Tablero leerJson(JSONObject obj) {
        int tCode = (int) (long) obj.get("tableroCode");
        String tTitle = (String) obj.get("tableroName");
        String tDescription = (String) obj.get("tableroDescription");
        String tOffDescription = (String) obj.get("tableroOffDescription");
        boolean tIsOn = (boolean) obj.get("tableroIsOn");
        String tInfo = (String) obj.get("tableroInfo");
        JSONArray tPass = (JSONArray) obj.get("tableroPass");
        List<String> password = new ArrayList<>();
        for (Object o : tPass) {
            if (o != null) {
                String ob = (String) o;
                password.add(ob);
            }
        }
        Tablero t = new Tablero(tCode, tTitle, tDescription, tOffDescription, tIsOn, tInfo, password);
        return t;
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
        if (this.isOn) { //si el Tablero está prendido
            System.out.println(this.itemDescription); //Muestro descripcion del tablero
            System.out.println("");
            System.out.println("Hay información para la contraseña:");
            System.out.println(this.getPassInfo()); //muestro la info para la contraseña
            if (validatePassword()) {
                System.out.println("¡Contraseña correcta!");
                juego.getPlayer().getPlayerRoom().removeItem(this); // saco el tablero de la vista de la Room
                if (this.getIn() instanceof Exit) { //si está en una salida
                    Exit e = (Exit) this.getIn();
                    e.setIsOpened(true); //abro la salida
                    e.setInteractuable(null);//seteo el Interactuable a null para que no aparezca
                    System.out.println("Parece que la salida se abrió...");
                    System.out.println("");
                    System.out.println("Pasas hacia...");
                    juego.getPlayer().setPlayerRoom(e.getLeadsTo()); //voy hacia la siguiente Room
                } else if (this.getIn() instanceof Contenedor) { //si está en un Contenedor
                    Contenedor c = (Contenedor) this.getIn();
                    c.setInteractuable(null); //le quito el interactuable al Contenedor
                    c.open(); //abro el Contenedor
                }
            } else {
                System.out.println("La contraseña no es correcta...");
            }
        } else { //Si el tablero está apagado
            System.out.println(this.offDescription);
        }
    }

    public boolean validatePassword() {
        int cont = 0;
        for (int i = 0; i < password.size(); i++) {
            System.out.println("Introduce el " + (i + 1) + "° caracter: ");
            String input = in.nextLine();
            if (!(input.length() == password.get(i).length())) {
                System.out.println("No es un caracter válido para esta contraseña");
                break;
            }
            if (input.equalsIgnoreCase(password.get(i))) {
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
