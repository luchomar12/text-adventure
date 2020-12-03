package textadventure;

import Interfaces.*;
import Items.*;
import java.io.*;
import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import static textadventure.Game.juego;

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
        FileReader file = new FileReader("AdventureWorld.json"); //leo el json
        JSONParser parser = new JSONParser(); //creo el parser
        JSONObject jsonWorld = (JSONObject) parser.parse(file); //parseo y devuelvo el json
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
            Room r = new Room(rCode, rTitle, rDescription);//creo la room
            juego.getAllRooms().put(r.getRoomCode(), r);//agrego la room a la lista de rooms
        }
        //CREO EXITS
        for (Object exit : jsonExits) {
            JSONObject obj = (JSONObject) exit;
            int eCode = (int) (long) obj.get("exitCode");
            String eTitle = (String) obj.get("exitName");
            String eDescription = (String) obj.get("exitDescription");
            String eDirection = (String) obj.get("exitDirection");
            boolean eOpened = (boolean) obj.get("exitOpened");
            Exit e = new Exit(eCode, eTitle, eDescription, eDirection, eOpened);
            juego.getAllItems().put(e.getExitCode(), e);
        }

        //CREO CONTENEDORES
        for (Object contenedor : jsonContenedores) {
            JSONObject obj = (JSONObject) contenedor;
            int cCode = (int) (long) obj.get("contenedorCode");
            String cTitle = (String) obj.get("contenedorName");
            String cDescription = (String) obj.get("contenedorDescription");
            Contenedor c = new Contenedor(cCode, cTitle, cDescription);
            juego.getAllItems().put(c.getItemCode(), c);
        }

        //CREO TABLEROS
        for (Object tablero : jsonTableros) {
            JSONObject obj = (JSONObject) tablero;
            int tCode = (int) (long) obj.get("tableroCode");
            String tTitle = (String) obj.get("tableroName");
            String tDescription = (String) obj.get("tableroDescription");
            boolean tIsOn = (boolean) obj.get("tableroIsOn");
            String tInfo = (String) obj.get("tableroInfo");
            JSONArray tPass = (JSONArray) obj.get("tableroPass");
            List<String> password = new ArrayList<>();
            for (Object o : tPass) {
                if (o != null) {
                    String ob = (String) o;
                    password.add(ob);
                }
            }
            Tablero t = new Tablero(tCode, tTitle, tDescription, tIsOn, tInfo, password);
            juego.getAllItems().put(t.getItemCode(), t);
        }

        //CREO INTERRUPTORES
        for (Object interruptor : jsonInterruptores) {
            JSONObject obj = (JSONObject) interruptor;
            int iCode = (int) (long) obj.get("interruptorCode");
            String iTitle = (String) obj.get("interruptorName");
            String iDescription = (String) obj.get("interruptorDescription");
            String iUnlockedDescription = (String) obj.get("interruptorUnlockedDescription");
            Interruptor i = new Interruptor(iCode, iTitle, iDescription, iUnlockedDescription);
            juego.getAllItems().put(i.getItemCode(), i);
        }

        //CREO BLOQUEADORES
        for (Object bloqueador : jsonBloqueadores) {
            JSONObject obj = (JSONObject) bloqueador;
            int bCode = (int) (long) obj.get("bloqueadorCode");
            String bTitle = (String) obj.get("bloqueadorName");
            String bDescription = (String) obj.get("bloqueadorDescription");
            Bloqueador b = new Bloqueador(bCode, bTitle, bDescription);
            juego.getAllItems().put(b.getItemCode(), b);
        }

        //CREO LLAVES
        for (Object llave : jsonLlaves) {
            JSONObject obj = (JSONObject) llave;
            int rCode = (int) (long) obj.get("llaveCode");
            String rTitle = (String) obj.get("llaveName");
            String rDescription = (String) obj.get("llaveDescription");
            Llave ll = new Llave(rCode, rTitle, rDescription);
            juego.getAllItems().put(ll.getItemCode(), ll);
        }

        //CREO NOTAS
        for (Object nota : jsonNotas) {
            JSONObject obj = (JSONObject) nota;
            int nCode = (int) (long) obj.get("notaCode");
            String nTitle = (String) obj.get("notaName");
            String nDescription = (String) obj.get("notaDescription");
            String nNota = (String) obj.get("nNota");
            Nota n = new Nota(nCode, nTitle, nDescription, nNota);
            juego.getAllItems().put(n.getItemCode(), n);
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
            int rCode = (int) (long) obj.get("roomCode");
            JSONArray roomExits = (JSONArray) obj.get("roomExits"); //guardo las exits en un array
            JSONArray roomItems = (JSONArray) obj.get("roomItems"); //los items
            for (Object o : roomExits) {
                if (o != null) {
                    int ob = (int) (long) o;
                    juego.getAllRooms().get(rCode).addExit((Exit) juego.getAllItems().get(ob)); //agrego la exit a la room correspondiente a su codigo
                }
            }
            for (Object o : roomItems) {
                if (o != null) {
                    int ob = (int) (long) o;
                    juego.getAllRooms().get(rCode).addItem(juego.getAllItems().get(ob)); //agrego el item a la room correspondiente a su codigo
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
            Exit e = (Exit) juego.getAllItems().get(eCode);
            if (eLeadsTo != -1) {
                e.setLeadsTo(juego.getAllRooms().get(eLeadsTo));
            }
            if (eOpener != -1) {
                e.setOpener((Usable) juego.getAllItems().get(eOpener));
            }
            if (eInteractuable != -1) {
                e.setInteractuable((Interactuable) juego.getAllItems().get(eInteractuable));
            }
        }

        //SETEO CONTENEDORES
        for (Object contenedor : jsonContenedores) {
            JSONObject obj = (JSONObject) contenedor;
            int cCode = (int) (long) obj.get("contenedorCode");
            int cOpener = (int) (long) obj.get("contenedorOpener");
            int cInteractuable = (int) (long) obj.get("contenedorInteractuable");
            JSONArray cTreasures = (JSONArray) obj.get("contenedorTreasures");
            Contenedor c = (Contenedor) juego.getAllItems().get(cCode);
            if (cOpener != -1) {
                c.setOpener((Usable) juego.getAllItems().get(cOpener));
            }
            if (cInteractuable != -1) {
                c.setInteractuable((Interactuable) juego.getAllItems().get(cInteractuable));
            }
            for (Object o : cTreasures) {
                if (o != null) {
                    int ob = (int) (long) o;
                    c.addTreasure((Guardable) juego.getAllItems().get(ob));
                }
            }
        }

        //SETEO TABLEROS
        for (Object tablero : jsonTableros) {
            JSONObject obj = (JSONObject) tablero;
            int tCode = (int) (long) obj.get("tableroCode");
            int tIn = (int) (long) obj.get("tableroIn");
            Tablero t = (Tablero) juego.getAllItems().get(tCode);
            if (tIn != -1) {
                t.setIn((Interactuable) juego.getAllItems().get(tIn));
            }
        }

        //SETEO INTERRUPTORES
        for (Object interruptor : jsonInterruptores) {
            JSONObject obj = (JSONObject) interruptor;
            int iCode = (int) (long) obj.get("interruptorCode");
            int iUnlock = (int) (long) obj.get("interruptorUnlock");
            JSONArray interruptorActivate = (JSONArray) obj.get("interruptorActivate");
            Interruptor i = (Interruptor) juego.getAllItems().get(iCode);
            if (iUnlock != -1) {
                i.setUnlock((Exit) juego.getAllItems().get(iUnlock));
            }
            for (Object o : interruptorActivate) {
                if (o != null) {
                    int ob = (int) (long) o;
                    i.addActivate((Tablero) juego.getAllItems().get(ob));
                }
            }
        }

        //SETEO BLOQUEADORES
        for (Object bloqueador : jsonBloqueadores) {
            JSONObject obj = (JSONObject) bloqueador;
            int bCode = (int) (long) obj.get("bloqueadorCode");
            int bIsOn = (int) (long) obj.get("bloqueadorIsOn");
            int bOpener = (int) (long) obj.get("bloqueadorOpener");
            Bloqueador b = (Bloqueador) juego.getAllItems().get(bCode);
            if (bIsOn != -1) {
                b.setIsOn(juego.getAllItems().get(bIsOn));
            }
            if (bOpener != -1) {
                b.setOpener((Llave) juego.getAllItems().get(bOpener));
            }
        }
    }
}
