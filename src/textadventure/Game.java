package textadventure;

import Items.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class Game {

    //INICIALIZO ATRIBUTOS
    public static Scanner in = new Scanner(System.in); //atributo input
    public static Game juego; //instancia única de Game
    private static Player p = Player.getInstance(); //instancia única de Player
    private static Map<Integer, Room> rooms = new HashMap<>(); //Map con todas las Rooms
    private static Map<Integer, Item> items = new HashMap<>(); //Map con todos los Items
    private static Set<String> menu = new TreeSet<>(); //Conjunto con las opcinones del menú
    private static boolean isEnd = false; //atributo para cuando termine el juego

    //CONSTRUCTOR
    private Game() {

    }

    //PIDO PLAYER
    public static Player getPlayer() {
        return p;
    }

    //INICIALIZO TODO
    public static void init() throws FileNotFoundException, IOException, ParseException {

        //METODO PARA LEER JSON Y CREAR LOS OBJETOS
        Config config = Config.getInstance(); //creo instancia unica de configuracion
        JSONObject world = config.leerJson(); //leo el json
        config.crearObjetos(world); //creo los objetos
        config.setearObjectos(world); //seteo los objetos

        //AGREGO ACCIONES AL MENU
        menu.add("1. Moverse");
        menu.add("2. Tomar item");
        menu.add("3. Interactuar con... ");
        menu.add("4. Mostrar inventario");
        menu.add("q. Salir del juego");

        //SETEO LA HABITACION DE INICIO
        p.setPlayerRoom(Game.getAllRooms().get(100)); //Seteo al player en primer room
    }

    // GETTER DE TODOS LOS ROOMS E ITEMS
    public static Map<Integer, Room> getAllRooms() {
        return rooms;
    }

    public static Map<Integer, Item> getAllItems() {
        return items;
    }

    //=======================================================================
    //=======================================================================
    //PARA COMENZAR EL JUEGO EN EL MAIN
    public static void showIntroduction() {
        System.out.println("**********************************************");
        System.out.println("");
        System.out.println("No recuerdas bien cómo o por que....");
        System.out.println("Dónde te has perdido o dónde te has caido....");
        System.out.println("...");
        System.out.println("Vuelves a oir...vuelves a oler... madera...");
        System.out.println("Recuerdas tus manos, tus pies... tu cabeza...");
        System.out.println("...");
        System.out.println("Vuelves a ver... pero poca luz... vuelve la nitidez");
        System.out.println("...");
        System.out.println("-¿Por qué desperté?...aquí...");
        System.out.println("...");
        System.out.println("-¿Dónde estoy...?");
        System.out.println("...");
        System.out.println("");
        System.out.println("");
        System.out.println("**********************************************");
        System.out.println(">> Presiona ENTER para comenzar <<");
        System.out.print(">");
        in.nextLine();
    }

    //MUESTRO TITULO Y DESCRIPCION DE LA HABITACION 
    public static void showRoom() {
        System.out.println("===============================================================================");
        System.out.println(p.getPlayerRoom().getTitle()); //muestra el titulo de la habitación actual del jugador
        System.out.println("");
        System.out.println(p.getPlayerRoom().getDescription()); //muestro la descripcion de la habitación actual del jugador
        System.out.println("===============================================================================");
        if (p.getPlayerRoom().getTitle().equalsIgnoreCase("...")) {
            end();
            return;
        }
        if (p.getPlayerRoom().getAllItems().isEmpty()) { //Muestro los objetos de la habitación, si los hay
            System.out.println("");
            System.out.println(">>Ya no queda nada importante en esta habitación");
            System.out.println("");
        } else {
            p.showAllRoomItems();
        }
    }

    //MUESTRO MENU DE ACCIONES
    public static void showMenu() {
        System.out.println("_______________________________________________________________________________");
        System.out.println("");
        System.out.println("¿Qué quieres hacer?");
        for (String accion : menu) {
            System.out.println(accion);
        }
        System.out.print(">");

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
                p.interactWith(1);
                in.nextLine();
                break;
            case "4":
                p.showInventory();
                p.interactWith(2);
                in.nextLine();
                break;
            case "":
                System.out.println("Debes introducir una acción!");
                in.nextLine();
                break;
            default:
                System.out.println("No es una acción válida!");
                in.nextLine();
                break;
        }
    }

    //SALIDA - FIN DEL JUEGO
    public static void isEnd(boolean end) {
        isEnd = end;
    }

    public static boolean getEnd() {
        return isEnd;
    }

    public static void end() {
        isEnd(true);
        System.out.println("¡¡¡Finalmente eres libre!!!");
        in.nextLine();
        System.out.println("...");
        in.nextLine();
        System.out.println("¿ y ahora ?");
        in.nextLine();
        System.out.println("...");
        in.nextLine();
        System.out.println("***¡Gracias por Jugar!***");
        in.nextLine();
        System.out.println("Esta aventura de texto fue realizada por Luciano Marchese, Emiliano Santana y Verónica López Perea");
        in.nextLine();
    }
}
