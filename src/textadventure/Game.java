package textadventure;

import Items.Exit;
import Items.*;
import java.util.*;

public class Game {

    //INICIALIZO ATRIBUTOS
    public static Scanner in = new Scanner(System.in);
    public static Game juego;
    public static Player p = Player.getInstance();
    public static Set<Room> rooms = new HashSet<>();
    public static Set<String> menu = new TreeSet<>();
    public static boolean end = false;

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
        Room salida = new Room(); //salida   
        
        //CREACION DE ITEMS/SALIDAS/ETC
        //Room1
        Llave llave1 = new Llave("Llave pequeña", "Una LLAVE PEQUEÑA, tirada en un rincón. Sólo entraría en pequeños cerrojos.", r1);
        Exit exitToPasillo = new Exit("Puerta del Armario", "La puerta del armario no parece ceder, tampoco tiene cerrojo...\nTiene que haber alguna forma de abrirla", r1, "n", r2, false); //salida cerrada hacia room2
        Palanca palanca = new Palanca("Palanca", "Una PALANCA de madera incrustada en la pared. ¿Accionará algo?", r1, "Has accionado la palanca. El ruido de algo destrabándose te sobresalta."); //está en r1 y habilita la exit1
        
        //Room2
        Llave llave2 = new Llave("Llave de cobre", "Una LLAVE DE COBRE, de tamaño chico", r2);
        Llave destornillador = new Llave("Destornillador", "Un destornillanor, puede ser útil para varias cosas...", r2);
        Exit exitToSala = new Exit("Puerta del Pasillo", "En la PUERTA DEL PASILLO el cerrojo ya está libre...Debo introducir una llave para abrirla", r2, "n", r3, false); //salida cerrada hacia room3
        PlacaMetalica placa = new PlacaMetalica("Placa metalica", "Una PLACA METALICA en el cerrojo de la puerta.\nNo puedo introducir ninguna llave así.\nTiene 4 tornillos. Creo que puedo desatornillarla con algo", r2, destornillador);
        Nota nota1 = new Nota("Nota", "Es una NOTA... ¿o poema?... Creo que llego a leerlo... ¿está en clave?", r2);
        Cofre cofre1 = new Cofre("Cofre Chico", "Un COFRE CHICO de madera, tiene una pequeña cerradura", r2);
        
        //Room3
        Exit exitToGarage = new Exit("toGarage", "salida al garage", r3, "w", r4, true); //esta me lleva al garage, ya esta abierta
        Exit exitToHabitacion = new Exit("Puerta de Madera", "Una lujosa puerta de madera. Tiene un cerrojo ornamentado en plata. Está cerrada", r3, "e", r6, false);
        Exit exitToSecreta = new Exit("Puerta electrónica", "Parece una puerta corrediza electrica, está cerrada. Debo poder activarla con algo", r3, "n", r8, false);
        Tablero tablero1 = new Tablero("Tablero electrico", "Un TABLERO ELECTRICO. Está al lado de una de las puertas", r3, false);
        Nota diario = new Nota("Diario personal", "Un DIARIO PERSONAL. Tiene sólo una historia... podría decirme algo interesante", r3);
        
        //Room4
        Exit exitToPatio = new Exit("Puerta corrediza", "Una puerta corrediza, no tiene llave pero está trabada. Podría usar algo como palanca para destrabarla", r4, "s", r5, false);
        Palanca interruptor = new Palanca("Interruptor", "Un interruptor, supongo prende la luz...", r4, "Moviste el interruptor ¡ahora sí se puede ver!");
        Cofre cofre2 = new Cofre("Cofre de metal", "Un COFRE DE METAL. Tiene una pequeña cerradura",r4);
        Tablero tablero2 = new Tablero("Display digital", "Un DISPLAY DIGITAL. Está apagado...",r4 ,false);
        Llave llavePlata = new Llave("Llave de Plata", "Una Llave mediana de Plata, muy ornamentada", r4);
        
        //Room5
        Palanca switchElectrico = new Palanca("Switch Electrico", "Un SWITCH ELECTRICO para activar la corriente electrica de algo...",r5, "Se prendió un led, algo parece haberse activado");
        
