package TicTacToe;

import java.awt.event.*;
import java.util.Random;

/**
 * Game
 */
class Game implements ActionListener{

    boolean playerTurn;     //true if it's the player's turn and false if it's the AI's turn.
    Board board = new Board();
    Player playerX = new Player("X", true);
    Player playerO = new Player("O", false);

    public Game() {
        
        //choose randomly the player to play the first turn.
        Random random = new Random();
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (random.nextBoolean()) {
            playerTurn = true;
            board.label.setText("X Turn");
        }
        else {
            board.label.setText("O Turn");
            playerO.makeTurn(board, playerO.bestMoveIndex(board));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        
    }

    public static void main(String[] args) {
        Game game = new Game();
    }

    
}