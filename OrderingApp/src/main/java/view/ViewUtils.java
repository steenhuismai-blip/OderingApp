package view;

import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.IntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;

public class ViewUtils {
    public void fixScrollPaneViewport(ScrollPane sp, String style) {
        Platform.runLater(() -> {
            var viewport = sp.lookup(".viewport");
            if (viewport != null)
                viewport.setStyle(style);
        });
    }

    public HBox createQuantityBox(IntegerProperty quantityProperty, DoubleBinding fontSize, DoubleBinding fontSizeLarge,
            HBox parentRow) {
        HBox quantityBox = new HBox(0);
        quantityBox.setAlignment(Pos.CENTER);

        Button minusButton = new Button("-");
        Button plusButton = new Button("+");
        Label quantityLabel = new Label();
        quantityLabel.textProperty().bind(quantityProperty.asString());
        quantityLabel.setAlignment(Pos.CENTER);
        quantityLabel.setTranslateY(0.5);

        quantityBox.getChildren().addAll(minusButton, quantityLabel, plusButton);

        quantityBox.prefWidthProperty().bind(parentRow.widthProperty().multiply(0.33));
        quantityBox.prefHeightProperty().bind(parentRow.heightProperty().multiply(0.565));

        bindWidths(quantityBox, minusButton, quantityLabel, plusButton);
        bindHeights(quantityBox, minusButton, quantityLabel, plusButton);
        applyFontStyles(fontSize, fontSizeLarge, minusButton, quantityLabel, plusButton);

        return quantityBox;
    }

    private void bindWidths(HBox box, Button minus, Label label, Button plus) {
        minus.prefWidthProperty().bind(box.prefWidthProperty().multiply(0.25));
        plus.prefWidthProperty().bind(box.prefWidthProperty().multiply(0.25));
        label.prefWidthProperty().bind(box.prefWidthProperty().multiply(0.5));
    }

    private void bindHeights(HBox box, Button minus, Label label, Button plus) {
        minus.prefHeightProperty().bind(box.prefHeightProperty());
        plus.prefHeightProperty().bind(box.prefHeightProperty());
        label.prefHeightProperty().bind(box.prefHeightProperty());
    }

    private void applyFontStyles(DoubleBinding fontSize, DoubleBinding fontSizeLarge,
            Button minus, Label label, Button plus) {
        minus.styleProperty().bind(fontSize.asString(FXStyles.BUTTON_MINUS + "-fx-font-size: %.0fpx;"));
        plus.styleProperty().bind(fontSize.asString(FXStyles.BUTTON_PLUS + "-fx-font-size: %.0fpx;"));
        label.styleProperty().bind(fontSizeLarge.asString(FXStyles.QUANTITY_PILL_SMALL + "-fx-font-size: %.0fpx;"));
    }
}