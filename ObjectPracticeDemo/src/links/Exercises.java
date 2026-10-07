package links;

public class Exercises {

    public static void exercise1(){

        TypeA ref1 = new TypeA(2);
        TypeA ref2 = new TypeA(1);
       //??


       // testCorrect(""+ref1.id + ref2.id + ref3.id, "221");
    }

    public static void exercise2() {
        new TypeA(1);
        new TypeA(2);
        //??

        //testCorrect("" + ref1.id + ref2.id + ref3.id + ref4.id,"2112" );
    }

    public static void exercise3(){
        TypeA ref1 = new TypeA(1);
        TypeA ref2 = new TypeA(2);
        TypeA ref3 = new TypeA(3);
        //??

        //testCorrect("" + ref1.id + ref2.id + ref3.id + ref4.id + ref5.id,"21132" );
    }

    public static void exercise4(){

        TypeB ref1 = new TypeB(1);
        TypeB ref2 = new TypeB(2);
        //??

    //    testCorrect("" + ref1.id + ref2.id + ref1.field1.id + ref2.field1.id, "1221" );
    }

    public static void exercise5(){
       TypeB ref1 = new TypeB(1);
       TypeB ref2 = new TypeB(2);
       TypeB ref3 = new TypeB(3);
       //??

      // testCorrect(""+ ref1.id + ref2.id + ref3.id + ref1.field1.id + ref2.field1.id + ref3.field1.id, "123312");

    }

    public static void exercise6(){
        TypeB ref1 = new TypeB(1);
        //??

        //testCorrect("" + ref1.id + ref1.field1.id + ref1.field1.field1.id  + ref1.field1.field1.field1.id,"1321" );
    }

    public static void testCorrect(String s1, String s2){
        if (s1.equals(s2)) System.out.println("Correct");
        else System.out.println("Incorrect");
    }

    public static void doExercises(){
        System.out.print("Link exercise 1: \n\t");
        exercise1(); System.out.println();
        System.out.print("Link exercise 2: \n\t");
        exercise2(); System.out.println();
        System.out.print("Link exercise 3: \n\t");
        exercise3(); System.out.println();
        System.out.print("Link exercise 4: \n\t");
        exercise4(); System.out.println();
        System.out.print("Link exercise 5: \n\t");
        exercise5(); System.out.println();
        System.out.print("Link exercise 6: \n\t");
        exercise6(); System.out.println();
    }
}
