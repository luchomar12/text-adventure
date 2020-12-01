package textadventure;

import Items.*;
import java.util.*;
public class Game {

    //INICIALIZO ATRIBUTOS
    public static Scanner in = new Scanner(System.in);
    public static Game juego;
    public static Player p = Player.getInstance();
    private Set<Room> rooms = new HashSet<>();
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
    public void init(){
        
        //INICIALIZO HABITACIONES
        Room r1 = new Room();
        Room r2 = new Room();
        Room r3 = new Room();
        Room r4 = new Room();
        Room r5 = new Room();
        Room r6 = new Room();
        Room r7 = new Room();
        Room r8 = new Room();
        Room salida = new Room(); //salida

        //CREACION DE ITEMS/SALIDAS/ETC
        
        //Room1
        Llave llave1 = new Llave(1, "Llave pequeña", "Una LLAVE PEQUEÑA, tirada en un rincón. Sólo entraría en pequeños cerrojos.");
        Exit exitToPasillo = new Exit(2, "Puerta del Armario", "La puerta del armario no parece ceder, tampoco tiene cerrojo...\nTiene que haber alguna forma de abrirla", "n", r2, false); //salida cerrada hacia room2
        Palanca palanca = new Palanca(3,"Palanca", "Una PALANCA de madera incrustada en la pared. ¿Accionará algo?","Has accionado la palanca. El ruido de algo destrabándose te sobresalta."); //está en r1 y habilita la exit1

        //Room2
        Llave llave2 = new Llave(4, "Llave de cobre", "Una LLAVE DE COBRE, de tamaño chico");
        Llave destornillador = new Llave(5, "Destornillador", "Un destornillanor, puede ser útil para varias cosas...");
        Exit exitToSala = new Exit(6, "Puerta del Pasillo", "En la PUERTA DEL PASILLO el cerrojo ya está libre...Debo introducir una llave para abrirla","n", r3, false); //salida cerrada hacia room3
        PlacaMetalica placa = new PlacaMetalica(7, "Placa metalica", "Una PLACA METALICA en el cerrojo de la puerta.Tiene 4 tornillos...", destornillador);
        Nota nota1 = new Nota(8, "Nota", "Es una NOTA... Creo que llego a leerla... ¿está escrita en clave?");
        Cofre cofre1 = new Cofre(9, "Cofre Chico", "Un COFRE CHICO de madera, tiene una pequeña cerradura");

        //Room3
        Exit exitToGarage = new Exit(10, "toGarage", "salida al garage","w", r4, true); //esta me lleva al garage, ya esta abierta
        Exit exitToHabitacion = new Exit(11, "Puerta de Madera", "Una lujosa puerta de madera. Tiene un cerrojo ornamentado en plata. Está cerrada","e", r6, false);
        Exit exitToSecreta = new Exit(12, "Puerta electrónica", "Parece una puerta corrediza electrica, está cerrada. Al costado hay un tabero.","n", r8, false);
        Tablero tablero1 = new Tablero(13, "Tablero electrico", "Hay un TABLERO ELECTRICO está al lado de la puerta", false);
        Nota diario = new Nota(14, "Diario personal", "Un DIARIO PERSONAL. Tiene sólo una historia... podría decirme algo interesante");

        //Room4
        Exit exitToPatio = new Exit(15, "Puerta corrediza", "Una puerta corrediza, no tiene llave pero está trabada. Podría usar algo como palanca para destrabarla","s", r5, false);
        Palanca interruptor = new Palanca(16, "Interruptor", "Un interruptor, supongo prende la luz...", "Moviste el interruptor ¡ahora sí se puede ver!");
        Cofre cofre2 = new Cofre(17, "Cofre de metal", "Un COFRE DE METAL. No tiene cerradura");
        Tablero tablero2 = new Tablero(18, "Display digital", "Ahora está activado. Parece que hay que introducir algo en la pantalla",false);
        Llave llavePlata = new Llave(19, "Llave de Plata", "Una Llave mediana de Plata, muy ornamentada");

        //Room5
        Palanca switchElectrico = new Palanca(20, "Switch Electrico", "Un SWITCH ELECTRICO para activar la corriente electrica de algo...", "Se prendió un led, algo parece haberse activado");

        //Room6
        PlacaMetalica placa2 = new PlacaMetalica(38, "Placa Metalica","Una PLACA METALICA obstruye la boca del hogar. Está atornillada",destornillador);
        Cofre hogar = new Cofre(39, "Hogar", "Un gran HOGAR de piedra");
        Cofre cajaFuerte = new Cofre(21, "Caja Fuerte", "Una pequeña CAJA FUERTE con combinación a rosca");
        Exit exitToToilette = new Exit(22, "Puerta Toilette", "La pequeña pero elegante puerta está cerrada", "n", r7, false);
        Tablero combinacion = new Tablero(24, "Combinacion a rosca", "La caja fuerte tiene una combinación a rosca", true);
        Llave clip = new Llave(25, "Clip de pelo", "Un CLIP para el pelo de alambre muy fino... ¿me servirá?");
        Llave llaveOrnamentada = new Llave(26, "Llave ornamentada", "Una pequeña LLAVE ORNAMENTADA");
        Nota papelQuemado = new Nota(40,"Papel Quemado", "Aunque esté quemado se llega a leer algo");
        
        //Room7
        Cofre mochila = new Cofre(27, "Mochila", "Una MOCHILA. El cierre tiene un pequeño candado");
        Nota cuaderno = new Nota(28, "Cuaderno de Matematicas", "Un CUADERNO de matemáticas. Tiene algunas anotaciones... ");
        Llave llaveOro = new Llave(37, "Llave de Oro", "Una majestuosa LLAVE DE ORO, parece ser la mas importante");

        //Room8
        Exit exitToSalida = new Exit(29, "Puerta Cristalina", "Una extraña PUERTA CRISTALINA, tiene un gran cerrojo bañado en oro en el centro", "n", salida, false);

        //SETEO/AGREGO COMPONENTES
        //Room 1
        palanca.setUnlock(exitToPasillo);
        r1.setTitle("Armario");
        r1.setDescription("Te encuentras en un amplio armario.\nDe todas maneras sientes la asfixia y las paredes parecen encerrarte cada vez mas.\nParece sólo haber una puerta al frente... ¿estará abierta?");
        r1.addItem(llave1);
        r1.addItem(palanca);
        r1.addExit(exitToPasillo); //salida al norte a pasillo

        //Room 2   
        nota1.setNota("Viajamos por el reloj, por el tiempo\n-dos veces hacia el pasado-tres hacia el futuro-\nGira la rueda, has el intento\n-cuatro veces hacia el pasado-una hacia el futuro-");
        placa.setIsOn(exitToSala);
        exitToSala.setOpener(llave2);
        exitToSala.setInteractuable(placa);
        cofre1.setOpener(llave1);
        cofre1.addTreasure(llave2);
        cofre1.addTreasure(nota1);
        cofre1.addTreasure(destornillador);
        r2.setTitle("Pasillo");
        r2.setDescription("Estás en un tenue pasillo. Entran tres delgados hilos de luz desde unas finas aberturas en lo alto de las paredes.\nAl costado hay una mesa con una pequeña cajita. Al fondo una puerta.");
        r2.addItem(cofre1);
        r2.addExit(new Exit(30, "s", r1)); //salida al sur a armario (ya abierta)
        r2.addExit(exitToSala); //salida al norte a sala

        //Room 3
        diario.setNota("Doblé dos veces a la derecha, luego izquierda.\nObservé por arriba, luego giré a la izquierda\nPor último me agaché para saltar con ganas dos veces hacia arriba!");
        tablero1.setPassword(new String[]{"1", "0", "8", "9"});
        tablero1.setPassInfo("\"La contraseña es numérica. Ingresa la correcta y lograrás... salir\"");
        tablero1.setIn(exitToSecreta);
        exitToHabitacion.setOpener(llavePlata);
        exitToSecreta.setInteractuable(tablero1);
        r3.addItem(diario);
        r3.setTitle("Sala");
        r3.setDescription("Te encuentras en una gran sala de estar. Ves varias puertas y un librito apoyado sobre una cómoda.\nMmm... una de las puertas parece entreabierta... Otra tiene ... ¿un tablero?");
        r3.addExit(new Exit(31, "s", r2));//salida al sur a pasillo (ya abierta)
        r3.addExit(exitToGarage); //salida al oeste a garage
        r3.addExit(exitToHabitacion); //salida al este a habitacion/cerrada
        r3.addExit(exitToSecreta);

        //Room 4
        cofre2.setInteractuable(tablero2);
        cofre2.addTreasure(llavePlata);
        exitToPatio.setOpener(destornillador);
        tablero2.setPassword(new String[]{"de", "de", "iz", "ar", "iz", "ab", "ar", "ar"});
        tablero2.setPassInfo("\"La contraseña requiere de unas direcciones (de,iz,ar,ab). Introduce una dirección por vez\"");
        tablero2.setIn(cofre2);
        r4.setTitle("Garage");
        r4.setDescription("Un garage. No hay ningún auto pero veo varias herramientas y objetos");
        r4.setDark(true);
        r4.addExit(new Exit(32, "e", r3)); //salida a room3 (abierta)
        r4.addExit(exitToPatio); //salida al patio, trabada
        r4.addItem(interruptor);
        r4.addItem(cofre2);

        //Room 5
        switchElectrico.addTablero(tablero2);
        switchElectrico.addTablero(tablero1);
        r5.addItem(switchElectrico);
        r5.addExit(new Exit(33, "n", r4));//de vuelta al garage, al norte
        r5.setTitle("Patio");
        r5.setDescription("Un patio, completamente desierto y enrejado. Mas allá se ven bosques, praderas y montañas...");

        //Room 6
        combinacion.setPassInfo("Debo introducir secuencias de sentido(iz,de)/numero(1-9). Ejemplo: \"iz3\"");
        combinacion.setPassword(new String[]{"iz2", "de3", "iz4", "de1"});
        combinacion.setIn(cajaFuerte);
        hogar.addTreasure(clip);
        hogar.addTreasure(papelQuemado);
        hogar.setInteractuable(placa2);
        papelQuemado.setNota("...y por favor deshazte de esta nota...\n...caer en manos equivocadas...");
        placa2.setIsOn(hogar);
        cajaFuerte.setInteractuable(combinacion);
        cajaFuerte.addTreasure(llaveOrnamentada);
        exitToToilette.setOpener(llaveOrnamentada);
        r6.addItem(cajaFuerte);
        r6.addItem(hogar);
        r6.addExit(exitToToilette);
        r6.addExit(new Exit(34, "w", r3)); //puerta del dormitorio hacia la sala (ya abierta)
        r6.setTitle("Dormitorio");
        r6.setDescription("Un lujoso dormitorio. La cama es inmensa");

        //Room 7
        cuaderno.setNota("Puedes inventar cualquier número de 3 cifras\nSi inviertes sus dígitos ¿Cuál es el mayor? ¡Réstalo al menor!\n¿Qué queda? ¿y si lo inviertes también?\nA ver si podemos sumar ese último número al resultado de la resta anterior\n¡ESA ES LA RESPUESTA!\nNota: No deben ser los 3 dígitos iguales.\nSi la resta da un número de 2 dígitos agrégale el 0 en las centenas");
        mochila.setOpener(clip);
        mochila.addTreasure(cuaderno);
        mochila.addTreasure(llaveOro);
        r7.addItem(mochila);
        r7.addExit(new Exit(35, "s", r6)); //puerta hacia dormitorio (ya abierta)
        r7.setTitle("Toilette");
        r7.setDescription("El baño en suite del dormitorio");

        //Room 8
        exitToSalida.setOpener(llaveOro);
        r8.addExit(exitToSalida);
        r8.addExit(new Exit(36, "s", r3)); //puerta hacia sala (ya abierta)
        r8.setTitle("Habitación secreta");
        r8.setDescription("Una rara habitación. Parece una cápsula. La luz viene de todos lados sin un origen fijo.");

        //Salida
        salida.setTitle("...");
        salida.setDescription("\nSusurrantes brisas inundan tu escuchar\nImponentes montañas deleitan tu vista\nPrados eternos se abren ante ti");
        

        //AGREGO HABITACIONES AL CONJUNTO ROOMS
        rooms.add(r1);
        rooms.add(r2);
        rooms.add(r3);
        rooms.add(r4);
        rooms.add(r5);
        rooms.add(r6);
        rooms.add(r7);
        rooms.add(r8);
        rooms.add(salida);        
          
        //AGREGO ACCIONES AL MENU
        menu.add("1. Moverse");
        menu.add("2. Tomar item");
        menu.add("3. Interactuar con... ");
        menu.add("4. Mostrar inventario");
        menu.add("q. Salir del juego");

        //SETEO LA HABITACION DE INICIO
        p.setPlayerRoom(r1);
        //p.inventory.add(llaveOro);
        //p.inventory.add(destornillador);

    }
    

    //=======================================================================
    //PARA COMENZAR EL JUEGO EN EL MAIN
    //MUESTRO TITULO Y DESCRIPCION DE LA HABITACION 
    public void showRoom() {

        if (p.getPlayerRoom().isDark()) {
            p.choose();
        } else {
            System.out.println("========================================================");
            System.out.println(p.getPlayerRoom().getTitle()); //muestra el titulo de la habitación actual del jugador
            System.out.println("========================================================");
            System.out.println(p.getPlayerRoom().getDescription()); //muestro la descripcion de la habitación actual del jugador
            if(p.getPlayerRoom().getTitle().equalsIgnoreCase("...")){
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

    public void isEnd(boolean end){
        this.end = end;
    }
    
    public boolean getEnd(){
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
        System.out.println("Esta aventura de texto fue realizada por Luciano Marchese, Emiliano Santand y Verónica López Perea");
    }
}
