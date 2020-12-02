package Items;

import Interfaces.*;
import java.util.*;
import textadventure.*;
import static textadventure.Game.in;

public class Contenedor extends Item implements Interactuable, Abrible {

    //ATRIBUTOS
    private Usable opener; //el item Usable que lo abre
    private Interactuable interactuable; //el Interactuable que bloquea al contenedor (opcional)
    private Set<Guardable> treasures = new HashSet<>(); //items dentro del contenedor
    //INTs
    private int intOpener;
    private int intInteractuable;
    private Set<Integer> intTreasures = new HashSet<>();

    //CONSTRUCTOR
    public Contenedor(int code, String name, String description, int opener, int interactuable, Set<Integer> treasures) {
        super(code, name, description);
        this.intOpener = opener;
        this.intInteractuable = interactuable;
        this.intTreasures = treasures;
    }

    public int getIntOpener() {
        return intOpener;
    }

    public int getIntInteractuable() {
        return intInteractuable;
    }

    public Set<Integer> getIntTreasures() {
        return intTreasures;
    }

    //OPENER
    public void setOpener(Usable item) {

        this.opener = item;
    }

    public Usable getOpener() {
        return this.opener;
    }

    //INTERACTUABLE
    public Interactuable getInteractuable() {
        return this.interactuable;
    }

    public void setInteractuable(Interactuable interactuable) {
        this.interactuable = interactuable;
    }

    //TREASURES
    public void getTreasures() {
        for (Guardable treasure : treasures) {
            System.out.println("    -" + treasure);
            Game.p.inventory.add(treasure); //Agrego los tesoros al inventario
        }
    }

    public void addTreasure(Guardable item) {
        treasures.add(item);
    }

    public void removeTreasure(Guardable item) {
        treasures.remove(item);
    }

    //INTERFACE Interactuable
    @Override
    public void interact() {
        if (this.getInteractuable() == null) { // si NO tiene interactuable tiene llave
            System.out.println("Para abrirlo debes usar una llave");
            System.out.println("");
            if (Game.p.inventory.isEmpty()) {
                System.out.println("No tienes nada pasa usar");
            } else {
                System.out.println("¿Qué vas a usar?");
                System.out.println("");
                Game.p.showInventory();
                System.out.println("");
                System.out.print("> ");
                String input = in.nextLine();
                if (!this.validateInteract(input)) {
                    if (input.equalsIgnoreCase("")) {
                        System.out.println("No has hecho nada...");
                    } else {
                        System.out.println("No es el item correcto...");
                    }
                }
            }
        } else { //si TIENE un interactuable
            Item i = (Item) this.getInteractuable();
            System.out.println("Tiene un/a " + i.getItemName()); //Muestro el nombre del interactuable
            this.getInteractuable().interact(); //Interactúo con el Interactuable
        }
    }

    @Override
    public boolean validateInteract(String input) {
        for (Guardable item : Game.p.inventory) { //busco en el inventario
            Item obj = (Item) item; //guardo en Item el Storable
            if (input.equalsIgnoreCase(obj.getItemName())) { //si mi entrada es igual al nombre del item
                Usable llave = (Usable) obj;
                return this.openWith(llave);
            }
        }
        return false;
    }

    //INTERFACE Abrible
    @Override
    public boolean openWith(Usable item) {
        if (item.equals(this.getOpener())) {
            this.open();
            return true;
        }
        return false;
    }

    public void open() {
        System.out.println("");
        System.out.println(">> ¡Lo abriste! <<");
        System.out.println("");
        System.out.println("Encuentras y guardas: ");
        this.getTreasures();
        Game.p.getPlayerRoom().removeItem((Item) this);//saco el contenedor de la habitación porque ya no lo necesito
    }
}
