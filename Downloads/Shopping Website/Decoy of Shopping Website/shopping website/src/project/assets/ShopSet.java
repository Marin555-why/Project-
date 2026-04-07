package project.assets;
import java.util.Arrays;

public class ShopSet{
    private final ShopItem[] setItems;
    public ShopSet(ShopItem[] setItems) {
        this.setItems = (setItems != null) ? setItems : new ShopItem[0];
    }
    public ShopItem[] getSetItems() {
        return Arrays.copyOf(setItems, setItems.length);
    }
}
