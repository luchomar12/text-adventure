package Items;
import Interfaces.Abrible;
import Interfaces.Storable;
import Interfaces.Usable;
import Interfaces.Interactuable;
import java.util.*;
import textadventure.*;
import static textadventure.Game.in;
import static textadventure.Player.jugador;

public class Cofre extends Item implements Interactuable, Abrible{
    
    private Usable opener;
    private boolean opened;
    private Set<Storable> treasures = new HashSet<>();
    
    public Cofre(String name, String description, Room itemRoom){
        super(name, description, itemRoom);
        this.opened = false;
    }
    
    public void setOpener(Usable item){
        this.opener = item;
    }
    
    public void addTreasure(Storable item){
        treasures.add(item);
    }
    
    
    @Override
    public void interact(){
        if(!this.opened){
            System.out.println("Para abrirlo debes usar una llave");
            if(Game.p.inventory.isEmpty()){
                System.out.println("No tienes nada pasa usar");
            }else{
                System.out.println("¿Qué vas a usar?");
                System.out.println("");
                for(Storable i : Game.p.inventory){
                    System.out.println(i);
                }
                System.out.print("> ");
                String entry = in.nextLine();
                for(Storable item : Game.p.inventory){ //busco en el inventario
                    Item obj = (Item) item; //guardo en Item el Storable
                    if(entry.equalsIgnoreCase(obj.getItemName())){ //si mi entrada es igual al nombre del item
                        Usable llave = (Usable) obj;
                        this.open(llave);
                        break;
                    }else{
                        System.out.println("No puedes usar -"+entry+"-");
                    }
                }
            }
        }else{
            System.out.println("Este cofre ya fue abierto, no hay nada en él");
        }
    }
    
    @Override
    public void open(Usable item){
        if(item.equals(opener)){
            System.out.println(">> ¡Abriste el cofre! <<");
            this.opened = true;
            System.out.println("");
            System.out.println("Encuentras y guardas: ");
            for(Storable treasure : treasures){
                System.out.println("    -"+treasure);
                Game.p.inventory.add(treasure);
            }
            Item llave = (Item) this.opener;
            Game.p.getPlayerRoom().removeItem(this); //saco el cofre de la habitación porque ya no lo necesito
            //System.out.println("(Descartas la -"+llave.getItemName()+"- porque ya no la necesitas)");
            //Game.p.removeItem((Storable) llave);
        }else{
            System.out.println("No es la llave correcta");
        }
    }    
}
