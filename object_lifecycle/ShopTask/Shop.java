package ShopTask;

public class Shop {
    private String name;
    private int numberOfArticles;
    private int[] articles; //number of each article the shop has in stock.

    static {System.out.println("Welcome to the new shop");}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfArticles() {
        return numberOfArticles;
    }

    public void setNumberOfArticles(int numberOfArticles) {
        if (numberOfArticles >= 0) {this.numberOfArticles = numberOfArticles;}
    }

    public int[] getArticles() {
        return articles;
    }

    public void setArticles(int[] articles) {
        if (articles != null && isPositive(articles)) {this.articles = articles;}
    }

    public Shop(String name, int numberOfArticles, int[] articles) {
        this.name = name;
        this.numberOfArticles = numberOfArticles;
        this.articles = articles;
    }

    public Shop()  {
        this.name = "shop open soon";
        this.numberOfArticles = 0;
        this.articles = new int[1];
    }

    public boolean isPositive(int[] array) {
        for (int j : array) {if (j < 0) {return false;}}
        return true;
    }

    public void decrementArticle(int i) {
        if (i >= 0 && i < articles.length && articles[i] > 0) {articles[i]--;}
        else {System.out.println("Decrement not possible");}
    }
}
