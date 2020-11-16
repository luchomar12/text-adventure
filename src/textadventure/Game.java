package textadventure;

import java.util.Scanner;

public class Game {
    public static Game juego;
    public Scanner in = new Scanner(System.in);
    public Player p = Player.getInstance();

    public Item i0 = new Item(0, 3, "Llave de Plata", "Una gran llave de plata. Puede ser de ayuda.");
    public Item[] ir3 = new Item[1];

    public Room r0 = new Room(0, 1, -1, -1, -1, "Armario", "Te encuentras en un amplio armario.", ir3);
    public Room r1 = new Room(1, 2, 0, -1, -1, "Pasillo", "Un pasillo tenue, ves una puerta al final.", ir3);
    public Room r2 = new Room(2, 7, 1, 5, 3, "Vestíbulo", "Un amplio vestíbulo con dos puertas a los costados.", ir3);
    public Room r3 = new Room(3, -1, -1, 2, 4, "Garage", "Un garage, ves una llave en el piso.", ir3);
    public Room[] rooms = new Room[]{r0,r1,r2,r3};
    
    private Game(){}
    
    public static Game getInstance(){
        if(juego == null){
            return new Game();
        }
        return juego;
    }

    public int moverse() {
        while (true) {
            System.out.println("¿Dónde quieres ir? ('n'/'s'/'e'/'w') / 'b' (volver al menú)");
            int mover = in.next().charAt(0);
            switch (mover) {
                case 'b':
                    return -2; //con -2 vuelvo al menú principal
                case 'n':
                    return rooms[p.getPlayerRoom()].getNorth();
                case 's':
                    return rooms[p.getPlayerRoom()].getSouth();
                case 'e':
                    return rooms[p.getPlayerRoom()].getEast();
                case 'w':
                    return rooms[p.getPlayerRoom()].getWest();
                default:
                    System.out.println("No es un lugar válido.");
                    break; //no pudes ir allí.
            }
        }

    }

    public void getInput(int option) {
        int moverse = moverse();
        switch (option) {
            case 1:
                p.moverA(moverse);
                break;
            case 2:
                p.takeItem(i0);
                break;
        }
    }

}
