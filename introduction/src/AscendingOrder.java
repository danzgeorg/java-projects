package src;

import java.util.Scanner;

public class AscendingOrder {
    public static boolean order_check(int a, int b, int c) {
        return a < b && b < c;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter the first number: ");
        int num1 = input.nextInt();
        System.out.println("Enter the second number: ");
        int num2 = input.nextInt();
        System.out.println("Enter the third number: ");
        int num3 = input.nextInt();
        if (order_check(num1, num2, num3)) {
            System.out.println("True");
        } else {
            System.out.println("False");
        }


    }

}