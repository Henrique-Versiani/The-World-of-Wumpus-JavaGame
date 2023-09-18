package mainwumpus;

import java.util.Random;

class Wumpus1 {
    private int row;
    private int column;

    public Wumpus1(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public void move() {
        Random random = new Random();
        int direction = random.nextInt(4);  //4 opcoes de movimento

        switch (direction) {
            case 0: // pra cima
                row--;
                break;
            case 1: // pra baixo
                row++;
                break;
            case 2: // pra esquerda
                column--;
                break;
            case 3: // pra direita
                column++;
                break;
        }
    }

    public boolean isAtAgentPosition(int agentRow, int agentColumn) {
        return row == agentRow && column == agentColumn;
    }

    public void killPlayer(Player player) {
        player.decreaseEnergy(player.getEnergy());
    }
}
