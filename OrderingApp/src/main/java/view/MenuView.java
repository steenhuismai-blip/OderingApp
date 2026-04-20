package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MenuView extends HBox {
    public MenuView() {
        setPadding(new Insets(5, 10, 5, 10));
        setSpacing(10);
        setAlignment(Pos.CENTER_LEFT);

        ImageView logo = new ImageView(new Image("file:src/resources/images/logo.png"));
        logo.setFitWidth(32);
        logo.setFitHeight(32);

        // Lettertype
        Label titleLabel = new Label("Fruit Shop Demo");
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setFont(Font.font("Montserrat", FontWeight.BOLD, 22));

        getChildren().addAll(logo, titleLabel);
    }
}