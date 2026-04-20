package model.fruitbasket;

import java.util.List;
import model.fruitinfo.Fruit;

public class DealsBasket extends FruitBasket {
    private static final double TARGET_WEIGHT_GRAMS = 5000; // 5 kg
    
    public DealsBasket() {
        super("Aanbieding Mand", 9.99);
    }
    
    @Override
    public void fillBasket(List<Fruit> allFruit) {
        if (allFruit == null || allFruit.isEmpty()) {
            return;
        }
        
        double totalWeight = 0;
        
        // Vult mand tot target gewicht met fruit uit deals
        for (Fruit fruit : allFruit) {
            if (fruit.getDeal() != null && totalWeight + fruit.getWeight() <= TARGET_WEIGHT_GRAMS) {
                contents.add(fruit);
                totalWeight += fruit.getWeight();
            }
        }
    }
}
