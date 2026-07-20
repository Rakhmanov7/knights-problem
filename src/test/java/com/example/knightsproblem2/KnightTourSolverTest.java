package com.example.knightsproblem2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KnightTourSolverTest {

    @Test
    void noTourOnSmallBoards() {
        assertFalse(KnightTourSolver.possible(2));
        assertFalse(KnightTourSolver.possible(3));
        assertFalse(KnightTourSolver.possible(4));
        assertNull(KnightTourSolver.solve(2, 0, 0));
        assertNull(KnightTourSolver.solve(4, 0, 0));
    }

    @Test
    void findsTourOnNormalBoards() {

        int[] sizes = {1, 5, 6, 8};
        for (int n : sizes) {
            KnightTourSolver.Result r = KnightTourSolver.solve(n, 0, 0);
            assertNotNull(r, "should find a tour for " + n + "x" + n);
            assertTrue(checkTour(r.board), "tour looks wrong for " + n);
        }
    }

    private boolean checkTour(int[][] board) {
        int n = board.length;
        boolean[] used = new boolean[n * n + 1];
        int[] dr = {-2, -2, -1, -1, 1, 1, 2, 2};
        int[] dc = {-1, 1, -2, 2, -2, 2, -1, 1};
        int[][] pos = new int[n * n + 1][];

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                int m = board[r][c];
                if (m < 1 || m > n * n || used[m]) return false;
                used[m] = true;
                pos[m] = new int[]{r, c};
            }
        }

        for (int m = 1; m < n * n; m++) {
            int r = pos[m][0];
            int c = pos[m][1];
            int nr = pos[m + 1][0];
            int nc = pos[m + 1][1];
            boolean ok = false;
            for (int i = 0; i < 8; i++) {
                if (r + dr[i] == nr && c + dc[i] == nc) {
                    ok = true;
                    break;
                }
            }
            if (!ok) return false;
        }
        return true;
    }
}
