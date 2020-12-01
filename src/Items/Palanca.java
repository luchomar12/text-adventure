package Items;

import static textadventure.Game.in;
import Interfaces.Interactuable;
import java.util.HashSet;
import java.util.Set;
import static textadventure.Game.p;

public class Palanca extends Item implements Interactuable {

    //ATRIBUTOS
    private Exit unlock; //la palanca puede desbloquear una salida
    private Set<Tablero> activate = new HashSet<>();//la palanca puede activar uno o mas tableros
    private String unlockedDescription; //lo que aparece cuando activo la palanca

    public Palanca(int code, String itemName, String itemDescription, String unlockedDescription) {
        super(code, itemName, itemDescription);
        this.unlockedDescription = unlockedDescription;

    }

    public void setUnlock(Exit exit) {
        this.unlock = exit;
    }

    public void addTablero(Tablero tablero) {
        this.activate.add(tablero);
    }

    @Override
    public void interact() {
        System.out.println("");
        System.out.println("¿Mover? (si/no)");
        System.out.println("");
        System.out.print("> ");
        String input = in.nextLine();
        if (!validateInteract(input)) {
            System.out.println("No has hecho nada...");
        }
    }

    @Override
    public boolean validateInteract(String input) {
        if (input.equalsIgnoreCase("si")) {
            System.out.println(this.unlockedDescription); //muestro lo que sucedió
            if (unlock != null) { //si abre una salida
                unlock.setIsOpened(true); // abro la salida
            }
            if (!this.activate.isEmpty()) { //si tiene tableros para activar
                for (Tablero t : this.activate) { //los busco
                    t.setIsOn(true); //los activo
                }
            }
            if (p.getPlayerRoom().isDark()) { //si la habitación está a oscuras
                p.getPlayerRoom().setDark(false); //la prendo
            }
            p.getPlayerRoom().removeItem(this); //saco la palanca de la vista de la Room
            return true;
        }
        return false;
    }
}
