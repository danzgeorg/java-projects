package src;

public class FibonacciNumbers {
    public static void main(String[] args) {
        int n = 8;
        System.out.println("The fibonacci (" + n + ") = " + fibonacciSequence(n));
    }
    public static int fibonacciSequence(int n) {
        return n==0? 0: n==1? 1: fibonacciSequence(n-1) + fibonacciSequence(n-2);
    }
}
