package view;

import controller.UIHandler;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.fruitbasket.DealsBasket;
import model.fruitbasket.ExoticBasket;
import model.fruitbasket.LocalBasket;
import model.fruitinfo.Fruit;


public class BasketView extends VBox {
    
    public BasketView(List<Fruit> allFruit, UIHandler uiHandler) {
        setPadding(new Insets(20));
        setSpacing(20);
        setAlignment(Pos.TOP_CENTER);
        
        
        Label titleLabel = new Label("Fruitmanden");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");
        
        
        HBox basketsRow = new HBox(15);
        basketsRow.setAlignment(Pos.CENTER);
        basketsRow.setPadding(new Insets(10));
        
        // de 3 frruitmanden met polymorfisme
        BasketCard exoticBasket = new BasketCard(new ExoticBasket(), allFruit, uiHandler);
        BasketCard dealsBasket = new BasketCard(new DealsBasket(), allFruit, uiHandler);
        BasketCard localBasket = new BasketCard(new LocalBasket(), allFruit, uiHandler);
        
        basketsRow.getChildren().addAll(exoticBasket, dealsBasket, localBasket);
        
        getChildren().addAll(titleLabel, basketsRow);
    }
}
