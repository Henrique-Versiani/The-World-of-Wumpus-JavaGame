package mainwumpus;

class Gold extends Item{
    private int totalGold;

    public void numberOfGold(int totalGold) {
        this.totalGold = totalGold;
    }

    public void incrementGold() {
        this.totalGold++;
    }

    public int getTotal() {
        return totalGold;
    }
}
