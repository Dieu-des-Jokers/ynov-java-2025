package moneyminer;

public class Player {
    private Tools currentTool;
    private Inventory inventory;
    private double money;

    public Player() {
            this.inventory = new Inventory();
            this.money = 0.0;
            this.currentTool = new Tools("Pelle en bois", 1); // Outil de départ
    }

    public void showInventory() {
        inventory.displayItems();
        System.out.println("Money: " + money);
    }

    public void addItem(String item) {
        inventory.addItem(item);
    }

    public void addMoney(double amount) {
        money += amount;
    }

    public double getMoney() {
        return money;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public boolean hasItem(String item) {
        return inventory.hasItem(item);
    }

    public void removeItem(String item) {
        inventory.removeItem(item);
    }
    // Ajoutez cette méthode pour améliorer l'outil
    public boolean upgradeTool(double upgradeCost) {
        if (money >= upgradeCost) {
            money -= upgradeCost;
            // On pourrait ici améliorer l'outil, par exemple en augmentant son efficacité
            currentTool = new Tools(currentTool.getName(), currentTool.getEfficiency() + 1); // Augmente l'efficacité
            return true;
        }
        return false;
    }

    public Tools getCurrentTool() {
        return currentTool;
    }
}
