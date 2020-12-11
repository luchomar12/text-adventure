package textadventure;

import Interfaces.Guardable;
import Interfaces.Interactuable;
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

    /* 
    PROFE: 
    no esta bueno tener atributos estaticos. 
    Todos estos atributos pueden ser atributos de la instancia de Game. 
    Llegado el caso pueden tener 2 objetos Game al mismo tiempo.
    
    RESUELTO OK
     */
    //INICIALIZO ATRIBUTOS
    private static Game juego;
    private Player p;
    private Scanner in = new Scanner(System.in); // PROFE: aca tiene mas sentido guardar el scanner porque lo usan en muchos lados.
    private Map<Integer, Room> rooms = new HashMap<>(); //Map con todas las Rooms
    private Map<Integer, Item> items = new HashMap<>(); //Map con todos los Items
    private Set<String> menu = new TreeSet<>(); //Conjunto con las opcinones del menú
    private boolean isEnd = false; //atributo para cuando termine el juego
    private String introduction;
    private String finalDescription;

    //CONSTRUCTOR
    public Game() {}

    public static Game dameInstancia(){
        if(juego == null){
            juego = new Game();
        }
        return juego;
    }
    
    //PIDO PLAYER
    public Player getPlayer() {
        if(p == null){
            this.p = new Player();
        }
        return this.p;
    }

    //LEO JSON
    public JSONObject leerJson() throws FileNotFoundException, IOException, ParseException {
        FileReader file = new FileReader("AdventureWorld.json"); //leo el json
        JSONParser parser = new JSONParser(); //creo el parser
        JSONObject jsonWorld = (JSONObject) parser.parse(file); //parseo y devuelvo el json
        return jsonWorld;
    }

    //INICIALIZO TODO
    public void init() throws FileNotFoundException, IOException, ParseException {

        //METODO PARA LEER JSON Y CREAR LOS OBJETOS
        JSONObject world = leerJson(); //leo el json
        crearObjetos(world); //creo los objetos
        setearObjectos(world); //seteo los objetos

        //AGREGO ACCIONES AL MENU
        menu.add("1. Moverse");
        menu.add("2. Tomar item");
        menu.add("3. Interactuar con... ");
        menu.add("4. Mostrar inventario");
        menu.add("q. Salir del juego");
    }

    public void addRoom(int code, Room r) {
        rooms.put(code, r);
    }

    public void addItem(int code, Item i) {
        items.put(code, i);
    }

    public Room getRoom(int i) {
        Room r = rooms.get(i);
        return r;
    }

    public Item getItem(int i) {
        Item item = items.get(i);
        return item;
    }

    public void crearObjetos(JSONObject jsonWorld) {

        /* 
            PROFE: 
            No esta bueno pedir la coleccion y hacer put uno. Eso rompe el encapsulamiento
            Es mejor si ponen un metodo agregarRoom en GAME. Lo mismo cuando hacen getAllItems().
            
            Tambien pueden poner la logica para deserializar el JSONObject en cada clase Room, Exit, Contenedor, etc...
            pueden poner un metodo estatico que tome el JSONObject y devuelva una instancia de esa clase
        
        
            RESUELTO OK
         */
        //CREO LOS JSON ARRAYS
        JSONArray jsonRooms = (JSONArray) jsonWorld.get("Rooms");
        JSONArray jsonExits = (JSONArray) jsonWorld.get("Exits");
        JSONArray jsonLlaves = (JSONArray) jsonWorld.get("Llaves");
        JSONArray jsonContenedores = (JSONArray) jsonWorld.get("Contenedores");
        JSONArray jsonNotas = (JSONArray) jsonWorld.get("Notas");
        JSONArray jsonInterruptores = (JSONArray) jsonWorld.get("Interruptores");
        JSONArray jsonTableros = (JSONArray) jsonWorld.get("Tableros");
        JSONArray jsonBloqueadores = (JSONArray) jsonWorld.get("Bloqueadores");

        //CREO ROOMS
        for (Object room : jsonRooms) {
            JSONObject obj = (JSONObject) room;
            Room r = Room.leerJson(obj);
            addRoom(r.getRoomCode(), r);//agrego la room a la lista de rooms
        }
        //CREO EXITS
        for (Object exit : jsonExits) {
            JSONObject obj = (JSONObject) exit;
            Exit e = Exit.leerJson(obj);
            addItem(e.getExitCode(), e);
        }

        //CREO CONTENEDORES
        for (Object contenedor : jsonContenedores) {
            JSONObject obj = (JSONObject) contenedor;
            Contenedor c = Contenedor.leerJson(obj);
            addItem(c.getItemCode(), c);
        }

        //CREO TABLEROS
        for (Object tablero : jsonTableros) {
            JSONObject obj = (JSONObject) tablero;
            Tablero t = Tablero.leerJson(obj);
            addItem(t.getItemCode(), t);
        }

        //CREO INTERRUPTORES
        for (Object interruptor : jsonInterruptores) {
            JSONObject obj = (JSONObject) interruptor;
            Interruptor i = Interruptor.leerJson(obj);
            addItem(i.getItemCode(), i);
        }

        //CREO BLOQUEADORES
        for (Object bloqueador : jsonBloqueadores) {
            JSONObject obj = (JSONObject) bloqueador;
            Bloqueador b = Bloqueador.leerJson(obj);
            addItem(b.getItemCode(), b);
        }

        //CREO LLAVES
        for (Object llave : jsonLlaves) {
            JSONObject obj = (JSONObject) llave;
            Llave ll = Llave.leerJson(obj);
            addItem(ll.getItemCode(), ll);
        }

        //CREO NOTAS
        for (Object nota : jsonNotas) {
            JSONObject obj = (JSONObject) nota;
            Nota n = Nota.leerJson(obj);
            addItem(n.getItemCode(), n);
        }
    }

    public void setearObjectos(JSONObject jsonWorld) {
        //CREO LOS JSON ARRAYS
        JSONArray jsonTextos = (JSONArray) jsonWorld.get("Textos");
        JSONArray jsonPlayerStart = (JSONArray) jsonWorld.get("PlayerStart");
        JSONArray jsonRooms = (JSONArray) jsonWorld.get("Rooms");
        JSONArray jsonExits = (JSONArray) jsonWorld.get("Exits");
        JSONArray jsonContenedores = (JSONArray) jsonWorld.get("Contenedores");
        JSONArray jsonInterruptores = (JSONArray) jsonWorld.get("Interruptores");
        JSONArray jsonTableros = (JSONArray) jsonWorld.get("Tableros");
        JSONArray jsonBloqueadores = (JSONArray) jsonWorld.get("Bloqueadores");

        //=======================================================================
        //SETEO TODO (llaves y notas no necesitan)
        
        //ASIGNO TEXTOS PROPIOS DEL JUEGO
        for(Object texto: jsonTextos){
            JSONObject obj = (JSONObject) texto;
            String intro = (String) obj.get("introduction");
            String finalDesc = (String) obj.get("final");
            this.introduction = intro;
            this.finalDescription = finalDesc;
        }
        
        //ASIGNO HABITACIÓN INICIAL E INVENTARIO
        for (Object player : jsonPlayerStart) {
            JSONObject obj = (JSONObject) player;
            int startRoom = (int) (long) obj.get("startRoom");
            JSONArray inventario = (JSONArray) obj.get("inventory");
            Set<Integer> inv = new HashSet<>();

            /*
            PROFE: lo mismo aca, en vez de pedir la coleccion y hacer get, mejor hacer un metodo getRoom, getItem, etc
            En realidad, todo esto podria estar en la clase Game, y ahi pueden acceder a la coleccion directamente.
            Sobre todo si pasan los deserializar a las clases correspondientes.
            
            RESUELTO OK
             */
            
            //CREO al JUGADOR y seteo el room de inicio
            getPlayer().setPlayerRoom(getRoom(startRoom));
            

            for (Object o : inventario) {
                if (o != null) {//siempre y cuando haya algo
                    int ob = (int) (long) o;
                    getPlayer().addInventory((Guardable) getItem(ob));
                }
            }
        }

        //SETEO ROOMS
        for (Object room : jsonRooms) { //vuelvo a recorrer el json
            JSONObject obj = (JSONObject) room;
            int rCode = (int) (long) obj.get("roomCode");
            JSONArray roomExits = (JSONArray) obj.get("roomExits"); //guardo las exits en un array
            JSONArray roomItems = (JSONArray) obj.get("roomItems"); //los items
            for (Object o : roomExits) {
                if (o != null) {
                    int ob = (int) (long) o;
                    getRoom(rCode).addExit((Exit) getItem(ob)); //agrego la exit a la room correspondiente a su codigo
                }
            }
            for (Object o : roomItems) {
                if (o != null) {
                    int ob = (int) (long) o;
                    getRoom(rCode).addItem(getItem(ob)); //agrego el item a la room correspondiente a su codigo
                }
            }
        }

        //SETEO EXITS
        for (Object exit : jsonExits) { //vuelvo a recorrer el json
            JSONObject obj = (JSONObject) exit;
            int eCode = (int) (long) obj.get("exitCode");
            int eLeadsTo = (int) (long) obj.get("exitLeadsTo");
            int eOpener = (int) (long) obj.get("exitOpener");
            int eInteractuable = (int) (long) obj.get("exitInteractuable");
            Exit e = (Exit) getItem(eCode);
            if (eLeadsTo != -1) {
                e.setLeadsTo(getRoom(eLeadsTo));
            }
            if (eOpener != -1) {
                e.setOpener((Usable) getItem(eOpener));
            }
            if (eInteractuable != -1) {
                e.setInteractuable((Interactuable) getItem(eInteractuable));
            }
        }

        //SETEO CONTENEDORES
        for (Object contenedor : jsonContenedores) {
            JSONObject obj = (JSONObject) contenedor;
            int cCode = (int) (long) obj.get("contenedorCode");
            int cOpener = (int) (long) obj.get("contenedorOpener");
            int cInteractuable = (int) (long) obj.get("contenedorInteractuable");
            JSONArray cTreasures = (JSONArray) obj.get("contenedorTreasures");
            Contenedor c = (Contenedor) getItem(cCode);
            if (cOpener != -1) {
                c.setOpener((Usable) getItem(cOpener));
            }
            if (cInteractuable != -1) {
                c.setInteractuable((Interactuable) getItem(cInteractuable));
            }
            for (Object o : cTreasures) {
                if (o != null) {
                    int ob = (int) (long) o;
                    c.addTreasure((Guardable) getItem(ob));
                }
            }
        }

        //SETEO TABLEROS
        for (Object tablero : jsonTableros) {
            JSONObject obj = (JSONObject) tablero;
            int tCode = (int) (long) obj.get("tableroCode");
            int tIn = (int) (long) obj.get("tableroIn");
            Tablero t = (Tablero) getItem(tCode);
            if (tIn != -1) {
                t.setIn((Interactuable) getItem(tIn));
            }
        }

        //SETEO INTERRUPTORES
        for (Object interruptor : jsonInterruptores) {
            JSONObject obj = (JSONObject) interruptor;
            int iCode = (int) (long) obj.get("interruptorCode");
            int iUnlock = (int) (long) obj.get("interruptorUnlock");
            JSONArray interruptorActivate = (JSONArray) obj.get("interruptorActivate");
            Interruptor i = (Interruptor) getItem(iCode);
            if (iUnlock != -1) {
                i.setUnlock((Exit) getItem(iUnlock));
            }
            for (Object o : interruptorActivate) {
                if (o != null) {
                    int ob = (int) (long) o;
                    i.addActivate((Tablero) getItem(ob));
                }
            }
        }

        //SETEO BLOQUEADORES
        for (Object bloqueador : jsonBloqueadores) {
            JSONObject obj = (JSONObject) bloqueador;
            int bCode = (int) (long) obj.get("bloqueadorCode");
            int bIsOn = (int) (long) obj.get("bloqueadorIsOn");
            int bOpener = (int) (long) obj.get("bloqueadorOpener");
            Bloqueador b = (Bloqueador) getItem(bCode);
            if (bIsOn != -1) {
                b.setIsOn(getItem(bIsOn));
            }
            if (bOpener != -1) {
                b.setOpener((Llave) getItem(bOpener));
            }
        }
    }

    //=======================================================================
    //=======================================================================
    //PARA COMENZAR EL JUEGO EN EL MAIN
    public void showIntroduction() {
        /* 
        PROFE:
        La introduccion puede estar en el JSON tambien. 
        Es parte de la descripcion del juego, a diferencia de los textos como
        "Ya no queda nada importante en esta habitación" y 
        "¿Qué quieres hacer?"
        que son genericos
        
        RESUELTO OK
         */
        System.out.println("**********************************************");
        System.out.println(this.introduction); 
        System.out.println("**********************************************");
        System.out.println(">> Presiona ENTER para comenzar <<");
        System.out.print(">");
        in.nextLine();
    }

    //MUESTRO TITULO Y DESCRIPCION DE LA HABITACION 
    public void showRoom() {
        System.out.println("===============================================================================");
        System.out.println(p.getPlayerRoom().getTitle()); //muestra el titulo de la habitación actual del jugador
        System.out.println("");
        System.out.println(getPlayer().getPlayerRoom().getDescription()); //muestro la descripcion de la habitación actual del jugador
        System.out.println("===============================================================================");
        if (getPlayer().getPlayerRoom().getTitle().equalsIgnoreCase("...")) {
            end();
            return;
        }
        if (getPlayer().getPlayerRoom().getAllItems().isEmpty()) { //Muestro los objetos de la habitación, si los hay
            System.out.println("");
            System.out.println(">>Ya no queda nada importante en esta habitación");
            System.out.println("");
        } else {
            getPlayer().showAllRoomItems();
        }
    }

    //MUESTRO MENU DE ACCIONES
    public void showMenu() {
        System.out.println("_______________________________________________________________________________");
        System.out.println("");
        System.out.println("¿Qué quieres hacer?");
        for (String accion : menu) {
            System.out.println(accion);
        }
        System.out.print(">");

    }

    //INTERPRETO LA ELECCION DEL MENU Y LLAMO A SU CORRESPONDIENTE METODO
    public void actionMenu(String entry) {
        entry = entry.trim().toLowerCase();
        switch (entry) {
            case "1":
                getPlayer().moverPlayer();
                break;
            case "2":
                getPlayer().takeItem();
                in.nextLine();
                break;
            case "3":
                getPlayer().interactWith(1);
                in.nextLine();
                break;
            case "4":
                getPlayer().showInventory();
                getPlayer().interactWith(2);
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
        isEnd = end;
    }

    public boolean getEnd() {
        return isEnd;
    }

    public void end() {
        isEnd(true);
        System.out.println(this.finalDescription);
        in.nextLine();
    }
}
