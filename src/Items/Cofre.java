package Items;

import Interfaces.*;
import java.util.*;
import textadventure.*;
import static textadventure.Game.in;

public class Cofre extends Item implements Interactuable, Abrible {

    //ATRIBUTOS
    private Usable opener; //el item Usable que lo abre
    private Interactuable interactuable; //el Interactuable que bloquea al Cofre (opcional)
    private Set<Storable> treasures = new HashSet<>(); //items dentro del cofre

    //CONSTRUCTOR
    public Cofre(int code, String name, String description) {
        super(code, name, description);
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
        return interactuable;
    }

    public void setInteractuable(Interactuable interactuable) {
        this.interactuable = interactuable;
    }

    //TREASURES
    public void getTreasures() {
        for (Storable treasure : treasures) {
            System.out.println("    -" + treasure);
            Game.p.inventory.add(treasure); //Agrego los tesoros al inventario
        }
    }

    public void addTreasure(Storable item) {
        treasures.add(item);
    }

    public void removeTreasure(Storable item) {
        treasures.remove(item);
    }

    //INTERFACE Interactuable
    @Override
    public void interact() {
        if (this.getInteractuable() != null) { //si TIENE un interactuable
            Item i = (Item) this.getInteractuable();
            System.out.println("Tiene un/a " + i.getItemName()); //Muestro el nombre del interactuable
            this.getInteractuable().interact(); //Interactúo con el Interactuable
        } else { // si NO tiene interactuable tiene llave
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
                    if(input.equalsIgnoreCase("")){
                        System.out.println("No has hecho nada...");
                    }else{
                        System.out.println("No es el item correcto...");
                    }
                }
            }
        }
    }

    @Override
    public boolean validateInteract(String input) {
        for (Storable item : Game.p.inventory) { //busco en el inventario
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
        if (item.equals(opener)) {
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
        Game.p.getPlayerRoom().removeItem((Item) this);//saco el cofre de la habitación porque ya no lo necesito
    }
}
