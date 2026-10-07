package src;

import java.util.Scanner;

public class MonthToNumber {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a month: ");
        String month = input.nextLine().toUpperCase();

        int monthNumber = getMonthNumber(month);

        if (monthNumber != -1) {
            System.out.println("Month number is " + monthNumber);
        } else {
            System.out.println("Month number is not valid.");
        }
    }

    public static int getMonthNumber(String month) {
        switch (month) {
            case "JANUARY":
                return 1;
            case "FEBRUARY":
                return 2;
            case "MARCH":
                return 3;
            case "APRIL":
                return 4;
            case "MAY":
                return 5;
            case "JUNE":
                return 6;
            case "JULY":
                return 7;
            case "AUGUST":
                return 8;
            case "SEPTEMBER":
                return 9;
            case "OCTOBER":
                return 10;
            case "NOVEMBER":
                return 11;
            case "DECEMBER":
                return 12;
            default:
                return -1;
        }
    }
}
