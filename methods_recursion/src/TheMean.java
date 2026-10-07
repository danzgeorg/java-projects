package src;

public class TheMean {
    public static void main(String[] args) {
        if (arithmeticMean(17,43) > geometricMean(17,43)) {
            System.out.println("The mean is greater than the geometric mean");
        }
        else {
            System.out.println("The mean is less than the geometric mean");
        }

    }
    public static double arithmeticMean(double a,double b) {
        return (a+b)/2;
    }
    public static double geometricMean(float a,float b) {
        return Math.sqrt(a*b);
    }
}
