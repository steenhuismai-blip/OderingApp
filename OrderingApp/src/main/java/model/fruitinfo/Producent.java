package model.fruitinfo;

public class Producent {
    private String boer;
    private String boerderij;
    private String provincieOfStreek;
    private String boerBericht;

    public Producent(String boer, String boerderij, String provincieOfStreek, String boerBericht) {
        this.boer = boer;
        this.boerderij = boerderij;
        this.provincieOfStreek = provincieOfStreek;
        this.boerBericht = boerBericht;
    }

    public String getBoer() {
        return boer;
    }

    public String getBoerderij() {
        return boerderij;
    }

    public String getProvincieOfStreek() {
        return provincieOfStreek;
    }

    public String getBoerBericht() {
        return boerBericht;
    }
}
