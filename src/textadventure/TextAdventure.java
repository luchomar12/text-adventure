package textadventure;

import java.util.*;

public class TextAdventure {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Game juego = Game.getInstance();

        System.out.println("Bienvenido a la Aventura de Texto");
        while (true) {
            int lugar = juego.p.getPlayerRoom();
            System.out.println("");
            System.out.println("====================");
            System.out.println(juego.rooms[lugar].getNombre());
            System.out.println(juego.rooms[lugar].getDescription());
            System.out.println("");
            System.out.println("¿Qué hacer?");
            System.out.println("1. Moverse");
            System.out.println("2. Tomar item");
            System.out.print("--->");
            juego.getInput(in.nextInt());

        }
        //System.out.println("Adios!!");
    }

}