        //Room6
        Cofre cajaFuerte = new Cofre("Caja Fuerte", "Una pequeña CAJA FUERTE con combinación a rosca",r6);
        Exit exitToToilette = new Exit("Puerta Toilette", "La pequeña pero elegante puerta está cerrada", r6, "n", r7, false);
        Nota papelQuemado = new Nota("Papel Quemado", "Un papel con los bordes quemados. De todas maneras algo se llega a leer.", r6);
        Tablero combinacion = new Tablero("Combinacion a rosca", "La caja fuerte tiene una combinación a rosca",r6,true);
        Llave clip = new Llave("Clip de pelo", "Un CLIP para el pelo de alambre muy fino... ¿me servirá?",r6);
        Llave llaveOrnamentada = new Llave("Llave ornamentada", "Una pequeña LLAVE ORNAMENTADA",r6);
        
        //Room7
        Cofre mochila = new Cofre("Mochila", "Una MOCHILA con un pequeño candado",r7);
        Nota cuaderno = new Nota("Cuadero de Matematicas", "Un CUADERNO de matemáticas. Tiene algunas anotaciones... mmm",r7);
        
        //Room8
        Exit exitToSalida = new Exit("Puerta final", "Una puerta de cristal, tiene un gran cerrojo bañado en oro en el centro", r8, "n", salida, false);

        //Salida
        
        //SETEO/AGREGO COMPONENTES
        //Room 1
        palanca.setUnlock(exitToPasillo);
        r1.setTitle("Armario");
        r1.setDescription("Te encuentras en un amplio armario.\nDe todas maneras sientes la asfixia y las paredes parecen encerrarte cada vez mas.\nParece sólo haber una puerta al frente... ¿estará abierta?");
        r1.addItem(llave1);
        r1.addItem(palanca);
        r1.addExit(exitToPasillo); //salida al norte a pasillo

        //Room 2   
        nota1.setNota("\"La poca luz que entra en mi\nCasi a tientas te dejó\nSin cuenta lo contó\nY del pasillo se salió\"\n...\nEstan remarcados el primer y el tecer párrafo...");
        exitToSala.setIsBlocked(true);
        placa.setPlace(exitToSala);
        exitToSala.setOpener(llave2);
        exitToSala.setIsInteractuable(false);
        exitToSala.setInteractuable(placa);
        cofre1.setOpener(llave1);
        cofre1.addTreasure(llave2);
        cofre1.addTreasure(nota1);
        cofre1.addTreasure(destornillador);
        r2.setTitle("Pasillo");
        r2.setDescription("Estás en un tenue pasillo. Entran tres delgados rayos de luz desde unas finas aberturas en lo alto de las paredes.\n"
                + "Al costado hay una mesa con una pequeña cajita. Al fondo una puerta.");
        r2.addItem(cofre1);
        r2.addExit(new Exit("toArmario", "vuelta al armario", r2, "s", r1, true)); //salida al sur a armario (ya abierta)
        r2.addExit(exitToSala); //salida al norte a sala

        //Room 3
        diario.setNota("Doblé dos veces a la derecha, luego izquierda.\nObservé por arriba, luego giré a la izquierda\nPor último me agaché para saltar con ganas dos veces hacia arriba!");
        tablero1.setPassword(new String []{"1","0","8","9"});
        tablero1.setIn(exitToSecreta);
        exitToHabitacion.setOpener(llavePlata);
        exitToSecreta.setInteractuable(tablero1);
        r3.addItem(tablero1);
        r3.addItem(diario);
        r3.setTitle("Sala");
        r3.setDescription("Te encuentras en una gran sala de estar. Ves varias puertas y un librito apoyado sobre una cómoda.\nMmm... una de las puertas parece entreabierta... Otra tiene ... ¿un tablero?");
        r3.addExit(new Exit("toPasillo", "al pasillo", r3, "s", r2, true));//salida al sur a pasillo (ya abierta)
        r3.addExit(exitToGarage); //salida al oeste a garage
        r3.addExit(exitToHabitacion); //salida al este a habitacion/cerrada
        r3.addExit(exitToSecreta);

