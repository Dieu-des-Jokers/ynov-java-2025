package moneyminer;

import moneyminer.minerals.*;
import java.util.Random;
import java.util.Scanner;

public class GameEngine {
    Player player;
    private Random random;
    private Market market;

    public GameEngine() {
        player = new Player();
        random = new Random();
        market = new Market();
    }

    public void start() {
        System.out.println("Bienvenue dans moneyMiner!");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Que voulez-vous faire ? (miner, inventaire, vendre, améliorer, quitter)");
            String action = scanner.nextLine();
            switch (action) {
                case "miner":
                    mine();
                    break;
                case "inventaire":
                    player.showInventory();
                    break;
                case "vendre":
                    sellAll(scanner);
                    break;
                case "améliorer":
                    double upgradeCost = 50;
                    if (player.upgradeTool(upgradeCost)) {
                        System.out.println("Votre outil a été amélioré !");
                    } else {
                        System.out.println("Vous n'avez pas assez d'argent pour améliorer votre outil. Il vous manque : "+ (upgradeCost - player.getMoney()) + " unités d'argent.");
                    }
                    break;
                case "quitter":
                    System.out.println("Merci d'avoir jouer !");
                    return;
                default:
                    System.out.println("Action non reconnue.");
            }
        }
    }

    void sellAll(Scanner scanner) {
        System.out.println("Vous allez vendre tous vos articles.");

        // Liste des articles que le joueur peut vendre
        String[] items = {"charbon", "fer", "or", "émeraude", "diamant"};
        double totalPrice = 0;

        // Parcourir tous les articles et vérifier s'ils sont en possession du joueur
        for (String item : items) {
            if (player.hasItem(item)) {
                double price = market.sell(item);
                player.removeItem(item);
                totalPrice += price;
                System.out.println("Vous avez vendu " + item + " pour " + price + ".");
            }
        }

        // Vérifier si le joueur a vendu quelque chose
        if (totalPrice > 0) {
            player.addMoney(totalPrice);
            System.out.println("Vous avez vendu tous vos articles pour un total de " + totalPrice + ". Votre nouveau solde est : " + player.getMoney());
        } else {
            System.out.println("Vous n'avez aucun article à vendre.");
        }
    }
    void mine() {
        double chance = random.nextDouble();
        int quantity = player.getCurrentTool().getEfficiency(); // Récupérer l'efficacité de l'outil

        for (int i = 0; i < quantity; i++) {
            if (chance < 0.30) {
                player.addItem(new Coal().getName());
                System.out.println("Vous avez trouvé du charbon !");
            } else if (chance < 0.55) {
                player.addItem(new Iron().getName());
                System.out.println("Vous avez trouvé du fer !");
            } else if (chance < 0.80) {
                player.addItem(new Gold().getName());
                System.out.println("Vous avez trouvé de l'or !");
            } else if (chance < 0.95) {
                player.addItem(new Emerald().getName());
                System.out.println("Vous avez trouvé de l'émeraude !");
            } else {
                player.addItem(new Diamond().getName());
                System.out.println("Vous avez trouvé du diamant !");
            }
        }
    }
}
