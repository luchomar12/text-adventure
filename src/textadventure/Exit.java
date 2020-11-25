package textadventure;

import Interfaces.Abrible;
import Interfaces.Usable;
import Interfaces.Interactuable;

public class Exit implements Interactuable, Abrible{
    private String direction;
    private Room leadsTo;
    private boolean isOpened;
    private String openedDescription;
    private String closedDescription;
    private Interactuable interactuable;


    public Exit(String direction, Room leadsTo, boolean isOpened){
        this.direction = direction;
        this.leadsTo = leadsTo;
        this.isOpened = isOpened;
    }
    
    public Interactuable getInteractuable() {
        return interactuable;
    }

    public void setInteractuable(Interactuable interactuable) {
        this.interactuable = interactuable;
    }
    
    public String getOpenedDescription() {
        return openedDescription;
    }

    public void setOpenedDescription(String openedDescription) {
        this.openedDescription = openedDescription;
    }

    public String getClosedDescription() {
        return closedDescription;
    }

    public void setClosedDescription(String closedDescription) {
        this.closedDescription = closedDescription;
    }    
     
    public void setLeadsTo(Room room){
        this.leadsTo = room;
    }
    
    public Room getLeadsTo(){
        return this.leadsTo;
    }
    
    public String getDirection(){
        return this.direction;
    }
    
    public boolean isOpened(){
        return this.isOpened;
    }
    
    public void setIsOpened(boolean isOpened){
        this.isOpened = isOpened;
    }
    
    

    @Override
    public int hashCode() {
        return this.direction.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        Exit e = (Exit) obj;
        return this.hashCode() == e.hashCode();
    }

    @Override
    public void interact() {
        if(this.isOpened){
            System.out.println(this.openedDescription);
        }else{
            System.out.println(this.closedDescription);            
        }
    }

    @Override
    public void open(Usable item) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
