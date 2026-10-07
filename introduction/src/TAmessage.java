package src;

import java.util.Scanner;

public class TAmessage {
    public static void main(String[] args) {
        String TA_name;
        String name;
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the teacher assistant's name: ");
        TA_name = input.nextLine();
        System.out.print("Enter your name: ");
        name = input.nextLine();
        System.out.print(String.format("Dear %s! \nI enjoy your tutorials, they are awesome! \n From %s ", TA_name, name));

    }
}
