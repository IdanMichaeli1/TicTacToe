package TicTacToe;

import java.awt.event.*;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 * Game class of Tic-Tac-Toe to run the game.
 */
class Game implements ActionListener{

    Board board = new Board();
    Player playerX;
    Player playerO;
    Random random = new Random();
    boolean playerXTurn;    //true if it's playerX's turn and false if it's playerO's turn.
    boolean isTwoPlayers;   //true if the game is player vs player and false if player vs computer 

    /**
     * Constructor for the Game class.
     */
    public Game() {

        //Add actionListner to the buttons
        for (int i = 0; i < board.NUMBER_OF_BUTTONS; i++) {
            board.buttons[i].addActionListener(this);
        }
        board.resetButton.addActionListener(this);

        //Choose type of game to play (i.e 1 player or 2 players)
        String[] options = { "1 Player", "2 Players" };
        String answer = (String) JOptionPane.showInputDialog(board.frame, "Choose a type of game to play", "Type of game", JOptionPane.PLAIN_MESSAGE, null, options, "1 Player");
        if (answer == null) {
            board.frame.dispose();
            return;
        }
        else if (answer.equals(options[1])) {
            isTwoPlayers = true;
            this.playerX = new Player("X", true);
            this.playerO = new Player("O", true);
        }
        //if the game is 1 player choose randomally if the human player is "X" or "O"
        else if (random.nextBoolean()) {
            this.playerX = new Player("X", true);
            this.playerO = new Player("O", false);
        }
        else {
            this.playerX = new Player("X", false);
            this.playerO = new Player("O", true);
        }

        //Choose randomly the player to play the first turn.
        sleep(500);
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
        //Clicking on the "New Game" button will close the current window and open a new one with a new game.
        if (e.getSource() == board.resetButton) {
            board.frame.dispose();
            Game newGame = new Game();
            return;
        }
        //Clicking on any button from the Tic-Tac-Toe board sets it to "X" or "O" according to the type of game and the player's turn.
        for (int i = 0; i < board.NUMBER_OF_BUTTONS; i++) {
            if (e.getSource() == board.buttons[i]) {
                //if the two players are humans
                if (isTwoPlayers) {
                    twoPlayersGameplay(board, i);
                }
                //if playerX is human and playerO is ai
                else if (playerXTurn && playerX.isHuman) {
                    onePlayerGameplay(board, playerX, playerO, i);
                }
                //if playerO is human and playerX is ai
                else if (!playerXTurn && playerO.isHuman) {
                    onePlayerGameplay(board, playerO, playerX, i);
                }
            }
        }
    }

    /**
     * Puts a mark on the board based on the button that was clicked by either one of the two players.
     * @param board the Tic-Tac-Toe board.
     * @param index the index that was chosen to put the mark.
     */
    private void twoPlayersGameplay(Board board, int index) {
        if (playerXTurn) {
            //if the button that was clicked is empty
            if (board.buttons[index].getText().isEmpty()) {
                playerX.makeTurn(board, index);
            }
        }
        else if (board.buttons[index].getText().isEmpty()) {
            playerO.makeTurn(board, index);
        }
        nextTurn();
        board.check();
    }

    /**
     * Puts the human player's mark on the board based on the button that was clicked and/or puts the opponent's mark
     * based on the AI's algorithm.
     * @param board the Tic-Tac-Toe board.
     * @param human the human player.
     * @param ai the computer player.
     * @param index the index that was chosen to put the mark for the human player.
     */
    private void onePlayerGameplay(Board board, Player human, Player ai, int index) {
        //if the button that was clicked is empty and there is no winner yet play the human's turn
        if (board.buttons[index].getText().isEmpty() && board.check().isEmpty()) {
            human.makeTurn(board, index);
            nextTurn();
            //if the board isn't full and there is no winner yet, play the AI's turn
            if (board.check().isEmpty() && board.isAvailable()) {
                aiTurn(board, ai);
            }
        }
    }

    /**
     * Makes the AI's turn.
     * @param board the Tic-Tac-Toe board.
     * @param player the ai player.
     */
    private void aiTurn(Board board, Player player) {
        player.makeTurn(board, player.bestMoveIndex(board));
        nextTurn();
        board.check();
    }

    /**
     * Update the next player turn.
     */
    private void nextTurn() {
        board.isGame = true;
        if (playerXTurn) {
            playerXTurn = false;
        }
        else {
            playerXTurn = true;
        }
    }

    /**
     * halt the program to a requested period of time.
     * @param millis the requested time to halt the programs in milliseconds.
     */
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