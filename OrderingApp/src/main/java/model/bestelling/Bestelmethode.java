package model.bestelling;

import model.cart.Cart;

public abstract class Bestelmethode {
    //gedeelde llogica voor alle bestelmethodes
    public double berekenTotaal(Cart cart) {
        return cart.getTotalPrice();
    }

    public abstract String bevestig(Cart cart); 
    
}
