package model.enums;

public enum PriceCategory {
    ALL("Alles"),
    CHEAP("Goedkoop"),
    EXPENSIVE("Duur");

    private final String displayName;

    PriceCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}