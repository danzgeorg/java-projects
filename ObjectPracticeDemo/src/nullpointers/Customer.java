package nullpointers;

import java.util.ArrayList;

public class Customer {

    String name;
    String address;

    ArrayList<Account> accounts;

    Customer(String nameVal){
        name = nameVal;
    }

    void setAddress(String addressVal){
        address = addressVal;
    }

    void addAccount(Account accountRef){
        accounts.add(accountRef);
    }

}
