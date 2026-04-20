package model.enums;

public enum DealFilter {
    ALL("Alle producten"),
    DEALS_ONLY("Alle deals"),
    NO_DEALS("Geen deals");

    private final String displayName;

    DealFilter(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}