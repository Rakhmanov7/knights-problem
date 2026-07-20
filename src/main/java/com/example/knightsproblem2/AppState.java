package com.example.knightsproblem2;

import java.util.List;

public class AppState {

    public static int boardSize = 8;
    public static int startRow = 0;
    public static int startCol = 0;
    public static int[][] board;
    public static List<int[]> path;
    public static String message = "";

    public static boolean solve(int n, int row, int col) {
        boardSize = n;
        startRow = row;
        startCol = col;
        board = null;
        path = null;

        if (n != 1 && n < 5) {
            message = "No tour exists on a " + n + "x" + n + " board.";
            return false;
        }

        KnightTourSolver.Result result = KnightTourSolver.solve(n, row, col);
        if (result == null) {
            message = "Couldn't find a tour from (" + row + ", " + col + ").";
            return false;
        }

        board = result.board;
        path = result.path;
        startRow = result.startRow;
        startCol = result.startCol;

        if (result.startRow != row || result.startCol != col) {
            message = "Couldn't start at (" + row + ", " + col + "), "
                    + "used (" + result.startRow + ", " + result.startCol + ") instead.";
        } else {
            message = "Found a tour! " + (n * n) + " squares from (" + row + ", " + col + ").";
        }
        return true;
    }
}
