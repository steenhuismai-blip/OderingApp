package test;

import java.util.List;
import java.util.Map;
import model.fruitinfo.Deal;
import model.fruitinfo.Fruit;
import model.fruitinfo.FruitFactory;
import model.fruitinfo.FruitPrice;
import model.fruitinfo.LocatieFruit;
import model.fruitinfo.PrijzenEnDealsFactory;
import model.vestigingen.ProvincieVestigingen;
import model.vestigingen.Vestiging;
import model.vestigingen.VestigingenFactory;

public class ParserValidationTest {
    public static void main(String[] args) {
        // Parse fruits
        FruitFactory fruitFactory = new FruitFactory("./src/resources/data/vruchtenlijst.json");
        List<Fruit> fruits = fruitFactory.parseFruits();

        // Parse prijzen en deals
        PrijzenEnDealsFactory prijzenFactory = new PrijzenEnDealsFactory("./src/resources/data/prijzenendeals.json");
        Map<String, FruitPrice> prijzenMap = prijzenFactory.parsePrijzen();

        // Merge prices/deals into fruits
        for (Fruit f : fruits) {
            FruitPrice fp = prijzenMap.get(f.getFruitName());
            if (fp != null) {
                f.setPrice(fp.getPrice());
                f.setDeal(fp.getDeal());
            }
        }

        System.out.println("==== FRUITS ====");
        for (Fruit f : fruits) {
            Deal deal = f.getDeal();
            String dealStr = deal != null ? deal.getLabel() + " (" + deal.getAdjustedPrice() + ")" : "None";
            System.out.println(
                    "Fruit: " + f.getFruitName()
                            + " | Price: " + f.getPrice()
                            + " | Display price: " + f.getDisplayPrice()
                            + " | Deal: " + dealStr
                            + " | Origin: " + f.getOrigin()
                            + " | Image path: " + f.getDisplayImagePath());
        }

        // Parse vestigingen
        VestigingenFactory vestigingenFactory = new VestigingenFactory("./src/resources/data/vestigingen.json");
        List<ProvincieVestigingen> provincies = vestigingenFactory.parseVestigingen();

        System.out.println("\n==== VESTIGINGEN ====");
        for (ProvincieVestigingen pv : provincies) {
            System.out.println("Province: " + pv.getProvincie());
            for (Vestiging v : pv.getVestigingen()) {
                System.out.println("  Vestiging: " + v.getStad() + ", " + v.getAdres());
                for (LocatieFruit lf : v.getFruit()) {
                    System.out.println(
                            "    Fruit: " + lf.getNaam() + " | Availability: " + lf.getBeschikbaarheidLocatie());
                }
            }
        }
    }
}