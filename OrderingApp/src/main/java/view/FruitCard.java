package view;

import controller.UIHandler;
import java.io.InputStream;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.cart.Cart;
import model.cart.CartFruit;
import model.fruitinfo.Fruit;

public class FruitCard extends VBox {
        private Fruit fruit;
        private final Cart cart;

        private final IntegerProperty displayedQuantity = new SimpleIntegerProperty(0);

        public FruitCard(Fruit fruit, Cart cart, UIHandler uiHandler, ViewUtils viewUtils) {
                this.fruit = fruit;
                this.cart = cart;

                // Card layout: vertical
                setMaxWidth(Double.MAX_VALUE);
                prefHeightProperty().bind(prefWidthProperty().multiply(0.9));
                setAlignment(Pos.TOP_CENTER);
                setSpacing(8);
                setPadding(new Insets(8));
                setStyle(FXStyles.FRUIT_CARD);

                // Hover effect
                this.setOnMouseEntered(e -> setStyle(FXStyles.FRUIT_CARD_HOVER));
                this.setOnMouseExited(e -> setStyle(FXStyles.FRUIT_CARD));

                // 1) Header row
                HBox headerRow = new HBox(5);
                headerRow.setAlignment(Pos.CENTER);

                Label nameLabel = new Label(fruit.getFruitName());
                nameLabel.setStyle(FXStyles.CARD_TITLE);
                nameLabel.setWrapText(true);
                nameLabel.setAlignment(Pos.CENTER);
                nameLabel.setMaxWidth(Double.MAX_VALUE);
                nameLabel.styleProperty().bind(Bindings.createStringBinding(
                                () -> FXStyles.CARD_TITLE + "-fx-font-size: " + (getWidth() / 18) + "px;",
                                widthProperty()));
                nameLabel.setTranslateX(18);

               
                // region spacers neemt beschikbare ruimte in en duwt infoButton naar rechts
                Region spacerLeft = new Region();
                HBox.setHgrow(spacerLeft, Priority.ALWAYS);
                Region spacerRight = new Region();
                HBox.setHgrow(spacerRight, Priority.ALWAYS);

                
                Button infoButton = new Button("i");
                infoButton.setStyle(FXStyles.INFO_BUTTON);

                
                infoButton.setOnAction(click -> {
                        Stage currentStage = (Stage) this.getScene().getWindow();
                        FruitInfoView infoView = new FruitInfoView(currentStage, fruit);
                        infoView.show();
                });

                
                headerRow.getChildren().addAll(spacerLeft, nameLabel, spacerRight, infoButton);

                // 2) Afbeelding (midden)
                String imagePath = fruit.getDisplayImagePath();
                ImageView imageView = createImageViewForFruit(imagePath);
                imageView.setFitWidth(96);
                imageView.setFitHeight(96);
                imageView.setPreserveRatio(true);
                imageView.setSmooth(true);

                // 3) Controls (minus, quantity, plus) onder de afbeelding
                HBox controlRow = new HBox();
                controlRow.setAlignment(Pos.CENTER);
                controlRow.setSpacing(0);
                controlRow.setPadding(new Insets(4, 0, 0, 0));

                // Buttons
                DoubleBinding fontSize = controlRow.widthProperty().multiply(0.047);
                DoubleBinding fontSizeLarge = controlRow.widthProperty().multiply(0.04);

                HBox quantityBox = viewUtils.createQuantityBox(displayedQuantity, fontSize, fontSizeLarge, controlRow);

                Button minusButton = (Button) quantityBox.getChildren().get(0);
                Button plusButton = (Button) quantityBox.getChildren().get(2);

                // Button actions
                plusButton.setOnAction(e -> {
                        uiHandler.addFruit(fruit);
                        updateBinding();
                });
                minusButton.setOnAction(e -> {
                        uiHandler.removeFruit(fruit);
                        updateBinding();
                });

                controlRow.getChildren().addAll(quantityBox);

                // 4) prijs
                Label priceLabel;

                boolean hasDeal = fruit.hasActiveDeal();

                priceLabel = new Label(String.format(
                                "Price: € %.2f | %.0f g",
                              fruit.getDisplayPrice(),
                              fruit.getWeight()));

                String baseStyle = hasDeal
                                ? FXStyles.PRICE_LABEL_DEAL
                                : FXStyles.PRICE_LABEL_BASE;

                priceLabel.setMaxWidth(Double.MAX_VALUE);
                priceLabel.setAlignment(Pos.CENTER);

                double priceLabelFontSize = getWidth() / 26;
                priceLabel.setStyle(baseStyle + "-fx-font-size: " + priceLabelFontSize + "px;");

                VBox.setMargin(priceLabel, new Insets(4, 0, 0, 0));

                Label countdownLabel = createCountdownLabel();

                // 5) Extra informatie
                Label infoLabel = new Label(fruit.getStory() != null ? fruit.getStory() : "Meer informatie volgt...");
                infoLabel.setWrapText(true);
                infoLabel.setAlignment(Pos.CENTER);
                infoLabel.setStyle(FXStyles.INFO_LABEL);
                infoLabel.setMaxWidth(Double.MAX_VALUE);
                VBox.setMargin(infoLabel, new Insets(2, 0, 0, 0));

                // Voeg alle elementen toe in de gewenste volgorde
                this.getChildren().addAll(headerRow, imageView, controlRow, priceLabel, countdownLabel, infoLabel);

                // initial binding
                updateBinding();
        }

