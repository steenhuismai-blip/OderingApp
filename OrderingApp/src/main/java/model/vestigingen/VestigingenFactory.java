package model.vestigingen;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import model.enums.Province;
import model.fruitinfo.LocatieFruit;

public class VestigingenFactory {

    private final String filePath;
    private List<ProvincieVestigingen> provincies = new ArrayList<>();

    private String extractPostcode(String adres) {
    if (adres == null) return null;

    // Zoek patroon: 4 cijfers + 2 letters (met of zonder spatie)
    Pattern p = Pattern.compile("([0-9]{4})\\s*([A-Za-z]{2})");
    Matcher m = p.matcher(adres);

    if (m.find()) {
        return m.group(1) + m.group(2); // bijv. "9712HS"
    }

    return null;
}

    public VestigingenFactory(String filePath) {
        this.filePath = filePath;
    }

    public List<ProvincieVestigingen> parseVestigingen() {
        provincies.clear();

        try (FileReader reader= new FileReader(filePath)){
            JSONParser parser = new JSONParser();
            JSONObject root = (JSONObject) parser.parse(reader);

            JSONArray provArray = (JSONArray) root.get("provinces");

            for (Object pObj : provArray) {
                JSONObject pJson = (JSONObject) pObj;

                String provincieNaam = ((String) pJson.get("provincie")).replace("-", "_");
                Province province = Province.valueOf(provincieNaam.toUpperCase());

                JSONArray vestigingArray = (JSONArray) pJson.get("vestigingen");

                List<Vestiging> vestigingen = new ArrayList<>();

                for (Object vObj : vestigingArray) {
                    JSONObject vJson = (JSONObject) vObj;

                    String stad = (String) vJson.get("stad");
                    String adres = (String) vJson.get("adres");
                    String postcode = extractPostcode(adres);
                    JSONArray fruitArray = (JSONArray) vJson.get("fruit");

                    List<LocatieFruit> locatieFruitList = new ArrayList<>();

                    for (Object fObj : fruitArray) {
                        JSONObject fJson = (JSONObject) fObj;

                        String naam = (String) fJson.get("naam");
                        long beschikbaarheid = (long) fJson.get("beschikbaarheid_lokatie");

                        LocatieFruit lf = new LocatieFruit(naam, (int) beschikbaarheid);
                        locatieFruitList.add(lf);
                    }

                    Vestiging vestiging = new Vestiging(stad, adres, postcode, locatieFruitList);
                    vestigingen.add(vestiging);
                }

                ProvincieVestigingen provincieData = new ProvincieVestigingen(province, vestigingen);

                provincies.add(provincieData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
                
        return provincies;
    }

    public Vestiging getNearestStore(String klantPostcode) {
        // Placeholder logic for nearest store based on postcode
        if (klantPostcode == null) return null;
        
        klantPostcode = klantPostcode.replaceAll("\\s+", "");
        Integer klantPC = extractPostcodeNumber(klantPostcode);
        if (klantPC == null) return null;
    
        Vestiging nearest = null;
        int smallestDistance = Integer.MAX_VALUE;

        for(ProvincieVestigingen provincie: provincies) {
            for (Vestiging v : provincie.getVestigingen()) {
                    
                if (v.getPostcode() == null) continue;

                Integer winkelPC = extractPostcodeNumber(v.getPostcode());
                if (winkelPC == null) continue;

                int distance = Math.abs(klantPC - winkelPC);

                if (distance < smallestDistance) {
                        smallestDistance = distance;
                        nearest = v;
                }
            }
        }
        return nearest;
    }
    private Integer extractPostcodeNumber(String postcode) {
        if(postcode == null || postcode.length() < 4) return null;
            
        try {
            return Integer.parseInt(postcode.substring(0, 4));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}

    

