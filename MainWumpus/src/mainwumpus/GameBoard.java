package mainwumpus;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

public class GameBoard extends JPanel {
    private final int rows;
    private final int cols;
    private final Place[][] gameMap;
    private boolean[][] hiddenMap;
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
    private final List<Point> pitSmell = new ArrayList<>();
    private final List<Point> wumpus1Smell = new ArrayList<>();
    private final List<Point> wumpus2Smell = new ArrayList<>();
    public boolean isAliveW1;
    public boolean isAliveW2;
    


    
    
    public GameBoard(int rows, int cols, Place[][] gameMap) {
        this.rows = rows;
        this.cols = cols;
        this.gameMap = gameMap;        
        setPreferredSize(new Dimension(cols * cellSize, rows * cellSize));
        pits = new HashSet<>();
        woods = new HashSet<>();
       
        hiddenMap = new boolean[rows][cols];    //mapa escondido
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                hiddenMap[i][j] = true;
            }
        }
        hiddenMap[14][0] = false; 
        this.isAliveW1 = true;
        this.isAliveW2 = true;
        
    }

    
    public void debugMap(){
        hiddenMap = new boolean[15][15];    //liberar o mapa
        for(int i = 0; i < 15; i++) {
            for(int j = 0; j < 15; j++) {
                hiddenMap[i][j] = false;
            }
        }
    }
    
    public void hideMap(){
        hiddenMap = new boolean[15][15];    //esconder o mapa
        for(int i = 0; i < 15; i++) {
            for(int j = 0; j < 15; j++) {
                hiddenMap[i][j] = true;
            }
        }
    }
    
    public void LanternDown(int row, int col) {
        for (int i = row; i < 15; i++) {
            hiddenMap[i][col] = false;
        }
    }

    public void LanternRight(int row, int col) {
        for (int i = col; i < 15; i++) {
            hiddenMap[row][i] = false;
        }
    }
    
    public void LanternUp(int row, int col) {
        for (int i = row; i >= 0; i--) {
            hiddenMap[i][col] = false;
        }
    }

    public void LanternLeft(int row, int col) {
        for (int i = col; i >= 0; i--) {
            hiddenMap[row][i] = false;
        }
    }

    public void setPlayerPosition(int row, int col) {
        this.playerRow = row;
        this.playerCol = col;
        hiddenMap[row][col] = false; // quando o jogador passar entao fica false
        
    }
    
    public void setWumpus1Position(int row, int col) {
        wumpus1Row = row;
        wumpus1Col = col;
    }
    
    public void setWumpus2Position(int row, int col) {
        wumpus2Row = row;
        wumpus2Col = col;
    }
    
    private Set<Point> getNeighborSquares(int row, int col) {
        Set<Point> smell = new HashSet<>();
        int[] dx = {0, 0, -1, 1};
        int[] dy = {-1, 1, 0, 0};

        for (int i = 0; i < 4; i++) {
            int newRow = row + dx[i];
            int newCol = col + dy[i];

            if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
                smell.add(new Point(newCol, newRow));
            }
        }

        return smell;
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
            Wood wood = new Wood(2,woodRow,woodCol); 
            gameMap[woodRow][woodCol].setObject(wood); 
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
        
        

        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                int x = col * cellSize;
                int y = row * cellSize;

                g.setColor(Color.WHITE); // fundo
                g.fillRect(x, y, cellSize, cellSize);

                g.setColor(Color.BLACK); // borda
                g.drawRect(x, y, cellSize, cellSize);

                if(hiddenMap[row][col]) {   // se tiver true entao ta escondido
                    g.setColor(new Color(173,216,230)); 
                    g.fillRect(x, y, cellSize, cellSize);
                    g.setColor(Color.BLACK); // borda
                    g.drawRect(x, y, cellSize, cellSize);
                } else {
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
                    if (hiddenMap[row][col]) {
                        for (Point point : pitSmell) {
                            int px = point.x * cellSize;
                            int py = point.y * cellSize;
                            g.setColor(new Color(173, 216, 230)); // Azul claro
                            g.fillRect(px, py, cellSize, cellSize);
                            g.setColor(Color.BLACK); // Borda
                            g.drawRect(px, py, cellSize, cellSize);
                        }
                    } else {
                        for (Point point : pitSmell) {
                            int px = point.x * cellSize;
                            int py = point.y * cellSize;
                            g.setColor(Color.BLUE); // Azul
                            g.fillRect(px, py, cellSize, cellSize/2);
                        }
                    }
            }
        }
        
        
        // Wumpus1
        if(isAliveW1 == true){
        int wumpus1X = wumpus1Col * cellSize;
        int wumpus1Y = wumpus1Row * cellSize;

        if (hiddenMap[wumpus1Row][wumpus1Col]) {
            g.setColor(new Color(173, 216, 230));
            g.fillRect(wumpus1X, wumpus1Y, cellSize, cellSize);
            g.setColor(Color.BLACK); // borda
            g.drawRect(wumpus1X, wumpus1Y, cellSize, cellSize);
        } else {
                g.setColor(new Color(220, 20, 60)); // vermelho
                g.fillRect(wumpus1X, wumpus1Y, cellSize, cellSize);
            }
        
        
         if (hiddenMap[wumpus1Row][wumpus1Col]) {
            for (Point point : wumpus1Smell) {
                int wx = point.x * cellSize;
                int wy = point.y * cellSize;
                g.setColor(new Color(173, 216, 230)); 
                g.fillRect(wx, wy, cellSize, cellSize);
                g.setColor(Color.BLACK); // borda
                g.drawRect(wx, wy, cellSize, cellSize);
            } 
         } else {
                for (Point point : wumpus1Smell) {
                    int wx = point.x * cellSize;
                    int wy = point.y * cellSize;
                    g.setColor(Color.GREEN); // verde
                    g.fillRect(wx, wy, cellSize, cellSize);
                }
            }   
        }
        
        // Wumpus2
      if(isAliveW2 == true){
        int wumpus2X = wumpus2Col * cellSize;
        int wumpus2Y = wumpus2Row * cellSize;

        if (hiddenMap[wumpus2Row][wumpus2Col]) {
            g.setColor(new Color(173, 216, 230));
            g.fillRect(wumpus2X, wumpus2Y, cellSize, cellSize);
            g.setColor(Color.BLACK); // borda
            g.drawRect(wumpus2X, wumpus2Y, cellSize, cellSize);
        } else {
                g.setColor(new Color(60, 179, 113)); // verde
                g.fillRect(wumpus2X, wumpus2Y, cellSize, cellSize);
            }
            
        if (hiddenMap[wumpus2Row][wumpus2Col]) {
            for (Point point : wumpus2Smell) {
                int wx = point.x * cellSize;
                int wy = point.y * cellSize;
                g.setColor(new Color(173, 216, 230)); // escondido
                g.fillRect(wx, wy, cellSize, cellSize);
                g.setColor(Color.BLACK); // borda
                g.drawRect(wx, wy, cellSize, cellSize);
            }
        } else{
            for (Point point : wumpus2Smell) {
                int wx = point.x * cellSize;
                int wy = point.y * cellSize;
                g.setColor(Color.GREEN); // verde
                g.fillRect(wx, wy, cellSize, cellSize);
            }
        } 
      }


        // O jogador é a bolinha rosa
        g.setColor(new Color(255,20,147));  // rosa
        int playerX = playerCol * cellSize;
        int playerY = playerRow * cellSize;
        g.fillOval(playerX, playerY, cellSize, cellSize);
        updatePitSmell(); 
        
    }
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
            // fora do tabuleiro
            return false;
        }

        Place nextPlace = gameMap[row][col];
        return !(nextPlace != null && nextPlace.getObject() instanceof Pit);
    }
    
    public boolean pitAt(int row, int col) {
        // ve se tem pit
        return pits.stream().anyMatch(pit -> pit.getRow() == row && pit.getCol() == col);
    }
    
    public boolean woodAt(int row, int col) {
       return woods.stream().anyMatch(wood -> wood.getRow() == row && wood.getCol() == col);
    }
    
    public void updateWumpusSmell() {
            wumpus1Smell.clear();
            wumpus2Smell.clear();

            wumpus1Smell.addAll(getNeighborSquares(wumpus1Row, wumpus1Col));

            wumpus2Smell.addAll(getNeighborSquares(wumpus2Row, wumpus2Col));
        }
    
    
    public boolean wumpus1At(int row, int col) {
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            Place cell = gameMap[row][col];
            if (cell != null && cell.getObject() instanceof Wumpus1) {
                return true;
            }
        }
        return false;
    }
    
    public boolean wumpus2At(int row, int col) {
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            Place cell = gameMap[row][col];
            if (cell != null && cell.getObject() instanceof Wumpus2) {
                return true;
            }
        }
        return false;
    }
    
    public void updatePitSmell() {
        pitSmell.clear();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (pitAt(row, col)) {
                    if (Math.abs(playerRow - row) + Math.abs(playerCol - col) == 1) {
                        pitSmell.addAll(getNeighborSquares(row, col));
                    }
                }
            }
        }
    }

    public void clearGameElements() {
        for (int row = 0; row < gameMap.length; row++) {
            for (int col = 0; col < gameMap[row].length; col++) {
                gameMap[row][col].setObject(null); 
            }
        }
    }
    
}
   
