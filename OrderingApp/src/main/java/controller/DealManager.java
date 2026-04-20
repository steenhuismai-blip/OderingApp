package controller;

import model.fruitinfo.Fruit;
import model.fruitinfo.Deal;

import java.util.ArrayList;
import java.util.List;

public class DealManager {
    public static List<Fruit> getActiveDeals(List<Fruit> allFruits) {
        List<Fruit> result = new ArrayList<>();
        for (int i = 0; i < allFruits.size(); i++) {
            Fruit f = allFruits.get(i);
            if (f != null && f.hasActiveDeal()) {
                result.add(f);
            }
        }
        return result;
    }

    //
    public static void applyWinterDealToWinterFruits(List<Fruit> allFruits, Deal winterDeal) {
        if (winterDeal == null)
            return;

        for (int i = 0; i < allFruits.size(); i++) {
            Fruit f = allFruits.get(i);
            if (f != null && f.isWinterFruit() && f.getDeal() == null) {
                f.setDeal(winterDeal);
            }
        }
    }
}
