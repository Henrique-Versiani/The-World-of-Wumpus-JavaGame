package mainwumpus;

import java.util.Random;

public class Wumpus2 {
    private int row;
    private int column;

    public Wumpus2(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public void move() {
        Random random = new Random();
        int direction = random.nextInt(7);  //8 direcoes possiveis

        switch (direction) {
            case 0: // Movimento pra cima e pra esquerda
                row -= 2;
                column--;
                break;
            case 1: // Movimento pra cima e pra direita
                row -= 2;
                column++;
                break;
            case 2: // Movimento pra direita e pra cima
                column += 2;
                row--;
                break;
            case 3: // Movimento pra direita e para baixo
                column += 2;
                row++;
                break;
            case 4: // Movimento pra baixo e pra esquerda
                row += 2;
                column--;
                break;
            case 5: // Movimento pra baixo e pra direita
                row += 2;
                column++;
                break;
            case 6: // Movimento pra esquerda e pra cima
                column -= 2;
                row--;
                break;
            case 7: // Movimento pra esquerda e pra baixo
                column -= 2;
                row++;
                break;
        }
    }

    public boolean isAtAgentPosition(int agentRow, int agentColumn) {
        return row == agentRow && column == agentColumn;
    }

    public void consumePlayerEnergy(Player player) {
        int playerEnergy = player.getEnergy();
        int energyToConsume = playerEnergy / 2;
        player.decreaseEnergy(energyToConsume);
    }
}
