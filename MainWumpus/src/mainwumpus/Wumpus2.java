package mainwumpus;

import java.util.Random;

public class Wumpus2 {
    private int row;
    private int column;
    private GameBoard gameBoard;

    public Wumpus2(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        Random random = new Random();
        this.row = random.nextInt(gameBoard.getRows());
        this.column = random.nextInt(gameBoard.getCols());
    }


    public void move(int mapRows, int mapColumns, Place[][] gameMap) {
        Random random = new Random();
        int direction;

        do {
            direction = random.nextInt(11);  // 12 direções possíveis
            int nextRow = row;
            int nextCol = column;

            switch (direction) {
                case 0: // Movimento pra cima e pra esquerda
                    nextRow--;
                    nextCol--;
                    break;
                case 1: // Movimento pra cima e pra direita
                    nextRow--;
                    nextCol++;
                    break;
                case 2: // Movimento pra direita e pra cima
                    nextCol++;
                    nextRow--;
                    break;
                case 3: // Movimento pra direita e para baixo
                    nextCol++;
                    nextRow++;
                    break;
                case 4: // Movimento pra baixo e pra esquerda
                    nextRow++;
                    nextCol--;
                    break;
                case 5: // Movimento pra baixo e pra direita
                    nextRow++;
                    nextCol++;
                    break;
                case 6: // Movimento pra esquerda e pra cima
                    nextCol--;
                    nextRow--;
                    break;
                case 7: // Movimento pra esquerda e pra baixo
                    nextCol--;
                    nextRow++;
                    break;
                case 8:
                    if (nextCol >= 2)
                        nextCol -= 2;
                    break;
                case 9:
                    if (nextCol <= mapColumns - 3)
                        nextCol += 2;
                    break;
                case 10:
                    if (nextRow >= 2)
                        nextRow -= 2;
                    break;
                case 11:
                    if (nextRow <= mapRows - 3)
                        nextRow += 2;
                    break;
            }

            // Verificar se pode fazer o movimento
            if (nextRow >= 0 && nextRow < mapRows && nextCol >= 0 && nextCol < mapColumns) {
                Place nextPlace = gameMap[nextRow][nextCol];
                if (nextPlace == null || !(nextPlace.getObject() instanceof Pit)) {
                    row = nextRow;
                    column = nextCol;
                    break;
                }
            }
        } while (true);

        gameBoard.setWumpus2Position(row, column);
        gameBoard.repaint();
    }
    
    public int getRow(){
        return row;
    }
    
    public int getColumn(){
        return column;
    }
}
