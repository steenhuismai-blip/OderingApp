package model.fruitinfo;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class FruitFactory {

    private final String filePath;

    public FruitFactory(String filePath) {
        this.filePath = filePath;
    }

    public List<Fruit> parseFruits() {
        List<Fruit> fruits = new ArrayList<>();

        try (FileReader reader = new FileReader(filePath)) {
            JSONParser parser = new JSONParser();
            JSONObject root = (JSONObject) parser.parse(reader);

            JSONArray vruchtenArray = (JSONArray) root.get("vruchten");

            for (Object obj : vruchtenArray) {
                JSONObject f = (JSONObject) obj;

                String name = (String) f.get("naam");
                String origin = (String) f.get("herkomst");
                String story = (String) f.get("verhaal");

                Fruit fruit = new Fruit(
                        name,
                        0.0,
                        0.0,
                        story,
                        null
                );

                fruit.setOrigin(origin);
                fruits.add(fruit);
            }

        } catch (Exception e) {
            System.err.println("Error parsing fruit data: " + e.getMessage());
        }

        return fruits.isEmpty() ? Collections.emptyList() : fruits;
    }
}