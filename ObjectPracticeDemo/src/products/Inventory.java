package products;

import java.util.ArrayList;

public class Inventory {
    ArrayList<Product> products;

    void addProduct(Product p){
        products.add(p);
    }

    void removeProduct(int productCode){
        int index = -1;
        for (int i=0; i<products.size(); i++)
            if (products.get(i).code == productCode)
                index = i;

        if (index >= 0)
            products.remove(index);
    }

    Product findProduct(int productCode){
        for (int i=0; i<products.size(); i++) {
            Product p = products.get(i);
            if (p.code == productCode)
                return p;
        }
        return null;
    }
}
