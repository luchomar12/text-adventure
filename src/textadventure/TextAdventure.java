package textadventure;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import org.json.simple.parser.ParseException;

public class TextAdventure {

    public static void main(String[] args) throws IOException, FileNotFoundException, ParseException {
        Scanner in = new Scanner(System.in);
        String input;
        Game.init(); //inicializar todas las variables del juego
        Game.showIntroduction(); //muestra introducción del juego

        while (true) {
            Game.showRoom(); //descripción de la habitación en la que me encuentro
            if (Game.getEnd()) {
                break;
            }
            Game.showMenu(); //muestro el menú de acciones
            input = in.nextLine().trim().toLowerCase(); //entrada de acciones
            if (input.equals("q")) {
                break; //opcion para salir del juego
            }
            Game.actionMenu(input); //interpreto el input
        }
        System.out.println("¡¡Hasta la próxima!!"); //lo que se muestra cuando salgo del juego
    }
}
