import java.util.Scanner;

public class SumToInteger {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a positive integer: ");
        int num = input.nextInt();
        if (num > 0) {
            int sum = calculate(num);
            System.out.print(String.format("The sum of 1 to %d is %d ", num, sum));
        }
    }
    public static int calculate(int n) {
        int total = 0;
        for (int i = 1; i <= n; i++) {
            total += i;
        }
        return total;
    }
}
