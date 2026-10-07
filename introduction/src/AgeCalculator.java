package src;

import java.util.Scanner;

public class AgeCalculator {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.print("Enter the current year: ");
        int currYear = Integer.parseInt(input.nextLine());

        System.out.print("Enter your birth year: ");
        int birthYear = Integer.parseInt(input.nextLine());

        int age = currYear - birthYear;
        System.out.println("Your age is " + age);
    }
}
