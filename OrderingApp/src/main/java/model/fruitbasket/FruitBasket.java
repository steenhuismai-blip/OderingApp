package model.fruitbasket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.fruitinfo.Fruit;

public abstract class FruitBasket extends Fruit {
    protected List<Fruit> contents;
    
protected FruitBasket(String name, double price) {
    super(name, price, 0.0, "", null);
    this.contents = new ArrayList<>();
}
    
    // Abstracte methode, elke mand vult zichzelf op een andere manier
    public abstract void fillBasket(List<Fruit> allFruit);
    
    
    public double getTotalWeight() {
        return contents.stream()
            .mapToDouble(Fruit::getWeight)
            .sum();
    }
    
    // Getters
    public List<Fruit> getContents() {
        return Collections.unmodifiableList(contents);
    }
    
    
    @Override
    public String toString() {
        return String.format("FruitBasket{name='%s', price=%.2f, items=%d}",
                getFruitName(), getPrice(), contents.size());
    }
}
