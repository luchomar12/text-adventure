package textadventure;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import org.json.simple.parser.ParseException;

public class TextAdventure {

    public static void main(String[] args) throws IOException, FileNotFoundException, ParseException {
        Scanner in = new Scanner(System.in);
        Game juego = Game.dameInstancia();
        String input;
        juego.init(); //inicializar todas las variables del juego
        juego.showIntroduction(); //muestra introducción del juego

        while (true) {
            juego.showRoom(); //descripción de la habitación en la que me encuentro
            if (juego.getEnd()) {
                break;
            }
            juego.showMenu(); //muestro el menú de acciones
            input = in.nextLine().trim().toLowerCase(); //entrada de acciones
            if (input.equals("q")) {
                break; //opcion para salir del juego
            }
            juego.actionMenu(input); //interpreto el input
        }
        System.out.println("¡¡Hasta la próxima!!"); //lo que se muestra cuando salgo del juego
    }

}
