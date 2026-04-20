package model.fruitinfo;

public class DiscountFruit extends Fruit {

    public DiscountFruit(String fruitName, double price, double weight, String story, String imagePath, Deal deal) {
        super(fruitName, price, weight, story, imagePath, deal);
    }

    @Override
    public double getDisplayPrice() {
        return getDeal().getAdjustedPrice();
    }
}