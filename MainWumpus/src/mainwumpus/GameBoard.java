package mainwumpus;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GameBoard extends JPanel {
    private final int rows;
    private final int cols;
    private final Place[][] gameMap;
    private int playerRow;
    private int playerCol;
    private final int cellSize = 30;
    private final int numberOfPits = 5;

    public GameBoard(int rows, int cols, Place[][] gameMap) {
        this.rows = rows;
        this.cols = cols;
        this.gameMap = gameMap;
        setPreferredSize(new Dimension(cols * cellSize, rows * cellSize));
    }

    public void setPlayerPosition(int row, int col) {
        this.playerRow = row;
        this.playerCol = col;
        repaint();
    }

    public void initializeGameElements() {
        Random random = new Random();

        // posicao ouro
        int goldRow = random.nextInt(rows);
        int goldCol = random.nextInt(cols);
        gameMap[goldRow][goldCol] = new Place(goldRow, goldCol);
        gameMap[goldRow][goldCol].setObject(new Gold());

        // posicao madeira
        int woodRow = random.nextInt(rows);
        int woodCol = random.nextInt(cols);
        gameMap[woodRow][woodCol] = new Place(woodRow, woodCol);
        gameMap[woodRow][woodCol].setObject(new Wood(2));

        // posicao wumpus 1
        int wumpus1Row = random.nextInt(rows);
        int wumpus1Col = random.nextInt(cols);
        gameMap[wumpus1Row][wumpus1Col] = new Place(wumpus1Row, wumpus1Col);
        gameMap[wumpus1Row][wumpus1Col].setObject(new Wumpus1(wumpus1Row, wumpus1Col));

        // posicao wumpus 2
        int wumpus2Row = random.nextInt(rows);
        int wumpus2Col = random.nextInt(cols);
        gameMap[wumpus2Row][wumpus2Col] = new Place(wumpus2Row, wumpus2Col);
        gameMap[wumpus2Row][wumpus2Col].setObject(new Wumpus2(wumpus2Row, wumpus2Col));

        for (int i = 0; i < numberOfPits; i++) {
            int randomRow;
            int randomCol;
            do {
                randomRow = random.nextInt(rows);
                randomCol = random.nextInt(cols);
            } while (gameMap[randomRow][randomCol] != null); // Certifique-se de que a célula está vazia

            gameMap[randomRow][randomCol] = new Place(randomRow, randomCol);
            gameMap[randomRow][randomCol].setObject(new Pit(5));
        }

        repaint();
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
                        g.setColor(Color.YELLOW);
                        g.fillRect(x, y, cellSize, cellSize);
                    } else if (object instanceof Wood) {
                        g.setColor(Color.ORANGE);
                        g.fillRect(x, y, cellSize, cellSize);
                    } else if (object instanceof Wumpus1) {
                        g.setColor(Color.GREEN);
                        g.fillRect(x, y, cellSize, cellSize);
                    } else if (object instanceof Wumpus2) {
                        g.setColor(Color.BLUE);
                        g.fillRect(x, y, cellSize, cellSize);
                    } else if (object instanceof Pit) {
                        g.setColor(new Color(139, 69, 19)); // Marrom
                        g.fillRect(x, y, cellSize, cellSize);
                    }
                }
            }
        }

        // O jogador é a bolinha vermelha
        g.setColor(Color.RED);
        int playerX = playerCol * cellSize;
        int playerY = playerRow * cellSize;
        g.fillOval(playerX, playerY, cellSize, cellSize);
    }


}
