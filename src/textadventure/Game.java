package textadventure;
import java.util.*;

public class Game {
    public Scanner in = new Scanner(System.in);
    public static Game juego;
    public static Player p = Player.getInstance();
    public Set<Room> rooms = new HashSet<>();
    public static Set<String> menu = new HashSet<>();
    
    public Game(){}
    
    public static Game getInstance(){
        if(juego == null){
            return new Game();
        }
        return juego;
    }
    
    
    //inicializando items, rooms, menu, etc    
    public void init(){
        //inicializo los items
        Item llave = new Item("Lave de Plata", "Una pesada llave de plata. Parece abrir algo importante");
        Item soga = new Item("Soga", "Una soga fina. Se podría atar a algo");
        Item nota = new Item("Nota", "Una nota con unos números: 438");
        Item tablero = new Item("Tablero", "Un tablero donde puedes ingresar números.");
        
        //inicializo las habitaciones
        Room r1 = new Room();
        Room r2 = new Room();
        Room r3 = new Room();
        Room r4 = new Room();
        Room r5 = new Room(); //salida

        //seteo las habitaciones
        r1.setTitle("Armario");
        r1.setDescription("Te encuentras en un amplio armario, hay una llave colgada");
        r1.addItem(llave);
        r1.addExit(new Exit("n", r2)); //salida al norte a room2
        r2.setTitle("Pasillo");
        r2.setDescription("Un tenue pasillo, ves una soga en el piso");
        r2.addItem(soga);
        r2.addExit(new Exit("s", r1)); //salida al sur a room1
        r2.addExit(new Exit("e", r3)); //salida al este a room3
        r3.setTitle("Sala");
        r3.setDescription("Parece una sala de estar, hay una nota sobre la mesa");
        r3.addItem(nota);
        r3.addExit(new Exit("w", r2)); //salida al oeste a room2
        r3.addExit(new Exit("s", r4)); //salida al sur a room4
        r4.setTitle("Habitación");
        r4.setDescription("Es una pequeña habitación, ves un tablero en la puerta de enfrente");
        r4.addItem(tablero);
        r4.addExit(new Exit("n", r3));  //salida al norte a room3
        r4.addExit(new Exit("s", r5)); //salida al sur y gana juego
        r5.setTitle("Salida!");
        r5.setDescription("Bien hecho! has encontrado la salida!");
        
        //agrego las habitaciones al conjunto de rooms
        rooms.add(r1);
        rooms.add(r2);
        rooms.add(r3);
        rooms.add(r4);
        rooms.add(r5);
        
        //agrego items del menu
        menu.add("1. Moverse");
        menu.add("2. Tomar item");
        
        //seteo la room inicial del jugador
        p.setPlayerRoom(r1);
        
    }
    
    
    //=======================================================================
    
    //PARA COMENZAR EL JUEGO EN EL MAIN
    
    //muestro titulo del room y descripcion
    public static void showRoom(){
        System.out.println(p.getPlayerRoom().getTitle());
        System.out.println(p.getPlayerRoom().getDescription());
    }
    
    //muestro menu de acciones
    public static void showMenu(){
        for(String i : menu){
            System.out.println(i);
        }
    }
    
    //interpreto la eleccion de accion y llamo a su correspondiente método
    public void actionMenu (String in){
        switch(in){
            case "1":
                p.moverPlayer();
                break;
            case "2":
                //agarrar item,
                break;
            case "3":
                //....
        }
    }
}
