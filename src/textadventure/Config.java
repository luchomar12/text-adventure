package textadventure;

import Interfaces.*;
import Items.*;
import java.io.*;
import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import static textadventure.Game.rooms;

//ESTA CLASE TIENE 3 METODOS
// leerJson() --> LEE TODO EL JSON, LO PARSEA Y DEVUELVE EL JSONOBJECT
// crearObjectos() --> TOMA EL JSONOBJECT, LO RECORRE Y POR CADA OBJETO LO VA CREANDO
// setearObjectos() --> VUELVE A RECORRER EL JSONOBJECT, ESTA VEZ YA LOS OBJETOS ESTÁN CREADOS, ENTONCES PUEDO SETEARLOS
public class Config {

    private static Config config;

    private Config() {
    }

    public static Config getInstance() {
        if (config == null) {
            return new Config();
        }
        return config;
    }

    public JSONObject leerJson() throws FileNotFoundException, IOException, ParseException {
        FileReader file = new FileReader("src/AdventureWorld.json");
        JSONParser parser = new JSONParser();
        JSONObject jsonWorld = (JSONObject) parser.parse(file);
        return jsonWorld;
    }

    public void crearObjetos(JSONObject jsonWorld) {
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
            Game.getAllExits().put(e.getExitCode(), e);
            Game.getAllItems().put(e.getExitCode(), e);
        }

        //CREO CONTENEDORES
        for (Object contenedor : jsonContenedores) {
            JSONObject obj = (JSONObject) contenedor;
            int cCode = (int) (long) obj.get("contenedorCode");
            String cTitle = (String) obj.get("contenedorName");
            String cDescription = (String) obj.get("contenedorDescription");
            int cOpener = (int) (long) obj.get("contenedorOpener");
            int cInteractuable = (int) (long) obj.get("contenedorInteractuable");
            JSONArray cTreasures = (JSONArray) obj.get("contenedorTreasures");
            Set<Integer> treasures = new HashSet<>();
            for (Object o : cTreasures) {
                if (o != null) {
                    int ob = (int) (long) o;
                    treasures.add(ob);
                }
            }
            Contenedor c = new Contenedor(cCode, cTitle, cDescription, cOpener, cInteractuable, treasures);
            Game.getAllContenedores().put(c.getItemCode(), c);
            Game.getAllItems().put(c.getItemCode(), c);
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
            Game.getAllTableros().put(t.getItemCode(), t);
            Game.getAllItems().put(t.getItemCode(), t);
        }

        //CREO INTERRUPTORES
        for (Object interruptor : jsonInterruptores) {
            JSONObject obj = (JSONObject) interruptor;
            int iCode = (int) (long) obj.get("interruptorCode");
            String iTitle = (String) obj.get("interruptorName");
            String iDescription = (String) obj.get("interruptorDescription");
            String iUnlockedDescription = (String) obj.get("interruptorUnlockedDescription");
            int iUnlock = (int) (long) obj.get("interruptorUnlock");
            JSONArray interruptorActivate = (JSONArray) obj.get("interruptorActivate");
            Set<Integer> activate = new HashSet<>();
            for (Object o : interruptorActivate) {
                if (o != null) {
                    int ob = (int) (long) o;
                    activate.add(ob);
                }
            }
            Interruptor i = new Interruptor(iCode, iTitle, iDescription, iUnlockedDescription, iUnlock, activate);
            Game.getAllInterruptores().put(i.getItemCode(), i);
            Game.getAllItems().put(i.getItemCode(), i);
        }

        //CREO BLOQUEADORES
        for (Object bloqueador : jsonBloqueadores) {
            JSONObject obj = (JSONObject) bloqueador;
            int bCode = (int) (long) obj.get("bloqueadorCode");
            String bTitle = (String) obj.get("bloqueadoraName");
            String bDescription = (String) obj.get("bloqueadorDescription");
            int bIsOn = (int) (long) obj.get("bloqueadorIsOn");
            int bOpener = (int) (long) obj.get("bloqueadorOpener");
            Bloqueador b = new Bloqueador(bCode, bTitle, bDescription, bIsOn, bOpener);
            Game.getAllBloqueadores().put(b.getItemCode(), b);
            Game.getAllItems().put(b.getItemCode(), b);
        }

        //CREO LLAVES
        for (Object llave : jsonLlaves) {
            JSONObject obj = (JSONObject) llave;
            int rCode = (int) (long) obj.get("llaveCode");
            String rTitle = (String) obj.get("llaveName");
            String rDescription = (String) obj.get("llaveDescription");
            Llave ll = new Llave(rCode, rTitle, rDescription);
            Game.getAllLlaves().put(ll.getItemCode(), ll);
            Game.getAllItems().put(ll.getItemCode(), ll);
        }

