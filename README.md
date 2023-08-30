# Tic-Tac-Toe Game

A simple implementation of the classic Tic-Tac-Toe game with a graphical user interface (GUI). The game supports both two-player mode and single-player mode against an AI opponent that uses the minimax algorithm for decision-making.

## Getting Started

These instructions will help you compile and run the Tic-Tac-Toe game on your local machine.

### Prerequisites

To run the game, you'll need to have Java Development Kit (JDK) installed on your machine.

### Running the Game

1. Open your terminal/command prompt.
2. Navigate to the project directory.
3. Compile the Java source files using the following command:

    ```
    javac TicTacToe/*.java
    ```

4. Run the game using the following command:

    ```
    java TicTacToe.Game
    ```

## Gameplay

The game offers the following features:

- Two-player mode: Play against a friend.
- Single-player mode: Play against an AI opponent using the minimax algorithm.
- Graphical user interface with clickable buttons to make moves.
- Players can choose to start a new game at any time.

## Code Structure

The project consists of the following Java files:

- `Board.java`: Represents the Tic-Tac-Toe game board and its graphical user interface.
- `Player.java`: Defines the player class, including the AI player using the minimax algorithm.
- `Game.java`: Main class to run the game, manage player turns, and handle interactions.

## How to Play

1. Choose a type of game to play: single player or two players.
2. Click on the buttons to place your mark ("X" or "O") on the board.
3. The game will announce the winner when a player wins or declare a tie when no more moves are available.
4. Click the "New Game" button to start a new game.

## Acknowledgments

- The project was inspired by the classic Tic-Tac-Toe game.
- The minimax algorithm implementation for the AI opponent was based on common AI principles.
