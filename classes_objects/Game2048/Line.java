package Game2048;

public class Line {
    private final int n;
    private final int[] line;

    public Line(int n) {
        this.line = new int[n];
        this.n = n;
    }

    public void updateLine(int number){
        for (int i = 0; i < n; i++) {
            line[i] = number;
        }
    }

    public int[] getLine() {
        return line;
    }
}

