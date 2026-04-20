package view;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.layout.TilePane;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;

public class GridView extends ScrollPane {
    private TilePane tilePane;
    private ObservableList<? extends Node> sourceList;

    public GridView(ObservableList<? extends Node> cards) {
        setHbarPolicy(ScrollBarPolicy.NEVER);
        setVbarPolicy(ScrollBarPolicy.NEVER);

        tilePane = new TilePane();
        tilePane.setHgap(10);
        tilePane.setVgap(30);
        tilePane.setPadding(new Insets(0));
        tilePane.setPrefColumns(5);
        tilePane.setStyle("-fx-background-color: lightblue;");

        tilePane.getChildren().addAll(cards);

        setContent(tilePane);
        setFitToWidth(true);

        if (cards != null) {
            bindToList(cards);
        }

        tilePane.prefWidthProperty().bind(widthProperty());

        bindCardWidths(cards);
        setStyle(FXStyles.TRANSPARENT_BACKGROUND);
        getContent().setStyle(FXStyles.TRANSPARENT_BACKGROUND);
    }

    public void bindToList(ObservableList<? extends Node> cards) {
        if (sourceList != null) {
            try {
                sourceList.removeListener(listChangeListener);
            } catch (Exception ignored) {
            }
        }

        sourceList = cards;
        tilePane.getChildren().setAll(cards);

        cards.addListener(listChangeListener);
    }

    private final ListChangeListener<Node> listChangeListener = change -> {
        Platform.runLater(() -> {
            if (sourceList != null) {
                tilePane.getChildren().setAll(sourceList);
            }
        });
    };

    public void updateList(ObservableList<? extends Node> newCards) {
        if (sourceList != null) {
            sourceList.removeListener(listChangeListener);
        }
        sourceList = newCards;
        tilePane.getChildren().setAll(newCards);
        newCards.addListener(listChangeListener);

        bindCardWidths(newCards);
    }

    private void bindCardWidths(ObservableList<? extends Node> cards) {
        double hgap = tilePane.getHgap();
        double leftPadding = tilePane.getPadding().getLeft();
        double rightPadding = tilePane.getPadding().getRight();

        for (Node node : cards) {
            if (node instanceof FruitCard card) {
                card.prefWidthProperty()
                        .bind(tilePane.widthProperty().subtract(leftPadding + rightPadding + (2 * hgap) + 1).divide(5.2));
                card.setMaxWidth(Double.MAX_VALUE);
            }
        }
    }
}