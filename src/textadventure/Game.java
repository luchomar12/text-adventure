package textadventure;

import Interfaces.Interactuable;
import Interfaces.Storable;
import Interfaces.Usable;
import Items.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Game {

    //INICIALIZO ATRIBUTOS
    public static Scanner in = new Scanner(System.in);
    public static Game juego;
    public static Player p = Player.getInstance();
    public static Map<Integer, Room> rooms = new HashMap<>();
    public static Map<Integer, Exit> exits = new HashMap<>();
    private static Map<Integer, Llave> llaves = new HashMap<>();
    private static Map<Integer, Cofre> cofres = new HashMap<>();
    private static Map<Integer, Nota> notas = new HashMap<>();
    private static Map<Integer, Palanca> palancas = new HashMap<>();
    private static Map<Integer, Tablero> tableros = new HashMap<>();
    private static Map<Integer, PlacaMetalica> placas = new HashMap<>();
    private static Map<Integer, Item> items = new HashMap<>();
    private Set<String> menu = new TreeSet<>();
    private boolean end = false;

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

    //INICIALIZO ITEMS, HABITACIONES, MENU, ETC 
    public void init() throws FileNotFoundException, IOException, ParseException {
        FileReader file = new FileReader("src/Items/prueba.json");
        JSONParser parser = new JSONParser();
        JSONObject jsonWorld = (JSONObject) parser.parse(file);

        //CREO LOS JSON ARRAYS
        JSONArray jsonRooms = (JSONArray) jsonWorld.get("Rooms");
        JSONArray jsonExits = (JSONArray) jsonWorld.get("Exits");
        JSONArray jsonLlaves = (JSONArray) jsonWorld.get("Llaves");
        JSONArray jsonCofres = (JSONArray) jsonWorld.get("Cofres");
        JSONArray jsonNotas = (JSONArray) jsonWorld.get("Notas");
        JSONArray jsonPalancas = (JSONArray) jsonWorld.get("Palancas");
        JSONArray jsonTableros = (JSONArray) jsonWorld.get("Tableros");
        JSONArray jsonPlacas = (JSONArray) jsonWorld.get("Placas");

        //CREO ROOMS
        for (Object room : jsonRooms) {
            JSONObject obj = (JSONObject) room;
            int rCode = (int) (long) obj.get("roomCode");
            String rTitle = (String) obj.get("roomTitle");
            String rDescription = (String) obj.get("roomDescription");
            JSONArray roomExits = (JSONArray) obj.get("roomExits"); //guardo las exits en un array
            JSONArray roomItems = (JSONArray) obj.get("roomItems");
            Room r = new Room(rCode, rTitle, rDescription);//creo la room
            for (Object o : roomExits) { //agrego el codigo de las exits
                if (o != null) {
                    int ob = (int) (long) o;
                    r.addIntegerExits(ob);
                }
            }
            for (Object o : roomItems) { //agrego el codigo de los items
                if (o != null) {
                    int ob = (int) (long) o;
                    r.addIntegerItems(ob);
                }
            }
            rooms.put(r.getRoomCode(), r);//agrego la room a la lista de rooms
        }
        //CREO EXITS
        for (Object exit : jsonExits) {
            JSONObject obj = (JSONObject) exit;
            int eCode = (int) (long) obj.get("exitCode");
            String eTitle = (String) obj.get("exitName");
            String eDescription = (String) obj.get("exitDescription");
            String eDirection = (String) obj.get("exitDirection");
            boolean eOpened = (boolean) obj.get("exitOpened");
            int eLeadsTo = (int) (long) obj.get("exitLeadsTo");
            int eOpener = (int) (long) obj.get("exitOpener");
            int eInteractuable = (int) (long) obj.get("exitInteractuable");
            Exit e = new Exit(eCode, eTitle, eDescription, eDirection, eOpened, eLeadsTo, eOpener, eInteractuable);
            exits.put(e.getExitCode(), e);
            items.put(e.getExitCode(), e);
        }

        //CREO COFRES
        for (Object cofre : jsonCofres) {
            JSONObject obj = (JSONObject) cofre;
            int cCode = (int) (long) obj.get("cofreCode");
            String cTitle = (String) obj.get("cofreName");
            String cDescription = (String) obj.get("cofreDescription");
            int cOpener = (int) (long) obj.get("cofreOpener");
            int cInteractuable = (int) (long) obj.get("cofreInteractuable");
            JSONArray cTreasures = (JSONArray) obj.get("cofreTreasures");
            Set<Integer> treasures = new HashSet<>();
            for (Object o : cTreasures) {
                if (o != null) {
                    int ob = (int) (long) o;
                    treasures.add(ob);
                }
            }
            Cofre c = new Cofre(cCode, cTitle, cDescription, cOpener, cInteractuable, treasures);
            cofres.put(c.getItemCode(), c);
            items.put(c.getItemCode(), c);
        }

        //CREO TABLEROS
        for (Object tablero : jsonTableros) {
            JSONObject obj = (JSONObject) tablero;
            int tCode = (int) (long) obj.get("tableroCode");
            String tTitle = (String) obj.get("tableroName");
            String tDescription = (String) obj.get("tableroDescription");
            boolean tIsOn = (boolean) obj.get("tableroIsOn");
            String tInfo = (String) obj.get("tableroInfo");
            int tIn = (int) (long) obj.get("tableroIn");
            JSONArray tPass = (JSONArray) obj.get("tableroPass");
            List<String> password = new ArrayList<>();
            for (Object o : tPass) {
                if (o != null) {
                    String ob = (String) o;
                    password.add(ob);
                }
            }
            Tablero t = new Tablero(tCode, tTitle, tDescription, tIsOn, tInfo, tIn, password);
            tableros.put(t.getItemCode(), t);
            items.put(t.getItemCode(), t);
        }

        //CREO PALANCAS
        for (Object palanca : jsonPalancas) {
            JSONObject obj = (JSONObject) palanca;
            int pCode = (int) (long) obj.get("palancaCode");
            String pTitle = (String) obj.get("palancaName");
            String pDescription = (String) obj.get("palancaDescription");
            String pUnlockedDescription = (String) obj.get("palancaUnlockedDescription");
            int pUnlock = (int) (long) obj.get("palancaUnlock");
            JSONArray palancaActivate = (JSONArray) obj.get("palancaActivate");
            Set<Integer> activate = new HashSet<>();
            for (Object o : palancaActivate) {
                if (o != null) {
                    int ob = (int) (long) o;
                    activate.add(ob);
                }
            }
            Palanca p = new Palanca(pCode, pTitle, pDescription, pUnlockedDescription, pUnlock, activate);
            palancas.put(p.getItemCode(), p);
            items.put(p.getItemCode(), p);
        }

        //CREO PLACAS
        for (Object placa : jsonPlacas) {
            JSONObject obj = (JSONObject) placa;
            int plCode = (int) (long) obj.get("placaCode");
            String plTitle = (String) obj.get("placaName");
            String plDescription = (String) obj.get("placaDescription");
            int pIsOn = (int) (long) obj.get("placaIsOn");
            int pOpener = (int) (long) obj.get("placaOpener");
            PlacaMetalica pm = new PlacaMetalica(plCode, plTitle, plDescription, pIsOn, pOpener);
            placas.put(pm.getItemCode(), pm);
            items.put(pm.getItemCode(), pm);
        }

        //CREO LLAVES
        for (Object llave : jsonLlaves) {
            JSONObject obj = (JSONObject) llave;
            int rCode = (int) (long) obj.get("llaveCode");
            String rTitle = (String) obj.get("llaveName");
            String rDescription = (String) obj.get("llaveDescription");
            Llave ll = new Llave(rCode, rTitle, rDescription);
            llaves.put(ll.getItemCode(), ll);
            items.put(ll.getItemCode(), ll);
        }

        //CREO NOTAS
        for (Object nota : jsonNotas) {
            JSONObject obj = (JSONObject) nota;
            int nCode = (int) (long) obj.get("notaCode");
            String nTitle = (String) obj.get("notaName");
            String nDescription = (String) obj.get("notaDescription");
            String nNota = (String) obj.get("nota");
            Nota n = new Nota(nCode, nTitle, nDescription, nNota);
            notas.put(n.getItemCode(), n);
            items.put(n.getItemCode(), n);
        }

        //=======================================================================
        //SETEO DE TODO
        //SETEO ROOMS
        for (Object room : jsonRooms) { //vuelvo a recorrer el json
            JSONObject obj = (JSONObject) room;
            JSONArray roomExits = (JSONArray) obj.get("roomExits"); //guardo las exits en un array
            JSONArray roomItems = (JSONArray) obj.get("roomItems"); //los items
            for (Room r : this.getAllRooms().values()) { //por cada habitacion
                for (Object o : roomExits) {
                    if (o != null) {
                        int ob = (int) (long) o;
                        if (r.getIntegerExits().contains(ob)) { //si la habitacion tiene el codigo del exit
                            r.addExit(this.getAllExits().get(ob)); //agrego la exit
                        }
                    }
                }
                for (Object o : roomItems) {
                    if (o != null) {
                        int ob = (int) (long) o;
                        if (r.getIntegerItems().contains(ob)) { //si la habitación tiene el codigo del item
                            r.addItem(this.getAllItems().get(ob)); //agrego el item
                        }
                    }
                }
            }
        }

        //SETEO EXITS
        for (Object exit : jsonExits) { //vuelvo a recorrer el json
            JSONObject obj = (JSONObject) exit;
            int eLeadsTo = (int) (long) obj.get("exitLeadsTo");
            int eOpener = (int) (long) obj.get("exitOpener");
            int eInteractuable = (int) (long) obj.get("exitInteractuable");
            for (Exit e : this.getAllExits().values()) { // recorro las Exits creadas
                // si los id de los objetos del json coinciden con el id que guardé en cada Exit
                if (eLeadsTo != -1 && eLeadsTo == e.getIntLeadsTo()) {
                    e.setLeadsTo(Game.getAllRooms().get(eLeadsTo));
                }
                if (eOpener != -1 && eOpener == e.getIntOpener()) {
                    e.setOpener((Usable) Game.getAllLlaves().get(eOpener));
                }
                if (eInteractuable != -1 && eInteractuable == e.getIntInteractuable()) {
                    e.setInteractuable((Interactuable) Game.getAllItems().get(eInteractuable));
                }
            }
        }

        //SETEO COFRES
        for (Object cofre : jsonCofres) {
            JSONObject obj = (JSONObject) cofre;
            int cOpener = (int) (long) obj.get("cofreOpener");
            int cInteractuable = (int) (long) obj.get("cofreInteractuable");
            JSONArray cTreasures = (JSONArray) obj.get("cofreTreasures");
            Set<Integer> treasures = new HashSet<>();
            for (Object o : cTreasures) {
                if (o != null) {
                    int ob = (int) (long) o;
                    treasures.add(ob);
                }
            }
            for (Cofre c : Game.getAllCofres().values()) {
                if (cOpener != -1 && cOpener == c.getIntOpener()) {
                    c.setOpener((Usable) Game.getAllLlaves().get(cOpener));
                }
                if (cInteractuable != -1 && cInteractuable == c.getIntInteractuable()) {
                    c.setInteractuable((Interactuable) Game.getAllItems().get(cInteractuable));
                }
                for (int t : treasures) { //recorro la lista de tesoros INT
                    if (c.getIntTreasures().contains(t)) { //si la lista que ya habia guardado contiene al item
                        c.addTreasure((Storable) Game.getAllItems().get(t)); //lo agrego
                    }
                }
            }
        }

        //SETEO TABLEROS
        for (Object tablero : jsonTableros) {
            JSONObject obj = (JSONObject) tablero;
            int tIn = (int) (long) obj.get("tableroIn");
            for (Tablero t : Game.getAllTableros().values()) {
                if (tIn != -1 && tIn == t.getIntIn()) {
                    t.setIn((Interactuable) Game.getAllItems().get(tIn));
                }
            }

        }

        //SETEO PALANCAS
        for (Object palanca : jsonPalancas) {
            JSONObject obj = (JSONObject) palanca;
            int pUnlock = (int) (long) obj.get("palancaUnlock");
            JSONArray palancaActivate = (JSONArray) obj.get("palancaActivate");
            Set<Integer> activate = new HashSet<>();
            for (Object o : palancaActivate) {
                if (o != null) {
                    int ob = (int) (long) o;
                    activate.add(ob);
                }
            }
            for (Palanca p : Game.getAllPalancas().values()) {
                if (pUnlock != -1 && pUnlock == p.getIntUnlock()) {
                    p.setUnlock((Exit) Game.getAllExits().get(pUnlock));
                }
                for (int a : activate) { //recorro la lista de tesoros INT
                    if (p.getIntActivate().contains(a)) { //si la lista que ya habia guardado contiene al item
                        p.addActivate((Tablero) Game.getAllTableros().get(a)); //lo agrego
                    }
                }
            }
        }

        //SETEO PLACAS
        for (Object placa : jsonPlacas) {
            JSONObject obj = (JSONObject) placa;
            int pIsOn = (int) (long) obj.get("placaIsOn");
            int pOpener = (int) (long) obj.get("placaOpener");
            for (PlacaMetalica p : Game.getAllPlacasMetalicas().values()) {
                if (pIsOn != -1 && pIsOn == p.getIntIsOn()) {
                    p.setIsOn(Game.getAllItems().get(pIsOn));
                }
                if (pOpener != -2 && pOpener == p.getIntOpener()) {
                    p.setOpener((Llave) Game.getAllLlaves().get(pOpener));
                }
            }
        }

        //AGREGO ACCIONES AL MENU
        menu.add("1. Moverse");
        menu.add("2. Tomar item");
        menu.add("3. Interactuar con... ");
        menu.add("4. Mostrar inventario");
        menu.add("q. Salir del juego");

        //SETEO LA HABITACION DE INICIO
        p.setPlayerRoom(Game.getAllRooms().get(100)); //Seteo al player en primer room
        //p.inventory.add(Game.getAllLlaves().get(5));
    }

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

    public static Map<Integer, Cofre> getAllCofres() {
        return cofres;
    }

    public static Map<Integer, Nota> getAllNotas() {
        return notas;
    }

    public static Map<Integer, Palanca> getAllPalancas() {
        return palancas;
    }

    public static Map<Integer, PlacaMetalica> getAllPlacasMetalicas() {
        return placas;
    }

    public static Map<Integer, Item> getAllItems() {
        return items;
    }

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
        this.end = end;
    }

    public boolean getEnd() {
        return this.end;
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
