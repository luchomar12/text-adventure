package Items;

import Interfaces.Interactuable;
import Interfaces.Usable;
import Interfaces.Guardable;
import static textadventure.Game.in;
import static textadventure.Game.juego;

public class Bloqueador extends Item implements Interactuable {

    //ATRIBUTOS
    private Llave opener; //en estos casos sería el destornillador que es de tipo Llave
    private Item isOn;

    //CONSTRUCTOR
    public Bloqueador(int code, String itemName, String itemDescription) {
        super(code, itemName, itemDescription);
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
        System.out.println(this.itemDescription); //muestro la descripcion del bloqueador
        if (juego.getPlayer().inventory.isEmpty()) { //si no tengo nada para abrirlo
            System.out.println("");
            System.out.println("No tienes nada pasa usar");
        } else {
            in.nextLine();
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
    }

    @Override
    public boolean validateInteract(String input) {
        for (Guardable item : juego.getPlayer().inventory) { //busco en el inventario
            Item i = (Item) item; //guardo en Item el Storable
            if (input.equalsIgnoreCase(i.getItemName())) { //si mi entrada es igual al nombre del item
                if (i instanceof Usable) { //si lo que elijo es un usable
                    if (this.getOpener().equals((Usable) i)) { //si el opener es igual mi elección  
                        System.out.println("¡Has desatornillado la placa!");
                        juego.getPlayer().getPlayerRoom().removeItem(this); //borro el bloqueador
                        if (this.getIsOn() instanceof Exit) {
                            Exit e = (Exit) this.getIsOn();
                            e.setInteractuable(null); //saco el bloqueador de la Exit
                        }
                        if (this.getIsOn() instanceof Contenedor) {
                            Contenedor c = (Contenedor) this.getIsOn();
                            c.setInteractuable(null); //saco el bloqueador del Contenedor
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
