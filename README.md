# Knight's Tour

JavaFX project for the knight's tour problem — find a path where the knight visits every square on the board exactly once.

## How to run

```bash
./mvnw clean javafx:run
```

(Windows: `mvnw.cmd clean javafx:run`)

You need JDK 21+.

## How it works

1. Pick a board size `n` and a starting square
2. Hit **Solve**
3. The knight animates through the tour (visited squares turn orange)

I used Warnsdorff's rule for the search (always move to the square with the fewest options). For smaller boards it can also backtrack if needed.

**Heads up:** there is no tour on 2x2, 3x3, or 4x4 boards. 1x1 and 5x5+ are fine.

## Files

- `KnightTourSolver.java` – the actual algorithm
- `AnimatedTourBoard.java` – board + knight animation
- `PrimaryController` / `SecondaryController` – the two screens
- `AppState.java` – shares the solve result between screens

## License

None yet.
