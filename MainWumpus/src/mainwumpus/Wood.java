package mainwumpus;

public class Wood extends Item {

    private int quantity = 0;
    private int row;
    private int col;

    public Wood(int initialQuantity, int row, int col) {
        this.quantity = 0;
        this.row = row;
        this.col = col;
    }
    
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getWood() {
        return quantity;
    }

    public void collectWood() {
        quantity++;
    }
    public void decrementWood() {
        if (quantity > 0) {
            quantity--;
        }
    }
    

}

