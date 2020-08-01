package Tests;

import JSuso.Sudoku;
import org.junit.jupiter.api.Test;
import java.util.Arrays;


class SudokuTest {

    @Test
    void test(){
        Sudoku suso1 = new Sudoku(new int[][]{
                {2, 9, 0, 0, 0, 0, 6, 0, 5},
                {0, 0, 0, 6, 4, 3, 0, 0, 0},
                {6, 1, 0, 0, 0, 0, 3, 8, 0},
                {0, 0, 0, 0, 5, 0, 0, 7, 6},
                {0, 3, 6, 0, 7, 0, 0, 2, 9},
                {0, 0, 7, 0, 0, 6, 0, 3, 8},
                {0, 2, 0, 9, 0, 5, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 8, 0, 0},
                {4, 6, 5, 2, 8, 7, 0, 0, 0}});

        Sudoku ai_escargot_filledin = new Sudoku(new int[][]{
                {1, 6, 2, 8, 5, 7, 4, 9, 3},
                {5, 3, 4, 1, 2, 9, 6, 7, 8},
                {7, 8, 9, 6, 4, 3, 5, 2, 1},
                {4, 7, 5, 3, 1, 2, 9, 8, 6},
                {9, 1, 3, 5, 8, 6, 7, 4, 2},
                {6, 2, 8, 7, 9, 4, 1, 3, 5},
                {3, 5, 6, 4, 7, 8, 2, 1, 9},
                {2, 4, 1, 9, 3, 5, 8, 6, 7},
                {8, 9, 7, 2, 6, 1, 3, 5, 4}
        });


        // Check empty method
        int[] empty02 = new int[]{0, 2};
        assert Arrays.equals(suso1.find_empty(), empty02);
        assert ai_escargot_filledin.find_empty() == null;

        // Check safe
        assert !suso1.check_safe(2, empty02);
        assert suso1.check_safe(3, empty02);
        assert !suso1.check_safe(6, empty02);
        int[] empty64 = new int[]{6, 4};
        assert suso1.check_safe(1, empty64);
        assert !suso1.check_safe(2, empty64);

        // check Solve()
        Sudoku in = new Sudoku(new int[][]{
                {0, 0, 2, 5, 0, 0, 0, 7, 0},
                {0, 0, 4, 1, 6, 0, 0, 8, 0},
                {0, 5, 8, 0, 0, 0, 0, 4, 0},
                {0, 0, 0, 0, 2, 5, 0, 0, 3},
                {0, 0, 0, 0, 8, 1, 0, 0, 7},
                {1, 6, 0, 0, 0, 0, 0, 0, 0},
                {8, 0, 0, 0, 0, 7, 1, 0, 0},
                {0, 0, 9, 3, 0, 2, 7, 0, 0},
                {3, 0, 0, 0, 0, 0, 5, 0, 0}
        });
        Sudoku out = new Sudoku(new int[][]{
                {9, 1, 2, 5, 4, 8, 3, 7, 6},
                {7, 3, 4, 1, 6, 9, 2, 8, 5},
                {6, 5, 8, 2, 7, 3, 9, 4, 1},
                {4, 8, 7, 9, 2, 5, 6, 1, 3},
                {2, 9, 3, 6, 8, 1, 4, 5, 7},
                {1, 6, 5, 7, 3, 4, 8, 9, 2},
                {8, 2, 6, 4, 5, 7, 1, 3, 9},
                {5, 4, 9, 3, 1, 2, 7, 6, 8},
                {3, 7, 1, 8, 9, 6, 5, 2, 4}
        });
        in.solve();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assert in.grid[i][j] == out.grid[i][j];
            }
        }
    }



}