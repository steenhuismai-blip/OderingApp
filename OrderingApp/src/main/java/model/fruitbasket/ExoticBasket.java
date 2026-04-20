package model.fruitbasket;

import java.util.List;
import model.fruitinfo.Fruit;

public class ExoticBasket extends FruitBasket {
    private static final double TARGET_WEIGHT_GRAMS = 5000; // 5 kg
    
    public ExoticBasket() {
        super("Exotische Mand", 15.00);
    }
    
    @Override
    public void fillBasket(List<Fruit> allFruit) {
        if (allFruit == null || allFruit.isEmpty()) {
            return;
        }
        
        double totalWeight = 0;
        
        // Vul mand met exotisch fruit 
        for (Fruit fruit : allFruit) {
            if (isExotic(fruit) && totalWeight + fruit.getWeight() <= TARGET_WEIGHT_GRAMS) {
                contents.add(fruit);
                totalWeight += fruit.getWeight();
            }
        }
    }
    
    private boolean isExotic(Fruit fruit) {
        if (fruit == null || fruit.getOrigin() == null) {
            return false;
        }
        
        String fruitorigin = fruit.getOrigin().toLowerCase();
        
        return !fruitorigin.contains("nederland") 
            && !fruitorigin.contains("spanje") 
            && !fruitorigin.contains("belgië")
            && !fruitorigin.contains("duitsland")
            && !fruitorigin.contains("frankrijk");
    }
}
