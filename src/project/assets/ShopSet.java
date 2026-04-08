package project.assets;
import java.util.ArrayList;
import java.util.List;

public class ShopSet{
   private String setName;
   private List<ShopItem> items;

   public ShopSet(String setName, List<ShopItem> items) {
       this.setName = setName;
       this.items = new ArrayList<>(items);
   } 
   public ShopSet(String setName) {
       this.setName = setName;
       this.items = new ArrayList<>();
   }
   public ShopSet(ShopSet other){
    this.setName = other.setName;
    this.items = new ArrayList<>(other.items);
   }   

   public void addItem(ShopItem item) {
       items.add(item);
   }
   public List<ShopItem> getItems() {
       return items;
   }
    public String getSetName() {
         return setName;
    }
    public double getTotalPrice() {
        double total = 0;
        for (ShopItem item : items) {
            total += item.getPrice()*item.getQuantity();
        }
        return total;
    }
}