        //Room 4
        cofre2.setInteractuable(tablero2);
        cofre2.addTreasure(llavePlata);
        exitToPatio.setIsInteractuable(true);
        exitToPatio.setOpener(destornillador);
        tablero2.setPassword(new String[]{"de","de","iz","ar","iz","ab","ar","ar"});
        tablero2.setPista("\"La contraseña requiere de unas direcciones (de,iz,ar,ab). Introduce una dirección por vez\"");
        tablero2.setIn(cofre2);
        r4.setTitle("Garage");
        r4.setDescription("Un garage. No hay ningún auto pero veo varias herramientas y objetos");
        r4.setDark(true);
        r4.addExit(new Exit("toSala", "salida a la sala", r4, "e", r3, true)); //salida a room3 (abierta)
        r4.addExit(exitToPatio); //salida al patio, trabada
        r4.addInteractuableItem(interruptor);
        r4.addItem(cofre2);

        //Room 5
        switchElectrico.addTablero(tablero2);
        switchElectrico.addTablero(tablero1);
        r5.addItem(switchElectrico);
        r5.addExit(new Exit("alGarage", "vuelta al garage", r5, "n", r4, true));//de vuelta al garage, al norte
        r5.setTitle("Patio");
        r5.setDescription("Un patio, completamente desierto y enrejado. Mas allá se ven bosques y montañas...");

        //Room 6
        combinacion.setPista("Debo introducir secuencias de dirección horaria (iz,de)/numero(1-9). Ejemplo: \"iz3\"");
        combinacion.setPassword(new String[]{"iz2"});
        combinacion.setIn(cajaFuerte);
        cajaFuerte.setInteractuable(combinacion);
        cajaFuerte.addTreasure(llaveOrnamentada);
        cajaFuerte.addTreasure(papelQuemado);
        cajaFuerte.addTreasure(clip);
        papelQuemado.setNota("Nota para la habitación final");
        exitToToilette.setOpener(llaveOrnamentada);
        r6.addItem(cajaFuerte);
        r6.addExit(exitToToilette);
        r6.addExit(new Exit("aLaSala", "vuelta a la sala", r6, "w", r3, true)); //puerta del dormitorio hacia la sala (ya abierta)
        r6.setTitle("Dormitorio");
        r6.setDescription("Un lujoso dormitorio. La cama es inmensa");

        //Room 7
        cuaderno.setNota("Puedes inventar cualquier número de 3 cifras\nSi inviertes sus dígitos ¿Cuál es el mayor? ¡Réstalo al menor!\n¿Qué queda? ¿y si lo inviertes también?\nA ver si podemos sumar ese último número al resultado de la resta anterior\n¡ESA ES LA RESPUESTA!\n(PD: No deben ser los 3 dígitos iguales.\nSi la resta da un número de 2 dígitos agrégale el 0 en las centenas)");
        mochila.setOpener(clip);
        mochila.addTreasure(cuaderno);
        r7.addItem(mochila);
        r7.addExit(new Exit("toDormitorio", "vueelta a dormitorio", r7, "w", r6, true)); //puerta hacia dormitorio (ya abierta)
        r7.setTitle("Toilette");
        r7.setDescription("El baño en suite del dormitorio");

        //Room 8
        r8.addExit(new Exit("ToSalaVuelta", "vuelta a la sala", r8, "s", r3, true)); //puerta hacia sala (ya abierta)
        r8.setTitle("Habitación secreta");
        r8.setDescription("Una rara habitación. Es toda blanca, la luz viene de todos lados sin un origen fijo.");

        //Room Salida
        salida.setTitle("salida");

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
        p.setPlayerRoom(r6);
        p.inventory.add(destornillador);

    }

    //=======================================================================
    //PARA COMENZAR EL JUEGO EN EL MAIN
    //MUESTRO TITULO Y DESCRIPCION DE LA HABITACION 
    public static void showRoom() {
        if (p.getPlayerRoom().getTitle().equals("salida")) {
            juego.end();
        }
        if (p.getPlayerRoom().isDark()) {
            p.choose();
        } else {
            System.out.println("========================================================");
            System.out.println(p.getPlayerRoom().getTitle()); //muestra el titulo de la habitación actual del jugador
            System.out.println("========================================================");
            System.out.println(p.getPlayerRoom().getDescription()); //muestro la descripcion de la habitación actual del jugador
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

    //SALIDA - FIN DEL JUEGO
    public static void end() {
        System.out.println("FELICITACIONES HAS GANADO!!!");
        juego.end = true;
    }

}
