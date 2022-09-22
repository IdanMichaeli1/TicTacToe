package TicTacToe;

import java.awt.event.*;
import java.util.Random;

/**
 * Game class of Tic-Tac-Toe to run the game.
 */
class Game implements ActionListener{

    Board board = new Board();
    Player playerX = new Player("X", true);
    Player playerO = new Player("O", false);
    Random random = new Random();
    boolean playerXTurn;     //true if it's playerX's turn and false if it's playerO's turn.

    /**
     * Constructor for the Game class.
     */
    public Game() {
        //Add actionListner to the buttons
        for (int i = 0; i < board.NUMBER_OF_BUTTONS; i++) {
            board.buttons[i].addActionListener(this);
        }
        board.resetButton.addActionListener(this);

        //Choose randomly the player to play the first turn.
        sleep(1200);
        if (random.nextBoolean()) {
            playerXTurn = true;
            board.label.setText("X Turn");
            if (!playerX.isHuman) {
                playerX.makeTurn(board, playerX.bestMoveIndex(board));
                nextTurn();
            }
        }
        else {
            board.label.setText("O Turn");
            if (!playerO.isHuman) {
                playerO.makeTurn(board, playerO.bestMoveIndex(board));
                nextTurn();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Clicking on the "New Button" will close the current window and open a new one with a new game.
        if (e.getSource() == board.resetButton) {
            board.frame.dispose();
            Game newGame = new Game();
            return;
        }
        //Clicking on any of the NUMBER_OF_BUTTONS button from the Tic-Tac-Toe board sets it to X according to the player's turn.
        for (int i = 0; i < board.NUMBER_OF_BUTTONS; i++) {
            if (e.getSource() == board.buttons[i]) {
                if (playerXTurn) {
                    //If the button that was clicked is empty and there is no winner yet play the player's turn.
                    if (board.buttons[i].getText().isEmpty() && board.check().isEmpty()) {
                        playerX.makeTurn(board, i);
                        nextTurn();
                        //If the board isn't full and there is no winner yet, play the AI's turn.
                        if (board.check().isEmpty() && board.isAvailable()) {
                            playerO.makeTurn(board, playerO.bestMoveIndex(board));
                            nextTurn();
                            board.check();
                        }
                    }
                }
            }
        }
        
    }

    /**
     * Update the next player turn.
     */
    public void nextTurn() {
        board.isGame = true;
        if (playerXTurn) {
            playerXTurn = false;
        }
        else {
            playerXTurn = true;
        }
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
    }
}