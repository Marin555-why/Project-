package project.assets;

public class ShopItem {
    private final String name;
    private final double price;
    private final int quantity;
    private final boolean isPartofSet;
   

    public ShopItem(String name, double price, int quantity, boolean isPartofSet) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.isPartofSet = isPartofSet;  
    }

    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public int getQuantity() {
        return quantity;
    }
    public boolean isPartofSet() {
        return isPartofSet;
    }
}
