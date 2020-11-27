package Items;

import textadventure.Game;
import static textadventure.Game.in;
import Interfaces.Interactuable;
import textadventure.Room;

public class Palanca extends Item implements Interactuable {

    private Exit unlock; //la palanca desbloquea una salida
    private String unlockedDescription;

    public Palanca(String itemName, String itemDescription, Room itemRoom, Exit unlock, String unlockedDescription) {
        super(itemName, itemDescription, itemRoom);
        this.unlock = unlock;
        this.unlockedDescription = unlockedDescription;
        
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
            unlock.setIsOpened(true);
            this.itemRoom.removeItem(this);
            if(this.itemRoom.isDark()){
                this.itemRoom.setDark(false);
            }
        } else {
            System.out.println("No has hecho nada...");
        }
    }
}
