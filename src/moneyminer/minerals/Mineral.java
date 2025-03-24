package moneyminer.minerals;

public abstract class Mineral {
    protected String name;
    protected double dropRate;

    public Mineral(String name, double dropRate) {
        this.name = name;
        this.dropRate = dropRate;
    }

    public String getName() {
        return name;
    }

    public double getDropRate() {
        return dropRate;
    }
}
