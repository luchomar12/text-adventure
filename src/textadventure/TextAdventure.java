package textadventure;

import java.util.*;

public class TextAdventure {

    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String input;
        Game juego = Game.getInstance();
        juego.init(); //inicializar todas las variables del juego

        System.out.println("Bienvenido a la Aventura de Texto");
        while (true) {
            juego.showRoom(); //descripción de la habitación en la que me encuentro
            juego.showMenu(); //muestro el menú de acciones
            input = in.nextLine().trim().toLowerCase(); //entrada de acciones
            if(input.equals("q")) break; //opcion para salir del juego
            juego.actionMenu(input); //interpreto el input
        }
        System.out.println("Adios!!"); //lo que se muestra cuando salgo del juego
    }

}
