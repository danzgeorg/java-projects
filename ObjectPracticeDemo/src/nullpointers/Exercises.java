package nullpointers;

public class Exercises {
    public static void doExercises(){

        //have a look at the images BankStructure.png (available in this package
        //and in lecture notes) and browse the Account, Customer, and Bank classes
        //to try and understand how they map to the structure shown in the image

        //have a look at the BankContent.png image and at the code below

        //try running the code - you will start getting NullPointerExceptions.
        //Follow 'the recipe' shown in class to track down and clear these errors.
        //Seek advice from colleagues, TAs, lecturer if you can't figure it out.

        //place a debug breakpoint next to the last line of this method
        //(System.out.println("done")) and use the debugger to inspect
        //that our javaBank has the 'contents' that we expect it to:
        //all the right customers linked to all the right accounts.

        Bank javaBank = null;

        Customer customer1 = javaBank.createNewCustomer("Radu");
        Customer customer2 = javaBank.createNewCustomer("Charlie");

        Account sharedAccount = javaBank.createNewAccount(customer1);
        sharedAccount.addOwner(customer2);

        System.out.println("done");
    }
}
