package TicTacToe;

import java.awt.*;
import javax.swing.*;


/**
 * Board
 */
public class Board {

    static final int NUMBER_OF_BUTTONS = 9;
    
    JFrame frame = new JFrame();
    JLayeredPane layeredPane = new JLayeredPane();
    JPanel titlePanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JPanel resetButtonPanel = new JPanel();
    JLabel label = new JLabel();
    JButton[] buttons = new JButton[NUMBER_OF_BUTTONS];
    JButton resetButton = new JButton("New Game");
    boolean isGame;     //true if this is the actual game and false if the game is played by the minimax algo.

    /**
     * Constructor of the board
     */
    public Board() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(430, 550);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
        frame.setTitle("Tic-Tac-Toe");
        ImageIcon logo = new ImageIcon("logo.jpg");
        frame.setIconImage(logo.getImage());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        layeredPane.setLayout(new BorderLayout());
        layeredPane.setBounds(0, 0, 430, 550);
        
        label.setBackground(Color.white);
        label.setForeground(Color.black);
        label.setFont(new Font("Ink Free", Font.BOLD,50));
        label.setText("Tic-Tac-Toe");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setOpaque(true);
        
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBounds(0, 0, 430, 50);

        buttonPanel.setLayout(new GridLayout(3, 3, 10, 10));
        buttonPanel.setBackground(Color.black);
        for (int i = 0; i < NUMBER_OF_BUTTONS; i++) {
            buttons[i] = new JButton();
            buttonPanel.add(buttons[i]);
            buttons[i].setBackground(Color.white);
            buttons[i].setFont(new Font("Ink Free", Font.BOLD,70));
            buttons[i].setFocusable(false);
            //buttons[i].addActionListener(this);
        }

        titlePanel.add(label);
        layeredPane.add(titlePanel, BorderLayout.NORTH);
        layeredPane.add(buttonPanel);
        frame.add(layeredPane);
    }

    /**
     * Setup of the "New Game" button.
     */
    public void newGame() {
        for (int i = 0; i < NUMBER_OF_BUTTONS; i++) {
            buttons[i].setEnabled(false);
        }
        layeredPane.setLayout(null);
        resetButtonPanel.setLayout(new BorderLayout());
        resetButtonPanel.setBounds(153, 200, 110, 40);
        resetButtonPanel.add(resetButton);
        resetButton.setFocusable(false);
        //resetButton.addActionListener(this);
        layeredPane.add(resetButtonPanel, JLayeredPane.DRAG_LAYER);
    }

    /**
     * Checks if the game is over, that means if one of the players won the game or if it's a tie or neither, 
     * and return the value of the board based on the evaluate function.
     * @return the value of the board based on the evaluate function.
     */
    public int check() {
        //check horizontally for a winning combination
        for (int i = 0; i <= 6; i += 3){
            if (equal3(buttons[i], buttons[i + 1], buttons[i + 2])) {
                return evaluate(winner(i, i + 1, i + 2));
            }
        }

        //check vertically for a winning combination
        for (int j = 0; j < 3; j++) {
            if (equal3(buttons[j], buttons[j + 3], buttons[j + 6])) {
                return evaluate(winner(j, j + 3, j + 6));
            }
        }

        //check diagonally for a winning combination
        if (equal3(buttons[0], buttons[4], buttons[8])) {
            return evaluate(winner(0, 4, 8));
        }
        else if (equal3(buttons[2], buttons[4], buttons[6])){
            return evaluate(winner(2, 4, 6));
        }
        
        //check if the game is a tie
        else if (!isAvailable() && isGame) {
            label.setText("It's a Tie");
            newGame();
        }
        return 0;
    }

    /**
     * Checks if the Tic-Tac-Toe board is not full.
     * @return true if at least one of the buttons is available and false otherwise.
     */
    public boolean isAvailable() {
        for (int i = 0; i < NUMBER_OF_BUTTONS; i++) {
            if (buttons[i].getText().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * returns the winner as a String.
     * If the actual game is over (not in the minimax runs), declare the winner and
     * change the background
     * of the winning combination, also call to the newGame funcion.
     * @param x the index of the first button.
     * @param y the index of the second button.
     * @param z the index of the third button.
     * @return the winner as a String.
     */
    private String winner(int x, int y, int z) {
        if (isGame) {
            label.setText(buttons[x].getText() + " Wins!");
            buttons[x].setBackground(Color.green);
            buttons[y].setBackground(Color.green);
            buttons[z].setBackground(Color.green);
            newGame();
        }
        return buttons[x].getText();
    }

    /**
     * evaluation of the winning board based on it's winner.
     * @param winner the winner if there exist such one.
     * @return 10 if "O" is the winner, -10 if "X" is the winner.
     */
    private int evaluate(String winner) {
        if (winner == "O") {
            return 10;
        }
        else {
            return -10;
        }
    }

    /**
     * Checks if 3 buttons from the Tic-Tac-Toe board are the same and not empty.
     * @param x first button.
     * @param y second button.
     * @param z third button.
     * @return true if three buttons from the Tic-Tac-Toe board are the same and false otherwise.
     */
    private boolean equal3(JButton x, JButton y, JButton z) {
        return x.getText().equals(y.getText()) && x.getText().equals(z.getText()) && !x.getText().isEmpty();
    }

    
}