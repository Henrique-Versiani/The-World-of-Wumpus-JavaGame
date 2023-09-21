package mainwumpus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

public class WelcomeScreen extends JFrame {
    private CountDownLatch latch = new CountDownLatch(1);

    public WelcomeScreen() {
        setTitle("Welcome to Wumpus Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Welcome to Wumpus Game");
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label, BorderLayout.CENTER);

        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                closeWelcomeScreen();
            }
        });

        JButton closeGameButton = new JButton("Close Game");
        closeGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                closeWelcomeScreen();
                System.exit(0);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        buttonPanel.add(closeGameButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void closeWelcomeScreen() {
        latch.countDown(); 
        dispose(); 
    }

    public void waitForClose() {
        try {
            latch.await(); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
