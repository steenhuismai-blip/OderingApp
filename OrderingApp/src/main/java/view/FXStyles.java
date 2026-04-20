package view;

public class FXStyles {

        private FXStyles() {
        }

        // =========================================================
        // LABEL STYLES
        // =========================================================
        public static final String TITLE = "-fx-font-size: 18px; -fx-font-weight: bold;";
        public static final String TOTAL_LABEL = "-fx-font-size: 14px; -fx-font-weight: bold;";
        public static final String SAVINGS_LABEL = "-fx-font-size: 11px;"
                        + "-fx-text-fill: #8a8a8a;"
                        + "-fx-font-style: italic;";
        public static final String CARD_TITLE = "-fx-font-weight: bold;"; // dynamic font-size bound in code
        public static final String PRICE_LABEL_BASE = "-fx-font-family: 'Arial';"
                        + "-fx-font-style: italic;"
                        + "-fx-text-fill: #5a6c7a;"; // dynamic font-size bound in code
        public static final String PRICE_LABEL_DEAL = PRICE_LABEL_BASE
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: #2e7d32;";

        public static final String INFO_LABEL = "-fx-font-family: 'Arial';"
                        + "-fx-font-style: italic;"
                        + "-fx-font-size: 10px;"
                        + "-fx-text-fill: #555;"
                        + "-fx-text-alignment: center;";

        // =========================================================
        // CONTROLS (buttons & quantity pill)
        // =========================================================
        public static final String BUTTON_MINUS = "-fx-background-color: #c7d2df;" +
                        "-fx-text-fill: black;" +
                        "-fx-font-family: 'Arial';" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 6 0 0 6;" +
                        "-fx-border-width: 0;" +
                        "-fx-padding: 0;" +
                        "-fx-cursor: hand;";

        public static final String BUTTON_PLUS = "-fx-background-color: rgba(199, 210, 223, 1);" +
                        "-fx-text-fill: black;" +
                        "-fx-font-family: 'Arial';" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 0 6 6 0;" +
                        "-fx-border-width: 0;" +
                        "-fx-padding: 0;" +
                        "-fx-cursor: hand;";

        public static final String QUANTITY_PILL_SMALL = "-fx-background-color: #f4fbf4;" +
                        "-fx-border-color: #ced6e0;" +
                        "-fx-font-family: 'Arial';" +
                        "-fx-font-weight: bold;" +
                        "-fx-border-width: 1.35;" +
                        "-fx-border-insets: -0.7 0 -0.7 0, 0;" +
                        "-fx-padding: 0;" +
                        "-fx-background-radius: 0;";

        // =========================================================
        // INFO POPUP
        // =========================================================
        public static final String INFO_BUTTON = "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-family: 'Arial';" +
                        "-fx-background-radius: 50%;" +
                        "-fx-background-color: #4A90E2;" +
                        "-fx-text-fill: white;" +
                        "-fx-border-width: 0;" +
                        "-fx-padding: 0;" +
                        "-fx-alignment: center;" +
                        "-fx-min-width: 25px;" +
                        "-fx-min-height: 25px;" +
                        "-fx-max-width: 25px;" +
                        "-fx-max-height: 25px;" +
                        "-fx-cursor: hand;";

        public static final String CLOSE_BUTTON = "-fx-background-color: #e74c3c;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 6;" +
                        "-fx-padding: 10 20;" +
                        "-fx-cursor: hand;";

        // =========================================================
        // CART COMPONENT
        // =========================================================
        public static final String CART_CONTAINER = "-fx-background-color: #dff7d6;" +
                        "-fx-background-radius: 12;" +
                        "-fx-border-radius: 12;" +
                        "-fx-border-color: #b4e6a4;" +
                        "-fx-border-width: 1;" +
                        "-fx-padding: 20 15;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 8, 0.1, 0, 2);";

        public static final String CART_ROW = "-fx-background-color: #f0f8f0;" +
                        "-fx-border-color: #a7e0a1;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-radius: 10;" +
                        "-fx-padding: 10;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 4, 0, 0, 1);";

        // =========================================================
        // FRUIT CARD COMPONENT
        // =========================================================
        public static final String FRUIT_CARD = "-fx-background-color: dff7d6;" +
                        "-fx-background-radius: 12;" +
                        "-fx-border-radius: 12;" +
                        "-fx-border-color: #b4e6a4;" +
                        "-fx-border-width: 1;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.07), 8, 0.1, 0, 1);";

        public static final String FRUIT_CARD_DISABLED = "-fx-background-color: #e0e0e0;" +
                        "-fx-background-radius: 12;" +
                        "-fx-border-radius: 12;" +
                        "-fx-border-color: #b4b4b4;" +
                        "-fx-border-width: 1;" +
                        "-fx-text-fill: #a0a0a0;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.03), 4, 0.05, 0, 1);";

