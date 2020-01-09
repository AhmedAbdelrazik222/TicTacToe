import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

/**
 * A class modelling a tic-tac-toe (noughts and crosses, Xs and Os) game.
 * 
 * 
 * @author Lynn Marshall
 * @author Ahmed Abdelrazik
 * @version November 24, 2019
 */

public class TicTacToe implements ActionListener
{
    JButton[][] button; // 3X3 array of buttons
    private int numOccSquares; // number of Occupied Squares
    private boolean gameDone; // true if winner found
    private BorderLayout border; // Border layout for the content pane
    private JMenuBar menu;
    private JMenuItem newGame;
    private JMenuItem quit;
    private JLabel label;

 /** 
  * Constructs a new Tic-Tac-Toe board and sets up the basic
  * JFrame containing a JTextArea in a JScrollPane GUI.
  */ 
     public TicTacToe() {
     gameDone = false;
     numOccSquares = 0;
     
     JFrame frame = new JFrame("Tic Tac Toe");
     frame.getContentPane().setPreferredSize(new Dimension(1000, 600));
     
     Container content = frame.getContentPane();
     border = new BorderLayout();
     content.setLayout(border);
     
     button = new JButton[3][3];
     JPanel buttonPanel = new JPanel();
     buttonPanel.setLayout(new GridLayout(3,3));
     
     menu = new JMenuBar();
     frame.setJMenuBar(menu);
     
     for(int i=0;i<3;i++) {
         for(int j=0; j<3; j++) {
             button[i][j] = new JButton(); // initilaizes all the buttons
             button[i][j].setText("");
             button[i][j].setFont(new Font(null, Font.BOLD, 50));
             buttonPanel.add(button[i][j]);
             button[i][j].addActionListener(this);
         }
     }
     content.add(buttonPanel, BorderLayout.CENTER);
     
     JMenu fileMenu = new JMenu("Menu");
     fileMenu.setFont(new Font(null, Font.BOLD, 20));
     menu.add(fileMenu);
     
     newGame = new JMenuItem("New Game"); 
     newGame.setFont(new Font(null, Font.BOLD, 20));
     fileMenu.add(newGame);
      
     quit = new JMenuItem("Quit");
     quit.setFont(new Font(null, Font.BOLD, 20));
     fileMenu.add(quit);
     
     
     // this allows us to use shortcuts (e.g. Ctrl-N and Ctrl-Q) 
     final int SHORTCUT_MASK = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(); // to save typing
     newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, SHORTCUT_MASK));
     quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK));
     
     // listen for menu selections
     quit.addActionListener(this);
     newGame.addActionListener(this); 
     
     //label part
     JPanel labelPanel = new JPanel();
     label = new JLabel("game just started and X's turn");
     label.setFont(new Font("Serif", Font.PLAIN, 25));
     labelPanel.add(label);
     labelPanel.setSize(250,250);
     content.add(labelPanel, BorderLayout.SOUTH);
        
     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // pressing the red x closes the game      
     frame.pack();
     frame.setResizable(true);
     frame.setVisible(true);  
 } 
 
 /**
  * Clears the board to play the game again without having to make a new game.
  * @param row of the board
  * @param column of the board
  */ 
 
 private void hasWinner(int row, int col) {
     if(numOccSquares > 4) {
           if(button[row][0].getText().equals(button[row][1].getText()) &&
              button[row][1].getText().equals(button[row][2].getText())) { // check row "row"
                gameDone = true;
           }
           else if(button[0][col].getText().equals(button[1][col].getText()) &&
                   button[1][col].getText().equals(button[2][col].getText())) { // check column "col"
                gameDone = true;
           }               
           else if(button[0][0].getText().equals(button[1][1].getText()) &&
                   button[1][1].getText().equals(button[2][2].getText())) { // if row=col check one diagonal
                gameDone = true;
           }
           else if(button[0][2].getText().equals(button[1][1].getText()) &&
                   button[1][1].getText().equals(button[2][0].getText())) { // if row=2-col check other diagonal
                gameDone = true; 
           }
     }
  }
 
  public void actionPerformed(ActionEvent e)
   {
       Object object = e.getSource(); // find out whether a menu item or a button was clicked
       if(object instanceof JButton) {// object pressed is a button
           JButton buttonPressed = (JButton) e.getSource();// find out which button has been pressed
           if(numOccSquares % 2 == 0) { // if this is true then its X's turn
               for(int i=0; i<3; i++){                     
                   for(int j=0; j<3; j++) { // use for loops to find the button was pressed
                       if(buttonPressed == button[i][j]) {
                           button[i][j].setText("X");
                           button[i][j].setEnabled(false);
                           numOccSquares++;
                           hasWinner(i, j);                            
                           if(gameDone) {
                               disableButtons();
                               label.setText(" Game Over, Player X Wins!");
                           }
                           else if(numOccSquares == 9 && !gameDone) {
                               label.setText(" Game Over, Tie!");
                           }
                           else {
                               label.setText(" Game In Progress, O Player's Turn!");
                           }
                       }
                   }
               }         
           }
           else { // O's turn
               for(int i=0; i<3; i++){
                   for(int j=0; j<3; j++) {
                       if(buttonPressed == button[i][j]) {
                           button[i][j].setText("O");
                           button[i][j].setEnabled(false);
                           numOccSquares++;
                           hasWinner(i, j);             
                           if(gameDone) {
                               disableButtons();
                               label.setText(" Game Over, Player O Wins!");
                           }
                           else if(numOccSquares == 9 && !gameDone) {
                               label.setText(" Game Over, Tie!");
                           }
                           else {
                               label.setText(" Game In Progress, X Player's Turn!");
                           }
                       }
                   }
               }        
           }
       }   
       else { // object is a menuItem       
           JMenuItem elem = (JMenuItem) object; // Find out which menuItem has been clicked
           if(elem == newGame) { // the player chose to start a new game
               clearBoard();            
           }
           if (elem == quit) { // the player chose to quit the game
               System.exit(0);            
           }
       }
    }
  
 /**
  * Clears the board 
  */
 
 private void clearBoard() {
     for(int i=0;i<3;i++) {
         for(int j=0; j<3; j++) {
            button[i][j].setEnabled(true);
            numOccSquares = 0;
            button[i][j].setText("");
            gameDone = false;
        }
    }
 } 
 
 /**
  *  Disables the buttons after a winner is found;
  */
 private void disableButtons() {
     for(int i=0;i<3;i++) {
         for(int j=0; j<3; j++) {
            button[i][j].setEnabled(false);
        }
    }
 }
}
