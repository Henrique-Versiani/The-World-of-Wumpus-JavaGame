package mainwumpus;

class Arrow extends Item{
    private int totalArrow;

    public void numberOfArrows(int totalArrow) {
        this.totalArrow = totalArrow;
    }

    public void decrementArrow() {
        this.totalArrow--;
    }

    public int getTotal() {
        return totalArrow;
    }
    
    public boolean createArrow(Wood wood) {
        if (wood.getQuantity() > 0) {
            wood.collectWood(-1);
            totalArrow++;
            return true; 
        }
        return false;
    }
}
