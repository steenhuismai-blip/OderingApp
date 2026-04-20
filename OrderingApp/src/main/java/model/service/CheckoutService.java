package model.service;

import model.cart.Cart;
import model.vestigingen.VestigingenFactory;
import model.vestigingen.Vestiging;

public class CheckoutService {

    private final VestigingenFactory vestigingenFactory;

    public CheckoutService( VestigingenFactory vestigingenFactory) {
        this.vestigingenFactory = vestigingenFactory;
    }

    public String buildAddress(String straat, String huisnummer, String postcode, String plaats) {
        return straat + " " + huisnummer + ", " + postcode + " " + plaats;
    }

    public Vestiging getNearestStore(String postcodeString){
        return vestigingenFactory.getNearestStore(postcodeString);
    }

    public String placeOrder(boolean isBezorging, String adres, String winkelAdres, Cart cart) {
        if (isBezorging && adres.trim().isEmpty()) {
            return "Adres is ongeldig";
        } 
        if (!isBezorging && winkelAdres == null) {
            return "Geen winkel geselecteerd";
        } 
        return "Bestelling geplaatst!";
        }
    }