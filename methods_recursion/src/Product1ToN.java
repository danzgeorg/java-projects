package src;

import java.util.Scanner;

public class Product1ToN {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("What number do you want the products for? ");
        int n = input.nextInt();
        System.out.println("The end product is " + product1ToN(n));
    }
    public static int product1ToN(int n) {
        if (n > 0) {
            int product = 1;
            for (int i = 1; i <= n; i++) {
                product *= i;
            }
            return product;
        }
        else if (n < 0){
            int product = -1;
            for (int i = n; i <= -1 ; i++) {
                product *= i;
            }
            return product;
        }
        else {
            return 0;
        }

    }
}
