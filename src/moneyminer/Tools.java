package moneyminer;

public class Tools {
    private String name;
    private int efficiency;

    public Tools(String name, int efficiency) {
        this.name = name;
        this.efficiency = efficiency;
    }

    public String getName() {
        return name;
    }

    public int getEfficiency() {
        return efficiency;
    }
}
