package project.assets;
import java.util.Arrays;

public class Order {

    private final ShopItem[] cartItems;
    private final int[] quantities;
    private int count;
   

    private static final double BULK_DISCOUNT = 0.10;
    private static final double SET_DISCOUNT = 0.05;
    private static final double QUANTITY_DISCOUNT = 0.02;

    public Order(int size) {
        cartItems = new ShopItem[size];
        quantities = new int[size];
        count = 0;
    }

    public void addItem(ShopItem item, int quantity) {
        if (count < cartItems.length) {   
        cartItems[count] = item;
            quantities[count] = quantity;
            count++;
    } else {
            System.out.println("Cart is full!");
        }
    }
    public void displayCart() {
        System.out.println("Items in Cart:");
        for (int i = 0; i < count; i++) {
            ShopItem item = cartItems[i];
            System.out.println("- " + item.getName()
                    + " | Price: $" + item.getPrice());
        }
    }

    public double calculateTotal() {
        double total = 0.0;
        int setCount = 0;
        int totalQuantity = 0;

        for (int i = 0; i < count; i++) {
            total += cartItems[i].getPrice() * quantities[i];
            /*shopItem item = cartItems[i];
            total += item.getPrice() * item.getGetOrderedQuantity();
            totalQuantity += item.getQuantity();

            if (item.isPartofSet()) {
                setCount++;*/
            }

        if (count > 5) {
            total *= (1 - BULK_DISCOUNT);
        }

        if (setCount >= 3) {
            total *= (1 - SET_DISCOUNT);
        }

        if (totalQuantity < 2) {
            total *= (1 - QUANTITY_DISCOUNT);
        }

        return total;
    }
    
    public ShopItem[] getItems(){
        return Arrays.copyOf(cartItems, count);
    }
    public int getItemCount(){
        return count;
    }
}