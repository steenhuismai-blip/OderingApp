package view;

import controller.UIHandler;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.cart.Cart;
import model.cart.CartFruit;
import model.fruitinfo.Fruit;

public class CartView extends BorderPane {
    private final UIHandler uiHandler;
    private final ViewUtils viewUtils;

    private final VBox fruitContainer;
    private final Label totalLabel;
    private Label savingsLabel;

    public CartView(Cart cart, UIHandler uiHandler, ViewUtils viewUtils) {
        this.uiHandler = uiHandler;
        this.viewUtils = viewUtils;

        setPadding(new Insets(10));
        setStyle(FXStyles.CART_CONTAINER);

        // Title
        Label titleLabel = new Label("Mijn winkelwagen");
        titleLabel.setStyle(FXStyles.TITLE);
        setTop(titleLabel);
        BorderPane.setMargin(titleLabel, new Insets(0, 0, 10, 0));

        // Fruit container
        fruitContainer = new VBox(5);
        fruitContainer.setPadding(new Insets(5, 0, 5, 0));
        fruitContainer.setStyle(FXStyles.TRANSPARENT_BACKGROUND);

        // ScrollPane
        ScrollPane scrollPane = new ScrollPane(fruitContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
        scrollPane.setStyle(FXStyles.SCROLLPANE_TRANSPARENT);

        viewUtils.fixScrollPaneViewport(scrollPane, "-fx-background-color: #dff7d6;");

        setCenter(scrollPane);

        // Bottom total area
        VBox bottomBox = new VBox(2);
        bottomBox.setPadding(new Insets(5, 10, 5, 10));
        bottomBox.setAlignment(Pos.CENTER_RIGHT);

        // Savings label (subtle, grey, hidden by default)
        savingsLabel = new Label();
        savingsLabel.setStyle(FXStyles.SAVINGS_LABEL);
        savingsLabel.setVisible(false);
        savingsLabel.setManaged(false);

        // Total row
        HBox totalRow = new HBox(10);
        totalRow.setAlignment(Pos.CENTER_RIGHT);

        totalLabel = new Label("Totaal: €0,00");
        totalLabel.setStyle(FXStyles.TOTAL_LABEL);
        totalRow.getChildren().add(totalLabel);

        bottomBox.getChildren().addAll(savingsLabel, totalRow);
        setBottom(bottomBox);

        //Checkout button
        Button checkoutButton = new Button("Afrekenen");
        checkoutButton.setOnAction(e -> uiHandler.getMainController().showCheckout());
        
        //add button to bottom box
        bottomBox.getChildren().add(checkoutButton);

        // Loop through all CartFruits that currently exist in the cart (initially none)
        // and set up observation for them. This ensures that any pre-existing items
        // would be linked to the CartView UI immediately.
        for (CartFruit fruit : cart.getAllCartFruits()) {
            observeCartFruit(fruit);
        }

        // Attach a listener to the Cart itself. This listener is called whenever a
        // new CartFruit is created in the cart. The observeCartFruit method is called
        // automatically with the newly created CartFruit, so the UI can respond to it.
        cart.addCartFruitListener(fruit -> observeCartFruit(fruit));
    }

    private void observeCartFruit(CartFruit fruit) {
        // If the CartFruit already has a quantity > 0, create its UI row immediately.
        // This handles the case where a CartFruit might have been pre-populated or
        // restored from a saved state.
        if (fruit.getQuantity() > 0 && !rowExists(fruit)) {
            attachRowListener(fruit);
        }

        // Attach a listener to the CartFruit's quantity property.
        // This listener automatically updates the UI when the quantity changes:
        // - Lazily create a row when quantity goes from 0 -> 1+
        // - Remove the row when quantity goes back to 0
        // - Always update the total price display whenever quantity changes
        // Map-check vervangen door rowExists()
        if (fruit.getQuantity() > 0 && !rowExists(fruit)) {
            attachRowListener(fruit);
        }

        fruit.quantityProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.intValue() > 0 && !rowExists(fruit)) {
                attachRowListener(fruit);
            } else if (newVal.intValue() == 0 && rowExists(fruit)) {
                removeRow(fruit);
            }
            updateTotalLabel();
        });
    }

    // nieuwe hulpfunctie rowExists()
    private boolean rowExists(CartFruit fruit) {
        for (Node node : fruitContainer.getChildren()) {
            if (node.getUserData() instanceof CartFruit cf &&
                    cf.getFruitName().equals(fruit.getFruitName())) {
                return true;
            }
        }
        return false;
    }

    private void attachRowListener(CartFruit fruit) {
        HBox row = createFruitRow(fruit);
        row.setUserData(fruit);
        fruitContainer.getChildren().add(row);
    }

    private void removeRow(CartFruit fruit) {
        // Map vervangen door iteratie
        for (Node node : fruitContainer.getChildren()) {
            if (node.getUserData() instanceof CartFruit cf &&
                    cf.getFruitName().equals(fruit.getFruitName())) {
                fruitContainer.getChildren().remove(node);
                break;
            }
        }
    }

    private HBox createFruitRow(CartFruit fruit) {
        HBox row = new HBox(5);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(5, 10, 10, 8));
        row.setStyle(FXStyles.CART_ROW);

        DoubleBinding fontSize = row.widthProperty().multiply(0.04);
        DoubleBinding fontSizeLarge = row.widthProperty().multiply(0.06);
        IntegerProperty quantityProperty = (fruit != null) ? fruit.quantityProperty() : new SimpleIntegerProperty(0);

        Label nameLabel = createNameLabel(fruit, fontSizeLarge, row);
        HBox quantityBox = viewUtils.createQuantityBox(quantityProperty, fontSize, fontSizeLarge, row);
        Label priceLabel = createPriceLabel(fruit, fontSizeLarge, row);

        bindButtonActions(quantityBox, fruit);

        row.getChildren().addAll(nameLabel, quantityBox, priceLabel);
        return row;
    }

    private Label createNameLabel(CartFruit fruit, DoubleBinding fontSize, HBox row) {
        Label nameLabel = new Label(fruit.getFruitName());
        nameLabel.setAlignment(Pos.CENTER_LEFT);
        nameLabel.styleProperty().bind(fontSize.asString("-fx-font-size: %.0fpx;"));

        nameLabel.prefWidthProperty().bind(row.widthProperty().multiply(0.45));
        return nameLabel;
    }

    private Label createPriceLabel(CartFruit fruit, DoubleBinding fontSize, HBox row) {
        boolean hasDeal = fruit.getBaseFruit().hasActiveDeal();
        double unitPrice = hasDeal
                ? fruit.getBaseFruit().getDeal().getAdjustedPrice()
                : fruit.getBaseFruit().getPrice();

        Label priceLabel = new Label();
        priceLabel.setAlignment(Pos.CENTER_RIGHT);

        // Binding: quantity * prijs met deal indien van toepassing
        priceLabel.textProperty().bind(
                fruit.quantityProperty()
                        .multiply(unitPrice)
                        .asString("€%.2f"));

        priceLabel.prefWidthProperty().bind(row.widthProperty().multiply(0.22));

        String baseStyle = hasDeal
                ? FXStyles.PRICE_LABEL_DEAL
                : FXStyles.PRICE_LABEL_BASE;

        priceLabel.styleProperty().bind(
                fontSize.asString(baseStyle + "-fx-font-size: %.0fpx;"));

        return priceLabel;
    }

    private void bindButtonActions(HBox quantityBox, CartFruit fruit) {
        Button minusButton = (Button) quantityBox.getChildren().get(0);
        Button plusButton = (Button) quantityBox.getChildren().get(2);

        Fruit baseFruit = fruit.getBaseFruit();
        minusButton.setOnAction(e -> uiHandler.removeFruit(baseFruit));
        plusButton.setOnAction(e -> uiHandler.addFruit(baseFruit));
    }

    private void updateTotalLabel() {
        double normalTotal = 0.0;
        double discountedTotal = 0.0;

        for (Node node : fruitContainer.getChildren()) {
            if (node.getUserData() instanceof CartFruit cf) {

                int quantity = cf.getQuantity();
                double normalPrice = cf.getBaseFruit().getPrice();

                double effectivePrice = cf.getBaseFruit().hasActiveDeal()
                        ? cf.getBaseFruit().getDeal().getAdjustedPrice()
                        : normalPrice;

                normalTotal += quantity * normalPrice;
                discountedTotal += quantity * effectivePrice;
            }
        }

        double savings = normalTotal - discountedTotal;

        totalLabel.setText(String.format("Totaal: €%.2f", discountedTotal));

        if (savings > 0.01) {
            savingsLabel.setText(String.format("Je bespaart: €%.2f", savings));
            savingsLabel.setVisible(true);
            savingsLabel.setManaged(true);
        } else {
            savingsLabel.setVisible(false);
            savingsLabel.setManaged(false);
        }
    }
}