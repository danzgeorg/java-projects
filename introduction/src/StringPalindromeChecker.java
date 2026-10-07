package src;

import java.util.Scanner;

public class StringPalindromeChecker {
    public static void main(String[] args) {
        Scanner input =  new Scanner(System.in);
        System.out.print("Enter a string: ");
        String string = input.nextLine();

        if (isPalindrome(string)){
            System.out.println("The string is palindrome");
        }
        else {
            System.out.println("The string is not palindrome.");
        }
    }
    /*
    public static boolean isPalindrome(String str){
        int length = str.length();
        for (int i = 0; i < length /2; i++){
            if (str.charAt(i) != str.charAt(length - i - 1)){
                return false;
            }
        }
        return true;
    }
     */
    public static boolean isPalindrome(String string){
        StringBuilder original = new StringBuilder(string);
        return original.reverse().toString().equals(string);
    }
}

