package carTask;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter the model of the car: ");
        String model = input.nextLine();

        System.out.print("Enter the speed of the car: ");
        int speed = input.nextInt();

        System.out.print("Enter the miles of the car: ");
        double miles = input.nextDouble();

        carTask.Car car = new carTask.Car(model, speed, miles);

        System.out.printf("Car Model: %s, " + "Car Speed: %d, " + "Miles Driven: %.2f%n",
        car.getModel(), car.getSpeed(), car.getMiles());

    }
}