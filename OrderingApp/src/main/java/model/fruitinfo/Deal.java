package model.fruitinfo;

public class Deal {
    private String label;
    private double adjustedPrice;

    // NEW: countdown fields
    private long endTimeMillis;   // timestamp wanneer deal stopt

    public Deal(String label, double adjustedPrice, long endTimeMillis) {
        this.label = label;
        this.adjustedPrice = adjustedPrice;
        this.endTimeMillis = endTimeMillis;
    }

    public String getLabel() {
        return label;
    }

    public double getAdjustedPrice() {
        return adjustedPrice;
    }

    public long getEndTimeMillis() {
        return endTimeMillis;
    }

    // NEW: check of deal actief is
    public boolean isActive() {
        return System.currentTimeMillis() < endTimeMillis;
    }

    // NEW: hoeveel tijd nog tot einde
    public long getTimeRemainingSeconds() {
        long remaining = (endTimeMillis - System.currentTimeMillis()) / 1000;
        return Math.max(remaining, 0);
    }
}
