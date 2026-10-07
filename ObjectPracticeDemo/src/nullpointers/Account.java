package nullpointers;

import java.util.ArrayList;

public class Account {

    int number;
    ArrayList<Customer> owners;

    Account(int numberVal, Customer ownerRef){
        number = numberVal;
        ArrayList<Customer> owners = new ArrayList<Customer>();
        owners.add(ownerRef);
    }

    void addOwner(Customer ownerRef){
        owners.add(ownerRef);
        ownerRef.addAccount(this);
    }
}
