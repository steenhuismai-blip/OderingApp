package model.bestelling;
import model.cart.Cart;
import model.vestigingen.Vestiging;


public class Afhalen extends Bestelmethode {
    
    private final Vestiging winkel;

    public Afhalen(Vestiging winkel) {
        this.winkel = winkel;
    }

    @Override
    public String bevestig(Cart cart) {
        double totaal = berekenTotaal(cart);
         String totaalFormatted = String.format("%.2f", totaal);

        return "Afhalen  bij " + winkel.getAdres() + 
        "\nGeen extra kosten " + 
        "\nTotaal te betalen: €" + totaalFormatted;
    }
    
}
