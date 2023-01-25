import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    //Swing objects are created through the use of a form.
    private JPanel generalPanel;
    private JFormattedTextField enemyStore;
    private JFormattedTextField player0;
    private JFormattedTextField player1;
    private JFormattedTextField enemy5;
    private JFormattedTextField enemy4;
    private JFormattedTextField player2;
    private JFormattedTextField enemy3;
    private JFormattedTextField player4;
    private JFormattedTextField player3;
    private JFormattedTextField enemy2;
    private JFormattedTextField enemy1;
    private JFormattedTextField player5;
    private JFormattedTextField enemy0;
    private JFormattedTextField playerStore;
    private JPanel mainPanel;
    private JButton resetBoard;
    private JButton simulateGame;
    private JLabel labelGame;
    private JLabel playerLabel;
    private JLabel enemyLabel;

    //This method converts integer position values (from board)
    //into a JFormattedTextField object.
    //This functionality is crucial to the function of the GUI.
    private JFormattedTextField getPosition(int pos, int side)
    {
        if(side == 0)
        {
            switch (pos)
            {
                case 0: return player0;
                case 1: return player1;
                case 2: return player2;
                case 3: return player3;
                case 4: return player4;
                case 5: return player5;
                case 6: return playerStore;
            }
        }
        else
        {
            switch (pos)
            {
                case 0: return enemy0;
                case 1: return enemy1;
                case 2: return enemy2;
                case 3: return enemy3;
                case 4: return enemy4;
                case 5: return enemy5;
                case 6: return enemyStore;
            }
        }
        return player0;
    }

    //This internal method returns a 2D-array (board)
    //based on the current GUI state.
    private int[][] getBoard() throws Exception
    {
        int[][] temp = new int[2][7];

        for(int i = 0; i < 2; i++)
        {
            for(int j = 0; j < 7; j++)
            {
                String currVal = getPosition(j, i).getText();


                temp[i][j] = Integer.parseInt(currVal);

            }
        }

        return temp;
    }

    //This method sets all squares to the default value of 0.
    //It also resets their color to the default.
    private void resetBoard()
    {
        for(int i = 0; i < 2; i++)
        {
            for(int j = 0; j < 7; j++)
            {
                getPosition(j, i).setText("4");
                if(j == 6) getPosition(j, i).setBackground(Color.RED);
                else if(i == 0) getPosition(j, i).setBackground(Color.YELLOW);
                else getPosition(j, i).setBackground(Color.BLACK);
            }
        }
    }

    public GUI()
    {
        //Set up the GUI.
        setContentPane(generalPanel);
        setTitle("MancalaSolve");
        setSize(600, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        resetBoard();
        setVisible(true);

        //When the Reset Board button is pressed, execute the resetBoard method.
        resetBoard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetBoard();
            }
        });

        //When the Simulate Game button is pressed, create a board based on the GUI.
        //Use the board to create a Game object.
        //Simulate the newly created game through the Solver class.
        //Update the board to indicate the best move, as determined by the Solver algorithm.
        //The simulation will not run if the user's input is invalid. This is addressed with a try/catch block.
        simulateGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    int[][] gameBoard = getBoard();

                    Game g = new Game(4);
                    g.setBoard(gameBoard);

                    int[] solution = Solver.findOptimal(g);

                    getPosition(solution[0], 0).setBackground(Color.BLUE);

                    if(solution[1] > 0)
                    {
                        getPosition(solution[1], 0).setBackground(Color.GREEN);
                    }
                }
                catch (Exception ne)
                {
                    JOptionPane.showMessageDialog(null, "Please enter an integer for your board values!");
                }
            }
        });
    }

    //Main method for the GUI.
    public static void main(String[] args)
    {
        GUI testing = new GUI();
    }
}
