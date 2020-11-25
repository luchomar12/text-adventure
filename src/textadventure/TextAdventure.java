package textadventure;

import java.util.*;

public class TextAdventure {

    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String input;
        Game.getInstance();
        Game.init(); //inicializar todas las variables del juego

        System.out.println("Bienvenido a la Aventura de Texto");
        while (true) {
            Game.showRoom(); //descripción de la habitación en la que me encuentro
            Game.showMenu(); //muestro el menú de acciones
            input = in.nextLine().trim().toLowerCase(); //entrada de acciones
            if(input.equals("q")) break; //opcion para salir del juego
            Game.actionMenu(input); //hago la acción
        }
        System.out.println("Adios!!"); //lo que se muestra cuando salgo del juego
    }

}
