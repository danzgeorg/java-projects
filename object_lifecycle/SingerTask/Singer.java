package SingerTask;

public class Singer {
    private String name;
    private int age;
    private boolean professional;

    public Singer(String name, int age, boolean professional) {
        this.name = name;
        this.age = age;
        this.professional = professional;
        professional = true;
    }

    public int getAge() {return age;}

    public String getName() {return name;}

    public boolean isProfessional() {
        return professional;
    }

    public void setAge(int age) {
        if (age >= this.age) {this.age = age;}
    }

    public void reverseProfessional(boolean professional) {
        if (professional) {this.professional = false;}
        else if (!this.professional) {this.professional = true;}
    }

    public boolean isAdult () {return this.age >= 18;}
}
