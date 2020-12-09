package Items;

import Interfaces.*;
import java.util.Scanner;
import org.json.simple.JSONObject;
import textadventure.*;

public class Exit extends Item implements Interactuable, Abrible {

    //ATRIBUTOS
    private Game juego = Game.dameInstancia();
    private Scanner in = new Scanner(System.in);
    private String direction; //hacia donde está (n, s, e, w)
    private Room leadsTo; //La room a la que va
    private boolean isOpened; // si está abierta o cerrrada
    private Usable opener; //el item Usable (llave o similar) para abrirla
    private Interactuable interactuable; //algún interactuable (Bloqueador / Tablero)

    //CONSTRUCTORES
    public Exit(int code, String exitName, String exitDescription, String direction, boolean isOpened) {
        super(code, exitName, exitDescription);
        this.direction = direction;
        this.isOpened = isOpened;
    }

    public static Exit leerJson(JSONObject obj) {
        int eCode = (int) (long) obj.get("exitCode");
        String eTitle = (String) obj.get("exitName");
        String eDescription = (String) obj.get("exitDescription");
        String eDirection = (String) obj.get("exitDirection");
        boolean eOpened = (boolean) obj.get("exitOpened");
        Exit e = new Exit(eCode, eTitle, eDescription, eDirection, eOpened);
        return e;
    }

    public int getExitCode() {
        return this.itemCode;
    }

    //DIRECTION
    public String getDirection() {
        return this.direction;
    }

    //LEADS TO
    public Room getLeadsTo() {
        return this.leadsTo;
    }

    public void setLeadsTo(Room r) {
        this.leadsTo = r;
    }

    //IsOPENED
    public void setIsOpened(boolean isOpened) {
        this.isOpened = isOpened;
    }

    public boolean isOpened() {
        return this.isOpened;
    }

    //OPENER
    public Usable getOpener() {
        return this.opener;
    }

    public void setOpener(Usable u) {
        this.opener = u;
    }

    //INTERACTUABLE
    public Interactuable getInteractuable() {
        return this.interactuable;
    }

    public void setInteractuable(Interactuable i) {
        this.interactuable = i;
    }

    @Override
    public void interact() {
        System.out.println("");
        System.out.println("¿Qué vas a usar?");
        System.out.println("");
        juego.getPlayer().showInventory();
        System.out.println("");
        System.out.print("> ");
        String input = in.nextLine();
        if (!this.validateInteract(input)) {
            if (input.equals("")) {
                System.out.println("No has hecho nada...");
            } else {
                System.out.println("No es el item correcto...");
            }
        }
    }

    @Override
    public boolean validateInteract(String input) {
        for (Guardable item : juego.getPlayer().getInventory()) { //busco en el inventario
            Item obj = (Item) item; //guardo en Item el Storable
            if (input.equalsIgnoreCase(obj.getItemName()) && obj instanceof Usable) { //si mi entrada es igual al nombre del item y es unsable
                Usable llave = (Usable) obj;
                return this.openWith(llave);
            }
        }
        return false;
    }

    @Override
    public boolean openWith(Usable item) {
        if (item.equals(this.getOpener())) {
            this.isOpened = true; //abro la puerta
            juego.getPlayer().setPlayerRoom(this.leadsTo); //paso a siguiente habitación
            System.out.println("");
            System.out.println(">> ¡Abriste la puerta! <<");
            System.out.println("");
            System.out.println("Pasas hacia...");
            return true;
        } else {
            return false;
        }
    }

}
