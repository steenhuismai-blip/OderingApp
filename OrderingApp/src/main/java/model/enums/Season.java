package model.enums;

public enum Season {
    ALL("Alles"),
    SPRING("Lente"),
    SUMMER("Zomer"),
    AUTUMN("Herfst"),
    WINTER("Winter");

    private final String displayName;

    Season(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}