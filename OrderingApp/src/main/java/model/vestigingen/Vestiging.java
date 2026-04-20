package model.vestigingen;

import java.util.List;

import model.fruitinfo.LocatieFruit;

public class Vestiging {
    private String stad;
    private String adres;
    private String postcode;
    private List<LocatieFruit> fruit;

    public Vestiging(String stad, String adres, String postcode, List<LocatieFruit> fruit) {
        this.stad = stad;
        this.adres = adres;
        this.postcode = postcode;
        this.fruit = fruit;
    }

    public String getStad() {
        return stad;
    }

    public String getAdres() {
        return adres;
    }

    public String getPostcode() {
        return postcode;
    }
    public List<LocatieFruit> getFruit() {
        return fruit;
    }
     @Override
    public String toString() {
    return getStad() + " – " + getAdres();
}
         }
