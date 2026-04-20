package view;

import controller.UIHandler;
import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.fruitbasket.FruitBasket;
import model.fruitinfo.Fruit;


public class BasketCard extends VBox {
    private final FruitBasket basket;
    private final UIHandler uiHandler;
    private final List<Fruit> allFruit;
    private final IntegerProperty quantity = new SimpleIntegerProperty(0);

    public BasketCard(FruitBasket basket, List<Fruit> allFruit, UIHandler uiHandler) {
        this.basket = basket;
        this.uiHandler = uiHandler;
        this.allFruit = allFruit;
        
     
        basket.fillBasket(allFruit);
        
        buildCard();
    }
    
    private void buildCard() {
       
        setMaxWidth(Double.MAX_VALUE);
        setPrefWidth(200);
        setAlignment(Pos.TOP_CENTER);
        setSpacing(8);
        setPadding(new Insets(12));
        setStyle(FXStyles.FRUIT_CARD);
        
       
        setOnMouseEntered(e -> setStyle(FXStyles.FRUIT_CARD_HOVER));
        setOnMouseExited(e -> setStyle(FXStyles.FRUIT_CARD));
        
        
        HBox headerRow = new HBox(5);
        headerRow.setAlignment(Pos.CENTER);
        
        Label nameLabel = new Label(basket.getFruitName());
        nameLabel.setStyle(FXStyles.CARD_TITLE);
        nameLabel.setWrapText(true);
        nameLabel.setAlignment(Pos.CENTER);
        nameLabel.setMaxWidth(Double.MAX_VALUE);
        
        Region spacerLeft = new Region();
        HBox.setHgrow(spacerLeft, Priority.ALWAYS);
        Region spacerRight = new Region();
        HBox.setHgrow(spacerRight, Priority.ALWAYS);
        
        Button infoButton = new Button("i");
        infoButton.setStyle(FXStyles.INFO_BUTTON);
        infoButton.setOnAction(click -> {
            Stage currentStage = (Stage) this.getScene().getWindow();
            BasketInfoView infoView = new BasketInfoView(currentStage, basket, allFruit);
            infoView.show();
        });
        
        headerRow.getChildren().addAll(spacerLeft, nameLabel, spacerRight, infoButton);
        
        
        Label imageLabel = new Label("🧺");
        imageLabel.setStyle("-fx-font-size: 64px;");
        imageLabel.setAlignment(Pos.CENTER);
        imageLabel.setPrefHeight(96);
        
       
        HBox controlRow = new HBox(5);
        controlRow.setAlignment(Pos.CENTER);
        controlRow.setPadding(new Insets(4, 0, 0, 0));
        
        Button minusButton = new Button("-");
        minusButton.setStyle("-fx-font-size: 18px; -fx-min-width: 30px; -fx-min-height: 30px;");
        minusButton.setOnAction(e -> removeBasket());
        
        Label quantityLabel = new Label("0");
        quantityLabel.setStyle("-fx-font-size: 16px; -fx-min-width: 30px;");
        quantityLabel.setAlignment(Pos.CENTER);
        quantityLabel.textProperty().bind(quantity.asString());
        
        Button plusButton = new Button("+");
        plusButton.setStyle("-fx-font-size: 18px; -fx-min-width: 30px; -fx-min-height: 30px;");
        plusButton.setOnAction(e -> addBasket());
        
        controlRow.getChildren().addAll(minusButton, quantityLabel, plusButton);
        
        // 5kg standaardgewicht voor de manden
        Label priceLabel = new Label(String.format(
            "€%.2f | 5 kg",
            basket.getPrice()
        ));
        priceLabel.setStyle(FXStyles.PRICE_LABEL_BASE);
        priceLabel.setMaxWidth(Double.MAX_VALUE);
        priceLabel.setAlignment(Pos.CENTER);
        
        
        Label descLabel = new Label(getDescription());
        descLabel.setWrapText(true);
        descLabel.setAlignment(Pos.CENTER);
        descLabel.setStyle(FXStyles.INFO_LABEL);
        descLabel.setMaxWidth(Double.MAX_VALUE);
        
       
        getChildren().addAll(headerRow, imageLabel, controlRow, priceLabel, descLabel);
    }
    
    private void addBasket() {
        // Voegt mand toe als stuk fruit
        uiHandler.addFruit(basket);
        quantity.set(quantity.get() + 1);
    }
    
    private void removeBasket() {
        if (quantity.get() > 0) {
            // Verwijdert mand als stuk fruit
            uiHandler.removeFruit(basket);
            quantity.set(quantity.get() - 1);
        }
    }
    
    private String getDescription() {
        String explanation = "";
        
        if (basket instanceof model.fruitbasket.ExoticBasket) {
            explanation = "Exotisch fruit uit verre landen";
        } else if (basket instanceof model.fruitbasket.DealsBasket) {
            explanation = "Alle fruit met aanbiedingen";
        } else if (basket instanceof model.fruitbasket.LocalBasket) {
            explanation = "Nederlands fruit tot 5 kg";
        }
        
        List<Fruit> contents = basket.getContents();
        if (contents.isEmpty()) {
            return explanation;
        }
        
        String fruitNames = contents.stream()
            .map(Fruit::getFruitName)
            .reduce((a, b) -> a + ", " + b)
            .orElse("");
        
        return explanation + "\nBevat: " + fruitNames + " (" + contents.size() + " stuks)";
    }
}
