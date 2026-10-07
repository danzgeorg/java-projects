package products;

import java.util.ArrayList;

public class ShoppingCart {

    Inventory inventory;
    ArrayList<Product> products;

    ShoppingCart(Inventory inventory){
        //??
    }

    void addToCart(int code){
        Product p = inventory.findProduct(code);
       // p.quantity = p.quantity - 1;
        products.add(p);
    }
}
