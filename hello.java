public class MenuItem {
    String name;
    int quantity;
    double price;
}
MenuItem(String name, int quantity, double price) {
    this.name = name;
    this.quantity = quantity;
    this.price = price;
} // Constructor
double getTotalPrice() {
    return quantity * price;
} //  calculate total price
void displayItem() {
    System.out.println("Item: " + name + ", Quantity: " + quantity + ", Price per item: $" + price + ", Total Price: $" + getTotalPrice());
} // display item details
public class MenuApp {
    public static void main(String[] args) {
        MenuItem item1 = new MenuItem("PlaceHolder1", 2, 5.99);
        MenuItem item2 = new MenuItem("PlaceHolder2", 1, 2.99);
        
        item1.displayItem();
        item2.displayItem();
    }
}