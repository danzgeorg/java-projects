package SportspersonTask;

public class Main {
    public static void main(String[] args) {
        Sportsperson person = new Sportsperson("Daniel",18);

        while (person.getAge() <= 32) {
            System.out.printf("Updated age of %s: %d%n ", person.getName(), person.getAge());
            person.incrementAge();
        }
    }
}
