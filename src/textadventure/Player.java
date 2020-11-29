package textadventure;

import Items.Exit;
import Interfaces.Storable;
import Interfaces.Interactuable;
import Items.Item;
import Items.Palanca;
import java.util.*;
import static textadventure.Game.in;
import static textadventure.Game.p;

public class Player {

    public Scanner in = new Scanner(System.in);
    public static Player jugador;
    private Room playerRoom;
    public Set<Storable> inventory = new HashSet<>();

    private Player() {
    }

    public static Player getInstance() {
        if (jugador == null) {
            return new Player();
        }
        return jugador;
    }

    public Room getPlayerRoom() {
        return this.playerRoom;
    }

    public void setPlayerRoom(Room r) {
        this.playerRoom = r;
    }

    //metodo para moverse
    public void moverPlayer() {
        while (true) {
            System.out.println("Donde quieres ir? (n,s,e,w) o (b) para volver al menú.");
            String direction = in.nextLine();
            if (direction.equals("b")) {
                break;
            } else if (direction.equals("n") || direction.equals("s") || direction.equals("e") || direction.equals("w")) {
                Exit salida = this.playerRoom.isExit(direction);
                if (salida == null) {
                    System.out.println("No hay salida en esa dirección");
                    in.nextLine();
                } else if (salida.isOpened()) { //si la salida está aberta seteo nueva habitación y salgo del bucle.
                    this.setPlayerRoom(salida.getLeadsTo());
                    break;
                } else {
                    if (salida.getInteractuable() != null) { //si la salida requere interactuar con algún interactuable para abrirla/desbloquearla
                        Item s = (Item) salida.getInteractuable();
                        this.playerRoom.addItem(s); //agrego el interactuable a la lista de items visibles en la room
                        //System.out.println(s.getItemDescription()); //muestro la descripcion del interactuable
                        salida.getInteractuable().interact();
                        in.nextLine();
                        break;
                    } else {
                        System.out.println("");
                        System.out.println(salida.getClosedDescription()); //si no está abierta muestro su descripción de cerrada
                        salida.interact();
                        in.nextLine();
                        break;
                    }
                }
            } else {
                System.out.println("No es una dirección válida");
                in.nextLine();
            }
        }
    }

    public void takeItem() {
        if (this.playerRoom.getStorableItems().isEmpty()) { //si en esta habitación no hay items guardables
            System.out.println("No hay items que puedas tomar");
        } else {
            System.out.println("Puedes tomar los siguientes items, ¿cuál quieres? ");
            this.showAllStorableRoomItems(); //muestro todos los items que puedo tomar de la room
            System.out.println("");
            System.out.print("> ");
            String entry = in.nextLine(); //ingreso el item que quiero
            if (!this.validateTakeStorableItem(entry)) {
                System.out.println("No es un item válido");
            }//valido la entrada de teclado, si exite el item lo tomo, sino, imprimo "no es valido"
        }
    }

    public void removeItem(Storable item) { // para borrar items del inventario
        this.inventory.remove(item);
    }

    public void interactWith(String option) {
        if (option.equalsIgnoreCase("i")) { //si quiero interactuar con un interactuable del inventario
            this.validateInteract(this.getInteractuableInventory());
        } else if (option.equalsIgnoreCase("h")) { //si quiero interactuar con un interactuable de la room
            this.validateInteract(this.playerRoom.getInteractuableItems());
        } else {
            System.out.println("No es una opción válida");
        }
    }

    public void validateInteract(Set<Interactuable> interact) {
        if (interact.isEmpty()) { //si no tengo interactuables
            System.out.println("No hay ningún objeto con el que puedas interactuar");
        } else {
            System.out.println("¿Con qué objeto quieres interactuar?");
            this.showAllInteractuableItems(interact); //muestro los items interactuables del inventario/room
            System.out.println("");
            System.out.print("> ");
            String input = in.nextLine();
            if (!this.validateInteractuableItem(input, interact)) {
                System.out.println("No es un item correcto");
                //valido la entrada de teclado, si exite el item, interactúo, sino, imprimo "no es valido"
            }
        }
    }

    public Set<Interactuable> getInteractuableInventory() {
        Set<Interactuable> interactuableInventory = new HashSet<>();
        for (Storable item : inventory) {
            if (item instanceof Interactuable) {
                interactuableInventory.add((Interactuable) item);
            }
        }
        return interactuableInventory;
    }

    public void showInventory() {
        if (this.inventory.isEmpty()) {
            System.out.println("Aún no tienes nada en el inventario");
        } else {
            System.out.println("**INVENTARIO**");
            for (Storable item : inventory) {
                System.out.println("    -" + item);
            }
        }
    }

    public void showAllRoomItems() {
        System.out.println("Puedes ver... ");
        for (Item item : p.getPlayerRoom().getAllItems()) {
            System.out.println("    -" + item.getItemDescription());
        }
    }

    public void showAllStorableRoomItems() {
        for (Storable item : this.playerRoom.getStorableItems()) {
            System.out.println("    -" + item);
        }
    }

    public boolean validateTakeStorableItem(String input) {
        for (Storable item : this.playerRoom.getStorableItems()) {
            Item it = (Item) item;
            if (input.equalsIgnoreCase(it.getItemName())) {
                this.inventory.add((Storable) it);
                this.playerRoom.removeItem(it);
                if (it instanceof Interactuable) {
                    this.playerRoom.removeInteractuableItem((Interactuable) it);
                }
                System.out.println("Has tomado " + it.getItemName());
                return true;
            }
        }
        return false;
    }

    public void showAllInteractuableItems(Set<Interactuable> interact) {
        for (Interactuable item : interact) {
            Item it = (Item) item;
            System.out.println("    -" + it.getItemName());
        }
    }

    public boolean validateInteractuableItem(String input, Set<Interactuable> interact) {
        for (Interactuable item : interact) {
            Item i = (Item) item;
            if (input.equalsIgnoreCase(i.getItemName())) {
                item.interact();
                return true;
            }
        }
        return false;
    }

    public void choose() {
        System.out.println("Entras a la habitación pero...");
        System.out.println("***NO PUEDO VER NADA***");
        System.out.println("***EL INTERRUPTOR DEBE ESTAR CERCA...***");

        for (Interactuable item : this.playerRoom.getInteractuableItems()) {
            if (item instanceof Palanca) {
                item.interact();
                in.nextLine();
                Game.showRoom();
            }
        }
    }

}
