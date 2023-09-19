package mainwumpus;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWumpus extends JFrame {
    private Place[][] gameMap;
    private GameBoard gameBoard;
    private int playerRow;
    private int playerCol;

    public MainWumpus(Place[][] gameMap) {
        this.gameMap = gameMap;
        setTitle("Wumpus Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        playerRow = 0;
        playerCol = 0;
        
        gameBoard = new GameBoard(15, 15, gameMap);
        add(gameBoard, BorderLayout.CENTER);
        gameBoard.initializeGameElements();
        
        JPanel buttonPanel = new JPanel();
        add(buttonPanel, BorderLayout.SOUTH);

        JButton moveUpButton = new JButton("Cima");
        JButton moveDownButton = new JButton("Baixo");
        JButton moveLeftButton = new JButton("Esquerda");
        JButton moveRightButton = new JButton("Direita");

        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(moveUpButton);
        buttonPanel.add(moveDownButton);
        buttonPanel.add(moveRightButton);
        buttonPanel.add(moveLeftButton);

        moveUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                movePlayerUp();
            }
        });

        moveDownButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                movePlayerDown();
            }
        });

        moveLeftButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                movePlayerLeft();
            }
        });

        moveRightButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                movePlayerRight();
            }
        });

        setVisible(true);
    }

    private void movePlayerUp() {
        if (playerRow > 0) {
            playerRow--;
            gameBoard.setPlayerPosition(playerRow, playerCol);
            updateGrid();
        }
    }

    private void movePlayerDown() {
        if (playerRow < gameMap.length - 1) {
            playerRow++;
            gameBoard.setPlayerPosition(playerRow, playerCol);
            updateGrid();
        }
    }

    private void movePlayerLeft() {
        if (playerCol > 0) {
            playerCol--;
            gameBoard.setPlayerPosition(playerRow, playerCol);
            updateGrid();
        }
    }

    private void movePlayerRight() {
        if (playerCol < gameMap[0].length - 1) {
            playerCol++;
            gameBoard.setPlayerPosition(playerRow, playerCol);
            updateGrid();
        }
    }

    private void updateGrid() {
        // Atualizar o grid com base no estado atual do jogo
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Place[][] gameMap = new Place[15][15];
            new MainWumpus(gameMap);
        });
    }
}
