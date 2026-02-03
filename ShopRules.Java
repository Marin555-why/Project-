import java.util.ArrayList;

class Shop {
    ArrayList<Item> allItems = new ArrayList<>();
    ArrayList<ItemSet> allSets = new ArrayList<>();

    void addItem(Item i) {
        allItems.add(i);
    }

    void addSet(ItemSet s) {
        allSets.add(s);
    }

    void showItems() {
        for (Item i : allItems) {
            System.out.println(i.name + " | $" + i.price + " | Stock: " + i.stock);
        }
    }

    void showSets() {
        for (ItemSet s : allSets) {
            System.out.print(s.setName + " -> ");
            for (Item i : s.items) {
                System.out.print(i.name + " ");
            }
            System.out.println();
        }
    }

    void buyItem(User user, Item item) {
        if (!user.canPurchase()) {
            System.out.println("Guests cannot purchase.");
            return;
        }

        if (!item.available()) {
            System.out.println(item.name + " is out of stock.");
            return;
        }

        item.buyOne();
        System.out.println("Purchased item: " + item.name);
    }

    void buySet(User user, ItemSet set) {
        if (!user.canPurchase()) {
            System.out.println("Guests cannot purchase.");
            return;
        }

        if (!set.available()) {
            System.out.println("Set unavailable.");
            return;
        }

        set.buySet();
        System.out.println("Purchased set: " + set.setName);
        System.out.println("Total: $" + set.getSetPrice());
    }
    void purchaseSet(User user){
        if(!user.canPurchase){
            System.out.println("Guests cannot purchase.");
            return;
        }
    }
        
    }

