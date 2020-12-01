package textadventure;

import Items.Exit;
import Interfaces.Storable;
import Interfaces.Interactuable;
import Items.Item;
import Items.Palanca;
import java.util.*;
import static textadventure.Game.p;
import static textadventure.Game.in;
import static textadventure.Game.juego;

public class Player {

    public static Player jugador;
    private Room playerRoom;
    public Set<Storable> inventory = new HashSet<>();
    private boolean gano = false;

    private Player() {
    }

    public boolean isGano() {
        return this.gano;
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

    public void setPlayerRoom(Room room){
        this.playerRoom = room;
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
                    System.out.println("Pasas hacia...");
                    in.nextLine();
                    break;
                } else {
                    Interactuable i = salida.getInteractuable();
                    if (i != null) { //si la salida requere interactuar con algún interactuable para abrirla/desbloquearla
                        Item it = (Item) i;
                        this.playerRoom.addItem(it); //agrego el interactuable a la lista de items visibles en la room    
                        i.interact();
                        in.nextLine();
                        break;
                    } else { //si NO tiene interactuable sólo está CERRADA
                        if (salida.getOpener() != null) { //si la salida tiene llave para abrirse
                            System.out.println("");
                            System.out.println(salida.getItemDescription()); //si no está abierta muestro su descripción
                            salida.interact();
                            in.nextLine();
                            break;
                        } else {//si no tiene llave se abre de alguna otra manera (palanca), no hace falta interactuar
                            System.out.println("");
                            System.out.println(salida.getItemDescription()); //si no está abierta muestro su descripción
                            in.nextLine();
                            break;
                        }
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
            String input = in.nextLine(); //ingreso el item que quiero
            if (!this.validateTakeStorableItem(input)) {
                if (input.equalsIgnoreCase("")) {
                    System.out.println("No has hecho nada...");
                } else {
                    System.out.println("No es un item correcto");
                    //valido la entrada de teclado, si exite el item, interactúo, sino, imprimo "no es valido"
                }
            }
        }
    }

    public void removeItem(Storable item) { // para borrar items del inventario
        this.inventory.remove(item);
    }

    public void interactWith(int option) {
        if (option == 2) { //si quiero interactuar con un interactuable del inventario
            if (!this.inventory.isEmpty()) { //Si el inventario no está vacío                
                this.validateInteract(this.getInteractuableInventory());
            }
        } else if (option == 1) { //si quiero interactuar con un interactuable de la room
            this.validateInteract(this.playerRoom.getInteractuableItems());
        } else {
            System.out.println("No es una opción válida");
        }
    }

    public void validateInteract(Set<Interactuable> interact) {
        if (interact.isEmpty()) { //si no tengo interactuables
            System.out.println("- No hay ningún objeto con el que puedas interactuar");
        } else {
            System.out.println("¿Con qué quieres interactuar?");
            this.showAllInteractuableItems(interact); //muestro los items interactuables del inventario/room
            System.out.println("");
            System.out.print("> ");
            String input = in.nextLine();
            if (!this.validateInteractuableItem(input, interact)) {
                if (input.equalsIgnoreCase("")) {
                    System.out.println("No has hecho nada...");
                } else {
                    System.out.println("No es un item correcto");
                    //valido la entrada de teclado, si exite el item, interactúo, sino, imprimo "no es valido"
                }
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
            System.out.println("- Aún no tienes nada en el inventario");
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
                    this.playerRoom.removeItem(it);
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
                juego.showRoom();
            }
        }
    }

    public void win() {
        this.gano = true;
    }

}
