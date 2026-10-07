package Game2048;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Grid grid = new Grid(4); // create a 4x4 grid for the game
        // initialises 2 2's in random coordinates
        grid.random2();
        grid.random2();

        System.out.println("Welcome to 2048.");
        printGrid(grid.getGrid());

        while (true) {
            System.out.print("Type up, down, left, or right to start: ");
            String lowerCase = input.nextLine().trim().toLowerCase();
            int totalMoves = 0;

            switch (lowerCase) {
                case "up":
                    grid.playerUp();
                    totalMoves++;
                    break;
                case "down":
                    grid.playerDown();
                    totalMoves++;
                    break;
                case "left":
                    grid.playerLeft();
                    totalMoves++;
                    break;
                case "right":
                    grid.playerRight();
                    totalMoves++;
                    break;
                default:
                    System.out.println("Invalid move. Type up, down, left, right.");
                    continue;
            }

            grid.random2(); // adds a random 2 after moving
            printGrid(grid.getGrid());

            if (isGameWon(grid.getGrid())) {
                System.out.printf("Good job! You won with %d  moves.",totalMoves);
                break;
            } else if (!isMovePossible(grid.getGrid())) {
                System.out.printf("Game over! No more moves possible. You made %d ", totalMoves);
                break;
            }
        }

        input.close();
    }

    private static void printGrid(int[][] grid) {
        for (int[] cells : grid) {
            for (int cell : cells) {
                System.out.print(cell + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static boolean isGameWon(int[][] grid) {
        for (int[] cells : grid) {
            for (int cell : cells) {
                if (cell == 2048) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isMovePossible(int[][] grid) {
        // check for empty cells
        for (int[] cells : grid) {
            for (int cell : cells) {
                if (cell == 0) {
                    return true;
                }
            }
        }

        // check for possible merges in rows and columns
        for (int[] cells : grid) {
            for (int index = 0; index < cells.length - 1; index++) {
                if (cells[index] == cells[index + 1]) {
                    return true;
                }
            }
        }

        for (int j = 0; j < grid.length; j++) {
            for (int i = 0; i < grid.length - 1; i++) {
                if (grid[i][j] == grid[i + 1][j]) {
                    return true;
                }
            }
        }

        return false;
    }
}