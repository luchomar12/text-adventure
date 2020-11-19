package textadventure;
import java.util.*;
public class Location {
    private String roomTitle;
    private String roomDescription;
    private LinkedList listExits;
    
    public Location(){
        this.roomTitle = new String();
        this.roomDescription = new String();
        this.listExits = new LinkedList();
    }
    
    public Location(String title){
        this.roomTitle = title;
        this.roomDescription = new String();
        this.listExits = new LinkedList();
    }
    
    public Location(String title, String description){
        this.roomTitle = title;
        this.roomDescription = description;
        this.listExits = new LinkedList();
    }
    
    @Override
    public String toString(){
        return this.roomTitle;
    }
    
    public void addExit(Exit exit){
        this.listExits.add(exit);
    }
    
    public void removeExit(Exit exit){
        if(this.listExits.contains(exit)){
            this.listExits.remove(exit);
        }
    }
    
    public LinkedList getExits(){
        return (LinkedList) this.listExits.clone();
    }

    public String getRoomTitle() {
        return roomTitle;
    }

    public void setRoomTitle(String roomTitle) {
        this.roomTitle = roomTitle;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }
    
    
}
