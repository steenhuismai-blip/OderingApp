package view;

import controller.FilterManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.enums.DealFilter;
import model.enums.EnumDropdown;
import model.enums.PriceCategory;
import model.enums.Season;
import model.enums.SortType;

public class SortingView extends VBox {
    private final FilterManager filterManager;
    // Added search field //
    private TextField searchField;
    private final Label arrow = new Label("\u25BC");
    private final HBox contentBox = new HBox(10);
    private final VBox clipContainer = new VBox();
    private final StackPane arrowBox = new StackPane();
    private boolean expanded = false;
    private static final double EXPANDED_HEIGHT = 50;

    // Controls
    private ComboBox<SortType> sortDropdown;
    private ComboBox<PriceCategory> priceFilterDropdown;
    private ComboBox<DealFilter> dealFilterDropdown;
    private ToggleButton btnSpring, btnSummer, btnAutumn, btnWinter;
    private MenuButton filterMenu;
    private CheckMenuItem onlyAvailableItem;
    private CheckMenuItem fruitOfWeekItem;
    private Button clearFiltersBtn;

    public SortingView(FilterManager filterManager) {
        this.filterManager = filterManager;

        setPickOnBounds(false);
        setAlignment(Pos.TOP_CENTER);

        // setupArrow();
        setupSortingDropdown();
        setupPriceDropdown();
        setupDealDropdown();
        // setupSeasonButtons();
        setupAvailabilityCheckbox();
        setupClearFiltersButton();
        setupSearchfield();
        setupContentContainer();
        // setupClipContainer();
        setupHoverInteractions();
        // setupArrowToggle();
    }

    /*
     * ------------------------- Arrow -------------------------
     * private void setupArrow() {
     * arrow.setStyle("-fx-font-size: 9px;");
     * arrow.setAlignment(Pos.CENTER);
     * arrow.setPrefSize(30, 15);
     * 
     * arrowBox.setPrefSize(40, 15);
     * arrowBox.setMinSize(40, 15);
     * arrowBox.setMaxSize(40, 15);
     * arrowBox.setAlignment(Pos.CENTER);
     * arrowBox.setCursor(Cursor.HAND);
     * arrowBox.setStyle(FXStyles.ARROW_BOX);
     * arrowBox.getChildren().add(arrow);
     * }
     */

    /* ------------------------- Dropdown Menus ------------------------- */
    private void setupSortingDropdown() {
        sortDropdown = new EnumDropdown<>(SortType.values(), SortType::getDisplayName);
        sortDropdown.setOnAction(e -> filterManager.applySort(sortDropdown.getValue()));
        sortDropdown.setStyle(FXStyles.MODERN_DROPDOWN);
    }

    private void setupPriceDropdown() {
        priceFilterDropdown = new EnumDropdown<>(PriceCategory.values(), PriceCategory::getDisplayName);
        priceFilterDropdown.setOnAction(e -> filterManager.setPriceCategory(priceFilterDropdown.getValue()));
        priceFilterDropdown.setStyle(FXStyles.MODERN_DROPDOWN);
    }

    private void setupDealDropdown() {
        dealFilterDropdown = new EnumDropdown<>(DealFilter.values(), DealFilter::getDisplayName);
        dealFilterDropdown.setOnAction(
                e -> filterManager.setDealFilter(dealFilterDropdown.getValue()));
        dealFilterDropdown.setStyle(FXStyles.MODERN_DROPDOWN);
    }

