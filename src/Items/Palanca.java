package Items;


import static textadventure.Game.in;
import Interfaces.Interactuable;
import java.util.HashSet;
import java.util.Set;
import textadventure.Room;

public class Palanca extends Item implements Interactuable {

    private Exit unlock; //la palanca puede desbloquear una salida
    private Set<Tablero> activate = new HashSet<>();//la palanca puede activar uno o mas tableros
    private String unlockedDescription;

    public Palanca(String itemName, String itemDescription, Room itemRoom, String unlockedDescription) {
        super(itemName, itemDescription, itemRoom);
        this.unlockedDescription = unlockedDescription;

    }

    public void setUnlock(Exit exit) {
        this.unlock = exit;
    }
    
    public void addTablero(Tablero tablero){
        this.activate.add(tablero);
    }

    @Override
    public void interact() {
        System.out.println("");
        System.out.println("¿Mover? (si/no)");
        System.out.println("");
        System.out.print("> ");
        String input = in.nextLine();
        if (input.equalsIgnoreCase("si")) {
            System.out.println(this.unlockedDescription);
            if (unlock != null) {
                unlock.setIsOpened(true);
            }
            if(!this.activate.isEmpty()){
                for(Tablero t : this.activate){
                    t.setIsOn(true);
                }
            }
            if (this.itemRoom.isDark()) {
                this.itemRoom.setDark(false);
            }
            this.itemRoom.removeItem(this);
            this.itemRoom.removeInteractuableItem(this);
        } else {
            System.out.println("No has hecho nada...");
        }
    }
}
