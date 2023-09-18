package mainwumpus;

public class Wood extends Item {

    private int quantity;

    public Wood(int initialQuantity) {
        this.quantity = initialQuantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void collectWood(int amount) {
        quantity += amount;
    }

}