    /* ------------------------- Season buttons ------------------------- */
    private void setupSeasonButtons() {
        btnSpring = new ToggleButton(Season.SPRING.getDisplayName());
        btnSummer = new ToggleButton(Season.SUMMER.getDisplayName());
        btnAutumn = new ToggleButton(Season.AUTUMN.getDisplayName());
        btnWinter = new ToggleButton(Season.WINTER.getDisplayName());

        ToggleGroup seasonGroup = new ToggleGroup();
        btnSpring.setToggleGroup(seasonGroup);
        btnSummer.setToggleGroup(seasonGroup);
        btnAutumn.setToggleGroup(seasonGroup);
        btnWinter.setToggleGroup(seasonGroup);

        // Styles
        btnSpring.setStyle(FXStyles.SPRING);
        btnSummer.setStyle(FXStyles.SUMMER);
        btnAutumn.setStyle(FXStyles.AUTUMN);
        btnWinter.setStyle(FXStyles.WINTER);

        // Assign Season enum
        btnSpring.setUserData(Season.SPRING);
        btnSummer.setUserData(Season.SUMMER);
        btnAutumn.setUserData(Season.AUTUMN);
        btnWinter.setUserData(Season.WINTER);

        seasonGroup.selectedToggleProperty().addListener((obs, oldT, newT) -> {
            // Season filtering disabled — no action
        });
    }

    /* ------------------------- Availability checkbox ------------------------- */
    private void setupAvailabilityCheckbox() {
        filterMenu = new MenuButton("Filters");

        onlyAvailableItem = new CheckMenuItem("Toon fruit op voorraad");
        onlyAvailableItem.setSelected(true);
        onlyAvailableItem.setOnAction(e -> filterManager.setAvailabilityOnly(onlyAvailableItem.isSelected()));
        filterManager.setAvailabilityOnly(onlyAvailableItem.isSelected());

        fruitOfWeekItem = new CheckMenuItem("Fruit van de week");
        fruitOfWeekItem.setSelected(false);
        fruitOfWeekItem.setOnAction(e -> filterManager.setFruitOfTheWeekOnly(fruitOfWeekItem.isSelected()));

        filterMenu.getItems().addAll(onlyAvailableItem, fruitOfWeekItem);

        filterMenu.setStyle(FXStyles.MODERN_DROPDOWN);
    }

    /* ------------------------- Clear filters button ------------------------- */
    private void setupClearFiltersButton() {
        clearFiltersBtn = new Button("Filters wissen");
        clearFiltersBtn.setOnAction(e -> {
            filterManager.clearAllFilters();

            onlyAvailableItem.setSelected(false);
            fruitOfWeekItem.setSelected(false);
            priceFilterDropdown.setValue(PriceCategory.ALL);
            dealFilterDropdown.setValue(DealFilter.ALL);
            sortDropdown.setValue(SortType.A_Z);

            searchField.setText(""); // Clear search field
            filterManager.setSearchQuery(""); // Clear search query in filter manager

        });
        clearFiltersBtn.setStyle(FXStyles.MODERN_DROPDOWN);
    }

    /* ------------------------- Content container ------------------------- */
    private void setupContentContainer() {
        contentBox.getChildren().addAll(sortDropdown, priceFilterDropdown, dealFilterDropdown, searchField,
                filterMenu, clearFiltersBtn);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setPadding(new Insets(5, 10, 5, 10));
        contentBox.setStyle(FXStyles.CONTENT_CONTAINER);
        this.getChildren().add(contentBox);
    }

    /*
     * ------------------------- Clipping / collapse -------------------------
     * private void setupClipContainer() {
     * clipContainer.setAlignment(Pos.CENTER);
     * clipContainer.setMinHeight(0);
     * clipContainer.setPadding(new Insets(0, 0, 18, 0));
     * 
     * Group group = new Group(contentBox);
     * clipContainer.getChildren().add(group);
     * clipContainer.getChildren().add(contentBox);
     * 
     * Rectangle clip = new Rectangle();
     * clip.widthProperty().bind(clipContainer.widthProperty());
     * clip.heightProperty().bind(clipContainer.prefHeightProperty());
     * clipContainer.setClip(clip);
     * 
     * clipContainer.setPrefHeight(0);
     * 
     * VBox container = new VBox(clipContainer, arrowBox);
     * container.setAlignment(Pos.TOP_CENTER);
     * getChildren().add(container);
     * }
     */

