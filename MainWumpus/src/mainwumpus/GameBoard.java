package mainwumpus;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.awt.Point;

public class GameBoard extends JPanel {
    private final int rows;
    private final int cols;
    private final Place[][] gameMap;
    private int playerRow = 14;
    private int playerCol = 0;
    private final int cellSize = 40;
    private final int numberOfPits = 5;
    private int wumpus1Row;
    private int wumpus1Col;
    private int wumpus2Row;
    private int wumpus2Col;
    private final int numberOfWoods = 2;
    private final Set<Pit> pits;
    private int goldRow;
    private int goldCol;
    private int woodRow; 
    private int woodCol;
    private final Set<Wood> woods;

    public GameBoard(int rows, int cols, Place[][] gameMap) {
        this.rows = rows;
        this.cols = cols;
        this.gameMap = gameMap;
        setPreferredSize(new Dimension(cols * cellSize, rows * cellSize));
        pits = new HashSet<>();
        woods = new HashSet<>();
        
    }

    public void setPlayerPosition(int row, int col) {
        this.playerRow = row;
        this.playerCol = col;
    }
    
    public void setWumpus1Position(int row, int col) {
        wumpus1Row = row;
        wumpus1Col = col;
    }
    
    public void setWumpus2Position(int row, int col) {
        wumpus2Row = row;
        wumpus2Col = col;
    }
    

    public void initializeGameElements() {
        Random random = new Random();

        // posicao ouro
        do{
            goldRow = random.nextInt(rows);
            goldCol = random.nextInt(cols);
        } while (gameMap[goldRow][goldCol] != null);
        gameMap[goldRow][goldCol] = new Place(goldRow, goldCol);
        gameMap[goldRow][goldCol].setObject(new Gold());

        
        // posicao madeira
        for (int i = 0; i < numberOfWoods; i++) {
            do {
                woodRow = random.nextInt(rows);
                woodCol = random.nextInt(cols);
            } while (gameMap[woodRow][woodCol] != null);

            gameMap[woodRow][woodCol] = new Place(woodRow, woodCol);
            Wood wood = new Wood(2,woodRow,woodCol); // Crie a madeira
            gameMap[woodRow][woodCol].setObject(wood); // Defina a madeira na célula
            woods.add(wood);
        }

        // posicao inicial wumpus1
        do{
            wumpus1Row = random.nextInt(rows);
            wumpus1Col = random.nextInt(cols);
        }while (gameMap[wumpus1Row][wumpus1Col] != null);
        Wumpus1 wumpus1 = new Wumpus1(this);
        gameMap[wumpus1Row][wumpus1Col] = new Place(wumpus1Row, wumpus1Col);
        gameMap[wumpus1Row][wumpus1Col].setObject(wumpus1);

        // posicao inicial wumpus2
        do{
            wumpus2Row = random.nextInt(rows);
            wumpus2Col = random.nextInt(cols);
        }while (gameMap[wumpus2Row][wumpus2Col] != null);
        Wumpus2 wumpus2 = new Wumpus2(this);
        gameMap[wumpus2Row][wumpus2Col] = new Place(wumpus2Row, wumpus2Col);
        gameMap[wumpus2Row][wumpus2Col].setObject(wumpus2);

        for (int i = 0; i < numberOfPits; i++) {
            int randomRow;
            int randomCol;
            do {
                randomRow = random.nextInt(rows);
                randomCol = random.nextInt(cols);
            } while (gameMap[randomRow][randomCol] != null);

            gameMap[randomRow][randomCol] = new Place(randomRow, randomCol);
            Pit pit = new Pit(5, randomRow, randomCol);
            gameMap[randomRow][randomCol].setObject(pit);
            
            pits.add(pit);
        }

        gameMap[14][0] = new Place(14, 0);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = col * cellSize;
                int y = row * cellSize;

                g.setColor(Color.WHITE); // fundo
                g.fillRect(x, y, cellSize, cellSize);

                g.setColor(Color.BLACK); // borda
                g.drawRect(x, y, cellSize, cellSize);

                Place cell = gameMap[row][col];
                if (cell != null) {
                    Object object = cell.getObject();
                    if (object instanceof Gold) {
                        g.setColor(new Color(255,215,0));   // gold
                        g.fillRect(x, y, cellSize, cellSize);
                    } else if (object instanceof Wood) {
                        g.setColor(new Color(139, 69, 19)); //marrom
                        g.fillRect(x, y, cellSize, cellSize);
                    } else if (object instanceof Pit) {
                        g.setColor(Color.BLACK); 
                        g.fillRect(x, y, cellSize, cellSize);
                    }
                }
            }
        }
        
        

        // Wumpus1
        g.setColor(new Color(220,20,60));   // vermelho
        int wumpus1X = wumpus1Col * cellSize;
        int wumpus1Y = wumpus1Row * cellSize;
        g.fillRect(wumpus1X, wumpus1Y, cellSize, cellSize);

        // Wumpus2
        g.setColor(new Color(30,144,255)); // azul
        int wumpus2X = wumpus2Col * cellSize;
        int wumpus2Y = wumpus2Row * cellSize;
        g.fillRect(wumpus2X, wumpus2Y, cellSize, cellSize);

        // O jogador é a bolinha vermelha
        g.setColor(new Color(255,20,147));  // rosa
        int playerX = playerCol * cellSize;
        int playerY = playerRow * cellSize;
        g.fillOval(playerX, playerY, cellSize, cellSize);
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }
    
    public int getGoldCols() {
        return goldCol;
    }

    public int getGoldRows() {
        return goldRow;
    }
    
    public boolean isNextMoveSafe(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            // Fora do tabuleiro
            return false;
        }

        Place nextPlace = gameMap[row][col];
        return !(nextPlace != null && nextPlace.getObject() instanceof Pit);
    }
    
    public boolean pitAt(int row, int col) {
        // Verifica se tem pit
        return pits.stream().anyMatch(pit -> pit.getRow() == row && pit.getCol() == col);
    }
    
    public boolean woodAt(int row, int col) {
       return woods.stream().anyMatch(wood -> wood.getRow() == row && wood.getCol() == col);
    }



}
