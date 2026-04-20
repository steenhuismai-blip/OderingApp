package controller;

import model.cart.Cart;
import model.cart.CartFruit;
import model.fruitinfo.Fruit;
import model.vestigingen.VestigingenFactory;
import model.vestigingen.Vestiging;

public class UIHandler {
    private final Cart cart;
    private MainController mainController;
    private VestigingenFactory vestigingenFactory;
    

    public UIHandler(Cart cart) {
        this.cart = cart;
    }

    public void addFruit(Fruit fruit) {
        CartFruit cartFruit = cart.createOrGetCartFruit(fruit);
        cartFruit.increment();
         mainController.updateCheckoutView();
    }

       public void removeFruit(Fruit fruit) {
        CartFruit cartFruit = cart.getCartFruit(fruit);
        if (cartFruit != null)
            cartFruit.decrement();
        mainController.updateCheckoutView();
    }

    public void deleteFruit(Fruit baseFruit) {
        CartFruit fruit = cart.getCartFruit(baseFruit);
        fruit.reset();
         mainController.updateCheckoutView();
    }
     public void setVestigingenFactory(VestigingenFactory factory) {
        this.vestigingenFactory = factory;
    }

     public VestigingenFactory getVestigingenFactory() {
        return vestigingenFactory;
    }
  
     public Vestiging getNearestStore(String postcodeString){
        if (vestigingenFactory == null) return null;
        return vestigingenFactory.getNearestStore(postcodeString);
    }
   
    

    public String processCheckout(boolean isBezorging, String adres, String winkel, Cart cart) {
        if (isBezorging) {
            return handleDelivery(adres);
        } else {
            return handlePickup(winkel);
        }
    }

    public String handleDelivery(String adres) {

    if (adres == null || adres.isEmpty()) {
        return "Adres mag niet leeg zijn";
               
    }

    if (cart.isEmpty()) { 
        return "Winkelmand is leeg"; 
 } 

        return "Bestelling geplaatst voor: " + adres + " (Aantal items: " + cart.getItems().size() + ")";
    }

    public String handlePickup(String winkel) {
    if (winkel == null || winkel.isEmpty()) {
        return "Selecteer een winkel voor afhalen";
    }
    if (cart.isEmpty()) { 
        return "Winkelmand is leeg"; 
        
    } 
    else
        return "Afhalen bij: " + winkel + " (Aantal items: " + cart.getItems().size() + ")";
        
    }
    
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    public MainController getMainController() {
    return mainController;
    }
}
    


    
