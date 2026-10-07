package SportspersonTask;

public class Sportsperson {
    private final String name;
    private int age;

    public Sportsperson(String name, int age) {
        this.name = name;
        this.age = age;
    }
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }

    public void incrementAge() {
        age++;
    }

}
