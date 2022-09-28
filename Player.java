package TicTacToe;

import java.util.Random;

/**
 * Player class represents a player in Tic-Tac-Toe game.
 */
public class Player {

    String mark;
    String oponnentMark;
    boolean isHuman;
    
    /**
     * Constructor of the player class.
     * @param mark the mark of the player ("X" or "O")
     * @param isHuman true if the player is human and false if the player is AI player.
     */
    public Player(String mark, boolean isHuman) {
        this.mark = mark;
        this.isHuman = isHuman;
        if (mark.equals("X")) {
            this.oponnentMark = "O";
        }
        else {
            this.oponnentMark = "X";
        }
    }

    /**
     * Put this mark on the board in the requested spot.
     * @param board the Tic-Tac-Toe board.
     * @param index the index to put the mark on the board.
     */
    public void makeTurn(Board board, int index) {
        board.buttons[index].setText(mark);
        board.label.setText(oponnentMark + " Turn");
    }

    /**
     * Returns the best move index for this mark using the minimax algorithm method.
     * @param board a Tic-Tac-Toe board.
     * @return the best move index.
     */
    public int bestMoveIndex(Board board) {
        //if it's the first turn in the game, randomlly choose one of the corners to make it more interesting (otherwise the top left corner will be chosen evry game)
        if (board.isEmpty()) {
            Random random = new Random();
            int num = random.nextInt(4);
            if (num == 0) return 0;     //top-left corner
            if (num == 1) return 2;     //top-right corner
            if (num == 2) return 6;     //bottom-left corner
            if (num == 3) return 8;     //bottom-right corner
        }
        //if this is not the first turn in the game
        int bestMoveValue = Integer.MIN_VALUE;
        int bestMove = -1;
        for (int i = 0; i < Board.NUMBER_OF_BUTTONS; i++) {
            if (board.buttons[i].getText().isEmpty()) {
                board.buttons[i].setText(mark);   
                int moveValue = minimax(board, false, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
                board.buttons[i].setText("");
                if (moveValue > bestMoveValue) {
                    bestMoveValue = moveValue;
                    bestMove = i;
                }
            }
        }
        return bestMove;
    }

    /**
     * Minimax algorithm using alpha-beta pruning, A recursive function that considers all
     * the possible ways the game can go and returns the value of the traversed path based on the evaluate method.
     * @param board the Tic-Tac-Toe board.
     * @param isMax is the player is maximizing or minimizig.
     * @param depth the depth of the game. i.e the amount of plays that has been made in the game.
     * @param alpha the maximizing player's best option.
     * @param beta the minimizing player's best option.
     * @return the value of the traversed path untill the last move.
     */
    private int minimax(Board board, boolean isMax, int depth, int alpha, int beta) {
        board.isGame = false;     //to distinguish from the actual game
        int score = evaluate(board);
        if (score == 10) {
            return 10 - depth;
        }
        if (score == -10) {
            return -10 + depth;
        }
        if (!board.isAvailable()) {
            return 0;
        }
        //if it's the maximaizing player's turn
        if (isMax) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < Board.NUMBER_OF_BUTTONS; i++) {
                if (board.buttons[i].getText().isEmpty()) {
                    board.buttons[i].setText(mark);
                    int minimaxScore = minimax(board, false, depth + 1, alpha, beta);
                    bestScore = Math.max(bestScore, minimaxScore);
                    board.buttons[i].setText("");
                    alpha = Math.max(alpha, minimaxScore);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return bestScore;
        }
        //if it's the minimizing player's turn
        else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < Board.NUMBER_OF_BUTTONS; i++) {
                if (board.buttons[i].getText().isEmpty()) {
                    board.buttons[i].setText(oponnentMark);
                    int minimaxScore = minimax(board, true, depth + 1, alpha, beta);
                    bestScore = Math.min(bestScore, minimaxScore);
                    board.buttons[i].setText("");
                    beta = Math.min(beta, minimaxScore);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return bestScore;
        }
    }

    /**
     * Evaluation of the board.
     * @param board the Tic-Tac-Toe board.
     * @return 10 if the winner is this mark or -10 if the winner is this oponnentMark and 0 otherwise.
     */
    private int evaluate(Board board) {
        if (board.check().equals(mark)) {
            return 10;
        }
        if (board.check().equals(oponnentMark)) {
            return -10;
        }
        return 0;
    }
}