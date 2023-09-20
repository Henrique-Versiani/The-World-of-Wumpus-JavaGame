package mainwumpus;

import java.util.Random;

class Wumpus1 {
    private int row;
    private int column;
    private GameBoard gameBoard;


    public Wumpus1(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        Random random = new Random();
        this.row = random.nextInt(gameBoard.getRows());
        this.column = random.nextInt(gameBoard.getCols());
    }

    public void move(int mapRows, int mapColumns, Place[][] gameMap) {
        Random random = new Random();
        int direction;

        do {
            direction = random.nextInt(4);  // 4 opções de movimento
            int nextRow = row;
            int nextCol = column;

            switch (direction) {
                case 0: // para cima
                    nextRow--;
                    break;
                case 1: // para baixo
                    nextRow++;
                    break;
                case 2: // para a esquerda
                    nextCol--;
                    break;
                case 3: // para a direita
                    nextCol++;
                    break;
            }

            // Verificar se pode fazer o movimento
            if (nextRow >= 0 && nextRow < mapRows && nextCol >= 0 && nextCol < mapColumns) {    //se ta dentro do mapa
                Place nextPlace = gameMap[nextRow][nextCol];
                if (nextPlace == null || !(nextPlace.getObject() instanceof Pit)) { //se nao eh buraco
                    
                    row = nextRow;
                    column = nextCol;
                    break;
                }
            }
        } while (true);

        gameBoard.setWumpus1Position(row, column);
        gameBoard.repaint();
    }

    public int getRow(){
        return row;
    }
    
    public int getColumn(){
        return column;
    }

}
