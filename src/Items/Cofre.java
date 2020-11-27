package Items;

import Interfaces.Abrible;
import Interfaces.Storable;
import Interfaces.Usable;
import Interfaces.Interactuable;
import java.util.*;
import textadventure.*;
import static textadventure.Game.in;

public class Cofre extends Item implements Interactuable, Abrible {

    //Los cofres estarán cerrados siempre hasta que un Usable los abra
    private Usable opener; //el item Usable que lo abre
    private Set<Storable> treasures = new HashSet<>(); //items dentro del cofre

    public Cofre(String name, String description, Room itemRoom) {
        super(name, description, itemRoom);
    }

    public void setOpener(Usable item) {
        this.opener = item;
    }

    public Usable getOpener() {
        return this.opener;
    }

    public void getTreasures() {
        for (Storable treasure : treasures) {
            System.out.println("    -" + treasure);
            Game.p.inventory.add(treasure);
        }
    }

    public void addTreasure(Storable item) {
        treasures.add(item);
    }

    public void removeTreasure(Storable item) {
        treasures.remove(item);
    }

    @Override
    public void interact() {
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
                System.out.println("No puedes usar -" + input + "-");
            }
        }
    }

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

    @Override
    public boolean openWith(Usable item) {
        if (item.equals(opener)) {
            System.out.println("");
            System.out.println(">> ¡Abriste el cofre! <<");
            System.out.println("");
            System.out.println("Encuentras y guardas: ");
            this.getTreasures();
            Game.p.getPlayerRoom().removeItem((Item) this);
            Game.p.getPlayerRoom().removeInteractuableItem(this); //saco el cofre de la habitación porque ya no lo necesito
            return true;
        }
        return false;
    }
}
