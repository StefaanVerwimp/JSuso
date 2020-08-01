package JSuso;

import java.util.Arrays;

public class Sudoku {

    public int[][] grid;

    public Sudoku(int[][] grid){
        this.grid = grid;
    }

    // Prints out sudoku board to the terminal
    public void printSudoku(){
        int num_rows = 0;
        for (int i = 0; i < 9; i++) {
            if (num_rows%3 == 0) {
                System.out.println("-------------------------");
            }
            System.out.println("| " + this.grid[i][0] + " " + this.grid[i][1] + " " + this.grid[i][2] + " | " +
                    this.grid[i][3] + " " + this.grid[i][4] + " " + this.grid[i][5] + " | " +
                    this.grid[i][6] + " " + this.grid[i][7] + " " + this.grid[i][8] + " |");
            num_rows++;
        }
        System.out.println("-------------------------");
    }

    // Recursive backtracking
    public boolean solve() {
        // Find the first empty space on the board (searches left to right, top to bottom)
        int[] empty = this.find_empty();
        // If no empty positions are found, then the sudoku is solved
        if (empty == null) {
            return true;
        }
        // Test numbers 1 to 9 in empty position
        for (int i = 1; i < 10; i++){
            // Check if number is a valid input in the empty position
            if (this.check_safe(i, empty)) {
                // If input is valid, then fill in that number
                this.grid[empty[0]][empty[1]] = i;

                // Start solve() again
                if (this.solve()){
                    return true;
                }

                // If none of the numbers are a valid input, then solve() will return false, previously filled in
                // position is set to 0, the rest of the numbers are checked for this previous position, etc.
                // Backtracks as much as it needs to in order to find the solution given the constraints
                this.grid[empty[0]][empty[1]] = 0;
            }
        }
        return false;
    }

    // Find empty spaces on the board
    // Returns array with coordinate of empty space (row, column), or null if no empty spaces are found
    public int[] find_empty() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (this.grid[i][j] == 0) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    // Checks if a number is considered a valid input in a given position (see game rules sudoku for further explanation)
    // Returns true if input is valid, else returns false
    public boolean check_safe(int number, int[] position) {

        // Check row (position[0])
        for (int i = 0; i < 9; i++) {
            if (this.grid[position[0]][i] == number){
                return false;
            }
        }

        // Check column (position[1])
        for (int j = 0; j < 9; j++) {
            if (this.grid[j][position[1]] == number){
                return false;
            }
        }

        // Check 3x3 box
        int row_box = position[0] / 3;
        int col_box = position[1] / 3;
        for (int i = row_box*3; i < (row_box*3 + 3); i++) {
            for (int j = col_box*3; j < (col_box*3 + 3); j++) {
                int[] compare = new int[] {i, j};
                if (this.grid[i][j] == number && !Arrays.equals(compare, position)){
                    return false;
                }
            }
        }
        // If input is valid, returns true
        return true;
    }

    public static void main(String[] args) {
        Sudoku suso = new Sudoku(new int[][]{
                {2, 9, 0, 0, 0, 0, 6, 0, 5},
                {0, 0, 0, 6, 4, 3, 0, 0, 0},
                {6, 1, 0, 0, 0, 0, 3, 8, 0},
                {0, 0, 0, 0, 5, 0, 0, 7, 6},
                {0, 3, 6, 0, 7, 0, 0, 2, 9},
                {0, 0, 7, 0, 0, 6, 0, 3, 8},
                {0, 2, 0, 9, 0, 5, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 8, 0, 0},
                {4, 6, 5, 2, 8, 7, 0, 0, 0}});

        Sudoku ai_2012 = new Sudoku(new int[][]{
                {8, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 3, 6, 0, 0, 0, 0, 0},
                {0, 7, 0, 0, 9, 0, 2, 0, 0},
                {0, 5, 0, 0, 0, 7, 0, 0, 0},
                {0, 0, 0, 0, 4, 5, 7, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 3, 0},
                {0, 0, 1, 0, 0, 0, 0, 6, 8},
                {0, 0, 8, 5, 0, 0, 0, 1, 0},
                {0, 9, 0, 0, 0, 0, 4, 0, 0}
        });

        Sudoku ai_escargot = new Sudoku(new int[][]{
                {1, 0, 0, 0, 0, 7, 0, 9, 0},
                {0, 3, 0, 0, 2, 0, 0, 0, 8},
                {0, 0, 9, 6, 0, 0, 5, 0, 0},
                {0, 0, 5, 3, 0, 0, 9, 0, 0},
                {0, 1, 0, 0, 8, 0, 0, 0, 2},
                {6, 0, 0, 0, 0, 4, 0, 0, 0},
                {3, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 4, 0, 0, 0, 0, 0, 0, 7},
                {0, 0, 7, 0, 0, 0, 3, 0, 0}
        });

        Sudoku clue_17 = new Sudoku(new int[][]{
                {0, 0, 0, 7, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 4, 3, 0, 2, 0, 0},
                {0, 0, 0, 0, 0, 0,0 , 0, 6},
                {0, 0, 0, 5, 0, 9, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 4, 1, 8},
                {0, 0, 0, 0, 8, 1, 0, 0, 0},
                {0, 0, 2, 0, 0, 0, 0, 5, 0},
                {0, 4, 0, 0, 0, 0, 3, 0, 0}
        });

        long start = System.nanoTime();
        ai_2012.solve();
        long stop = System.nanoTime();
        ai_2012.printSudoku();
        System.out.println((float)(stop - start)/1_000_000_000);  // time to solve in seconds

    }

}
