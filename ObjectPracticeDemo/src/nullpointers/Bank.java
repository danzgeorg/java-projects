package nullpointers;

import java.util.ArrayList;

public class Bank {

    int nextFreeAccountNumber;

    ArrayList<Customer> customers;
    ArrayList<Account> accounts;

    Bank(){
        nextFreeAccountNumber = 0;

        accounts = new ArrayList<>();
    }

    Customer createNewCustomer(String name){

        //create a new customer and add them to the
        //bank's list of customers
        Customer newCustomer = new Customer(name);
        customers.add(newCustomer);

        //any new customer must have an account to start with
        Account newAccount = createNewAccount(newCustomer);

        return newCustomer;
    }

    //create an account for the given customer
    Account createNewAccount(Customer owner){


        //us the next free account number in the bank and make sure
        //to increment that number afterwards (since you've just used it up!)
        Account newAccount = new Account(nextFreeAccountNumber, owner);
        nextFreeAccountNumber++;

        //finally, add the account to the bank's list of accounts
        accounts.add(newAccount);

        //and of course add the account to the owner
        owner.addAccount(newAccount);

        return null;
    }
}
