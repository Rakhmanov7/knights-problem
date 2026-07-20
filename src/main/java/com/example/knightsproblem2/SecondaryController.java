package com.example.knightsproblem2;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.List;

public class SecondaryController {

    @FXML private StackPane boardHost;
    @FXML private Label lblTitle;
    @FXML private Label lblStatus;

    private AnimatedTourBoard tourBoard;
    private List<int[]> path;
    private int n;

    @FXML
    private void initialize() {
        lblStatus.setText(AppState.message);
        n = AppState.boardSize;
        path = AppState.path;

        if (path == null) {
            lblTitle.setText(n + "x" + n + " – no tour");

            Platform.runLater(() -> {
                boardHost.getChildren().setAll(BoardFactory.createBoard(n, getBoardSize()));
            });
            return;
        }

        lblTitle.setText(n + "x" + n + " knight's tour");
        Platform.runLater(this::startAnimation);
    }

    private void startAnimation() {
        if (tourBoard != null) {
            tourBoard.stop();
        }
        tourBoard = new AnimatedTourBoard(n, getBoardSize());
        boardHost.getChildren().setAll(tourBoard);

        Platform.runLater(() -> tourBoard.play(path));
    }

    private double getBoardSize() {
        double w = boardHost.getWidth();
        double h = boardHost.getHeight();
        if (w <= 0 || h <= 0) return 360;
        return Math.min(w, h) - 16;
    }

    @FXML
    private void replay() {
        if (path == null) return;
        if (tourBoard == null) {
            startAnimation();
        } else {
            tourBoard.play(path);
        }
    }

    @FXML
    private void showAll() {
        if (path == null) return;
        if (tourBoard == null) {
            tourBoard = new AnimatedTourBoard(n, getBoardSize());
            boardHost.getChildren().setAll(tourBoard);
        }
        tourBoard.showAll(path);
    }

    @FXML
    private void goPrimary() throws IOException {
        if (tourBoard != null) tourBoard.stop();
        App.setRoot("primary");
    }
}
