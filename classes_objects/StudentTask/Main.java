package StudentTask;

public class Main {
    public static void main(String[] args) {
        Student student1 = new Student("John",123409);
        Student student2 = new Student("Mark",123409);

        Group group = new Group(student1,student2);
        System.out.println(group.student2.getName());
    }
}
