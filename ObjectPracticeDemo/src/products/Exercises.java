package products;

public class Exercises {
    public static void doExercises(){

/*-----------------------------*/
    //// Exercise block 1.

        //1.1 how many classes are declared in the products package?

        //1.2 create three product objects; for each set their
        //fields to some meaningful values


        //1.3 add an integer field called quantity to the Product class
        //    and set a different quantity for each of your products

        //Uncomment the following 11 lines of code:
       /* Product pp1 = new Product();
        Product pp2 = pp1;
        Product pp3 = new Product();
        Product pp4 = new Product();
        pp3.code = 71;
        pp4.code = 71;
        new Product();
        Product pp5 = pp4;
        System.out.print(pp1 + "\n" + pp2 + "\n" + pp3 + "\n" + pp4 + "\n" + pp5);
        if (pp3 == pp4)
            System.out.println("Same product");*/


        //1.4 How many objects are created in those 11 lines of code?
        //1.5 How many reference variables exist now in the code above and
        //    what are their names?
        //1.6 Can you access all objects that are created? Why yes or no?

        //1.7 By the time the first print statement (long one) is reached,
        //    are any of the reference variables pointing to the same objects?
        //    Track the creation of the objects using the debugger all the way
        //    to the first print statement. How can you tell which variables
        //    reference the same object?

        //1.8 will "Same product" ever be printed? Justify your answer.




/*-----------------------------*/
   //// Exercise block 2

        //2.1 uncomment lines 8-11 in the Product class. Your code will
        // not run anymore. Interpret the error that you are getting -
        // what does it mean? What can you change in your code to get it
        // working again? There are two options - either where the error
        // occurs or in the Product code. Which one is the 'more correct'
        // fix and why?

        //2.2 add one more constructor to the Product class. This should
        //take four parameters and initialize all four fields of the
        //product in one go. Change your product creation code to
        //use this more exhaustive constructor.

        //2.3 have a look at the Inventory class:
        //  - How many fields does the class have?
        //  - How many parameters can you spot (anwywhere) in the class?
        //  - How many local variables can you spot?



/*-----------------------------*/
   //// Exercise block 3

        //3.1 With the colleague next to you: look at the Product, Inventory,
        //and ShoppingCart classes; don't change any code for now.
        // *Skim* through and check out their fields, constructors, methods,
        // and implementations. How do you think these classes work together?
        // Can you draw a map of how a few objects might exist and be linked
        //together in a typical usage scenario?

        //3.2 create an Inventory object without linking it to
        //an Inventory reference variable; why is this code not practical?

        //3.3 amend the code above to reference your object by a variable

        //3.4 what methods are available in your new Inventory object?
        //list them as comments below

        //3.5 try adding some the products you've already created to the inventory using the
        // appropriate method. You should get a NullPointerException. Why?

        //3.6 add a constructor to the Inventory class and add code to it to
        // intialise the products array list; check that the code works now

        //3.7 Create a new Product and add it to the inventory *without creating
        //a variable to reference it*! What does this even mean?

        //3.8 up to this point how many Objects has your product exercise
        // code created in total? (don't count String objects)


/*-----------------------------*/
    //// Exercise block 4
        //4.1 in the ShoppingCart class, uncomment line 16

        //4.2 in here, create two ShoppingCart objects - have a look at the
        //constructor of the ShoppingCart to understand the ShoppingCart's
        //'creation contract'  - what information do you need to provide when
        //creating a ShoppingCart?


        //4.3 try adding two products to one of the ShoppingCarts; you will probably
        //get one or two NullPointerException. Figure out why and fix the errors

        //4.4 try adding to the cart a product that doesn't exist (an invalid code).
        //What happens and why? Identify the cause of this but don't try to fix the code

        //4.5 in the ShoppingCart class, add a second parameter to the addProduct method -
        // - this should be called quantity and be of type integer. How would you change the
        //code inside the method to subtract the quantity from the inventory product and
        //add the quantity to the shopping cart product? (hint: you may have to create more
        //objects...).
    }
}
