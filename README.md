# Knight's Tour Visualizer

A JavaFX desktop app that visualizes the [Knight's Tour problem](https://en.wikipedia.org/wiki/Knight%27s_tour) on a chessboard grid. Enter a board dimension and the app renders an `n x n` grid to explore the problem visually.

## Tech Stack

- **Java 24**
- **JavaFX** (controls + FXML) for the UI
- **Maven** for build and dependency management

## Prerequisites

- JDK 24 installed ([check with `java -version`](https://www.oracle.com/java/technologies/downloads/))
- No need to install Maven separately — this project includes the Maven Wrapper (`mvnw`)

## Running the App

Clone the repo and run it with the included Maven wrapper:

```bash
git clone https://github.com/Rakhmanov7/KnightsProblem2.git
cd KnightsProblem2
./mvnw clean javafx:run
```

On Windows:

```bash
mvnw.cmd clean javafx:run
```

This launches a window titled **"Knights Problem"**. Enter a board size (`n`) in the text field to generate an `n x n` grid.

## Project Structure

```
src/main/java/com/example/knightsproblem2/
├── App.java                 # JavaFX application entry point
├── Launcher.java            # Launcher class
└── primaryController.java   # Controller for the primary view / board drawing logic
```

## Status

🚧 Work in progress — board visualization is implemented; knight's tour pathfinding logic is planned next.

## License

No license specified yet.