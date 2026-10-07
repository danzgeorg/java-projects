import java.util.Scanner;

public class OnlyPositiveInteger {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int number;


    do {
        System.out.println("Enter a number: ");
        number = input.nextInt();
        } while (number >= 0);
    System.out.println("Only positive integers are allowed.");
    }

}
