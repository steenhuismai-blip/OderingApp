package model.fruitbasket;

import java.util.List;
import model.fruitinfo.Fruit;

public class LocalBasket extends FruitBasket {
    private static final double TARGET_WEIGHT_GRAMS = 5000; // 5 kg
    
    public LocalBasket() {
        super("Lokale Mand", 15.00);
    }
    
    @Override
    public void fillBasket(List<Fruit> allFruit) {
        if (allFruit == null || allFruit.isEmpty()) {
            return;
        }
        
        double totalWeight = 0;
        
        // Filtert Nederlands fruit en vult de mand tot target gewicht 
        for (Fruit fruit : allFruit) {
            if (isDutch(fruit) && totalWeight + fruit.getWeight() <= TARGET_WEIGHT_GRAMS) {
                contents.add(fruit);
                totalWeight += fruit.getWeight();
            }
        }
    }
    
    private boolean isDutch(Fruit fruit) {
        if (fruit == null || fruit.getStory() == null) {
            return false;
        }
        return fruit.getStory().contains("Nederland");
    }
}
