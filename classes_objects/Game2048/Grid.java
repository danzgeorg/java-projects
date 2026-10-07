package Game2048;

import java.util.ArrayList;
import java.util.List;

public class Grid {
    private final int[][] grid;
    private final int n;

    public Grid(int n) {
        this.grid = new int [n][n];
        this.n = n;
    }

    public int[][] getGrid() {
        return grid;
    }

    public Line getRowFromLine(int i) {
        Line rowLine = new Line(grid[i].length); // create Line object using the length of the i'th row.
        for (int j = 0; j < grid[i].length; j++) { // copies each number from i'th row into the Line array
            rowLine.getLine()[j] = grid[i][j];
        }
        return rowLine;
    }

    public Line getReverseRowFromLine(int i) {
        Line reverseRowLine = new Line(grid.length);
        for (int j = 0; j < grid.length; j++) {
            reverseRowLine.getLine()[j] = grid[i][grid.length - 1 - j]; //get i'th row in reverse
        }
        return reverseRowLine;
    }

    public Line getColumnFromLine(int i) {
        Line columnLine = new Line(grid.length);
        for (int j = 0; j < grid.length; j++) {
            columnLine.getLine()[j] = grid[j][i]; // copies each number from i'th column into the Line array
        }
        return columnLine;
    }
    public Line getReverseColumnFromLine(int i) {
        Line reverseColumnLine = new Line(grid.length);
        for (int j = 0; j < grid.length; j++) {
            reverseColumnLine.getLine()[j] = grid[grid.length - 1 - j][i]; //get i'th column in reverse
        }
        return reverseColumnLine;
    }

    public void playerUp(){ // if player goes up all suitable numbers are pushed up and merged in each column
        for (int i = 0; i < n; i++) {
            Line column = getColumnFromLine(i);
            pushMerge(column.getLine());
            setColumnFromLine(i, column.getLine());
        }
    }
    public void playerDown(){ // if playe goes down all suitable are pushed down and merged in each column
        for (int i = 0; i < n; i++) {
            Line column = getColumnFromLine(i);
            pushMerge(column.getLine());
            setReverseColumnFromLine(i, column.getLine());
        }
    }
    public void playerLeft(){ // if player goes left all suitable numbers are pushed left and merged in each row
        for (int i = 0; i < n; i++) {
            Line row = getRowFromLine(i);
            pushMerge(row.getLine());
            setRowFromLine(i, row.getLine());
        }
    }
    public void playerRight(){ // if player goes right ll suitable nmbers are pushed right and merged in each row
        for (int i = 0; i < n; i++) {
            Line row = getRowFromLine(i);
            pushMerge(row.getLine());
            setReverseRowFromLine(i, row.getLine());
        }
    }
    private void pushMerge(int[] line) {
        int[] temp = new int[line.length];
        int index = 0;
        // push non 0 elements to the front
        for (int j : line) {
            if (j != 0) {
                temp[index] = j;
                index++;
            }
        }
        // merge adjacent elements if they're the same
        for (int i = 0; i < temp.length - 1; i++) {
            if (temp[i] != 0 && temp[i] == temp[i + 1]) {
                temp[i] *= 2;
                temp[i + 1] = 0;
            }
        }
        // push non 0 elements to the front after merging
        index = 0;
        for (int j : temp) {
            if (j != 0) {
                line[index] = j;
                index++;
            }
        }
        // fill remaining elements with 0
        while (index < line.length) {
            line[index] = 0;
            index++;
        }
    }

    private void setRowFromLine(int i, int[] line){
        for (int j = 0; j < n; j++) {
            grid[i][j] = line[j];
        }
    }
    private void setColumnFromLine(int i, int[] line){
        for (int j = 0; j < n; j++) {
            grid[j][i] = line[j];
        }
    }
    private void setReverseRowFromLine(int i, int[] line){
        for (int j = 0; j < n; j++) {
            grid[i][n - 1 - j] = line[j];
        }
    }
    private void setReverseColumnFromLine(int i, int[] line){
        for (int j = 0; j < n; j++) {
            grid[n - 1 - j][i] = line[j];
        }
    }
    // method to add a 2 in a random coordinate
    public void random2(){
        List<int[]> empties = new ArrayList<>();
        // find the cells that have a 0
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    empties.add(new int[]{i, j});
                }
            }
        }

        if (!empties.isEmpty()) {
            int m = empties.size(); // stores amount of empty spces
            int r = (int)(Math.random() * m) ; // generates a random integer r
            int[] position = empties.get(r); // retrieves the randomyl selected cell's corrdinates
            grid[position[0]][position[1]] = 2; // places a 2 in the randomly slected cell - position[0] being row and position[1] being column
        }
    }

}
