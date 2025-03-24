package moneyminer;

public class UpgradeTools {
    private Tools tool;
    private double cost;

    public UpgradeTools(Tools tool, double cost) {
        this.tool = tool;
        this.cost = cost;
    }

    public Tools getTool() {
        return tool;
    }

    public double getCost() {
        return cost;
    }
}
