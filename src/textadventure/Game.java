package textadventure;

import Items.*;
import java.util.*;

public class Game {

    //INICIALIZO ATRIBUTOS
    public static Scanner in = new Scanner(System.in);
    public static Game juego;
    public static Player p = Player.getInstance();
    public static Set<Room> rooms = new HashSet<>();
    public static Set<String> menu = new TreeSet<>();

    //CONSTRUCTOR
    private Game() {
    }

    //PIDO INSTANCIA UNICA
    public static Game getInstance() {
        if (juego == null) {
            return new Game();
        }
        return juego;
    }
    
    //PIDO PLAYER
    public Player getPlayer(){
        return this.p;
    }

    //INICIALIZO ITEMS, HABITACIONES, MENU, ETC 
    public void init() {

        //INICIALIZO ITEMS
        Llave llave1 = new Llave("Llave de cobre", "Una pequeña llave de cobre, quizás abra algo...");
        Llave llave2 = new Llave("Llave plateada", "Una pesada llave de plata. Parece abrir algo importante");
        Cofre cofre = new Cofre("Cofre", "Un cofre mediano de madera, tiene una cerradura");
        cofre.setOpener(llave2);
        cofre.addTreasure(new Llave("Llave dorada", "Parece la mas importante de todas"));
        

        //INICIALIZO HABITACIONES
        Room r1 = new Room();
        Room r2 = new Room();
        Room r3 = new Room();
        Room r4 = new Room();
        Room rs = new Room(); //salida

        //SETEO LAS HABITACIONES
        //Room 1
        r1.setTitle("Armario");
        r1.setDescription("Te encuentras en un amplio armario");
        r1.addItem(llave2);
        r1.addItem(cofre);
        r1.addExit(new Exit("n", r2)); //salida al norte a room2

        //Room 2
        r2.setTitle("Pasillo");
        r2.setDescription("Un tenue pasillo, ves una soga en el piso");
        //r2.addItem(soga);
        r2.addItem(cofre);
        r2.addExit(new Exit("s", r1)); //salida al sur a room1
        r2.addExit(new Exit("e", r3)); //salida al este a room3

        //Room 3
        r3.setTitle("Sala");
        r3.setDescription("Parece una sala de estar, hay una nota sobre la mesa y además un cofre en una esquina");
        //r3.addItem(nota);
        r3.addExit(new Exit("w", r2)); //salida al oeste a room2
        r3.addExit(new Exit("s", r4)); //salida al sur a room4

        //Room 4
        r4.setTitle("Habitación");
        r4.setDescription("Es una pequeña habitación, ves un tablero en la puerta de enfrente");
        //r4.addItem(tablero);
        r4.addExit(new Exit("n", r3));  //salida al norte a room3
        r4.addExit(new Exit("s", rs)); //salida al sur y gana juego

        //Room 5
        rs.setTitle("Salida!");
        rs.setDescription("Bien hecho! has encontrado la salida!");

        //AGREGO HABITACIONES AL CONJUNTO ROOMS
        rooms.add(r1);
        rooms.add(r2);
        rooms.add(r3);
        rooms.add(r4);
        rooms.add(rs);

        //AGREGO ACCIONES AL MENU
        menu.add("1. Moverse");
        menu.add("2. Tomar item");
        menu.add("3. Interactuar con... ");
        menu.add("4. Mostrar inventario");
        menu.add("q. Salir del juego");

        //SETEO LA HABITACION DE INICIO
        p.setPlayerRoom(r1);

    }

    //=======================================================================
    //PARA COMENZAR EL JUEGO EN EL MAIN
    //MUESTRO TITULO Y DESCRIPCION DE LA HABITACION 
    public static void showRoom() {
        System.out.println("========================================================");
        System.out.println(p.getPlayerRoom().getTitle()); //muestra el titulo de la habitación actual del jugador
        System.out.println(p.getPlayerRoom().getDescription()); //muestro la descripcion de la habitación actual del jugador
        if (p.getPlayerRoom().getAllItems().isEmpty()) { //Muestro los objetos de la habitación, si los hay
            System.out.println("Ya no hay nada importante en esta habitación");
        } else {
            System.out.println("En la habitación encuentras los siguientes objetos: ");
            for (Item item : p.getPlayerRoom().getAllItems()) {
                System.out.println("    -" + item);
            }
        }
    }

    //MUESTRO MENU DE ACCIONES
    public static void showMenu() {
        System.out.println("-----------------------");
        System.out.println("¿Qué quieres hacer?");
        if (!p.getPlayerRoom().getTitle().equals("Salida!")) { // SI No es el room SALIDA muestro las opciones
            for (String accion : menu) {
                System.out.println(accion);
            }
        }

    }

    //INTERPRETO LA ELECCION DEL MENU Y LLAMO A SU CORRESPONDIENTE METODO
    public static void actionMenu(String entry) {
        entry = entry.trim().toLowerCase();
        switch (entry) {
            case "1":
                p.moverPlayer();
                break;
            case "2":
                p.takeItem();
                in.nextLine();
                break;
            case "3":
                p.interactWith();
                in.nextLine();
                break;
            case "4":
                p.showInventory();
                in.nextLine();
                break;
            case "":
                System.out.println("Debes introducir una acción!");
                in.nextLine();
                break;
            default:
                System.out.println("No es una acción válida!");
                in.nextLine();
        }
    }
}
