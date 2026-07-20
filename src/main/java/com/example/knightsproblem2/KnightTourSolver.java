package com.example.knightsproblem2;

import java.util.ArrayList;
import java.util.List;

public class KnightTourSolver {

    private static final int[] dRow = {-2, -2, -1, -1, 1, 1, 2, 2};
    private static final int[] dCol = {-1, 1, -2, 2, -2, 2, -1, 1};

    public static class Result {
        public int[][] board;
        public List<int[]> path;
        public int startRow;
        public int startCol;

        public Result(int[][] board, List<int[]> path, int startRow, int startCol) {
            this.board = board;
            this.path = path;
            this.startRow = startRow;
            this.startCol = startCol;
        }
    }

    public static boolean possible(int n) {
        return n == 1 || n >= 5;
    }

    public static Result solve(int n, int startRow, int startCol) {
        if (n < 1 || startRow < 0 || startCol < 0 || startRow >= n || startCol >= n) {
            return null;
        }
        if (!possible(n)) {
            return null;
        }

        if (n == 1) {
            int[][] b = {{1}};
            List<int[]> p = new ArrayList<>();
            p.add(new int[]{0, 0});
            return new Result(b, p, 0, 0);
        }

        Result r = trySolve(n, startRow, startCol);
        if (r != null) {
            return r;
        }

        int[][] otherStarts = {
                {0, 0}, {0, n - 1}, {n - 1, 0}, {n - 1, n - 1},
                {0, 1}, {1, 0}, {n / 2, n / 2}
        };
        for (int[] s : otherStarts) {
            if (s[0] == startRow && s[1] == startCol) continue;
            if (s[0] < 0 || s[1] < 0 || s[0] >= n || s[1] >= n) continue;
            r = trySolve(n, s[0], s[1]);
            if (r != null) return r;
        }
        return null;
    }

    private static Result trySolve(int n, int sr, int sc) {
        int[][] board = new int[n][n];

        if (warnsdorff(board, sr, sc)) {
            return makeResult(board, sr, sc);
        }

        if (n <= 8) {
            board = new int[n][n];
            if (dfs(board, sr, sc, 1, n * n)) {
                return makeResult(board, sr, sc);
            }
        }
        return null;
    }

    private static boolean warnsdorff(int[][] board, int row, int col) {
        int n = board.length;
        int total = n * n;

        for (int move = 1; move <= total; move++) {
            board[row][col] = move;
            if (move == total) return true;

            List<int[]> next = getMovesSorted(board, row, col);
            if (next.isEmpty()) return false;

            row = next.get(0)[0];
            col = next.get(0)[1];
        }
        return false;
    }

    private static boolean dfs(int[][] board, int row, int col, int move, int total) {
        board[row][col] = move;
        if (move == total) return true;

        for (int[] m : getMovesSorted(board, row, col)) {
            if (dfs(board, m[0], m[1], move + 1, total)) {
                return true;
            }
        }

        board[row][col] = 0;
        return false;
    }

    private static List<int[]> getMovesSorted(int[][] board, int row, int col) {
        int n = board.length;
        List<int[]> list = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            int nr = row + dRow[i];
            int nc = col + dCol[i];
            if (nr >= 0 && nc >= 0 && nr < n && nc < n && board[nr][nc] == 0) {
                list.add(new int[]{nr, nc, countMoves(board, nr, nc)});
            }
        }

        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(j)[2] < list.get(i)[2]) {
                    int[] tmp = list.get(i);
                    list.set(i, list.get(j));
                    list.set(j, tmp);
                }
            }
        }
        return list;
    }

    private static int countMoves(int[][] board, int row, int col) {
        int n = board.length;
        int count = 0;
        for (int i = 0; i < 8; i++) {
            int nr = row + dRow[i];
            int nc = col + dCol[i];
            if (nr >= 0 && nc >= 0 && nr < n && nc < n && board[nr][nc] == 0) {
                count++;
            }
        }
        return count;
    }

    private static Result makeResult(int[][] board, int sr, int sc) {
        int n = board.length;

        int[][] where = new int[n * n + 1][];
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                where[board[r][c]] = new int[]{r, c};
            }
        }
        List<int[]> path = new ArrayList<>();
        for (int m = 1; m <= n * n; m++) {
            path.add(where[m]);
        }
        return new Result(board, path, sr, sc);
    }
}
