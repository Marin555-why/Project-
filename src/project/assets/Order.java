package project.assets;

import java.util.*;

public class Order {

    private String customerName;
    private List<Object> orderList;

    public Order(String customerName) {
        this.customerName = customerName;
        this.orderList = new ArrayList<>();
    }

    public void addItem(ShopItem item) {
        orderList.add(item);
    }

    public void addSet(ShopSet set) {
        orderList.add(new ShopSet(set));
    }

    public int getTotalItemCount() {
        int count = 0;

        for (Object obj : orderList) {
            if (obj instanceof ShopItem item) {
                count += item.getQuantity();
            } else if (obj instanceof ShopSet set) {
                for (ShopItem item : set.getItems()) {
                    count += item.getQuantity();
                }
            }
        }

        return count;
    }

    // Calculate total price dynamically
    public double calculateTotal() {
        double total = 0;

        for (Object obj : orderList) {
            if (obj instanceof ShopItem item) {
                total += item.getPrice() * item.getQuantity();
            } else if (obj instanceof ShopSet set) {
                total += set.getTotalPrice();
            }
        }
        int totalItems = getTotalItemCount();

        if (totalItems > 10) {
            total *= 0.80; // 20% off
        } else if (totalItems > 6) {
            total *= 0.85; // 15% off
        } else if (totalItems > 3) {
            total *= 0.90; // 10% off
        }

        return total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Customer: ").append(customerName).append("\n");
        sb.append("Order Details:\n");

        for (Object obj : orderList) {
            if (obj instanceof ShopItem item) {
                sb.append("- Item: ")
                  .append(item.getName())
                  .append(" ($")
                  .append(item.getPrice())
                  .append(")\n");
            } else if (obj instanceof ShopSet set) {
                sb.append("- Set: ").append(set.getSetName()).append("\n");

                for (ShopItem item : set.getItems()) {
                    sb.append("   • ")
                      .append(item.getName())
                      .append(" x").append(item.getQuantity())
                      .append(" ($")
                      .append(item.getPrice())
                      .append(")\n");
                }
            }
        }

        sb.append("Total Items: ").append(getTotalItemCount()).append("\n");
        sb.append("Final Price: $").append(calculateTotal());

        return sb.toString();
    }
}