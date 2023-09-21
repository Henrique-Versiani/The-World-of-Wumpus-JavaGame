package mainwumpus;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Player {
    private int energy = 100;
    private int positionX;
    private int positionY;
    private boolean lanternAvailable;
    private boolean arrowAvailable;
    private List<Object> backpack;
    private int backpackCapacity;
    private boolean hasGold = false;
    private int totalGold;
    private int totalWood;

    public Player() {
           
            this.backpack = new ArrayList<>();
            this.backpackCapacity = 1;
            positionX = 0;
            positionY = 14;
            lanternAvailable = true;
            arrowAvailable = true;
    }

    public boolean isBackpackFull() {
        return backpack.size() >= backpackCapacity;
    }
    
    public void addToBackpack(Item gameItem) {
        if (!isBackpackFull()) {
            backpack.add(gameItem);
        }
    }
   
    public void useLantern() {
            if (lanternAvailable) {
                // Tem que ver ainda como vão funcionar os métodos da lampada
                lanternAvailable = false;
            }
    }

    public void useArrow() {
            if (arrowAvailable) {
                // vou precisar do board
                arrowAvailable = false;
            }
    }

    public void createArrow() {
            Arrow arrow = new Arrow();
            Wood wood = new Wood(0,0,0);
            backpack.removeIf(item -> item instanceof Wood);
            backpack.add(arrow);
            arrow.addArrow();
            wood.decrementWood();
    }

    public void returnToStart() {

    }
    
    public void setEnergy(int newEnergy){
        energy = newEnergy;
    }
    
    public int getEnergy() {
        return energy;
    }
    
    public boolean hasGold() {
        return hasGold;
    }
    
    public void setHasGold(boolean hasGold) {
        this.hasGold = hasGold;
    }
    
    public void clearBackpack() {
        backpack.clear();
    }
    
    public void collectWood() {
        totalWood++;
    }
    
    public int getWood(){
        return totalWood;
    }
    
    public void collectGold() {
        totalGold++;
    }
    
    public int getGold(){
        return totalGold;
    }
    
}