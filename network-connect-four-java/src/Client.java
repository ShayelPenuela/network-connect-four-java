/**
 * @author: Shayel Penuela 
 * @Description:
 * Creates GUI for any users accessing connect four via a client.
 * Uses 'ConnectionThread', to accept the user's decisions and parses it into GUI style output
 */
import java.net.*;
import java.io.*;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Client implements ActionListener {

    public static void main(String[] args) {
        String hostName="localhost";
        int portNumber=1024;
        try (
                Socket conn=new Socket(hostName, portNumber);
                PrintWriter sockOut=new PrintWriter(conn.getOutputStream(),true);
                BufferedReader sockIn=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                Scanner termIn=new Scanner(System.in);
        ) {

            // Create a JFrame to hold the panel
            JFrame frame = new JFrame("Button Grid Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Create a JPanel with GridLayout
            JPanel panel = new JPanel(new GridLayout(7, 7)); // 3 rows, 3 columns

            // Create buttons
            JButton[][] buttons = new JButton[7][7]; // 3x3 grid of buttons

            // Create buttons and add them to the panel at specific positions
            for (int row = 0; row < 7; row++) {
                for (int col = 0; col < 7; col++) {
                    if (row == 0)
                    {
                        //Editing the buttons used to drop tokens
                        JButton button = new JButton(String.valueOf(col + 1));
                        button.setBackground(Color.BLACK);
                        button.setForeground(Color.GRAY);
                        buttons[row][col] = button; // Store button in the array for further reference
                        panel.add(button); // Add button to the panel
                    }
                    else
                    {
                        //Creating ordinary buttons
                        JButton button = new JButton();
                        buttons[row][col] = button; // Store button in the array for further reference
                        panel.add(button); // Add button to the panel
                    }
                }
            }

            //if button 1 is pressed
            buttons[0][0].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e)
                {
                    // Action to be performed when the button is clicked
                    String fromUser= "1";
                    sockOut.println(fromUser);
                }
            });

            //if button 2 is pressed
            buttons[0][1].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e)
                {
                    // Action to be performed when the button is clicked
                    String fromUser= "2";
                    sockOut.println(fromUser);
                }
            });

            // if button 3 is pressed
            buttons[0][2].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e)
                {
                    // Action to be performed when the button is clicked
                    String fromUser= "3";
                    sockOut.println(fromUser);
                }
            });

            // if button 4 is pressed
            buttons[0][3].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e)
                {
                    // Action to be performed when the button is clicked
                    String fromUser= "4";
                    sockOut.println(fromUser);
                }
            });

            // if button 5 is pressed
            buttons[0][4].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e)
                {
                    // Action to be performed when the button is clicked
                    String fromUser= "5";
                    sockOut.println(fromUser);
                }
            });

            // if button 6 is pressed
            buttons[0][5].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e)
                {
                    // Action to be performed when the button is clicked
                    String fromUser= "6";
                    sockOut.println(fromUser);
                }
            });

            // if button 7 is pressed
            buttons[0][6].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e)
                {
                    // Action to be performed when the button is clicked
                    String fromUser= "7";
                    sockOut.println(fromUser);
                }
            });
            // Place a specific button in a specific row and column
            //JButton specificButton = new JButton("Specific Button");
            //panel.add(specificButton); // Add specific button to the panel (it will be placed at the next available position)

            // Set frame size and make it visible
            frame.getContentPane().add(panel);
            frame.setSize(300, 300);
            frame.setVisible(true);

            //Row is 1 because the index at 0 is used for displaying numbers
            int col = 0;
            int row = 1;

            while (true)
            {
                //Reading from server (IMPORTANT TO UPDATE EVERYTHING IN THE GUI
                String fromServer=sockIn.readLine();

                //If the GUI user wins, then we create a pop-up to congratulate them
                if (fromServer.equals("You won!"))
                {
                    //Creating necessary tools for the pop up
                    JLabel winLabel = new JLabel("You Won!");
                    JPanel winPanel = new JPanel();
                    JFrame winningScreen = new JFrame("Winner!");

                    winPanel.add(winLabel);

                    winningScreen.add(winPanel,BorderLayout.CENTER);
                    winningScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    winningScreen.setSize(300, 300);
                    winningScreen.pack();
                    winningScreen.setVisible(true);

                    //The program breaks because there is a winner!
                    break;

                }

                //If the GUI user loses, then we create a pop-up to indicate a loss
                if (fromServer.equals("You lost :("))
                {
                    //Creating necessary tools for the pop up
                    JLabel loseLabel = new JLabel("You Lost!");
                    JPanel losePanel = new JPanel();
                    JFrame losingScreen = new JFrame("Loser!");

                    losePanel.add(loseLabel);

                    losingScreen.add(losePanel,BorderLayout.CENTER);
                    losingScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    losingScreen.setSize(300, 300);
                    losingScreen.pack();
                    losingScreen.setVisible(true);

                    //The program breaks because there is a winner!
                    break;

                }

                //Creating server into a char array to count the symbols corresponding to the board ('-', 'X', 'O')
                char [] serverChar = fromServer.toCharArray();

                for (int i = 0 ; i < serverChar.length; i ++)
                {

                    if (serverChar[i] == '-' || serverChar[i] == 'X' || serverChar[i] == 'O')
                    {


                        //if we have reached the end of a row (all columns have passed) we update the row index by 1, so we can access a new row
                        //col++;
                        System.out.println("Col: " + col + " Row:" + row);

                        //If it is an X, then we turn the corresponding button related to the terminal button into the colour red
                        if (serverChar[i] == 'X')
                        {
                            buttons[row][col].setBackground(Color.RED);

                        }

                        //If it is an O, we turn te corresponding button related to the termial button into the colour yello
                        if (serverChar[i] == 'O')
                        {
                            buttons[row][col].setBackground(Color.yellow);

                        }

                        //Making sure any blank spaces are just white
                        if (serverChar[i] == '-')
                        {
                            buttons[row][col].setBackground(Color.white);

                        }



                        //This check just makes sure we go to the next possible row
                        if (col % 6 == 0 && col != 0 )
                        {
                            row++;

                            System.out.println("row" + row);

                            if (row == 7) {
                                row = 1;

                            }



                        }
                        //every time we come across a symbol that represents the grid, then we move the 'y' or 'column' coordinate/index up
                        col++;
                    }

                }

                //Resetting col so we can re-use the variable next turn to modify the buttons based on the board
                col = 0;
            }



            //Needed
        } catch (UnknownHostException e) {
            System.out.println("I think there's a problem with the host name.");
        } catch (IOException e) {
            System.out.println("Had an IO error for the connection.");
        }



    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }

}