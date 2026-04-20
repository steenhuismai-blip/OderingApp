package model.fruitinfo;

public class LocatieFruit {
    private String naam;
    private int beschikbaarheidLocatie;

    public LocatieFruit(String naam, int beschikbaarheidLocatie) {
        this.naam = naam;
        this.beschikbaarheidLocatie = beschikbaarheidLocatie;
    }

    public String getNaam() {
        return naam;
    }

    public int getBeschikbaarheidLocatie() {
        return beschikbaarheidLocatie;
    }
}