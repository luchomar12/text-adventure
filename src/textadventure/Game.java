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
    public static void init() {

        //INICIALIZO HABITACIONES
        Room r1 = new Room();
        Room r2 = new Room();
        Room r3 = new Room();
        Room r4 = new Room();
        Room r5 = new Room();
        Room r6 = new Room();
        Room r7 = new Room();
        Room r8 = new Room();                
        Room rs = new Room(); //salida

        //LLAVES y UTILIZABLES
        Llave llave1 = new Llave("Llave pequeña", "Una LLAVE PEQUEÑA, tirada en un rincón. Sólo entraría en pequeños cerrojos.",r1);
        Llave llave2 = new Llave("Llave de cobre", "Una LLAVE DE COBRE, de tamaño chico",r2);
        Llave destornillador = new Llave("Destornillador","Un destornillanor, puede ser útil",r2);
        //Llave llave3 = new Llave("Llave plateada", "Una pesada LLAVE DE PLATA. Parece abrir algo importante");
        
        //INICIALIZO LAS SALIDAS
        Exit exit1 = new Exit("n", r2,false); //salida cerrada hacia room2
        exit1.setClosedDescription("La puerta del armario no parece ceder, tampoco tiene cerrojo...\nTiene que haber alguna forma de abrirla");
        Exit exit2 = new Exit("n", r3,false); //salida cerrada hacia room3
        exit2.setClosedDescription("La puerta está cerrada. Tene una placa de metal atornillada tapando el cerrojo.\nNo puedo introducir ninguna llave así.");
        PlacaMetalica placa = new PlacaMetalica("Placa metálica", "Una PLACA METALICA. Tiene 4 tornillos. Creo que puedo desatornillarla con algo", r2, destornillador);
        exit2.setInteractuable(placa);
        placa.setPlace(exit2);
        
        
        //NOTAS
        Nota nota1 = new Nota("Nota", "Es una nota... ¿o poema?... Creo que llego a leerlo... ¿está en clave?",r2);
        
        //COFRES
        Cofre cofre1 = new Cofre("Cofre Chico", "Un COFRE CHICO de madera, tiene una pequeña cerradura",r2);
        cofre1.setOpener(llave1);
        cofre1.addTreasure(llave2);
        cofre1.addTreasure(nota1);
        cofre1.addTreasure(destornillador);
        
        //OTROS
        Palanca palanca = new Palanca("Palanca", "Una PALANCA de madera incrustada en la pared. ¿Accionará algo?",r1, exit1); //está en r1 y habilita la exit1
        
        


        //SETEO LAS HABITACIONES
        //Room 1
        r1.setTitle("Armario");
        r1.setDescription("Te encuentras en un amplio armario.\nDe todas maneras sientes la asfixia y las paredes parecen encerrarte cada vez mas.\nParece sólo haber una puerta al frente... ¿estará abierta?");
        r1.addItem(llave1);
        r1.addItem(palanca);
        r1.addExit(exit1); //salida al norte a room2

        //Room 2
        r2.setTitle("Pasillo");
        r2.setDescription("Estás en un tenue pasillo. Entran tres delgados rayos de luz desde unas finas aberturas en lo alto de las paredes.\n"
                + "Al costado hay una mesa con una pequeña cajita. Al fondo una puerta.");
        r2.addItem(cofre1);
        r2.addExit(new Exit("s", r1,true)); //salida al sur a room1 (ya abierta)
        r2.addExit(exit2); //salida al norte a room3

        //Room 3
        r3.setTitle("Sala");
        r3.setDescription("Parece una sala de estar");
        //r3.addItem(palanca);
        r3.addExit(new Exit("w", r2,true)); //salida al oeste a room2
        r3.addExit(new Exit("s", r4,false)); //salida al sur a room4/cerrada

        //Room 4
        r4.setTitle("Habitación");
        r4.setDescription("Es una pequeña habitación, ves un TABLERO en la puerta de enfrente");
        //r4.addItem(tablero);
        r4.addExit(new Exit("n", r3,true));  //salida al norte a room3
        Exit puertaCerrada = new Exit("s", rs,false);
        puertaCerrada.setClosedDescription("Una gran puerta de metal. Está cerrada y no tiene cerrojos ni picaporte.");
        puertaCerrada.setOpenedDescription("La puerta está abierta de par en par");
        r4.addExit(puertaCerrada); //salida al sur y gana juego

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
        System.out.println("========================================================");
        System.out.println(p.getPlayerRoom().getDescription()); //muestro la descripcion de la habitación actual del jugador
        if (p.getPlayerRoom().getAllItems().isEmpty()) { //Muestro los objetos de la habitación, si los hay
            System.out.println("");
            System.out.println(">>Ya no queda nada importante en esta habitación");
        } else {
            System.out.println("Puedes ver... ");
            for (Item item : p.getPlayerRoom().getAllItems()) {
                System.out.println("    -" + item.getItemDescription());
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
                System.out.println("¿Inventario(i) o habitación(h)?");
                String option = in.nextLine();
                p.interactWith(option);
                in.nextLine();
                break;
            case "4":
                p.showInventory();
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
