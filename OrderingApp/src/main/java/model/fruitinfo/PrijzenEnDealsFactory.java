package model.fruitinfo;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class PrijzenEnDealsFactory {

    private final String filePath;

    public PrijzenEnDealsFactory(String filePath) {
        this.filePath = filePath;
    }

    public Map<String, FruitPrice> parsePrijzen() {
        Map<String, FruitPrice> prijzenMap = new HashMap<>();

        try (FileReader reader = new FileReader(filePath)) {
            JSONParser parser = new JSONParser();
            JSONObject root = (JSONObject) parser.parse(reader);

            JSONArray prijzenArray = (JSONArray) root.get("prijzen_en_deals");

            for (Object obj : prijzenArray) {
                JSONObject f = (JSONObject) obj;

                String naam = (String) f.get("naam");
                double pricePerUnit = f.get("pricePerUnit") != null ? ((Number) f.get("pricePerUnit")).doubleValue() : 0.0;
                double weightPerUnit = f.get("weightPerUnit") != null ? ((Number) f.get("weightPerUnit")).doubleValue() : 0.0;

                Deal deal = null;
                Object dealObj = f.get("deal");
                if (dealObj instanceof JSONObject d) {

                    String label = (String) d.get("label");
                    String type = (String) d.get("type");

                    Double adjustedPrice = null;
                    if ("percentage".equals(type)) {
                        int value = d.get("value") != null ? ((Number) d.get("value")).intValue() : 0;
                        adjustedPrice = pricePerUnit * (1 - (value / 100.0));
                    }

                    long durationHours = d.get("durationHours") != null ?
                            ((Number) d.get("durationHours")).longValue() : 0;

                    long endTimestamp = System.currentTimeMillis() + (durationHours * 3600 * 1000);

                    deal = new Deal(label, adjustedPrice, endTimestamp);
                }

                FruitPrice fruitPrice = new FruitPrice(pricePerUnit, weightPerUnit, deal);
                prijzenMap.put(naam, fruitPrice);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return prijzenMap;
    }
}