        //CREO NOTAS
        for (Object nota : jsonNotas) {
            JSONObject obj = (JSONObject) nota;
            int nCode = (int) (long) obj.get("notaCode");
            String nTitle = (String) obj.get("notaName");
            String nDescription = (String) obj.get("notaDescription");
            String nNota = (String) obj.get("nNota");
            Nota n = new Nota(nCode, nTitle, nDescription, nNota);
            Game.getAllNotas().put(n.getItemCode(), n);
            Game.getAllItems().put(n.getItemCode(), n);
        }
        
    }

    public void setearObjectos(JSONObject jsonWorld) {
        //CREO LOS JSON ARRAYS
        JSONArray jsonRooms = (JSONArray) jsonWorld.get("Rooms");
        JSONArray jsonExits = (JSONArray) jsonWorld.get("Exits");
        JSONArray jsonContenedores = (JSONArray) jsonWorld.get("Contenedores");
        JSONArray jsonInterruptores = (JSONArray) jsonWorld.get("Interruptores");
        JSONArray jsonTableros = (JSONArray) jsonWorld.get("Tableros");
        JSONArray jsonBloqueadores = (JSONArray) jsonWorld.get("Bloqueadores");

        //=======================================================================
        //SETEO DE TODO (llaves y notas no necesitan)
        //SETEO ROOMS
        for (Object room : jsonRooms) { //vuelvo a recorrer el json
            JSONObject obj = (JSONObject) room;
            JSONArray roomExits = (JSONArray) obj.get("roomExits"); //guardo las exits en un array
            JSONArray roomItems = (JSONArray) obj.get("roomItems"); //los items
            for (Room r : Game.getAllRooms().values()) { //por cada habitacion
                for (Object o : roomExits) {
                    if (o != null) {
                        int ob = (int) (long) o;
                        if (r.getIntegerExits().contains(ob)) { //si la habitacion tiene el codigo del exit
                            r.addExit(Game.getAllExits().get(ob)); //agrego la exit
                        }
                    }
                }
                for (Object o : roomItems) {
                    if (o != null) {
                        int ob = (int) (long) o;
                        if (r.getIntegerItems().contains(ob)) { //si la habitación tiene el codigo del item
                            r.addItem(Game.getAllItems().get(ob)); //agrego el item
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
            for (Exit e : Game.getAllExits().values()) { // recorro las Exits creadas
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

        //SETEO CONTENEDORES
        for (Object contenedor : jsonContenedores) {
            JSONObject obj = (JSONObject) contenedor;
            int cOpener = (int) (long) obj.get("contenedorOpener");
            int cInteractuable = (int) (long) obj.get("contenedorInteractuable");
            JSONArray cTreasures = (JSONArray) obj.get("contenedorTreasures");
            Set<Integer> treasures = new HashSet<>();
            for (Object o : cTreasures) {
                if (o != null) {
                    int ob = (int) (long) o;
                    treasures.add(ob);
                }
            }
            for (Contenedor c : Game.getAllContenedores().values()) {
                if (cOpener != -1 && cOpener == c.getIntOpener()) {
                    c.setOpener((Usable) Game.getAllLlaves().get(cOpener));
                }
                if (cInteractuable != -1 && cInteractuable == c.getIntInteractuable()) {
                    c.setInteractuable((Interactuable) Game.getAllItems().get(cInteractuable));
                }
                for (int t : treasures) { //recorro la lista de tesoros INT
                    if (c.getIntTreasures().contains(t)) { //si la lista que ya habia guardado contiene al item
                        c.addTreasure((Guardable) Game.getAllItems().get(t)); //lo agrego
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

        //SETEO INTERRUPTORES
        for (Object interruptor : jsonInterruptores) {
            JSONObject obj = (JSONObject) interruptor;
            int iUnlock = (int) (long) obj.get("interruptorUnlock");
            JSONArray interruptorActivate = (JSONArray) obj.get("interruptorActivate");
            Set<Integer> activate = new HashSet<>();
            for (Object o : interruptorActivate) {
                if (o != null) {
                    int ob = (int) (long) o;
                    activate.add(ob);
                }
            }
            for (Interruptor i : Game.getAllInterruptores().values()) {
                if (iUnlock != -1 && iUnlock == i.getIntUnlock()) {
                    i.setUnlock((Exit) Game.getAllExits().get(iUnlock));
                }
                for (int a : activate) { //recorro la lista de tesoros INT
                    if (i.getIntActivate().contains(a)) { //si la lista que ya habia guardado contiene al item
                        i.addActivate((Tablero) Game.getAllTableros().get(a)); //lo agrego
                    }
                }
            }
        }

        //SETEO BLOQUEADORES
        for (Object bloqueador : jsonBloqueadores) {
            JSONObject obj = (JSONObject) bloqueador;
            int bIsOn = (int) (long) obj.get("bloqueadorIsOn");
            int bOpener = (int) (long) obj.get("bloqueadorOpener");
            for (Bloqueador b : Game.getAllBloqueadores().values()) {
                if (bIsOn != -1 && bIsOn == b.getIntIsOn()) {
                    b.setIsOn(Game.getAllItems().get(bIsOn));
                }
                if (bOpener != -2 && bOpener == b.getIntOpener()) {
                    b.setOpener((Llave) Game.getAllLlaves().get(bOpener));
                }
            }
        }
    }
}
