package textadventure;
public class Exit {
    private String direction;
    private Room leadsTo;
    
    public Exit(String direction, Room leadsTo){
        this.direction = direction;
        this.leadsTo = leadsTo;
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

    @Override
    public int hashCode() {
        return this.direction.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        Exit e = (Exit) obj;
        return this.hashCode() == e.hashCode();
    }
}
