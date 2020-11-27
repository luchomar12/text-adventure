package Items;

import Interfaces.Abrible;
import Interfaces.Usable;
import Interfaces.Interactuable;
import Interfaces.Storable;
import java.util.Scanner;
import textadventure.Game;
import textadventure.Room;

public class Exit extends Item implements Interactuable, Abrible {

    private Scanner in = new Scanner(System.in);
    private String direction; //hacia donde está (n, s, e, w)
    private Room leadsTo; //a qué room va

    private boolean isOpened; // si está abierta o cerrrada
    private Usable opener; //el item Usable (llave o similar) para abrirla
    private boolean isInteractuable; //si está cerrada, es interacuable, para poder abrirla

    private boolean isBlocked; //si está bloqueada por algo
    private Interactuable interactuable; //algún interactuable (algo que la bloquee, un tablero, contraseña, etc)

    public Exit(String exitName, String closedDescription, Room exitRoom, String direction, Room leadsTo, boolean isOpened) {
        super(exitName, closedDescription, exitRoom);
        this.direction = direction;
        this.leadsTo = leadsTo;
        this.isOpened = isOpened;
    }

    public void setDirection(String d) {
        this.direction = d;
    }

    public String getDirection() {
        return this.direction;
    }

    public void setLeadsTo(Room room) {
        this.leadsTo = room;
    }

    public Room getLeadsTo() {
        return this.leadsTo;
    }

    public void setIsOpened(boolean isOpened) {
        this.isOpened = isOpened;
    }

    public boolean isOpened() {
        return this.isOpened;
    }

    public void setClosedDescription(String closedDescription) {
        this.itemDescription = closedDescription;
    }

    public String getClosedDescription() {
        return itemDescription;
    }

    public Usable getOpener() {
        return opener;
    }

    public void setOpener(Usable opener) {
        this.opener = opener;
    }

    public void setIsInteractuable(boolean isInteractuable) {
        this.isInteractuable = isInteractuable;
    }

    public boolean getIsInteractuable() {
        return this.isInteractuable;
    }

    public boolean isIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    public void setExitName(String name) {
        this.itemName = name;
    }

    public String getExitName() {
        return this.itemName;
    }

    public Interactuable getInteractuable() {
        return interactuable;
    }

    public void setInteractuable(Interactuable interactuable) {
        this.interactuable = interactuable;
    }

    @Override
    public void interact() {
        System.out.println("");
        System.out.println("¿Qué vas a usar?");
        System.out.println("");
        Game.p.showInventory();
        System.out.println("");
        System.out.print("> ");
        String input = in.nextLine();
        if (!this.validateUsableOpener(input)) {
            System.out.println("No puedes usar \"" + input + "\" aquí...");
        }
    }

    public boolean validateUsableOpener(String input) {
        for (Storable item : Game.p.inventory) { //busco en el inventario
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
        if (item.equals(opener)) {
            this.isOpened = true;
            this.setIsInteractuable(false);
            Item it = (Item) item;
            System.out.println("");
            System.out.println(">> ¡Abriste la puerta! <<");
            in.nextLine();
            Game.p.removeItem((Storable) item); //elimino la llave del inventario ya que no la voy a volver a usar
            Game.p.getPlayerRoom().removeInteractuableItem(this); //saco esta exit de los interactuables que me muestra la habitación
            Game.p.getPlayerRoom().removeItem(this);
            System.out.println("Descartas: \"" + it.getItemName() + "\". Ya no lo necesitas");
            System.out.println("");
            return true;
        } else {
            return false;
        }
    }
}
