package Items;

import Interfaces.Interactuable;
import Interfaces.Usable;
import Interfaces.Guardable;
import java.util.Scanner;
import org.json.simple.JSONObject;
import textadventure.Game;

public class Bloqueador extends Item implements Interactuable {

    //ATRIBUTOS
    private Game juego = Game.dameInstancia();
    private Llave opener; //en estos casos sería el destornillador que es de tipo Llave
    private Item isOn;
    private Scanner in = new Scanner(System.in);
    private String openedDescription;

    //CONSTRUCTOR
    public Bloqueador(int code, String itemName, String itemDescription, String openedDescription) {
        super(code, itemName, itemDescription);
        this.openedDescription = openedDescription;
    }

    public static Bloqueador leerJson(JSONObject obj) {
        int bCode = (int) (long) obj.get("bloqueadorCode");
        String bTitle = (String) obj.get("bloqueadorName");
        String bDescription = (String) obj.get("bloqueadorDescription");
        String bOpenedDescription = (String) obj.get("bloqueadorOpenedDescription");
        Bloqueador b = new Bloqueador(bCode, bTitle, bDescription, bOpenedDescription);
        return b;
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
        if (juego.getPlayer().getInventory().isEmpty()) { //si no tengo nada para abrirlo
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

    /* 
    PROFE: esta hardcodeado el mensaje "¡Has desatornillado la placa!"
    Hay un unico bloqueador y es una placa y se abre con el destornillador?
    Deberian poner el mensaje de desbloqueo en el JSON
    
    RESUELTO OK
     */
    @Override
    public boolean validateInteract(String input) {
        for (Guardable item : juego.getPlayer().getInventory()) { //busco en el inventario
            Item i = (Item) item; //guardo en Item el Storable
            /*
            PROFE: estos 3 ifs son muy confusos.
            Pueden descartar primero si no es un usable if (!(i instanceof Usable)) continue;
            y luego hacer un if con las otras dos condiciones con &&
            
            RESUELTO OK
             */
            if (!(i instanceof Usable)) {
                continue;//salto a la siguiente iteración hasta que el item sea Usable
            }
            if (input.equalsIgnoreCase(i.getItemName()) && this.getOpener().equals((Usable) i)) {
                System.out.println(">> "+this.openedDescription+" <<"); //muestro el string para cuando se abre o desbloquea
                juego.getPlayer().getPlayerRoom().removeItem(this); //borro el bloqueador
                if (this.getIsOn() instanceof Exit) {
                    Exit e = (Exit) this.getIsOn();
                    e.setInteractuable(null); //saco el bloqueador de la Exit
                }
                if (this.getIsOn() instanceof Contenedor) {
                    Contenedor c = (Contenedor) this.getIsOn();
                    c.setInteractuable(null); //saco el bloqueador del Contenedor
                    c.open(); //abro el contenedor
                }
                return true;
            }
        }
        return false;
    }
}
