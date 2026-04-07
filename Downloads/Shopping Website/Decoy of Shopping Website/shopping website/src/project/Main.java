package project;
import project.plugins.Shop;
public class Main {

    public static void main(String[] args) {
        //Shop shop = new Shop();
        //shop.start(new Scanner(System.in));
        new GUI(new Shop());
    }
}

        