package model.bestelling;
import model.cart.Cart;

public class Bezorging extends Bestelmethode {
    
    private final String adres;

    public Bezorging(String adres) {
        this.adres = adres;
    }

    @Override
    public String bevestig(Cart cart) {
        double totaal = berekenTotaal(cart);
        return "Bezorging naar: " + adres + " \nBezorgkosten: €4.95 " + "\nTotaal te betalen: €" + totaal;
    }
    @Override
    public double berekenTotaal(Cart cart) {
            return super.berekenTotaal(cart) + 4.95; // bezorgKosten; 
    }
    
}
