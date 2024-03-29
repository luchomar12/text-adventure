package textadventure;

import Items.Exit;
import Interfaces.Interactuable;
import Items.Item;
import java.util.*;
import Interfaces.Guardable;

public class Player {

    private Scanner in = new Scanner(System.in);
    // PROFE: para que es este player estatico? mejor tener una instancia de game y pedir el jugador ahi //RESUELTO OK
    private Room playerRoom;
    private Set<Guardable> inventory = new HashSet<>(); // PROFE: hacer un getter mejor de atributos publicos//RESUELTO OK
    private boolean gano = false;

    public Player() {}// PROFE: los metodos vacios escribanlos asi: {} sin enter en el medio//RESUELTO OK

    public boolean isGano() {
        return this.gano;
    }

    public Room getPlayerRoom() {
        return this.playerRoom;
    }

    public void setPlayerRoom(Room room) {
        this.playerRoom = room;
    }

    public void addInventory(Guardable item) {
        this.inventory.add(item);
    }

    public void removeItem(Guardable item) { // para borrar items del inventario
        this.inventory.remove(item);
    }
    
    public Set<Guardable> getInventory(){
        return this.inventory;
    }

    //MOVER JUGADOR
    public void moverPlayer() {
        // PROFE: pueden hacer que las direcciones que se muestran sean solo las que haya realmente algo en esa direccion?
        //RESUELTO OK
        System.out.println("¿A qué dirección? (n/s/e/o)| (b) Volver al menú.");
        System.out.print("Ves salida al: ");
        for(Exit e : getPlayerRoom().getExits()){ //muestro direcciones disponibles
            System.out.print(e.getDirection().toUpperCase());
            System.out.print(", ");
        }
        System.out.println("\b\b");
        System.out.print("> ");
        String direction = in.nextLine();
        if (direction.equals("b")) {
            return;
        } else if (direction.equals("n") || direction.equals("s") || direction.equals("e") || direction.equals("o")) {
            this.validateMover(direction); //valido la dirección para moverme
        } else {
            System.out.println("No es una dirección válida");
            in.nextLine();
        }
    }

    public void validateMover(String direction) {
        Exit exit = this.playerRoom.isExit(direction); //si hay salida este metodo me devuelve la Exit
        if (exit == null) { // si la salida es null es que no hay salida en esa dirección
            System.out.println("No hay salida en esa dirección");
        } else if (exit.isOpened()) { //si la salida está aberta seteo nueva habitación
            this.setPlayerRoom(exit.getLeadsTo());
            System.out.println("Pasas hacia...");
        } else { //la salida está cerrada, interactuo con la salida
            this.moverConInteractuables(exit);
        }
    }

    public void moverConInteractuables(Exit exit) {
        Interactuable i = exit.getInteractuable(); //busco el interactuable de esta Exit
        if (i == null) { //si la salida no tiene (o ya no tiene) un interactuable sólo está cerrada
            System.out.println("");
            System.out.println(exit.getItemDescription()); //muestro su descripción (me mostrará si requiere de una llave o no)
            if (exit.getOpener() != null) { //si la salida requiere de una llave entonces interactúo
                exit.interact();
            }
            in.nextLine();
        } else { //si la salida tiene un intermediario Interactuable
            Item it = (Item) i;
            System.out.println("Tiene un/a " + it.getItemName());
            i.interact(); //interactuo con el Interactuable
            in.nextLine();
        }
    }

    //TOMAR OBJETOS
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

    public boolean validateTakeStorableItem(String input) {
        for (Guardable item : this.playerRoom.getStorableItems()) {
            Item it = (Item) item;
            if (input.equalsIgnoreCase(it.getItemName())) {
                this.inventory.add((Guardable) it);
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

    //INTERACTUAR CON OBJETOS
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

    //METODOS DE INVENTARIO
    public void showInventory() {
        if (this.inventory.isEmpty()) {
            System.out.println("- Aún no tienes nada en el inventario");
        } else {
            System.out.println("**INVENTARIO**");
            for (Guardable item : inventory) {
                System.out.println("    -" + item);
            }
        }
    }

    public Set<Interactuable> getInteractuableInventory() {
        Set<Interactuable> interactuableInventory = new HashSet<>();
        for (Guardable item : inventory) {
            if (item instanceof Interactuable) {
                interactuableInventory.add((Interactuable) item);
            }
        }
        return interactuableInventory;
    }

    //MOSTRAR ITEMS
    public void showAllRoomItems() {
        System.out.println("Puedes ver... ");
        for (Item item : getPlayerRoom().getAllItems()) {
            System.out.println("    -" + item.getItemDescription());
        }
    }

    public void showAllStorableRoomItems() {
        for (Guardable item : this.playerRoom.getStorableItems()) {
            System.out.println("    -" + item);
        }
    }

    public void showAllInteractuableItems(Set<Interactuable> interact) {
        for (Interactuable item : interact) {
            Item it = (Item) item;
            System.out.println("    -" + it.getItemName());
        }
    }

    //GANAR
    public void win() {
        this.gano = true;
    }

}