        private ImageView createImageViewForFruit(String imagePath) {
                ImageView iv = new ImageView();
                Image loaded = null;

                // 1) Probeer classpath resource
                try (InputStream is = getClass().getResourceAsStream("/" + imagePath)) {
                        if (is != null) {
                                loaded = new Image(is);
                        }
                } catch (Exception ignored) {
                }

        // 2) Probeer lokale resources map
        if (loaded == null) {
            java.io.File f1 = new java.io.File("src/resources/" + imagePath);
            if (f1.exists()) {
                loaded = new Image(f1.toURI().toString());
            }
        }

        // 3) Placeholder
        if (loaded == null) {
            try (InputStream ph = getClass().getResourceAsStream("/images/placeholder.png")) {
                if (ph != null) {
                    loaded = new Image(ph);
                }
            } catch (Exception ignored) {
            }
        }

                // 4) Laatste fallback
                if (loaded == null) {
                        loaded = new javafx.scene.image.WritableImage(1, 1);
                }

                iv.setImage(loaded);
                return iv;
        }

        public void setAvailability(boolean available) {
                if (available) {
                        setStyle(FXStyles.FRUIT_CARD);
                        setOpacity(1.0);
                        setDisable(false);
                        // Re-enable hover
                        setOnMouseEntered(e -> setStyle(FXStyles.FRUIT_CARD_HOVER));
                        setOnMouseExited(e -> setStyle(FXStyles.FRUIT_CARD));
                } else {
                        setStyle(FXStyles.FRUIT_CARD_DISABLED);
                        setOpacity(0.6);
                        setDisable(true);
                        setOnMouseEntered(null);
                        setOnMouseExited(null);
                }
        }

        public final void updateBinding() {
                CartFruit cartFruit = cart.getCartFruit(fruit);
                displayedQuantity.unbind();
                if (cartFruit == null)
                        displayedQuantity.set(0);
                else
                        displayedQuantity.bind(cartFruit.quantityProperty());
        }

        public Fruit getFruit() {
                return fruit;
        }
        private Label createCountdownLabel() {
    Label label = new Label();
    label.setAlignment(Pos.CENTER);
    label.setMaxWidth(Double.MAX_VALUE);
    label.setStyle("-fx-text-fill: #cc0000; -fx-font-weight: bold;");

    if (!fruit.hasActiveDeal()) {
        label.setText("");
        return label;
    }

    Timeline timer = new Timeline(
        new KeyFrame(Duration.seconds(1), e -> {
            long remaining = fruit.getDeal().getTimeRemainingSeconds();

            if (remaining <= 0) {
                label.setText("Deal verlopen!");
                return;
            }

            long hours = remaining / 3600;
            long minutes = (remaining % 3600) / 60;
            long seconds = remaining % 60;

            label.setText(String.format(
                "Deal eindigt over %02d:%02d:%02d",
                hours, minutes, seconds
            ));
        })
    );

    timer.setCycleCount(Timeline.INDEFINITE);
    timer.play();

    return label;
}

}