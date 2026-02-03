
class Shop{
    static void main(String[] args){
        Login loginSystem = new Login();
        loginSystem.startLogin();
        ShopItems item1 = new ShopItems("Laptop", 100.0, 5);
        ShopItems item2 = new ShopItems("Mouse", 50.0, 10);
        ShopItems item3 = new ShopItems("Keyboard", 80.0, 1);
        ShopItems item4 = new ShopItems("Monitor", 300.0, 0);

        ShopItems[] items = {item1, item2, item3};
        ShopItemSets techSet = new ShopItemSets("Tech Set", items);

        System.out.println("Previous: $" + (item1.price + item2.price + item3.price));
        System.out.println("Total: $" + techSet.setPrice());

        techSet.purchaseSet();
    }
}
       

