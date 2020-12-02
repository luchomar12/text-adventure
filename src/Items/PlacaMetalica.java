package Items;

import Interfaces.Interactuable;
import Interfaces.Storable;
import Interfaces.Usable;
import textadventure.Game;
import static textadventure.Game.in;

public class PlacaMetalica extends Item implements Interactuable {

    //ATRIBUTOS
    private Llave opener; //en estos casos sería el destornillador que es de tipo Llave
    private Item isOn;
    //INTs
    private int intIsOn;
    private int intOpener;

    //CONSTRUCTOR
    public PlacaMetalica(int code, String itemName, String itemDescription, int pIsOn, int pOpener) {
        super(code, itemName, itemDescription);
        this.intIsOn = pIsOn;
        this.intOpener = pOpener;
    }

    public int getIntIsOn() {
        return intIsOn;
    }

    public int getIntOpener() {
        return intOpener;
    }

    public void setIsOn(Item item) {
        this.isOn = item;
    }

    public void setOpener(Llave item) {
        this.opener = item;
    }

    public Llave getOpener() {
        return this.opener;
    }

    public Item getIsOn() {
        return this.isOn;
    }

    @Override
    public void interact() {
        System.out.println(this.itemDescription); //muestro la descripcion de la placa
        if (Game.p.inventory.isEmpty()) { //si no tengo nada para abrirlo
            System.out.println("");
            System.out.println("No tienes nada pasa usar");
        } else {
            in.nextLine();
            System.out.println("¿Qué vas a usar?");
            System.out.println("");
            Game.p.showInventory();
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
    }

    @Override
    public boolean validateInteract(String input) {
        for (Storable item : Game.p.inventory) { //busco en el inventario
            Item i = (Item) item; //guardo en Item el Storable
            if (input.equalsIgnoreCase(i.getItemName())) { //si mi entrada es igual al nombre del item
                if (i instanceof Usable) { //si lo que elijo es un usable
                    if (this.getOpener().equals((Usable) i)) { //si el opener es igual mi elección  
                        System.out.println("¡Has desatornillado la placa!");
                        Game.p.getPlayerRoom().removeItem(this); //borro la placa
                        if (this.getIsOn() instanceof Exit) {
                            Exit e = (Exit) this.getIsOn();
                            e.setInteractuable(null); //saco la placa de la Exit
                        }
                        if (this.getIsOn() instanceof Cofre) {
                            Cofre c = (Cofre) this.getIsOn();
                            c.setInteractuable(null); //saco la placa del Cofre
                            c.open();
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
