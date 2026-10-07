public class ExpeditionMember {
    private String name;
    private int studentID;
    public static int member_counter = 0;

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ExpeditionMember(String name, int studentID) {
        member_counter++;
        this.name = name;
        this.studentID = studentID;
    }
}