    /* ------------------------- Hover interactions ------------------------- */
    private void setupHoverInteractions() {
        // arrowBox.setOnMouseEntered(e ->
        // arrowBox.setStyle(FXStyles.ARROW_BOX_HOVERING));
        // arrowBox.setOnMouseExited(e -> arrowBox.setStyle(FXStyles.ARROW_BOX));

        sortDropdown.setOnMouseEntered(e -> sortDropdown.setStyle(FXStyles.MODERN_DROPDOWN_HOVER));
        sortDropdown.setOnMouseExited(e -> sortDropdown.setStyle(FXStyles.MODERN_DROPDOWN));

        priceFilterDropdown.setOnMouseEntered(e -> priceFilterDropdown.setStyle(FXStyles.MODERN_DROPDOWN_HOVER));
        priceFilterDropdown.setOnMouseExited(e -> priceFilterDropdown.setStyle(FXStyles.MODERN_DROPDOWN));

        clearFiltersBtn.setOnMouseEntered(e -> clearFiltersBtn.setStyle(FXStyles.CLEAR_BUTTON_HOVER));
        clearFiltersBtn.setOnMouseExited(e -> clearFiltersBtn.setStyle(FXStyles.MODERN_DROPDOWN));
    }

    /*
     * ------------------------- Arrow toggle -------------------------
     * private void setupArrowToggle() {
     * arrowBox.setOnMouseClicked(e -> toggle());
     * }
     */
    /*
     * ------------------------- Expand/Collapse logic -------------------------
     * private void toggle() {
     * if (expanded)
     * collapse();
     * else
     * expand();
     * }
     * 
     * private void expand() {
     * expanded = true;
     * 
     * Timeline t = new Timeline(
     * new KeyFrame(Duration.ZERO,
     * new KeyValue(clipContainer.prefHeightProperty(),
     * clipContainer.getPrefHeight(),
     * Interpolator.EASE_BOTH),
     * new KeyValue(arrowBox.translateYProperty(), arrowBox.getTranslateY(),
     * Interpolator.EASE_BOTH),
     * new KeyValue(arrow.rotateProperty(), arrow.getRotate(),
     * Interpolator.EASE_BOTH)),
     * new KeyFrame(Duration.millis(200),
     * new KeyValue(clipContainer.prefHeightProperty(), EXPANDED_HEIGHT,
     * Interpolator.EASE_BOTH),
     * new KeyValue(arrowBox.translateYProperty(), -4, Interpolator.EASE_BOTH),
     * new KeyValue(arrow.rotateProperty(), 180, Interpolator.EASE_BOTH)));
     * t.play();
     * }
     * 
     * private void collapse() {
     * expanded = false;
     * 
     * Timeline t = new Timeline(
     * new KeyFrame(Duration.ZERO,
     * new KeyValue(clipContainer.prefHeightProperty(),
     * clipContainer.getPrefHeight(),
     * Interpolator.EASE_BOTH),
     * new KeyValue(arrowBox.translateYProperty(), arrowBox.getTranslateY(),
     * Interpolator.EASE_BOTH),
     * new KeyValue(arrow.rotateProperty(), arrow.getRotate(),
     * Interpolator.EASE_BOTH)),
     * new KeyFrame(Duration.millis(200),
     * new KeyValue(clipContainer.prefHeightProperty(), 0, Interpolator.EASE_BOTH),
     * new KeyValue(arrowBox.translateYProperty(), 0, Interpolator.EASE_BOTH),
     * new KeyValue(arrow.rotateProperty(), 0, Interpolator.EASE_BOTH)));
     * t.play();
     * }
     */

    /* ------------------------- Search field ------------------------- */
    private void setupSearchfield() {
        searchField = new TextField();
        searchField.setPromptText("Zoek fruit...");
        searchField.setStyle(FXStyles.MODERN_DROPDOWN);

        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            filterManager.setSearchQuery(newVal);
        });
    }
}