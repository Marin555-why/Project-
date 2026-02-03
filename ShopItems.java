class ShopItems {
    String name;
    double price;
    int stock;
    ShopItems(String name, double price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }
boolean inStock(){
    return stock > 0;
}
void canPurchase(){
    if(inStock()){
        this.stock--;
        // System.out.println("Purchase successful!");
    } else {
        System.out.println("Item out of stock.");
    }
}
}
