package mainwumpus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWumpus extends JFrame {
    private final Place[][] gameMap;
    private GameBoard gameBoard;
    private int playerRow;
    private int playerCol;
    private Wumpus1 wumpus1;
    private Wumpus2 wumpus2;
    private Player player;
    private Gold gold;
    private Arrow arrow;
    private Wood wood;
    private int energy = 100;
    private boolean hasGold = false;
    private boolean hasWood = false;
    private int totalLantern = 100;
    private int arrows=0;
    private int totalWood = 0;
    private int totalGold = 0;
    private boolean wumpusOneAlive;
    private boolean wumpusTwoAlive;
    private JLabel infoLabel;
    
    
    public MainWumpus(Place[][] gameMap) {
        this.gameMap = gameMap;
        setTitle("Wumpus Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        playerRow = 14;
        playerCol = 0;
        player = new Player();
        
        
        wumpusOneAlive = true;
        wumpusTwoAlive = true;
        
        gameBoard = new GameBoard(15, 15, gameMap);
        add(gameBoard, BorderLayout.CENTER);
        gameBoard.initializeGameElements();
        wumpus1 = new Wumpus1(gameBoard);
        wumpus2 = new Wumpus2(gameBoard);
        
        Gold gold = new Gold();
        Wood wood = new Wood(0,0,0);
        Arrow arrow = new Arrow();
        
        infoLabel = new JLabel("Life Energy: " + energy + "\n Backpack: \nWood: " + totalWood + "\n Gold: " + totalGold + "Arrow(s): " + arrows + "Lamp Energy: " + totalLantern);
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        infoLabel.setHorizontalAlignment(JLabel.CENTER);
        add(infoLabel, BorderLayout.WEST);


        infoLabel.setText("<html>Caption <br>Life Energy: " + energy + "</font><br>Backpack: </font><br><font color='#8B4513'>Wood: " + totalWood + "</font><br><font color='#FFD700'>Gold: " + totalGold + "</font><br>Arrow(s): " + arrows + "</font><br>Lamp Energy: " + totalLantern + "</font><br><font color='#DC143C'>Wumpus 1</font><br><font color='#3CB357'>Wumpus 2</font><br><font color='#FF1493'>Player</font><br><font color='black'>Pit</font><br><font color='blue'>Breeze</font><br><font color='#008000'>Smell</font></html>");

        repaint();
        
        
        JPanel eastPanel = new JPanel();
        add(eastPanel, BorderLayout.EAST);

        JButton craftArrowButton = new JButton("Craft Arrow");
        JButton clearBackpackButton = new JButton("Clear Backpack");
        JButton debugMapButton = new JButton("Debug Map");
        JButton hideMapButton = new JButton("Hide All Map");
        JButton useLanternDown = new JButton("Use the lamp Down");
        JButton useLanternRight = new JButton("Use the lamp Right");
        JButton useLanternUp = new JButton("Use the lamp Up");
        JButton useLanternLeft = new JButton("Use the lamp Left");;
        JButton shootGreenButton = new JButton("Shoot a Green Wumpus");
        JButton shootRedButton = new JButton("Shoot a Red Wumpus");
        JButton restartButton = new JButton("Restart");
        JButton exitButton = new JButton("Exit Game");
        

        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
        eastPanel.add(craftArrowButton);
        eastPanel.add(clearBackpackButton);
        eastPanel.add(debugMapButton);
        eastPanel.add(hideMapButton);
        eastPanel.add(useLanternUp);
        eastPanel.add(useLanternDown);
        eastPanel.add(useLanternRight);
        eastPanel.add(useLanternLeft);
        eastPanel.add(shootGreenButton);
        eastPanel.add(shootRedButton);
        eastPanel.add(restartButton);
        eastPanel.add(exitButton);
        
        
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
        
        
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });
        
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                        MainWumpus.this,
                        "Do you really want to quit the game?",
                        "Confirm Quit",
                        JOptionPane.YES_NO_OPTION
                );

                if (choice == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        
        
        shootRedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {        
                if(arrows > 0){
                    if (isPlayerAdjacentToWumpus1()) {
                        removeWumpus1();
                        arrows--;
                        player.clearBackpack();
                        wumpusOneAlive = false;
                        infoLabel.setText("<html>Caption <br>Life Energy: " + energy + "</font><br>Backpack: </font><br><font color='#8B4513'>Wood: " + totalWood + "</font><br><font color='#FFD700'>Gold: " + totalGold + "</font><br>Arrow(s): " + arrows + "</font><br>Lamp Energy: " + totalLantern + "</font><br><font color='#DC143C'>Wumpus 1</font><br><font color='#3CB357'>Wumpus 2</font><br><font color='#FF1493'>Player</font><br><font color='black'>Pit</font><br><font color='blue'>Breeze</font><br><font color='#008000'>Smell</font></html>");

                        repaint(); 
                        killedMessage();
                    } 
                } else{
                    emptyArrow();
                }
            }
        });
        
        shootGreenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {        
                if(arrows > 0){
                    if (isPlayerAdjacentToWumpus2()) {
                        removeWumpus2();
                        arrows--;
                        player.clearBackpack();
                        
                        wumpusTwoAlive = false;
                        infoLabel.setText("<html>Caption <br>Life Energy: " + energy + "</font><br>Backpack: </font><br><font color='#8B4513'>Wood: " + totalWood + "</font><br><font color='#FFD700'>Gold: " + totalGold + "</font><br>Arrow(s): " + arrows + "</font><br>Lamp Energy: " + totalLantern + "</font><br><font color='#DC143C'>Wumpus 1</font><br><font color='#3CB357'>Wumpus 2</font><br><font color='#FF1493'>Player</font><br><font color='black'>Pit</font><br><font color='blue'>Breeze</font><br><font color='#008000'>Smell</font></html>");

                        repaint();
                        killedMessage();
                         
                        
                    } 
                } else{
                    emptyArrow();
                }
            }
        });
        
        debugMapButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameBoard.debugMap();
                gameBoard.repaint();
            }
        });
        
        hideMapButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameBoard.hideMap();
                gameBoard.repaint();
            }
        });
        
        useLanternDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(totalLantern != 0){
                    gameBoard.LanternDown(playerRow, playerCol);
                    gameBoard.repaint();
                    totalLantern -= 50;
                    infoLabel.setText("<html>Caption <br>Life Energy: " + energy + "</font><br>Backpack: </font><br><font color='#8B4513'>Wood: " + totalWood + "</font><br><font color='#FFD700'>Gold: " + totalGold + "</font><br>Arrow(s): " + arrows + "</font><br>Lamp Energy: " + totalLantern + "</font><br><font color='#DC143C'>Wumpus 1</font><br><font color='#3CB357'>Wumpus 2</font><br><font color='#FF1493'>Player</font><br><font color='black'>Pit</font><br><font color='blue'>Breeze</font><br><font color='#008000'>Smell</font></html>");

                    repaint();
                } else{
                    emptyLantern();
                }
                    
            }
        });
        
        useLanternRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(totalLantern != 0){
                    gameBoard.LanternRight(playerRow, playerCol);
                    gameBoard.repaint();
                    totalLantern -= 50;
                    infoLabel.setText("<html>Caption <br>Life Energy: " + energy + "</font><br>Backpack: </font><br><font color='#8B4513'>Wood: " + totalWood + "</font><br><font color='#FFD700'>Gold: " + totalGold + "</font><br>Arrow(s): " + arrows + "</font><br>Lamp Energy: " + totalLantern + "</font><br><font color='#DC143C'>Wumpus 1</font><br><font color='#3CB357'>Wumpus 2</font><br><font color='#FF1493'>Player</font><br><font color='black'>Pit</font><br><font color='blue'>Breeze</font><br><font color='#008000'>Smell</font></html>");

                    repaint();
                } else{
                    emptyLantern();
                }
            }
        });
        
        useLanternUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(totalLantern != 0){
                    gameBoard.LanternUp(playerRow, playerCol);
                    gameBoard.repaint();
                    totalLantern -= 50;
                    infoLabel.setText("<html>Caption <br>Life Energy: " + energy + "</font><br>Backpack: </font><br><font color='#8B4513'>Wood: " + totalWood + "</font><br><font color='#FFD700'>Gold: " + totalGold + "</font><br>Arrow(s): " + arrows + "</font><br>Lamp Energy: " + totalLantern + "</font><br><font color='#DC143C'>Wumpus 1</font><br><font color='#3CB357'>Wumpus 2</font><br><font color='#FF1493'>Player</font><br><font color='black'>Pit</font><br><font color='blue'>Breeze</font><br><font color='#008000'>Smell</font></html>");

                    repaint();
                } else{
                    emptyLantern();
                }
                    
            }
        });
        
        useLanternLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(totalLantern != 0){
                    gameBoard.LanternLeft(playerRow, playerCol);
                    gameBoard.repaint();
                    totalLantern -= 50;
                    infoLabel.setText("<html>Caption <br>Life Energy: " + energy + "</font><br>Backpack: </font><br><font color='#8B4513'>Wood: " + totalWood + "</font><br><font color='#FFD700'>Gold: " + totalGold + "</font><br>Arrow(s): " + arrows + "</font><br>Lamp Energy: " + totalLantern + "</font><br><font color='#DC143C'>Wumpus 1</font><br><font color='#3CB357'>Wumpus 2</font><br><font color='#FF1493'>Player</font><br><font color='black'>Pit</font><br><font color='blue'>Breeze</font><br><font color='#008000'>Smell</font></html>");

                    repaint();
                } else{
                    emptyLantern();
                }
                    
            }
        });

        
        craftArrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!hasWood) {
                    notCraftMessage();
                } else { 
                    player.createArrow();
                    craftMessage();
                    arrows++;
                    hasWood = false;
                    totalWood--;
                    
                    infoLabel.setText("<html>Caption <br>Life Energy: " + energy + "</font><br>Backpack: </font><br><font color='#8B4513'>Wood: " + totalWood + "</font><br><font color='#FFD700'>Gold: " + totalGold + "</font><br>Arrow(s): " + arrows + "</font><br>Lamp Energy: " + totalLantern + "</font><br><font color='#DC143C'>Wumpus 1</font><br><font color='#3CB357'>Wumpus 2</font><br><font color='#FF1493'>Player</font><br><font color='black'>Pit</font><br><font color='blue'>Breeze</font><br><font color='#008000'>Smell</font></html>");

                    repaint();
                }
            }
        });
        
        clearBackpackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player.isBackpackFull()) {
                    clearBackpackMessage();
                    player.clearBackpack();
                    
                    totalGold = 0;
                    arrows=0;
                    infoLabel.setText("<html>Caption <br>Life Energy: " + energy + "</font><br>Backpack: </font><br><font color='#8B4513'>Wood: " + totalWood + "</font><br><font color='#FFD700'>Gold: " + totalGold + "</font><br>Arrow(s): " + arrows + "</font><br>Lamp Energy: " + totalLantern + "</font><br><font color='#DC143C'>Wumpus 1</font><br><font color='#3CB357'>Wumpus 2</font><br><font color='#FF1493'>Player</font><br><font color='black'>Pit</font><br><font color='blue'>Breeze</font><br><font color='#008000'>Smell</font></html>");

                    repaint();
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
            gameBoard.updatePitSmell();
        }
    }
    

    private void movePlayerDown() {
        if (playerRow < gameMap.length - 1) {
            playerRow++;
            gameBoard.setPlayerPosition(playerRow, playerCol);
            gameBoard.updatePitSmell();
        }
    }

    private void movePlayerLeft() {
        if (playerCol > 0) {
            playerCol--;
            gameBoard.setPlayerPosition(playerRow, playerCol);
            gameBoard.updatePitSmell();
        }
    }

    private void movePlayerRight() {
        if (playerCol < gameMap[0].length - 1) {
            playerCol++;
            gameBoard.setPlayerPosition(playerRow, playerCol);
            gameBoard.updatePitSmell();
        }
    }
    
    private void gameOver() {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Do you want to restart the game?",
                "Game Over",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            restartGame(); 
        } else {
            exitMessage(); 
            System.exit(0);
        }
    }

    
    private boolean pitAt(int row, int col) {
        return gameBoard.pitAt(row, col);
    }
    
    private void checkPlayerStatus() {
        if (wumpusOneAlive == true)  {
            if (wumpus1.getRow() == playerRow && wumpus1.getColumn() == playerCol) {
                JOptionPane.showMessageDialog(this, "Game Over! Player was killed by a Wumpus");
                gameOver();
            }
        } if(wumpusTwoAlive == true){
             if (wumpus2.getRow() == playerRow && wumpus2.getColumn() == playerCol) {
                energy -= 50;
                infoLabel.setText("<html>Caption <br>Life Energy: " + energy + "</font><br>Backpack: </font><br><font color='#8B4513'>Wood: " + totalWood + "</font><br><font color='#FFD700'>Gold: " + totalGold + "</font><br>Arrow(s): " + arrows + "</font><br>Lamp Energy: " + totalLantern + "</font><br><font color='#DC143C'>Wumpus 1</font><br><font color='#3CB357'>Wumpus 2</font><br><font color='#FF1493'>Player</font><br><font color='black'>Pit</font><br><font color='blue'>Breeze</font><br><font color='#008000'>Smell</font></html>");

                repaint();
                player.setEnergy(energy);

                if (energy == 0) {
                    JOptionPane.showMessageDialog(this, "Game Over! Player lost all energy");
                    gameOver();
                }
            }
        }
        if (pitAt(playerRow, playerCol)) {
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
                    player.collectGold();
                    hasGold = true;
                    gameMap[playerRow][playerCol].setObject(null);
                    
                    repaint(); 
                    totalGold++;
                    infoLabel.setText("<html>Caption <br>Life Energy: " + energy + "</font><br>Backpack: </font><br><font color='#8B4513'>Wood: " + totalWood + "</font><br><font color='#FFD700'>Gold: " + totalGold + "</font><br>Arrow(s): " + arrows + "</font><br>Lamp Energy: " + totalLantern + "</font><br><font color='#DC143C'>Wumpus 1</font><br><font color='#3CB357'>Wumpus 2</font><br><font color='#FF1493'>Player</font><br><font color='black'>Pit</font><br><font color='blue'>Breeze</font><br><font color='#008000'>Smell</font></html>");

                    repaint(); 
                    
                
                }
            }
        }
    
    private void pickUpWood() {
        if (!player.isBackpackFull()) {
            player.addToBackpack(wood);
            player.collectWood();
            gameMap[playerRow][playerCol].setObject(null);
            repaint();
            hasWood = true;
            totalWood++;
            infoLabel.setText("<html>Caption <br>Life Energy: " + energy + "</font><br>Backpack: </font><br><font color='#8B4513'>Wood: " + totalWood + "</font><br><font color='#FFD700'>Gold: " + totalGold + "</font><br>Arrow(s): " + arrows + "</font><br>Lamp Energy: " + totalLantern + "</font><br><font color='#DC143C'>Wumpus 1</font><br><font color='#3CB357'>Wumpus 2</font><br><font color='#FF1493'>Player</font><br><font color='black'>Pit</font><br><font color='blue'>Breeze</font><br><font color='#008000'>Smell</font></html>");

            repaint(); 
            
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
    
    private void exitMessage(){
        JOptionPane.showMessageDialog(this, "Exiting Wumpus Game...");
    }
    
    private void emptyLantern(){
        JOptionPane.showMessageDialog(this, "Your lantern has no battery.");
    }
    
    private void emptyArrow(){
        JOptionPane.showMessageDialog(this, "You don't have arrows.");
    }
    
    private void killedMessage(){
        if(gameBoard.isAliveW1 == true){
            JOptionPane.showMessageDialog(this, "You killed a Wumpus.");
        } else if(gameBoard.isAliveW1 == true){
            JOptionPane.showMessageDialog(this, "You killed a Wumpus.");
        } else{
            JOptionPane.showMessageDialog(this, "All Wumpus are dead.");
        }
    }
    
    private boolean isPlayerAdjacentToWumpus1() {
        return true;
    }
    private boolean isPlayerAdjacentToWumpus2() {
        return true;
    }
    
    private void removeWumpus1() {
            wumpusTwoAlive = false;
            gameBoard.isAliveW1 = false;
            repaint();
            gameBoard.repaint();
    }

    
    private void removeWumpus2() {
        wumpusTwoAlive = false;
        gameBoard.isAliveW2 = false;
        repaint();
        gameBoard.repaint();
    }
    

    private void restartGame() {
        dispose(); 
        SwingUtilities.invokeLater(() -> {
            Place[][] gameMap = new Place[15][15];
            new MainWumpus(gameMap); 
        });
    }





    public static void main(String[] args) {
        WelcomeScreen welcomeScreen = new WelcomeScreen();
        
        welcomeScreen.waitForClose();
        
        SwingUtilities.invokeLater(() -> {
            Place[][] gameMap = new Place[15][15];
            new MainWumpus(gameMap);
        });
    }
}
