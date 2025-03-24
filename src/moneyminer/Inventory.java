package moneyminer;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private Map<String, Integer> items;

    public Inventory() {
        items = new HashMap<>();
    }

    public void addItem(String item) {
        items.put(item, items.getOrDefault(item, 0) + 1);
    }

    public void removeItem(String item) {
        if (hasItem(item)) {
            items.put(item, items.get(item) - 100);
            if (items.get(item) <= 0) {
                items.remove(item);
            }
        }
    }

    public boolean hasItem(String item) {
        return items.containsKey(item) && items.get(item) > 0;
    }

    public void displayItems() {
        System.out.println("Votre inventaire :");
        items.forEach((item, quantity) -> System.out.println(item + ": " + quantity));
    }

    public Map<String, Integer> getItems() {
        return items;
    }
}
