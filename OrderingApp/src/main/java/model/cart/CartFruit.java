package model.cart;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import model.fruitinfo.Fruit;

public class CartFruit {
    private final Fruit baseFruit;
    private final IntegerProperty quantity = new SimpleIntegerProperty(0);

    public CartFruit(Fruit baseFruit) {
        if (baseFruit == null) {
            throw new IllegalArgumentException("CartFruit: baseFruit cannot be null");
        }
        this.baseFruit = baseFruit;
    }

    public Fruit getBaseFruit() {
        return baseFruit;
    }

    public String getFruitName() {
        return baseFruit.getFruitName();
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public void increment() {
        quantity.set(quantity.get() + 1);
    }

    public void decrement() {
        if (quantity.get() > 0)
            quantity.set(quantity.get() - 1);
    }

    public void reset() {
        quantity.set(0);
    }

    public double getTotalPrice() {
        return baseFruit.getPrice() * getQuantity();
    }
}