package com.example.knightsproblem2;

import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.util.List;

public class AnimatedTourBoard extends StackPane {

    private static final Color LIGHT = Color.web("#f0d9b5");
    private static final Color DARK = Color.web("#3d6b4f");

    private static final Color VISITED = Color.web("#e67e22");
    private static final Color CURRENT = Color.web("#2980b9");

    private final int n;
    private final double cell;
    private final double gap = 2;
    private final Rectangle[][] squares;
    private final StackPane knight;
    private SequentialTransition anim;

    public AnimatedTourBoard(int n, double maxSize) {
        this.n = n;

        double size = Math.max(160, maxSize);
        this.cell = Math.floor((size - (n - 1) * gap) / n);
        this.squares = new Rectangle[n][n];

        double side = n * cell + (n - 1) * gap;

        GridPane grid = new GridPane();
        grid.setHgap(gap);
        grid.setVgap(gap);

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                Rectangle sq = new Rectangle(cell, cell);
                if ((r + c) % 2 == 1) {
                    sq.setFill(DARK);
                } else {
                    sq.setFill(LIGHT);
                }
                squares[r][c] = sq;
                grid.add(sq, c, r);
            }
        }

        Pane overlay = new Pane();
        overlay.setPrefSize(side, side);
        overlay.setMinSize(side, side);
        overlay.setMaxSize(side, side);
        overlay.setMouseTransparent(true);

        knight = makeKnight();
        knight.setVisible(false);
        overlay.getChildren().add(knight);

        StackPane stack = new StackPane(grid, overlay);
        stack.setAlignment(Pos.CENTER);
        stack.setPrefSize(side, side);
        stack.setMinSize(side, side);
        stack.setMaxSize(side, side);

        getChildren().add(stack);
        setAlignment(Pos.CENTER);
        setPrefSize(side, side);
        setMinSize(side, side);
        setMaxSize(side, side);
    }

    private StackPane makeKnight() {
        double s = Math.max(18, cell * 0.78);
        Circle bg = new Circle(s / 2);
        bg.setFill(Color.web("#fdf6e3"));
        bg.setStroke(Color.BLACK);
        bg.setStrokeWidth(1.5);

        Label icon = new Label("♞");
        icon.setFont(Font.font("System", FontWeight.BOLD, Math.max(12, s * 0.72)));
        icon.setTextFill(Color.BLACK);

        StackPane piece = new StackPane(bg, icon);
        piece.setPrefSize(s, s);
        piece.setMinSize(s, s);
        piece.setMaxSize(s, s);
        return piece;
    }

    private void resetBoard() {
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                squares[r][c].setFill((r + c) % 2 == 1 ? DARK : LIGHT);
            }
        }
    }

    public void play(List<int[]> path) {
        stop();
        resetBoard();

        if (path == null || path.isEmpty()) {
            knight.setVisible(false);
            return;
        }

        int[] start = path.get(0);
        moveKnightTo(start[0], start[1]);
        knight.setVisible(true);
        squares[start[0]][start[1]].setFill(CURRENT);

        if (path.size() == 1) {
            squares[start[0]][start[1]].setFill(VISITED);
            return;
        }

        double ms = Math.max(200, Math.min(450, 10000.0 / path.size()));
        SequentialTransition seq = new SequentialTransition();

        for (int i = 0; i < path.size() - 1; i++) {
            int[] from = path.get(i);
            int[] to = path.get(i + 1);
            int fr = from[0], fc = from[1];
            int tr = to[0], tc = to[1];

            TranslateTransition slide = new TranslateTransition(Duration.millis(ms), knight);
            slide.setFromX(posX(fc));
            slide.setFromY(posY(fr));
            slide.setToX(posX(tc));
            slide.setToY(posY(tr));
            slide.setInterpolator(Interpolator.EASE_BOTH);

            ScaleTransition up = new ScaleTransition(Duration.millis(ms / 2), knight);
            up.setToX(1.2);
            up.setToY(1.2);
            ScaleTransition down = new ScaleTransition(Duration.millis(ms / 2), knight);
            down.setToX(1);
            down.setToY(1);

            ParallelTransition jump = new ParallelTransition(
                    slide, new SequentialTransition(up, down));

            boolean last = (i == path.size() - 2);
            jump.setOnFinished(e -> {
                squares[fr][fc].setFill(VISITED);
                squares[tr][tc].setFill(CURRENT);
                if (last) {
                    squares[tr][tc].setFill(VISITED);
                }
            });

            seq.getChildren().add(jump);
            seq.getChildren().add(new PauseTransition(Duration.millis(40)));
        }

        anim = seq;
        applyCss();
        layout();
        moveKnightTo(start[0], start[1]);
        seq.play();
    }

    public void showAll(List<int[]> path) {
        stop();
        resetBoard();
        if (path == null || path.isEmpty()) {
            knight.setVisible(false);
            return;
        }
        for (int[] sq : path) {
            squares[sq[0]][sq[1]].setFill(VISITED);
        }
        int[] end = path.get(path.size() - 1);
        moveKnightTo(end[0], end[1]);
        knight.setVisible(true);
    }

    public void stop() {
        if (anim != null) {
            anim.stop();
            anim = null;
        }
    }

    private void moveKnightTo(int row, int col) {
        knight.setTranslateX(posX(col));
        knight.setTranslateY(posY(row));
    }

    private double posX(int col) {
        return col * (cell + gap) + (cell - knight.getPrefWidth()) / 2;
    }

    private double posY(int row) {
        return row * (cell + gap) + (cell - knight.getPrefHeight()) / 2;
    }
}
