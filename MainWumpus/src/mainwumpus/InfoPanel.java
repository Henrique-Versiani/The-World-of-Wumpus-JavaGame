package mainwumpus;

import javax.swing.JLabel;
import javax.swing.JPanel;


    public class InfoPanel extends JPanel implements Observer {
        private JLabel energyValueLabel;
        private JLabel arrowValueLabel;
        private JLabel woodValueLabel;
        private JLabel goldValueLabel;
        private Player player;
        private Gold gold;
        private Wood wood;
        private Arrow arrow;
        private GameBoard gameBoard;

        public InfoPanel(Player player, Gold gold, Wood wood, Arrow arrow) {
            this.player = player;
            this.gold = gold;
            this.wood = wood;
            this.arrow = arrow;
            
            int playerEnergy = player.getEnergy();
            energyValueLabel = new JLabel("Energy: " + playerEnergy);
            int totalArrow = arrow.getArrow();
            arrowValueLabel = new JLabel("Arrow(s): " + totalArrow);
            int totalWood = player.getWood();
            woodValueLabel = new JLabel("Wood: " + totalWood);
            int totalGold = player.getGold();
            goldValueLabel = new JLabel("Gold: " + totalGold);

            add(energyValueLabel);
            add(arrowValueLabel);
            add(woodValueLabel);
            add(goldValueLabel);
            
            
            
            
        }

        @Override
        public void update() {
            int playerEnergy = player.getEnergy();
            energyValueLabel.setText("Energy: " + playerEnergy);
            int totalWood = wood.getWood();
            woodValueLabel.setText("Wood: " + totalWood);
            int totalArrow = arrow.getArrow();
            arrowValueLabel.setText("Arrow: " + totalArrow);
            int totalGold = gold.getGold();
            goldValueLabel.setText("Gold: " + totalGold);

            repaint();
        }
    }

