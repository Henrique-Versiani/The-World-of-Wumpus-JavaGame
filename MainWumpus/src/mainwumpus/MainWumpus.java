package mainwumpus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Point;
import java.util.ArrayList;

public class MainWumpus extends JFrame {
    private Place[][] gameMap;
    private GameBoard gameBoard;
    private int playerRow;
    private int playerCol;
    private Wumpus1 wumpus1;
    private Wumpus2 wumpus2;
    private Player player;
    private InfoPanel infoPanel;
    private Gold gold;
    private Arrow arrow;
    private Wood wood;
    private int energy = 100;
    private boolean hasGold = false;
    private boolean hasWood = false;

    public MainWumpus(Place[][] gameMap) {
        this.gameMap = gameMap;
        setTitle("Wumpus Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1360,720 );
        setLocationRelativeTo(null);
        playerRow = 14;
        playerCol = 0;
        player = new Player();
       
        
        gameBoard = new GameBoard(15, 15, gameMap);
        add(gameBoard, BorderLayout.CENTER);
        gameBoard.initializeGameElements();
        wumpus1 = new Wumpus1(gameBoard);
        wumpus2 = new Wumpus2(gameBoard);
        
        Gold gold = new Gold();
        Wood wood = new Wood(0,0,0);
        Arrow arrow = new Arrow();

        infoPanel = new InfoPanel(player, gold, wood, arrow);
        add(infoPanel, BorderLayout.WEST);
        setVisible(true);
        
        
        JPanel craftButtonPanel = new JPanel();
        add(craftButtonPanel, BorderLayout.EAST);

        JButton craftArrowButton = new JButton("Craft Arrow");
        JButton clearBackpackButton = new JButton("Clear Backpack");

        craftButtonPanel.setLayout(new BoxLayout(craftButtonPanel, BoxLayout.Y_AXIS));
        craftButtonPanel.add(craftArrowButton);
        craftButtonPanel.add(clearBackpackButton);

        
        JPanel buttonPanel = new JPanel();
        add(buttonPanel, BorderLayout.SOUTH);

        JButton moveUpButton = new JButton("Up");
        JButton moveDownButton = new JButton("Down");
        JButton moveLeftButton = new JButton("Left");
        JButton moveRightButton = new JButton("Right");

        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(moveUpButton);
        buttonPanel.add(moveDownButton);
        buttonPanel.add(moveRightButton);
        buttonPanel.add(moveLeftButton);
        
        gold = new Gold();
        wood = new Wood(2,0,0);
        arrow = new Arrow();
        
        infoPanel = new InfoPanel(player, gold, wood, arrow);
        
        craftArrowButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!hasWood) {
                notCraftMessage();
            } else { // 
                player.createArrow();
                craftMessage();
                infoPanel.update(); 
            }
        }
    });
        
        clearBackpackButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (player.isBackpackFull()) {
                clearBackpackMessage();
                player.clearBackpack();
                infoPanel.update(); 
            } else {
                notClearBackpackMessage();
            }
        }
    });
        

        moveUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                movePlayerUp();
                if (playerRow == gameBoard.getGoldRows() && playerCol == gameBoard.getGoldCols()) {
                    pickUpGold();
                } 
                if (gameBoard.woodAt(playerRow, playerCol)){
                    Wood wood = new Wood(0,0,0);
                    wood.collectWood();
                    pickUpWood();
                }
                wumpus1.move(gameBoard.getRows(), gameBoard.getCols(), gameMap);
                wumpus2.move(gameBoard.getRows(), gameBoard.getCols(), gameMap);
                checkPlayerStatus();
            }
        });
        

        moveDownButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                movePlayerDown();
                if (playerRow == gameBoard.getGoldRows() && playerCol == gameBoard.getGoldCols()) {
                    pickUpGold();
                } 
                if (gameBoard.woodAt(playerRow, playerCol)){
                    Wood wood = new Wood(0,0,0);
                    wood.collectWood();
                    pickUpWood();
                }
                wumpus1.move(gameBoard.getRows(), gameBoard.getCols(), gameMap);
                wumpus2.move(gameBoard.getRows(), gameBoard.getCols(), gameMap);
                checkPlayerStatus();
            }
        });

        moveLeftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                movePlayerLeft();
                if (playerRow == gameBoard.getGoldRows() && playerCol == gameBoard.getGoldCols()) {
                    pickUpGold();
                } 
                if (gameBoard.woodAt(playerRow, playerCol)){
                    Wood wood = new Wood(0,0,0);
                    wood.collectWood();
                    pickUpWood();;
                }
                wumpus1.move(gameBoard.getRows(), gameBoard.getCols(), gameMap);
                wumpus2.move(gameBoard.getRows(), gameBoard.getCols(), gameMap);
                checkPlayerStatus();
            }
        });

        moveRightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                movePlayerRight();
                if (playerRow == gameBoard.getGoldRows() && playerCol == gameBoard.getGoldCols()) {
                    pickUpGold();
                } 
                if (gameBoard.woodAt(playerRow, playerCol)){
                    Wood wood = new Wood(0,0,0);
                    wood.collectWood();
                    pickUpWood();
                }
                wumpus1.move(gameBoard.getRows(), gameBoard.getCols(), gameMap);
                wumpus2.move(gameBoard.getRows(), gameBoard.getCols(), gameMap);
                checkPlayerStatus();
            }
        });

        setVisible(true);
    }

    private void movePlayerUp() {
        if (playerRow > 0) {
            playerRow--;
            gameBoard.setPlayerPosition(playerRow, playerCol);
        }
    }
    

    private void movePlayerDown() {
        if (playerRow < gameMap.length - 1) {
            playerRow++;
            gameBoard.setPlayerPosition(playerRow, playerCol);
        }
    }

    private void movePlayerLeft() {
        if (playerCol > 0) {
            playerCol--;
            gameBoard.setPlayerPosition(playerRow, playerCol);
        }
    }

    private void movePlayerRight() {
        if (playerCol < gameMap[0].length - 1) {
            playerCol++;
            gameBoard.setPlayerPosition(playerRow, playerCol);
        }
    }
    
    private void gameOver() {
        System.exit(0);
    }
    
    private boolean pitAt(int row, int col) {
        return gameBoard.pitAt(row, col);
    }
    
    private void checkPlayerStatus() {
        if (wumpus1.getRow() == playerRow && wumpus1.getColumn() == playerCol) {
            JOptionPane.showMessageDialog(this, "Game Over! Player was killed by a Wumpus");
            gameOver();
        } else if (wumpus2.getRow() == playerRow && wumpus2.getColumn() == playerCol) {
            energy -= 50;
            player.setEnergy(energy);
            infoPanel.update();
            if (energy == 0) {
                JOptionPane.showMessageDialog(this, "Game Over! Player lost all energy");
                gameOver();
            }
        } else if (pitAt(playerRow, playerCol)) { // Verifica se jogador caiu num buraco
            JOptionPane.showMessageDialog(this, "Game Over! Player fell into a hole");
            gameOver();
        } else if (playerRow == 14 && playerCol == 0 && hasGold == true) {
            JOptionPane.showMessageDialog(this, "You Win!");
            gameOver();
        }
    }
    
    private void pickUpGold() {
        if (playerRow == gameBoard.getGoldRows() && playerCol == gameBoard.getGoldCols()) {
            if (!player.isBackpackFull() && !hasGold) {
                    player.addToBackpack(gold); 
                    hasGold = true;
                    gameMap[playerRow][playerCol].setObject(null);
                    infoPanel.update(); 
                    repaint(); 
                
                }
            }
        }
    
    private void pickUpWood() {
        if (!player.isBackpackFull()) {
            player.addToBackpack(wood);
            gameMap[playerRow][playerCol].setObject(null);
            infoPanel.update(); // Atualiza o painel de informações
            repaint(); // Redesenha o tabuleiro para refletir a mudança
            hasWood = true;
        }
    }
    
    private void craftMessage(){
        JOptionPane.showMessageDialog(this, "You used wood to craft an arrow.");
    }
    
    private void notCraftMessage(){
        JOptionPane.showMessageDialog(this, "You don't have wood to craft.");
    }
    
    private void clearBackpackMessage(){
        JOptionPane.showMessageDialog(this, "Your backpack is now empty.");
    }
    
    private void notClearBackpackMessage(){
        JOptionPane.showMessageDialog(this, "Your backpack is already empty.");
    }





    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Place[][] gameMap = new Place[15][15];
            new MainWumpus(gameMap);
        });
    }
}
