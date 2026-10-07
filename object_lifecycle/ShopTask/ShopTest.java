package ShopTask;

public class ShopTest {
    public static void main(String[] args) {
        // create a shop with initial data
        int[] initialArticles = {5, 6, 7};
        Shop shop = new Shop("Electronic Store", 3, initialArticles);

        // print shop details before purchase
        System.out.println("Shop Name: " + shop.getName());
        System.out.println("Initial stock: ");
        printArray(shop.getArticles());

        // create a buyer with articles to buy
        int[] articlesToBuy = {1, 2, 3}; // buyer is buying 1 of article 0, 2 of article 1, 3 of article 2
        Buyer buyer = new Buyer(shop, articlesToBuy);

        updateStock(buyer);

        // print shop details after purchase
        System.out.println("Stock after purchase: ");
        printArray(shop.getArticles());
    }

    public static void updateStock(Buyer buyer) {
        Shop shop = buyer.getShopToGo();
        int[] shopStock = shop.getArticles();
        int[] articlesToBuy = buyer.getArticlesToBuy();

        // check shop stock and articlesToBuy arrays are valid
        if (shopStock != null && articlesToBuy != null && shopStock.length == articlesToBuy.length) {
            for (int i = 0; i < articlesToBuy.length; i++) {
                for (int j = 0; j < articlesToBuy[i]; j++) {
                    shop.decrementArticle(i);} // decrement stock for each article
            }
        } else {System.out.println("Invalid purchase");}
    }

    public static void printArray(int[] array) {
        if (array != null) {
            for (int value : array) {System.out.print(value + " ");}
            System.out.println();
        } else {System.out.println("Array is null.");}
    }
}
