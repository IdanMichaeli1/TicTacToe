package TicTacToe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;

/**
 * Game class of Tic-Tac-Toe to run the game.
 */
class Game implements ActionListener {

    private Board board = new Board();
    private Player playerX;
    private Player playerO;
    private Random random = new Random();
    private boolean playerXTurn; // true if it's playerX's turn and false if it's playerO's turn.
    private boolean isTwoPlayers; // true if the game is 2 players and false if it's single player.

    /**
     * Constructor of the Game class.
     */
    public Game() {

        // add actionListner to the buttons
        for (int i = 0; i < Board.NUMBER_OF_BUTTONS; i++) {
            board.buttons[i].addActionListener(this);
        }
        board.resetButton.addActionListener(this);

        // choose a type of game to play (i.e single player or 2 players)
        String[] options = { "Single Player", "2 Players" };
        String answer = (String) JOptionPane.showInputDialog(board.frame, "Choose a type of game to play:",
                "Tic-Tac-Toe", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if (answer == null) {
            board.frame.dispose();
            return;
        } else if (answer.equals(options[1])) {
            isTwoPlayers = true;
            this.playerX = new Player("X", true);
            this.playerO = new Player("O", true);
        }
        // if the game is a single player choose randomally if the human player is "X"
        // or "O"
        else if (random.nextBoolean()) {
            this.playerX = new Player("X", true);
            this.playerO = new Player("O", false);
        } else {
            this.playerX = new Player("X", false);
            this.playerO = new Player("O", true);
        }

        // play the first turn of the game
        firstTurn();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // clicking on the "New Game" button
        if (e.getSource() == board.resetButton) {
            board.frame.dispose();
            new Game();
            return;
        }
        // set the clicked button to "X" or "O" according to the type of game and the
        // player's turn.
        for (int i = 0; i < Board.NUMBER_OF_BUTTONS; i++) {
            if (e.getSource() == board.buttons[i]) {
                if (isTwoPlayers) {
                    twoPlayersGameplay(board, i);
                } else if (playerXTurn && playerX.isHuman()) {
                    onePlayerGameplay(board, playerX, playerO, i);
                } else if (!playerXTurn && playerO.isHuman()) {
                    onePlayerGameplay(board, playerO, playerX, i);
                }
            }
        }
    }

    /**
     * Two human players gameplay.
     * Puts a mark on the board based on the button that was clicked by either one
     * of the two players.
     * 
     * @param board the Tic-Tac-Toe board.
     * @param index the index that was chosen to put the mark.
     */
    private void twoPlayersGameplay(Board board, int index) {
        // playerX's turn
        if (playerXTurn) {
            if (board.buttons[index].getText().isEmpty()) {
                playerX.makeTurn(board, index);
                nextTurn();
                board.check();
            }
        }
        // playerO's turn
        else if (board.buttons[index].getText().isEmpty()) {
            playerO.makeTurn(board, index);
            nextTurn();
            board.check();
        }
    }

    /**
     * One human player gameplay.
     * Puts the human player's mark on the board based on the button that was
     * clicked and/or
     * puts the opponent's mark based on the AI's algorithm.
     * 
     * @param board the Tic-Tac-Toe board.
     * @param human the human player.
     * @param ai    the computer player.
     * @param index the index that was chosen to put the mark by the human player.
     */
    private void onePlayerGameplay(Board board, Player human, Player ai, int index) {
        // play the human's turn
        if (board.buttons[index].getText().isEmpty() && board.check().isEmpty()) {
            human.makeTurn(board, index);
            nextTurn();
            // if the board isn't full and there is no winner yet, play the AI's turn
            if (board.check().isEmpty() && board.isAvailable()) {
                aiTurn(board, ai);
            }
        }
    }

    /**
     * Makes the AI's turn in a slight delay and adjust the game accordingly.
     * 
     * @param board  the Tic-Tac-Toe board.
     * @param player the AI player.
     */
    private void aiTurn(Board board, Player player) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                player.makeTurn(board, player.bestMoveIndex(board));
                nextTurn();
                board.check();
                timer.cancel();
            }

        }, 425);
    }

    /**
     * Update the next player turn.
     */
    private void nextTurn() {
        if (playerXTurn) {
            playerXTurn = false;
        } else {
            playerXTurn = true;
        }
    }

    /**
     * Chooses randomly the player to play the first turn,
     * if the player is AI player, also makes it's turn.
     */
    private void firstTurn() {
        if (random.nextBoolean()) {
            playerXTurn = true;
            board.label.setText("X Turn");
            if (!playerX.isHuman()) {
                aiTurn(board, playerX);
            }
        } else {
            board.label.setText("O Turn");
            if (!playerO.isHuman()) {
                aiTurn(board, playerO);
            }
        }
    }

    public static void main(String[] args) {
        new Game();
    }
}