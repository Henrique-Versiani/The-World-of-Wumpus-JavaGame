package mainwumpus;

class Gold extends Item{
    private int totalGold;
    private int row;
    private int col;

    public void numberOfGold(int totalGold, int row, int col) {
        this.totalGold = totalGold;
        this.row = row;
        this.col = col;
    }

    public void incrementGold() {
        this.totalGold++;
    }

    public int getGold() {
        return totalGold;
    }
}
