package mainwumpus;

import javax.swing.*;
import java.awt.*;

public class GameBoard extends JPanel {
    private int rows;
    private int cols;
    private int[][] gameMap;
    private int playerRow;
    private int playerCol;
    private int cellSize = 30; 

    public GameBoard(int rows, int cols, int[][] gameMap) {
        this.rows = rows;
        this.cols = cols;
        this.gameMap = gameMap;
        setPreferredSize(new Dimension(cols * cellSize, rows * cellSize)); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = col * cellSize;
                int y = row * cellSize;

                g.setColor(Color.WHITE); // Cor de fundo da célula
                g.fillRect(x, y, cellSize, cellSize);

                g.setColor(Color.BLACK);
                g.drawRect(x, y, cellSize, cellSize); // Borda preta
            }
        }

        //o player eh a bolinha vermelha
        g.setColor(Color.RED);
        int playerX = playerCol * cellSize;
        int playerY = playerRow * cellSize;
        g.fillOval(playerX, playerY, cellSize, cellSize);
    }

    public void setPlayerPosition(int row, int col) {
        this.playerRow = row;
        this.playerCol = col;
        repaint();
    }
}
