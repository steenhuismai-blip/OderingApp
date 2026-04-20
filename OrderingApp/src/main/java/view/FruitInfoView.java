package view;

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
import model.fruitinfo.Fruit;

public class FruitInfoView {
    private Fruit fruit;
    private Stage fruitInfoStage;

    // Constructor
    public FruitInfoView(Stage parentStage, Fruit fruit) {
        this.fruit = fruit;
        this.fruitInfoStage = new Stage();
        fruitInfoStage.initModality(Modality.APPLICATION_MODAL);
        fruitInfoStage.initStyle(StageStyle.UNDECORATED);
        fruitInfoStage.initOwner(parentStage);

        // VBox aanmaken
        VBox content = new VBox(15);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(20));
        content.setStyle("-fx-background-color: white;");

        // Labels maken
        Label nameLabel = new Label(fruit.getFruitName());
        nameLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label infoLabel = new Label(fruit.getStory());
        infoLabel.setWrapText(true);
        infoLabel.setMaxWidth(350);
        infoLabel.setStyle("-fx-font-size: 14px;");

        Label priceLabel = new Label(String.format("Prijs: € %.2f per %.0f g",
                fruit.getPrice(), fruit.getWeight()));
        priceLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Close button
        Button closeButton = new Button("Sluiten");
        closeButton.setStyle(FXStyles.CLOSE_BUTTON);
        closeButton.setOnAction(click -> fruitInfoStage.close());

        // add to vbox
        content.getChildren().addAll(nameLabel, infoLabel, priceLabel, closeButton);

        // Transparante achtergrond (overlay) 
        StackPane overlay = new StackPane();
        overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);"); 
        overlay.getChildren().add(content);

        // Sluit als je op de overlay klikt (buiten de content box)
        overlay.setOnMouseClicked(click -> {
            if (click.getTarget() == overlay) {
                fruitInfoStage.close();
            }
        });

        // scene met overlay
        Scene scene = new Scene(overlay, 400, 300);
        fruitInfoStage.setScene(scene);
    }

    // Popup tonen
    public void show() {
        fruitInfoStage.show();
    }
}