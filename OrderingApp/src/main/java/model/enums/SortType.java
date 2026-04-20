package model.enums;

public enum SortType {
    A_Z("A → Z"),
    Z_A("Z → A"),
    PRICE_LOW_HIGH("Prijs (laag → hoog)"),
    PRICE_HIGH_LOW("Prijs (hoog → laag)");

    private final String displayName;

    SortType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}