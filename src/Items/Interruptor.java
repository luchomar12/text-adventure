package Items;

import static textadventure.Game.in;
import Interfaces.Interactuable;
import java.util.HashSet;
import java.util.Set;
import static textadventure.Game.juego;

public class Interruptor extends Item implements Interactuable {

    //ATRIBUTOS
    private Exit unlock; // el Interruptor puede desbloquear una salida
    private Set<Tablero> activate = new HashSet<>();// el Interruptor puede activar uno o mas tableros
    private String unlockedDescription; //lo que aparece cuando activo la el Interruptor

    public Interruptor(int code, String itemName, String itemDescription, String unlockedDescription) {
        super(code, itemName, itemDescription);
        this.unlockedDescription = unlockedDescription;

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
            juego.getPlayer().getPlayerRoom().removeItem(this); //saco el Interruptor de la vista de la Room
            return true;
        }
        return false;
    }

}
