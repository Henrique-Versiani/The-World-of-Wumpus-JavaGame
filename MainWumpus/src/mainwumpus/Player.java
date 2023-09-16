package mainwumpus;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player {
    private int energyVital;
    private int maxCarryCapacity;
    private int currentCarryCapacity;
    private int positionX;
    private int positionY;
    private boolean lanternAvailable;
    private boolean arrowAvailable;
    private List<Item> inventory;

    public Player() {
            energyVital = 100;
            maxCarryCapacity = 3;
            currentCarryCapacity = 0;
            positionX = 0;
            positionY = 14;
            lanternAvailable = true;
            arrowAvailable = true;
            inventory = new ArrayList<>();
    }

    public void move(int newX, int newY) {

    }

    public void pickup(Item item) {

    }

    public void useLantern() {
            if (lanternAvailable) {
                // Tem que ver ainda como vão funcionar os métodos da lampada
                lanternAvailable = false;
            }
    }

    public void useArrow() {
            if (arrowAvailable) {
                // Implement arrow usage logic here
                arrowAvailable = false;
            }
    }

    public void craftArrow() {
        // Verifica se o jogador possui madeira no inventário
        boolean hasWood = false;
        for (Item item : inventory) {
            if (item instanceof Wood) {
                hasWood = true;
                break;
            }
        }

        if (hasWood) {
            Arrow arrow = new Arrow();
            inventory.add(arrow);
            currentCarryCapacity++;

            inventory.removeIf(item -> item instanceof Wood);

            System.out.println("You used wood to create an arrow.");
        } else {
            System.out.println("You don't have wood.");
        }
    }

    public void craftWood() {
        
    }

    public void returnToStart() {

    }

    public void encounterMonster(Wumpus1 monster) {

    }

    public void encounterObstacle(Obstacle obstacle) {

    }

    public void encounterGold(Gold gold) {
        if (currentCarryCapacity < maxCarryCapacity) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("You find gold! Do you want to collect? (Y for yes, N for no)");
            String choice = scanner.nextLine().trim().toUpperCase();
            
            if (choice.equals("S")) {
                inventory.add(gold);
                currentCarryCapacity++;
                
                // Dps tem que dar um jeito de tirar o ouro board
                
                System.out.println("You collected the gold!");
            } else if (choice.equals("N")) {
                System.out.println("You chose to left the gold behind.");
            } else {
                System.out.println("Invalid choice. Gold was not collected.");
            }
        } else {
            System.out.println("Your inventary is full. Drop off something to collect the gold.");
        }
    }

    public void encounterWall() {

    }
}