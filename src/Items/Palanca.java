package Items;

import static textadventure.Game.in;
import Interfaces.Interactuable;
import java.util.HashSet;
import java.util.Set;
import textadventure.Game;
import static textadventure.Game.p;

public class Palanca extends Item implements Interactuable {

    //ATRIBUTOS
    private Exit unlock; //la palanca puede desbloquear una salida
    private Set<Tablero> activate = new HashSet<>();//la palanca puede activar uno o mas tableros
    private String unlockedDescription; //lo que aparece cuando activo la palanca
    //INTS
    private int intUnlock;

    public int getIntUnlock() {
        return intUnlock;
    }

    public Set<Integer> getIntActivate() {
        return intActivate;
    }
    private Set<Integer> intActivate = new HashSet<>();

    public Palanca(int code, String itemName, String itemDescription, String unlockedDescription, int unlock, Set<Integer> activate) {
        super(code, itemName, itemDescription);
        this.unlockedDescription = unlockedDescription;
        this.intUnlock = unlock;
        this.intActivate = activate;

    }

    public Exit getUnlock() {
        return this.unlock;
    }

    public void setUnlock(Exit exit) {
        this.unlock = exit;
    }

    public Set<Tablero> getTableros() {
        return this.activate;
    }

    public void addActivate(Tablero tablero) {
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
            if (this.getUnlock() != null) { //si abre una salida
                this.getUnlock().setIsOpened(true); // abro la salida
            }
            if (!this.getTableros().isEmpty()) { //si tiene tableros para activar
                for (Tablero t : this.getTableros()) { //los busco
                    t.setIsOn(true); //los activo
                }
            }
            p.getPlayerRoom().removeItem(this); //saco la palanca de la vista de la Room
            return true;
        }
        return false;
    }
}
