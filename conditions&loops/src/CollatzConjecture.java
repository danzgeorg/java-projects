package src;

import java.util.Scanner;

public class CollatzConjecture {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a number: ");
        int number = input.nextInt();

        if (number <= 0) {
            System.out.println("Enter a positive integer."); //test for negative number
        } else {
            collatzSequence(number);
        }

    }
    // computes the number into a collatz sequence, returning line by line the stages
    public static void collatzSequence(int num) {
        while (num != 1) {
            // for even numbers
            System.out.println(num + " ");
            if (num % 2 == 0) {
                num /= 2;
            }
            // for odd numbers
            else {
                num = (3 * num) + 1;
            }
        }
        // print 1 when while breaks
        System.out.println(1);
    }
}
