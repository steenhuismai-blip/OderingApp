package view;

import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.fruitbasket.FruitBasket;
import model.fruitinfo.Fruit;


public class BasketInfoView {
    private final FruitBasket basket;
    private final List<Fruit> allFruit;
    private Stage basketInfoStage;

    public BasketInfoView(Stage parentStage, FruitBasket basket, List<Fruit> allFruit) {
        this.basket = basket;
        this.allFruit = allFruit;
        this.basketInfoStage = new Stage();
        basketInfoStage.initModality(Modality.APPLICATION_MODAL);
        basketInfoStage.initStyle(StageStyle.UNDECORATED);
        basketInfoStage.initOwner(parentStage);

        
        basket.fillBasket(allFruit);

        
        VBox content = new VBox(15);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(20));
        content.setStyle("-fx-background-color: white;");

        
        Label nameLabel = new Label(basket.getFruitName());
        nameLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        
        String description = getBasketDescription(basket);
        Label infoLabel = new Label(description);
        infoLabel.setWrapText(true);
        infoLabel.setMaxWidth(350);
        infoLabel.setAlignment(Pos.CENTER);
        infoLabel.setStyle("-fx-font-size: 14px;");

        
        Label contentsLabel = new Label(
            String.format("Bevat: %d stuks fruit", 
                basket.getContents().size())
        );
        contentsLabel.setAlignment(Pos.CENTER);
        contentsLabel.setStyle("-fx-font-size: 14px; -fx-font-style: italic;");

        
        Label priceLabel = new Label(String.format("Prijs: €%.2f", basket.getPrice()));
        priceLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        
        Button closeButton = new Button("Sluiten");
        closeButton.setStyle(FXStyles.CLOSE_BUTTON);
        closeButton.setOnAction(click -> basketInfoStage.close());

        
        content.getChildren().addAll(nameLabel, infoLabel, contentsLabel, priceLabel, closeButton);

        
        StackPane overlay = new StackPane();
        overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        overlay.getChildren().add(content);

       
        overlay.setOnMouseClicked(click -> {
            if (click.getTarget() == overlay) {
                basketInfoStage.close();
            }
        });

        
        Scene scene = new Scene(overlay, 400, 350);
        basketInfoStage.setScene(scene);
    }

    private String getBasketDescription(FruitBasket basket) {
        String name = basket.getFruitName();
        
        if (name.contains("Exotische")) {
            return "Exotisch en verrassend! Deze mand bevat 5kg fruit " +
                   "uit verre landen. Van sappige mango's tot zoete ananas - een wereldreis voor je smaakpapillen.";
        } else if (name.contains("Aanbieding")) {
            return "Alle fruit met aanbiedingen! Deze mand bevat 5kg fruit " +
                   "met al het fruit dat momenteel in de aanbieding is. Maximale besparing gegarandeerd.";
        } else if (name.contains("Lokale")) {
            return "Lokaal en vers! Deze mand bevat 5kg Nederlands fruit. " +
                   "Van een teler bij jou om de hoek. Steun de lokale boer en geniet van wat Nederland te bieden heeft.";
        }
        
        return "Een heerlijke selectie van vers fruit!";
    }

    public void show() {
        basketInfoStage.show();
    }
}
