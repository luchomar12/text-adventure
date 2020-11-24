package textadventure;

import java.util.*;

public class TextAdventure {

    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Game juego = Game.getInstance();
        juego.init(); //inicializar todas las variables del juego

        System.out.println("Bienvenido a la Aventura de Texto");
        while (true) {
            Game.showRoom(); //descripción de la habitación en la que me encuentro
            Game.showMenu(); //muestro el menú de acciones
            String entrada = in.nextLine();            
            entrada = entrada.trim().toLowerCase();
            if(entrada.equals("q")){
                break;
            }
            Game.actionMenu(entrada); //hago la acción
        }
        System.out.println("Adios!!");
    }

}
