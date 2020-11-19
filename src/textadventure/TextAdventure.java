package textadventure;

import java.util.*;

public class TextAdventure {

    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Game juego = Game.getInstance();
        juego.init();

        System.out.println("Bienvenido a la Aventura de Texto");
        while (true) {
            Game.showRoom();
            Game.showMenu();
            juego.actionMenu(in.nextLine());
        }
        //System.out.println("Adios!!");
    }

}
