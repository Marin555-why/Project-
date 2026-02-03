 class ShopItemSets {
    String setName;
    ShopItems[] items;

    ShopItemSets(String setName, ShopItems[] items) {
        this.setName = setName;
        this.items = items;
    }

    boolean inStock() {
        for (ShopItems item : items) {
            if (!item.inStock()) {
                return false;
            }
        }
        return true;
    }
    // 15% discount
    double setPrice() {
        double total = 0;
        for (ShopItems item : items) {
            total += item.price;
        }
        return total * 0.85; 
    }
void purchaseSet(){
    if(inStock()){
        for(ShopItems item : items){
            item.canPurchase();
        }
        System.out.println("Purchase successful!");
    } else {
        System.out.println("an item in the set are out of stock.");
}
}
}
