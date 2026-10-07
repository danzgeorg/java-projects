package src;

import java.util.Scanner;

public class Sum1ToN {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("What is the range you want to calculate the sum from 1 to n for? ");
        int num = input.nextInt();
        int sum = sumNumbers(num);
        System.out.println("The result is " + sum);
    }
    public static int sumNumbers(int n) {
        if (n > 0) {
            return n * (n + 1) / 2;
        }
        return 0;
    }
}
