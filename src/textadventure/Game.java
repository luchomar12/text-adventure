package textadventure;


import Items.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class Game {

    //INICIALIZO ATRIBUTOS
    public static Scanner in = new Scanner(System.in);
    public static Game juego;
    public static Player p = Player.getInstance();
    public static Map<Integer, Room> rooms = new HashMap<>();
    public static Map<Integer, Exit> exits = new HashMap<>();
    private static Map<Integer, Llave> llaves = new HashMap<>();
    private static Map<Integer, Contenedor> contenedores = new HashMap<>();
    private static Map<Integer, Nota> notas = new HashMap<>();
    private static Map<Integer, Interruptor> interruptores = new HashMap<>();
    private static Map<Integer, Tablero> tableros = new HashMap<>();
    private static Map<Integer, Bloqueador> bloqueadores = new HashMap<>();
    private static Map<Integer, Item> items = new HashMap<>();
    private Set<String> menu = new TreeSet<>();
    private boolean isEnd = false;

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
    public Player getPlayer() {
        return this.p;
    }

    //INICIALIZO TODO
    public void init() throws FileNotFoundException, IOException, ParseException {
        
        //METODO PARA LEER JSON Y CREAR LOS OBJETOS
        Config config = Config.getInstance(); //creo instancia unica de configuracion
        JSONObject world = config.leerJson();
        config.crearObjetos(world);
        config.setearObjectos(world);
        
        //AGREGO ACCIONES AL MENU
        menu.add("1. Moverse");
        menu.add("2. Tomar item");
        menu.add("3. Interactuar con... ");
        menu.add("4. Mostrar inventario");
        menu.add("q. Salir del juego");

        //SETEO LA HABITACION DE INICIO
        p.setPlayerRoom(Game.getAllRooms().get(100)); //Seteo al player en primer room
    }

    // GETTER DE TODOS LOS MAPS DE OBJETOS
    public static Map<Integer, Room> getAllRooms() {
        return rooms;
    }

    public static Map<Integer, Exit> getAllExits() {
        return exits;
    }

    public static Map<Integer, Llave> getAllLlaves() {
        return llaves;
    }

    public static Map<Integer, Tablero> getAllTableros() {
        return tableros;
    }

    public static Map<Integer, Contenedor> getAllContenedores() {
        return contenedores;
    }

    public static Map<Integer, Nota> getAllNotas() {
        return notas;
    }

    public static Map<Integer, Interruptor> getAllInterruptores() {
        return interruptores;
    }

    public static Map<Integer, Bloqueador> getAllBloqueadores() {
        return bloqueadores;
    }

    public static Map<Integer, Item> getAllItems() {
        return items;
    }
    
 
    //=======================================================================
    //=======================================================================
    
    //PARA COMENZAR EL JUEGO EN EL MAIN
    //MUESTRO TITULO Y DESCRIPCION DE LA HABITACION 
    public void showRoom() {
        System.out.println("========================================================");
        System.out.println(p.getPlayerRoom().getTitle()); //muestra el titulo de la habitación actual del jugador
        System.out.println("========================================================");
        System.out.println(p.getPlayerRoom().getDescription()); //muestro la descripcion de la habitación actual del jugador
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
    public void showMenu() {
        System.out.println("-----------------------");
        System.out.println("¿Qué quieres hacer?");
        for (String accion : menu) {
            System.out.println(accion);
        }

    }
    //INTERPRETO LA ELECCION DEL MENU Y LLAMO A SU CORRESPONDIENTE METODO
    public void actionMenu(String entry) {
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
    public void isEnd(boolean end) {
        this.isEnd = end;
    }

    public boolean getEnd() {
        return this.isEnd;
    }

    public void end() {
        this.isEnd(true);
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
