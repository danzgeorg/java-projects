package SingerTask;

public class Test {
    public static void main(String[] args) {
        Singer singer1 = new Singer("Chris",34,true);
        Singer singer2 = new Singer ("Maria",30,false);
        Duet duet1 = new Duet(singer1,singer2);

        Singer singer3 = new Singer("John",25,false);
        Singer singer4 = new Singer ("Mary",30,true);
        Duet duet2 = new Duet(singer3,singer4);

        Quatuor band1 = new Quatuor(duet1,duet2);

        System.out.printf("The name & age of the first singer of the second duet of the band is %s and %d.",
                band1.getDuet2().getSinger1().getName(),
                band1.getDuet2().getSinger1().getAge());
    }
}
