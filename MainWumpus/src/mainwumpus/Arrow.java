package mainwumpus;

class Arrow extends Item{
    private int totalArrow;

    public void numberOfArrows(int totalArrow) {
        this.totalArrow = totalArrow;
    }

    public void decrementArrow() {
        this.totalArrow--;
    }

    public int getArrow() {
        return totalArrow;
    }
    
    public void addArrow() {
        totalArrow++;
    }
    
    public boolean createArrow(Wood wood) {
        if (wood.getWood() > 0) {
            wood.collectWood();
            totalArrow++;
            return true; 
        }
        return false;
    }
}
