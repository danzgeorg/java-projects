package src;

public class TheMean2 {
    public static void main(String[] args) {
        double[] numbers = {45, 3, 12, 6};
        if (arithmeticMean(numbers) > geometricMean(numbers)) {
            System.out.println("The arithmetic mean is greater than the geometric mean.");
        }
        else if (arithmeticMean(numbers) < geometricMean(numbers)) {
            System.out.println("The arithmetic mean is less than the geometric mean.");
        }
        else {
            System.out.println("The arithmetic mean is equal to the geometric mean.");
        }

    }
    public static double arithmeticMean(double[] array) {
        double sum = 0;
        for (double n : array) {
            sum += n;
        }
        return sum / array.length;
    }
    public static double geometricMean(double[] array) {
        double ratio = 1.0 / array.length;
        for (double n : array) {
            ratio *= n;
        }
        return ratio;

    }
}
