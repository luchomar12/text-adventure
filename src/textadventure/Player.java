package textadventure;
public class Player {
    
    private static Player jugador;
    private int room;
    private Item [] inventory = new Item[10];
    private int capacidad = 10;
    private int cantidadItems = 0;
    
    private Player(){
        this.room = 0;
    }
    
    public static Player getInstance(){
        if(jugador == null){
            return new Player();
        }
        return jugador;
    }
    
    public int getPlayerRoom(){
        return this.room;
    }
    
    public boolean moverA(int destino){
        if(destino != -1){
            this.room = destino;
            return true;
        }else{
            System.out.println("No puedes moverte allí");
            return false;
        }
    }
    
    public void takeItem(Item item){
        if(cantidadItems<=capacidad){
            inventory[cantidadItems] = item;
            cantidadItems++;
        }else{
            System.out.println("No puedes llevar más items.");
        }
    }
    
    public void showItems(){
        for (Item item : inventory) {
            if(item != null){
                System.out.println(item);
            }
        }
    }
}
