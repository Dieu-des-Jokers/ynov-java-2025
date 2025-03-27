package moneyminer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Random;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

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
    private JPanel imagePanel;

    public InterfaceGraphique() {
        player = new Player();
        random = new Random();
        market = new Market();

        setTitle("MoneyMiner");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Création du JTextArea avec un ScrollPane
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(messageArea);
        add(scrollPane, BorderLayout.CENTER);

        imagePanel = new JPanel();
        imagePanel.setPreferredSize(new Dimension(350, 180));
        add(imagePanel, BorderLayout.NORTH);

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

        add(buttonPanel, BorderLayout.SOUTH);
    }

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

    private void displayImage(String imagePath) {
        imagePanel.removeAll(); // Retire toutes les anciennes images
        try {
            BufferedImage img = ImageIO.read(new File(imagePath));
            JLabel pic = new JLabel(new ImageIcon(img));
            imagePanel.add(pic);
        } catch (IOException e) {
            updateMessage("Erreur lors du chargement de l'image : " + imagePath);
        }
        imagePanel.revalidate();
        imagePanel.repaint();
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
            String foundItem;
            String imagePath = "";

            if (chance < 0.30) {
                foundItem = "charbon";
                imagePath = "src\\moneyminer\\img\\Charbon.png"; // Image de charbon
            } else if (chance < 0.55) {
                foundItem = "fer";
                imagePath = "src\\moneyminer\\img\\Fer.png"; // Image de fer
            } else if (chance < 0.80) {
                foundItem = "or";
                imagePath = "src\\moneyminer\\img\\Or.png"; // Image d'or
            } else if (chance < 0.95) {
                foundItem = "émeraude";
                imagePath = "src\\moneyminer\\img\\Emeraude.png"; // Image d'émeraude
            } else {
                foundItem = "diamant";
                imagePath = "src\\moneyminer\\img\\Diamant.png"; // Image de diamant
            }

            player.addItem(foundItem);
            updateMessage("Vous avez trouvé " + foundItem + " !");
            displayImage(imagePath); // Affiche l'image du minerai trouvé
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
            while (player.hasItem(item)) {
                double price = market.sell(item);
                player.removeItem(item);
                totalPrice += price;
                updateMessage("Vous avez vendu " + item + " pour " + price + ".");
            }
        }

        updateMessage("Prix total de la vente : " + totalPrice + ".");

        if (totalPrice > 0) {
            player.addMoney(totalPrice);
            updateMessage("Votre nouveau solde est : " + player.getMoney());
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
