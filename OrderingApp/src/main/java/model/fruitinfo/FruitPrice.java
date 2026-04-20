package model.fruitinfo;

public class FruitPrice {
    private double price;
    private double weight;
    private Deal deal;

    public FruitPrice(double price, double weight, Deal deal) {
        this.price = price;
        this.weight = weight;
        this.deal = deal;
    }

    public double getPrice() {
        return price;
    }

    public double getWeight() {
        return weight;
    }

    public Deal getDeal() {
        return deal;
    }
}
