package test;

import java.util.List;
import java.util.Map;
import model.fruitinfo.Fruit;
import model.fruitinfo.FruitFactory;
import model.fruitinfo.FruitPrice;
import model.fruitinfo.PrijzenEnDealsFactory;

public class FruitPriceTest {
    public static void main(String[] args) {
        String fruitFile = "./src/resources/data/vruchtenlijst.json";
        String prijzenFile = "./src/resources/data/prijzenendeals.json";

        FruitFactory fruitFactory = new FruitFactory(fruitFile);
        List<Fruit> fruits = fruitFactory.parseFruits();

        PrijzenEnDealsFactory prijzenFactory = new PrijzenEnDealsFactory(prijzenFile);
        Map<String, FruitPrice> prijzenMap = prijzenFactory.parsePrijzen();

        // Merge prices and deals
        for (Fruit f : fruits) {
            FruitPrice fp = prijzenMap.get(f.getFruitName());
            if (fp != null) {
                f.setPrice(fp.getPrice());
                f.setDeal(fp.getDeal());
            }
        }

        // Print all fruits with price and deal
        for (Fruit f : fruits) {
            System.out.println("Fruit: " + f.getFruitName() +
                    " | Price: " + f.getPrice() +
                    " | Deal: " + (f.getDeal() != null ? f.getDeal().getAdjustedPrice() + "%" : "None"));
        }
    }
}