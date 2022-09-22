package TicTacToe;

/**
 * Player class represents a player in Tic-Tac-Toe game.
 */
public class Player {

    String mark;
    String oponnentMark;
    boolean isHuman;
    
    /**
     * Constructor for the player class.
     * @param mark the mark of the player ("X" or "O")
     * @param isHuman is the player is human or AI player.
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
     * Draw the player's mark on the board in the requested spot.
     * @param board the Tic-Tac-Toe board.
     * @param index the index to put the mark in the array of buttons.
     */
    public void makeTurn(Board board, int index) {
        board.buttons[index].setText(mark);
        board.label.setText(oponnentMark + " Turn");
    }

    /**
     * Returns the best move index using the minimax method.
     * @return the best move index.
     */
    public int bestMoveIndex(Board board) {
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
     * @param isMax is the player is maximizing or minimizig.
     * @param depth the depth of the game. i.e the amount of plays that has been made.
     * @param alpha the maximizing player's best option.
     * @param beta the minimizing player'sbest option.
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
     * evaluation of the board.
     * @param board the Tic-Tac-Toe board.
     * @return 10 if the winner is the field "mark" and -10 if the winner is the "oponnentMark" and 0 otherwise.
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