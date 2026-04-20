package model.cart;

import java.util.*;
import java.util.function.Consumer;

import model.fruitinfo.Fruit;

public class Cart {
    private final Map<String, CartFruit> items = new HashMap<>();
    private final List<Consumer<CartFruit>> listeners = new ArrayList<>();

    public CartFruit getCartFruit(Fruit baseFruit) {
        if (baseFruit == null)
            return null;
        return items.get(baseFruit.getFruitName());
    }

    public Collection<CartFruit> getAllCartFruits() {
        return Collections.unmodifiableCollection(items.values());
    }

    public CartFruit createOrGetCartFruit(Fruit baseFruit) {
        if (baseFruit == null)
            throw new IllegalArgumentException("baseFruit cannot be null");

        return items.computeIfAbsent(baseFruit.getFruitName(), name -> {
            CartFruit newFruit = new CartFruit(baseFruit);
            notifyListeners(newFruit);
            return newFruit;
        });
    }

    // Register a listener for newly created CartFruits
    public void addCartFruitListener(Consumer<CartFruit> listener) {
        if (listener != null)
            listeners.add(listener);
    }

    // Notify all listeners about a new CartFruit
    private void notifyListeners(CartFruit fruit) {
        for (Consumer<CartFruit> listener : listeners) {
            listener.accept(fruit);
        }
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public Collection<CartFruit> getItems() {
    return items.values();
    }

    public void clear() {
        items.clear();
    }
    
    public double getTotalPrice() {
        double total = 0.0;
        for (CartFruit cf : items.values()) {
            total += cf.getTotalPrice();
        }
        return total;
    }
}
