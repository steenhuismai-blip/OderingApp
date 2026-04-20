package model.fruitinfo;

public class Fruit {
    private String fruitName;
    private double price;
    private double weight;
    private String story;
    
    // Deal
    private Deal deal;

    // Voor afbeeldingen
    private String imagePath;

    // Voor herkomst
    private String origin;

    // Constructor zonder deal
    public Fruit(String fruitName, double price, double weight, String story, String imagePath) {
        this.fruitName = fruitName;
        this.price = price;
        this.weight = weight;
        this.story = story;
        this.imagePath = imagePath;
    }

    // Constructor met deal
        public Fruit(String fruitName, double price, double weight, String story, String imagePath, Deal deal) {
        this.fruitName = fruitName;
        this.price = price;
        this.weight = weight;
        this.story = story;
        this.imagePath = imagePath;
        this.deal = deal;
    }

    // Getters
    public String getFruitName() {
        return fruitName;
    }

    public double getPrice() {
        return price;
    }
    public double getWeight() {
        return weight;
    }

    public String getStory() {
        return story;
    }

    public Deal getDeal() {
        return deal;
    }

    public String getImagePath() {
        return imagePath;
    }
        public String getOrigin() {
        return origin;
    }

    // Setters 
        public void setDeal(Deal deal) {
        this.deal = deal;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    public void setPrice(double price) {
    this.price = price;
}

public void setWeight(double weight) {
    this.weight = weight;
}
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    
    // Deal logica
        public boolean hasActiveDeal() {
        return deal != null && deal.isActive();
    }
    public boolean isWinterFruit() {
    if (fruitName == null) return false;

    String name = fruitName.toLowerCase();

    return name.contains("appel") ||
           name.contains("peer") ||
           name.contains("sinaasappel") ||
           name.contains("mandarijn");
}
        public double getDisplayPrice() {
        if (hasActiveDeal()) {
            return deal.getAdjustedPrice();
        }
        return price;
    }

    public String getDisplayImagePath() {
        if (imagePath != null && !imagePath.isBlank()) {
            return imagePath;
        }

        String name = fruitName == null ? "unknown" : fruitName.trim();

        if (name.isBlank()) {
            return "images/placeholder.png";
        }

        String fileName = name.toLowerCase().replaceAll("[^a-z0-9]+", "");

        if (fileName.isBlank()) {
            return "images/placeholder.png";
        }

        return "images/" + fileName + ".png";
    }
}