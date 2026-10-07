package src;

public class Method1 {
    public static void main(String[] args) {
        int result = formula(1250);
            System.out.println(result);
        }
        private static int formula(int n) {
            n = n * 4 + 5;
            return n;
        }
    }
