package app;

import controller.MainController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import view.BasketView;
import javafx.scene.Node;


import view.CartView;
import view.FXStyles;
import view.GridView;

public class AppManager {
    BorderPane rootLayout;
    private MainController controller;
    private HBox mainFruitView;
    private HBox mainBasketView;
    private StackPane viewContainer;
    private boolean showBaskets = false;

    private javafx.scene.Node originalTop;
    private javafx.scene.Node originalCenter;

    public AppManager(MainController controller) {
        this.controller = controller;
    }

    public void start(Stage stage) {
        rootLayout = new BorderPane();

        // Top bar
        HBox topBar = new HBox();
        topBar.setStyle(FXStyles.TOPBAR);

        // Toggle button
        Button toggleButton = new Button("🧺 Fruitmanden");
        toggleButton.setStyle("-fx-font-size: 14px; -fx-padding: 10 16; -fx-background-color: transparent; -fx-text-fill: white; -fx-cursor: hand; -fx-font-weight: bold;");
        toggleButton.setOnAction(e -> {
            toggleView();
            toggleButton.setText(showBaskets ? "🝎 Toon Fruit" : "🧺 Fruitmanden");
        });

        // Spacer pushes sortingView to the right
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        topBar.getChildren().addAll(controller.getMenuView(), toggleButton, spacer, controller.getSortingView());

        // Prevent collapse
        topBar.setMinHeight(Region.USE_PREF_SIZE);

        rootLayout.setTop(topBar);

        // Create fruit view (original center)
        mainFruitView = new HBox(15);
        mainFruitView.setPadding(new Insets(15));
        // Save original top 
        originalTop = topBar;

        // center HBox 3/4 ratio
        HBox center = new HBox(15);
        center.setPadding(new Insets(15));

        GridView gridView = controller.getGridView();
        CartView cartView = controller.getCartView();

        HBox.setMargin(cartView, new Insets(13, 0, 0, 0));
        HBox.setMargin(gridView, new Insets(11.5, 0, 0, 0));

        gridView.prefWidthProperty().bind(mainFruitView.widthProperty().multiply(0.75));
        cartView.prefWidthProperty().bind(mainFruitView.widthProperty().multiply(0.25));

        HBox.setHgrow(gridView, Priority.ALWAYS);
        HBox.setHgrow(cartView, Priority.ALWAYS);

        mainFruitView.getChildren().addAll(gridView, cartView);

        // Create basket view
        mainBasketView = new HBox(15);
        mainBasketView.setPadding(new Insets(15));
        BasketView basketView = controller.getBasketView();

        HBox.setMargin(basketView, new Insets(11.5, 0, 0, 0));

        basketView.prefWidthProperty().bind(mainBasketView.widthProperty().multiply(0.75));

        HBox.setHgrow(basketView, Priority.ALWAYS);

        mainBasketView.getChildren().add(basketView);

        // StackPane to hold both views (only one visible at a time)
        viewContainer = new StackPane();
        viewContainer.getChildren().add(mainFruitView);
        mainBasketView.setVisible(false);
        viewContainer.getChildren().add(mainBasketView);

        rootLayout.setCenter(viewContainer);

         // Save original center 
         originalCenter = viewContainer;

        Scene scene = new Scene(rootLayout, 1200, 800);
        stage.setTitle("Fruit Shop Demo");
        stage.setScene(scene);
        stage.show();
    }

    public BorderPane getRootLayout() {
        return rootLayout;
    }
    public Node getOriginalTop() { 
        return originalTop; 
    } 
    
    public Node getOriginalCenter() { 
        return originalCenter; 
        
    } 

    private void toggleView() {
        showBaskets = !showBaskets;
        
        CartView cartView = controller.getCartView();
        
        if (showBaskets) {
            
            mainFruitView.getChildren().remove(cartView);
            if (!mainBasketView.getChildren().contains(cartView)) {
                HBox.setMargin(cartView, new Insets(13, 0, 0, 0));
                cartView.prefWidthProperty().bind(mainBasketView.widthProperty().multiply(0.25));
                HBox.setHgrow(cartView, Priority.ALWAYS);
                mainBasketView.getChildren().add(cartView);
            }
        } else {
            
            mainBasketView.getChildren().remove(cartView);
            if (!mainFruitView.getChildren().contains(cartView)) {
                HBox.setMargin(cartView, new Insets(13, 0, 0, 0));
                cartView.prefWidthProperty().bind(mainFruitView.widthProperty().multiply(0.25));
                HBox.setHgrow(cartView, Priority.ALWAYS);
                mainFruitView.getChildren().add(cartView);
            }
        }
        
        mainFruitView.setVisible(!showBaskets);
        mainBasketView.setVisible(showBaskets);
    }
}