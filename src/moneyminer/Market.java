package moneyminer;

import java.util.HashMap;
import java.util.Map;

public class Market {
    private Map<String, Double> prices;

    public Market() {
        prices = new HashMap<>();
        prices.put("charbon", 0.5);
        prices.put("fer", 1.0);
        prices.put("or", 2.0);
        prices.put("émeraude", 5.0);
        prices.put("diamant", 10.0);
    }

    public double sell(String item) {
        return prices.getOrDefault(item, 0.0);
    }
}
