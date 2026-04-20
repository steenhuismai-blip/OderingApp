package view; 
import java.util.List;

import controller.UIHandler; 
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox; 
import model.cart.Cart;
import javafx.scene.control.TextArea;
import model.vestigingen.ProvincieVestigingen;
import model.vestigingen.Vestiging; 
import controller.CheckoutController;
import model.service.CheckoutService; 



public class CheckoutView extends VBox {
    private final UIHandler uiHandler;
    private final Cart cart;

    private CheckoutController controller;

    private RadioButton bezorgBtn;
    private RadioButton afhalenBtn; 

    private TextField straatField;
    private TextField huisnummerField;
    private TextField postcodeField;
    private TextField plaatsField;
    
    private ComboBox<Vestiging> winkelSelectie;
    private Label infoLabel; 
    private Label winkelLabel;
    private Label nearestLabel;
    private TextArea cartView;

    public CheckoutView(UIHandler uiHandler, Cart cart, List<ProvincieVestigingen> provincies) {
       this.uiHandler = uiHandler;
         this.cart = cart;

         CheckoutService service = new CheckoutService(uiHandler.getVestigingenFactory());
         this.controller = new CheckoutController(this, service, cart, uiHandler);

        //Ui opbouwen ---
        bezorgBtn = new RadioButton("Bezorgen"); 
        afhalenBtn = new RadioButton("Afhalen");
        ToggleGroup tg = new ToggleGroup(); 
        bezorgBtn.setToggleGroup(tg); 
        afhalenBtn.setToggleGroup(tg); 
        bezorgBtn.setSelected(true);

          //bezorgadres ---
        straatField = new TextField();
        straatField.setPromptText("Straatnaam");

        huisnummerField = new TextField();
        huisnummerField.setPromptText("Huisnummer");

        postcodeField = new TextField();
        postcodeField.setPromptText("Voer uw postcode in");

        plaatsField = new TextField();      
        plaatsField.setPromptText("Plaatsnaam");
        
        
        // Vul winkelkeuze combo box ---
        winkelSelectie = new ComboBox<>();
        for (ProvincieVestigingen prov : provincies) 
         winkelSelectie.getItems().addAll(prov.getVestigingen());
        
        infoLabel = new Label();
        winkelLabel = new Label("Bezorgwinkel:");
        nearestLabel = new Label();

        cartView = new TextArea();
        cartView.setEditable(false);

        // Standaard: bezorgen is geselecteerd
        showAddressFields(true);
        showStoreFields(false);

        Button orderButton = new Button("Bestelling plaatsen");
        Button refreshButton = new Button("Refresh");
        Button cancelButton = new Button("Annuleren");

        // events koppelen ---
          tg.selectedToggleProperty().addListener((obs, old, sel) -> {
          boolean isAfhalen = sel == afhalenBtn;      
          controller.onToggleAfhalen(isAfhalen);
          });
            

        postcodeField.textProperty().addListener((obs, old, nieuw) -> 
            controller.updateNearestStore(nieuw));

        orderButton.setOnAction(e -> controller.onOrder());
        refreshButton.setOnAction(e -> controller.onRefresh());
        cancelButton.setOnAction(e -> controller.onCancel());   

            this.setSpacing(10); 
            this.getChildren().addAll( 
            new Label("Bezorgen/Afhalen"), bezorgBtn, afhalenBtn,
            new Label("Bezorgadres"), postcodeField, straatField, huisnummerField, plaatsField, 
            winkelLabel, winkelSelectie, 
            
            orderButton, infoLabel, cartView, nearestLabel,refreshButton, cancelButton
            );
    

        updateCartView(cart);   
        }

        // Methoden die controller gebruikt ---
        public void showAddressFields(boolean visible) {
            straatField.setVisible(visible);
            huisnummerField.setVisible(visible);
            plaatsField.setVisible(visible);
            postcodeField.setVisible(true);
        }

        public void showStoreFields(boolean visible) {

           winkelSelectie.setVisible(true);
            nearestLabel.setVisible(true);
        }

        public void showNearestStore(Vestiging v) {

            if (v == null) { 
                nearestLabel.setText("Geen winkel gevonden" );
                return;
        }
            winkelSelectie.getSelectionModel().select(v);
            nearestLabel.setText("Dichtstbijzijnde winkel: " + v.getAdres());
        }
        
        public void showMessage(String msg) {
            infoLabel.setText(msg);
        }

        public void updateCartView(Cart cart) {
    StringBuilder sb = new StringBuilder();
    sb.append("Winkelwagen\n");
    sb.append("-------------------------\n");

    cart.getItems().forEach(item -> {
        String name = item.getBaseFruit().getFruitName();
        int amount = item.getQuantity();
        double price = item.getTotalPrice();

        sb.append(String.format("%-12s x%-3d €%.2f\n", name, amount, price));
    });

    sb.append("-------------------------\n");
    sb.append(String.format("Totaal:         €%.2f\n", cart.getTotalPrice()));

    cartView.setText(sb.toString());
}


        public void clearFields() {
            straatField.clear();
            huisnummerField.clear();
            postcodeField.clear();
            plaatsField.clear();
            winkelSelectie.getSelectionModel().clearSelection();
            infoLabel.setText("");
            nearestLabel.setText("");

        }

        // Getters voor controller ---
        public String getStraat() { return straatField.getText(); }
        public String getHuisnummer() { return huisnummerField.getText(); }
        public String getPostcode() { return postcodeField.getText(); }
        public String getPlaats() { return plaatsField.getText(); }
        public boolean isBezorging() { return bezorgBtn.isSelected(); }
        public Vestiging getSelectedWinkel() { return winkelSelectie.getValue(); }

    }

    
 
