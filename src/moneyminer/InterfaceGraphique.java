package moneyminer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfaceGraphique extends JFrame implements ActionListener {
    private GameEngine gameEngine;
    private JTextArea messageArea;
    private JButton mineButton;
    private JButton inventoryButton;
    private JButton sellButton;
    private JButton upgradeButton;
    private JButton quitButton;

    public InterfaceGraphique() {
        gameEngine = new GameEngine();

        setTitle("MoneyMiner");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        messageArea = new JTextArea();
        messageArea.setEditable(false);
        add(new JScrollPane(messageArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        mineButton = new JButton("Miner");
        inventoryButton = new JButton("Inventaire");
        sellButton = new JButton("Vendre");
        upgradeButton = new JButton("Améliorer");
        quitButton = new JButton("Quitter");

        mineButton.addActionListener(this);
        inventoryButton.addActionListener(this);
        sellButton.addActionListener(this);
        upgradeButton.addActionListener(this);
        quitButton.addActionListener(this);

        buttonPanel.add(mineButton);
        buttonPanel.add(inventoryButton);
        buttonPanel.add(sellButton);
        buttonPanel.add(upgradeButton);
        buttonPanel.add(quitButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void updateMessage(String message) {
        messageArea.append(message + "\n");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mineButton) {
            gameEngine.mine();
            updateMessage("Vous avez miné !");
        } else if (e.getSource() == inventoryButton) {
            gameEngine.player.showInventory(); // Vous devrez adapter cela pour afficher l'inventaire dans le JTextArea
            updateMessage("Inventaire affiché.");
        } else if (e.getSource() == sellButton) {
            gameEngine.sellAll(null); // Vous devrez adapter pour gérer les entrées utilisateur
            updateMessage("Vente effectuée.");
        } else if (e.getSource() == upgradeButton) {
            double upgradeCost = 50;
            if (gameEngine.player.upgradeTool(upgradeCost)) {
                updateMessage("Votre outil a été amélioré !");
            } else {
                updateMessage("Vous n'avez pas assez d'argent pour améliorer votre outil.");
            }
        } else if (e.getSource() == quitButton) {
            updateMessage("Merci d'avoir joué !");
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InterfaceGraphique gui = new InterfaceGraphique();
            gui.setVisible(true);
        });
    }
}
