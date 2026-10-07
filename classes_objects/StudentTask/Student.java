package StudentTask;

public class Student {
    private String name;
    private int IDnumber;
    private int[] marks;

    public void updateMarks(int i,int mark) {
        this.marks[i] = mark;
    }

    public int requiredMarks() {
        int count = 0;
        for (int mark : marks) {
            if (mark < 40) {
                count++;
            }
        }
        return count;
    }

    public Student(String name, int IDnumber) {
        this.name = name;
        this.IDnumber = IDnumber;
        this.marks = new int[10];
    }

    public String getName() {
        return name;
    }

    public int getIDnumber() {
        return IDnumber;
    }

    public int[] getMarks() {
        return marks;
    }

    public void setName(String name) {
        this.name = name;
    }
}
