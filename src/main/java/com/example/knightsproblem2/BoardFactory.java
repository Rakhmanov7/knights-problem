package com.example.knightsproblem2;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BoardFactory {

    public static GridPane createBoard(int n, double maxSize) {
        double gap = 2;
        double cell = Math.floor((Math.max(160, maxSize) - (n - 1) * gap) / n);

        GridPane grid = new GridPane();
        grid.setHgap(gap);
        grid.setVgap(gap);

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                Rectangle sq = new Rectangle(cell, cell);
                if ((r + c) % 2 == 1) {
                    sq.setFill(Color.web("#3d6b4f"));
                } else {
                    sq.setFill(Color.web("#f0d9b5"));
                }
                grid.add(sq, c, r);
            }
        }
        return grid;
    }
}
