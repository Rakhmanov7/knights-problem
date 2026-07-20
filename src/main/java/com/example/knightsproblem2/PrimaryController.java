package com.example.knightsproblem2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class PrimaryController {

    @FXML private TextField tfDim;
    @FXML private TextField tfStartRow;
    @FXML private TextField tfStartCol;
    @FXML private Label lblStatus;

    @FXML
    private void initialize() {
        tfDim.setText(String.valueOf(AppState.boardSize));
        tfStartRow.setText(String.valueOf(AppState.startRow));
        tfStartCol.setText(String.valueOf(AppState.startCol));
        if (!AppState.message.isEmpty()) {
            lblStatus.setText(AppState.message);
        }
    }

    @FXML
    private void solveTour() throws IOException {
        int n, row, col;

        try {
            n = Integer.parseInt(tfDim.getText().trim());
            row = Integer.parseInt(tfStartRow.getText().trim());
            col = Integer.parseInt(tfStartCol.getText().trim());
        } catch (Exception e) {
            lblStatus.setText("Please enter valid numbers.");
            return;
        }

        if (n < 1 || n > 20) {
            lblStatus.setText("Board size should be between 1 and 20.");
            return;
        }
        if (row < 0 || col < 0 || row >= n || col >= n) {
            lblStatus.setText("Start square has to be on the board (0 to " + (n - 1) + ").");
            return;
        }

        lblStatus.setText("Solving...");
        boolean ok = AppState.solve(n, row, col);
        lblStatus.setText(AppState.message);

        if (ok || !KnightTourSolver.possible(n)) {
            App.setRoot("secondary");
        }
    }
}
