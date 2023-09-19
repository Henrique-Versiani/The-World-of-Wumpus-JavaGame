package mainwumpus;

public class Place {
    private int pitWeight;
    private int wumpusWeight;
    private int visitedWeight;
    private final int totalWeight;
    private final int row;
    private final int column;
    private Object object;
    
    private String previous;
    
    private boolean foundGold;
    private boolean isExit;
    private boolean clear;
    
    public Place(int row, int column){
        this.row = row;
        this.column = column;
        clear = false;
        pitWeight = 0;
        wumpusWeight = 0;
        visitedWeight = 0;
        totalWeight = 0;
        this.object = null;
    }
    
    public void possiblePit(int pitWeight){
        if(clear)
            return;
        this.pitWeight += pitWeight;
    }
    
    public void possibleWumpus(int wumpusWeight){
        if(clear)
            return;
        this.wumpusWeight += wumpusWeight;
    }
    
    public void possiblePitAndWumpus(int pitWeight, int wumpusWeight){
        if(clear)
            return;
        this.pitWeight += pitWeight;
        this.wumpusWeight += wumpusWeight;
    }
    
    public void visited(int visitedWeight){
        this.visitedWeight += visitedWeight;
    }
    
    public void setClear(){
        this.wumpusWeight = 0;
        this.pitWeight = 0;
        clear = true;
    }
    
    public int totalWeight(){
        return pitWeight + wumpusWeight + visitedWeight;
    }
    
    public int getRow(){
        return row;
    }
    
    public int getColumn(){
        return column;
    }
    
    public void setPrevious(String previous){
        this.previous = previous;
    }
    
    public String getPrevious(){
        return previous;
    }
    
    public void discoverGold(){
        foundGold = true;
    }

    public void setAsExit(){
        isExit = true;
    }
    
    public boolean hasFoundGold(){
        return foundGold;
    }
    
    public boolean isExit(){
        return isExit;
    }
    
    public String getId(){
        return row + ":" + column;
    }
    
     public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
