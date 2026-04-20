package controller;
import model.bestelling.Bestelmethode;
import model.bestelling.Bezorging;
import model.bestelling.Afhalen;
import model.cart.Cart;
import model.vestigingen.Vestiging;
import model.service.CheckoutService;
import view.CheckoutView;

public class CheckoutController {
    private final CheckoutView view;
    private final CheckoutService service;
    private final Cart cart;
    private UIHandler uiHandler;

    public CheckoutController(CheckoutView view, CheckoutService service, Cart cart, UIHandler uiHandler) {
        this.view = view;
        this.service = service;
        this.cart = cart;
        this.uiHandler = uiHandler;
    }

    public void onToggleAfhalen(boolean isAfhalen) {
        view.showAddressFields(!isAfhalen);
        view.showStoreFields(isAfhalen);

        if (isAfhalen) {
            updateNearestStore(view.getPostcode());
        }
    }

    public void updateNearestStore(String postcode) {
       try{  Vestiging v = service.getNearestStore(postcode);
       view.showNearestStore(v);
       } catch (Exception e){
        view.showNearestStore(null);
       }
    } 

    // Implement order processing logic here
    public void onOrder() {
        
    Bestelmethode methode;

    if (view.isBezorging()) {
        String adres = service.buildAddress(
            view.getStraat(), 
            view.getHuisnummer(), 
            view.getPostcode(), 
            view.getPlaats()
        );
        methode = new Bezorging(adres);
    } else {
        methode = new Afhalen(view.getSelectedWinkel());
    }
    String bevestiging= methode.bevestig(cart);

    view.showMessage(bevestiging);
    }
    
    public void onRefresh(){
        view.clearFields();
        onToggleAfhalen(false);
    }
   
    public void onCancel(){
      uiHandler.getMainController().resetCheckout();
      uiHandler.getMainController().showMainScreen ();
    }


}

