package carTask;

public class Car {
    private final String model;
    private final int speed;
    private final double miles;

    public Car(String model, int speed, double miles) {
        this.model = model;
        this.speed = speed;
        this.miles = miles;
    }
    public String getModel() {
        return model;
    }
    public int getSpeed() {
        return speed;
    }
    public double getMiles() {
        return miles;
    }


}
