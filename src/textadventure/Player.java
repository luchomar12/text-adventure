package textadventure;
import java.util.*;
public class Player{
    
    public Scanner in = new Scanner(System.in);
    private static Player jugador;
    private Room playerRoom;
    
    
    public Player(){}
    
    public static Player getInstance(){
        if(jugador == null){
            return new Player();
        }
        return jugador;
    }
    
    public Room getPlayerRoom(){
        return this.playerRoom;
    }
    
    public void setPlayerRoom(Room r){
        this.playerRoom = r;
    }
    
    //metodo para moverse moverse
    public void moverPlayer(){
        boolean seMueve = false;
        while(!seMueve){
            System.out.println("Donde quieres ir? (n,s,e,w) o (b) para volver al menú.");            
            String direction = in.nextLine();
            if(direction.equals("b")){
                //Game.showRoom();
                break;
            }else if(this.playerRoom.getExit(direction, this)){ //da true si hay salida, false si no hay
                seMueve = true;//si hay salida salgo del bucle, sino vuelve a preguntar
            }else{
                System.out.println("No hay salida para esa dirección");    
            }                    
        }   
    }    
}
