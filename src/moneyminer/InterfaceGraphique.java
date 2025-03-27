package moneyminer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Random;

public class InterfaceGraphique extends JFrame implements ActionListener {
    private Player player;
    private Random random;
    private Market market;
    private JTextArea messageArea;
    private JButton mineButton;
    private JButton inventoryButton;
    private JButton sellButton;
    private JButton upgradeButton;
    private JButton quitButton;

    public InterfaceGraphique() {
        player = new Player();
        random = new Random();
        market = new Market();

        setTitle("MoneyMiner");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Centrer la fenêtre
        setLayout(new GridBagLayout()); // Utilisation de GridBagLayout pour la mise en page
        GridBagConstraints gbc = new GridBagConstraints();

        // Création d'un JTextArea avec un ScrollPane
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(messageArea);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 5;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 0.8;
        add(scrollPane, gbc);

        // Panel des boutons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 5, 10, 10));
        mineButton = createButton("Miner");
        inventoryButton = createButton("Inventaire");
        sellButton = createButton("Vendre");
        upgradeButton = createButton("Améliorer");
        quitButton = createButton("Quitter");

        buttonPanel.add(mineButton);
        buttonPanel.add(inventoryButton);
        buttonPanel.add(sellButton);
        buttonPanel.add(upgradeButton);
        buttonPanel.add(quitButton);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0.2;
        add(buttonPanel, gbc);
    }

    // Fonction pour créer un bouton avec des styles personnalisés
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(51, 153, 255));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.addActionListener(this);
        return button;
    }

    public void updateMessage(String message) {
        messageArea.append(message + "\n");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mineButton) {
            mine();
        } else if (e.getSource() == inventoryButton) {
            showInventory();
        } else if (e.getSource() == sellButton) {
            sellAll();
        } else if (e.getSource() == upgradeButton) {
            upgradeTool();
        } else if (e.getSource() == quitButton) {
            updateMessage("Merci d'avoir joué !");
            System.exit(0);
        }
    }

    private void mine() {
        double chance = random.nextDouble();
        int quantity = player.getCurrentTool().getEfficiency();

        for (int i = 0; i < quantity; i++) {
            if (chance < 0.30) {
                player.addItem("charbon");
                updateMessage("Vous avez trouvé du charbon !");
            } else if (chance < 0.55) {
                player.addItem("fer");
                updateMessage("Vous avez trouvé du fer !");
            } else if (chance < 0.80) {
                player.addItem("or");
                updateMessage("Vous avez trouvé de l'or !");
            } else if (chance < 0.95) {
                player.addItem("émeraude");
                updateMessage("Vous avez trouvé de l'émeraude !");
            } else {
                player.addItem("diamant");
                updateMessage("Vous avez trouvé du diamant !");
            }
        }
    }

    private void showInventory() {
        Map<String, Integer> inventoryMap = player.getInventory().getItems();
        StringBuilder inventory = new StringBuilder("Inventaire :\n");

        for (Map.Entry<String, Integer> entry : inventoryMap.entrySet()) {
            inventory.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        updateMessage(inventory.toString());
    }

    private void sellAll() {
        double totalPrice = 0;
        String[] items = {"charbon", "fer", "or", "émeraude", "diamant"};

        for (String item : items) {
            if (player.hasItem(item)) {
                double price = market.sell(item);
                player.removeItem(item);
                totalPrice += price;
                updateMessage("Vous avez vendu " + item + " pour " + price + ".");
            }
        }

        if (totalPrice > 0) {
            player.addMoney(totalPrice);
            updateMessage("Vous avez vendu tous vos articles pour un total de " + totalPrice + ". Votre nouveau solde est : " + player.getMoney());
        } else {
            updateMessage("Vous n'avez aucun article à vendre.");
        }
    }

    private void upgradeTool() {
        double upgradeCost = 50;
        if (player.upgradeTool(upgradeCost)) {
            updateMessage("Votre outil a été amélioré !");
        } else {
            updateMessage("Vous n'avez pas assez d'argent pour améliorer votre outil. Il vous manque : " + (upgradeCost - player.getMoney()) + " unités d'argent.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InterfaceGraphique gui = new InterfaceGraphique();
            gui.setVisible(true);
        });
    }
}