        // Fruitcard hover effect
        public static final String FRUIT_CARD_HOVER = "-fx-background-color: #c9f2bc;" +
                        "-fx-background-radius: 12;" +
                        "-fx-border-radius: 12;" +
                        "-fx-border-color: #9cd98e;" +
                        "-fx-border-width: 1;" +
                        "-fx-cursor: hand;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 12, 0.2, 0, 2);";

        // =========================================================
        // EXPANDABLE SORTING WINDOW — MINIMAL LIGHT
        // =========================================================
        public static final String EXPANDABLE_FILTER = "-fx-background-color: rgba(255,255,255,0.97);" +
                        "-fx-border-color: #ddd;" +
                        "-fx-border-radius: 4;" +
                        "-fx-background-radius: 4;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 4, 0, 0, 1);";

        public static final String MODERN_DROPDOWN = "-fx-background-color: #f0f8f0;" +
                        "-fx-border-radius: 4;" +
                        "-fx-background-radius: 4;" +
                        "-fx-padding: 4 8 4 8;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 2, 0, 0, 1);";

        public static final String MODERN_DROPDOWN_HOVER = "-fx-background-color: #f4fbf4;" + // slightly lighter
                        "-fx-border-radius: 5;" +
                        "-fx-background-radius: 5;" +
                        "-fx-padding: 4 8 4 8;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.12), 3, 0, 0, 1);";

        // Clear filters button hover
        public static final String CLEAR_BUTTON_HOVER = "-fx-background-color: #d9efd9;" + // slightly darker on hover
                        "-fx-border-radius: 5;" +
                        "-fx-background-radius: 5;" +
                        "-fx-padding: 4 8 4 8;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.12), 3, 0, 0, 1);";

        // Seasonal buttons
        private static final String BASE = "-fx-background-radius: 4;" +
                        "-fx-border-radius: 4;" +
                        "-fx-padding: 4 10 4 10;" +
                        "-fx-font-size: 12px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #333;";

        public static final String SPRING = BASE + "-fx-background-color: linear-gradient(#e3f9e5, #c6f2c2);";
        public static final String SUMMER = BASE + "-fx-background-color: linear-gradient(#fff8d1, #fff0a5);";
        public static final String AUTUMN = BASE + "-fx-background-color: linear-gradient(#ffe0d0, #ffc8a0);";
        public static final String WINTER = BASE + "-fx-background-color: linear-gradient(#e0efff, #bcd4f5);";

        public static final String SPRING_HOVER = BASE + "-fx-background-color: linear-gradient(#e9fdee, #d0f7c9);";
        public static final String SUMMER_HOVER = BASE + "-fx-background-color: linear-gradient(#fffce0, #fff6b3);";
        public static final String AUTUMN_HOVER = BASE + "-fx-background-color: linear-gradient(#ffe8dd, #ffd4a8);";
        public static final String WINTER_HOVER = BASE + "-fx-background-color: linear-gradient(#e6f2ff, #c9def5);";

        // Content container
        public static final String CONTENT_CONTAINER = "-fx-background-color: #174A2E;" +
                        "-fx-background-radius: 12;" +
                        "-fx-border-radius: 12;" +
                        "-fx-border-color: #a7e0a1;" +
                        "-fx-border-width: 0.1;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.10), 10, 0.2, 0, 2);";

        // Arrow handle
        public static final String ARROW_BOX = "-fx-background-color: linear-gradient(to bottom, #d9d9d9, #bfbfbf);" +
                        "-fx-border-color: #c0c0c0;" +
                        "-fx-shape: \"M 0 0 L 40 0 L 30 15 L 10 15 Z\";" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.18), 4, 0, 0, 2);" +
                        "-fx-cursor: hand;" +
                        "-fx-transition: all 0.2s ease-in-out;";

        public static final String ARROW_BOX_HOVERING = "-fx-background-color: linear-gradient(to bottom, #e6e6e6, #cfcfcf);"
                        +
                        "-fx-border-color: #bfbfbf;" +
                        "-fx-shape: \"M 0 0 L 40 0 L 30 15 L 10 15 Z\";" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.25), 4, 0, 0, 2);" +
                        "-fx-cursor: hand;";

        // =========================================================
        // MISC / UTILITY
        // =========================================================
        public static final String SCROLLPANE_TRANSPARENT = "-fx-background: #dff7d6; -fx-background-color: #dff7d6; -fx-border-color: transparent;";
        public static final String TRANSPARENT_BACKGROUND = "-fx-background-color: transparent;";
        public static final String TOPBAR = "-fx-background-color: #1F5C3A;" +
                        "-fx-border-color: #174A2E;" +
                        "-fx-border-width: 0 0 2 0;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 12, 0.2, 0, 2);" +
                        "-fx-padding: 12 20;";
}