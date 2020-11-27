package Items;

import Interfaces.Interactuable;
import Interfaces.Storable;
import Interfaces.Usable;
import java.util.Scanner;
import textadventure.Game;
import static textadventure.Game.in;
import textadventure.Room;

public class PlacaMetalica extends Item implements Interactuable {

    static private Scanner in = new Scanner(System.in);
    private Llave opener;
    private Exit place;

    public PlacaMetalica(String itemName, String itemDescription, Room itemRoom, Llave opener) {
        super(itemName, itemDescription, itemRoom);
        this.opener = opener;
    }

    public void setPlace(Exit exit) {
        this.place = exit;
    }

    @Override
    public void interact() {
        if (Game.p.inventory.isEmpty()) {
            System.out.println("No tienes nada pasa usar");
        } else {
            System.out.println("");
            System.out.println("¿Qué vas a usar?");
            System.out.println("");
            Game.p.showInventory();
            System.out.println("");
            System.out.print("> ");
            String input = in.nextLine();
            if (!this.validateInteract(input)) {
                System.out.println("No puedes usar \"" + input + "\" aquí...");
            }
        }
    }

    public boolean validateInteract(String input) {
        for (Storable item : Game.p.inventory) { //busco en el inventario
            Item i = (Item) item; //guardo en Item el Storable
            if (input.equalsIgnoreCase(i.getItemName())) { //si mi entrada es igual al nombre del item
                if (opener.equals((Llave) i)) {
                    System.out.println("¡Has desatornillado la placa!");
                    Game.p.getPlayerRoom().removeItem(this); //borro la placa
                    Game.p.getPlayerRoom().removeInteractuableItem(this); //borro la placa
                    Game.p.getPlayerRoom().addItem((Item) this.place); //agrego el exit interactuable para que se vea
                    this.place.setInteractuable(null);
                    this.place.setIsBlocked(false);
                    this.place.setIsInteractuable(true);
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

}
