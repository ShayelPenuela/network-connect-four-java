/**
 * @author: Shayel Penuela *
 * @Description:
 * Creates logic for connect four and allows to users to play using port connections
 */

import java.net.*;
import java.io.*;

public class ConnectionThread extends Thread {

    private Socket client1,client2;
    char [][] TwoD_Arr;

    //Used for our recursive algorithm to check where the token drops into when we select a column to drop it in
    int Max_I;

    //Used to determine who's turn it is.
    // If this variable is set to 1, then it is the 1st player's turn
    // If this variable is set to 2, then it is the 2nd player's turn
    int Turn;

    int check_I;
    int check_J;

    //Used to check if there is a winner
    boolean winner;

    //Used as a counter for the connect 4
    int fourCounter;

    public ConnectionThread(Socket c1, Socket c2) {client1=c1; client2=c2;}

    public void run() {
        try (
                PrintWriter out1 = new PrintWriter(client1.getOutputStream(), true);
                PrintWriter out2 = new PrintWriter(client2.getOutputStream(), true);
                BufferedReader in1 = new BufferedReader(new InputStreamReader(client1.getInputStream()));
                BufferedReader in2 = new BufferedReader(new InputStreamReader(client2.getInputStream()));
        ) {
            //Begin by setting 'winner' to false
            winner = false;

            //Introducing player to the game
            out1.println("Welcome to Connect 4!");
            out2.println("Welcome to Connect 4!");

            //We create the board (2D array and display it to the users)
            Board_Creation(out1,out2);

            //Telling users what to do right now
            out1.println();//Just for aesthetic reasons
            out2.println();//Just for aesthetic reasons
            out1.println("It is your turn. Please type the column you will like to drop your token in. (1 to 6)");
            out2.println("It is the other player's turn. Please wait for the end of their turn.");

            //This is used to read what each user types
            String inputLine;
            while (true) {

                Turn = 1;
                //Forever loop that breaks out ONLY if the player types a valid column entry. (Other character's will make you try again)
                while (true) {
                    inputLine = in1.readLine();


                    //7 cases (7 columns or choices to make)

                    if (inputLine.equals("1")) {
                        Edit_Board(inputLine, in1, in2, out1, out2);
                        break;
                    }

                    if (inputLine.equals("2")) {
                        Edit_Board(inputLine, in1, in2, out1, out2);
                        break;
                    }

                    if (inputLine.equals("3")) {
                        Edit_Board(inputLine, in1, in2, out1, out2);
                        break;
                    }

                    if (inputLine.equals("4")) {
                        Edit_Board(inputLine, in1, in2, out1, out2);
                        break;
                    }

                    if (inputLine.equals("5")) {
                        Edit_Board(inputLine, in1, in2, out1, out2);
                        break;
                    }

                    if (inputLine.equals("6")) {
                        Edit_Board(inputLine, in1, in2, out1, out2);
                        break;
                    }

                    if (inputLine.equals("7")) {
                        Edit_Board(inputLine, in1, in2, out1, out2);
                        break;
                    }

                    if (inputLine == null) break;

                    else{
                        out1.println("Invalid entry, please type an appropriate column number");
                    }
                }

                //Show 1st and 2nd player the update board
                display_board(out1,out2);


                out2.println(inputLine);
                out1.println(inputLine);

                //we know player 1 has won because it was just player 1's turn so this is when we would have checked if they have won or not
                if (winner == true)
                {
                    out1.println("You won!");
                    out2.println("You lost :(");
                    break;
                }

                //second player's turn
                Turn = 2;
                while (true) {
                    inputLine = in2.readLine();

                    if (inputLine.equals("1")) {
                        Edit_Board(inputLine, in1, in2, out1, out2);
                        break;
                    }

                    if (inputLine.equals("2")) {
                        Edit_Board(inputLine, in1, in2, out1, out2);
                        break;
                    }

                    if (inputLine.equals("3")) {
                        Edit_Board(inputLine, in1, in2, out1, out2);
                        break;
                    }

                    if (inputLine.equals("4")) {
                        Edit_Board(inputLine, in1, in2, out1, out2);
                        break;
                    }

                    if (inputLine.equals("5")) {
                        Edit_Board(inputLine, in1, in2, out1, out2);
                        break;
                    }

                    if (inputLine.equals("6")) {
                        Edit_Board(inputLine, in1, in2, out1, out2);
                        break;
                    }

                    if (inputLine.equals("7")) {
                        Edit_Board(inputLine, in1, in2, out1, out2);
                        break;
                    }

                    if (inputLine == null) break;

                    else{
                        out2.println("Invalid entry, please type an appropriate column number");
                    }

                }


                //Show 1st and 2nd player the update board
                display_board(out1,out2);

                out2.println(inputLine);
                out1.println(inputLine);

                //We know player 2 has won because it was just their turn
                if (winner == true)
                {
                    out2.println("You won!");
                    out1.println("You lost :(");
                    break;
                }



            }
        } catch (IOException e) {
            System.out.println("Ah nertz.");
        }
    }

    /**
     * Creates the initial board for both users
     * @param out1
     * @param out2
     */
    public void Board_Creation(PrintWriter out1, PrintWriter out2)
    {

        //Creating the initial screen for the 1st user to make the first move
        TwoD_Arr = new char[7][7];
        int count = 1;
        for (int i = 0; i < TwoD_Arr.length; i ++)
        {

            out1.println('\n');
            out2.println('\n');
            for (int j = 0; j < TwoD_Arr[i].length; j ++){
                if (i == 0)
                {
                    //For player 1 to see
                    out1.print(count);
                    out1.print("      ");

                    //For player 2 to see
                    out2.print(count);
                    out2.print("      ");
                    count++;

                }

                else {
                    TwoD_Arr[i][j] = '-';

                    //For player 1 to see
                    out1.print(TwoD_Arr[i][j] + "      ");

                    //For player 2 to see
                    out2.print(TwoD_Arr[i][j] + "      ");
                }

            }
        }

    }


    /**
     * Doesn't do much but parse the string the player types into an integer and then feeds it into the recursive method that does
     * the work ('drop_check()'
     * @param inputLine
     * @param in1
     * @param in2
     */
    public void Edit_Board(String inputLine, BufferedReader in1, BufferedReader in2, PrintWriter out1, PrintWriter out2)
    {
        int column_Dropped = Integer.parseInt(inputLine) - 1;
        Max_I = 6;
        drop_Check(Max_I,column_Dropped,in1,in2,out1,out2);
    }

    /**
     * This method is used to recursively check if the column we decide to drop the token in, is full, or we can place it inside
     * Base Case: the coordinate of the rows and columns holds '-' which is a free space
     * Recursive Case: If it hold's a X or O from another player, then we recurse and go a column higher which is (i-1)
     * Other Outcome: The column we choose is full so we are allowed to choose another one and retry this recursion
     *
     * @param i used as the row we are recursively checking to see if we can change the index or not
     * @param j used as the column we selected to drop the token into. (Doesn't change unless this column is full)
     * @param in1 used if it is player 1's turn to give them another chance to select a different column
     * @param in2 used if it is player 2's turn to give them another chance to select a differet column
     */
   public void drop_Check(int i, int j, BufferedReader in1, BufferedReader in2, PrintWriter out1, PrintWriter out2)
   {
       // '-' Means it is an open space so if the index we're at is '-' then we can transform it into an 'O' or 'X' depending
       //on which user's turn it is
       if (TwoD_Arr[i][j] == '-')
       {
           //if it is player 1's turn
           if (Turn == 1)
           {
               TwoD_Arr[i][j] = 'X';
           }

           //if it is player 2's turn
           else
           {
              TwoD_Arr[i][j] = 'O' ;
           }

           //we save the 'coordinate' of where we have dropped the token in the 2D array
           check_I = i;
           check_J = j;
           //we check if there is 4 in a row along the vertical axis
           fourCounter = 1;
           checkBelow(i,j);


           //we reset i and j so we can check horizontally
           i = check_I;
           j = check_J;
           //we check if there is 4 in a row along the horizontal axis
           fourCounter = 1;
           checkHorizontal(i,j);


           //we reset i and j so we can check horizontally
           i = check_I;
           j = check_J;
           //we check if there is 4 in a row along the diagonal
           fourCounter = 1;
           checkDiagonalOne(i,j);

           //we reset i and j so we can check horizontally
           i = check_I;
           j = check_J;
           //we check if there is 4 in a row along the diagonal
           fourCounter = 1;
           checkDiagonalTwo(i,j);

       }

       //If the index we're currently at has an 'X' or an 'O', then we cannot go here so we keep going up in connect 4 by
       //recursively calling this method to move the i variable
       else if (TwoD_Arr[i][j] == 'X' || TwoD_Arr[i][j] == 'O')
       {
           drop_Check(i-1,j, in1, in2,out1,out2);
       }

       //If we cannot place the token in this column, (it's full of tokens) then we tell user it is an invalid entry
       //and we ask them to try again
       else if (i == 0)
       {
           String re_input = null;
           //We let player 1 try again
           if (Turn == 1)
           {
               //We tell 1st player that they must try again
               out1.println("Invalid Entry: Please try another column that isn't full");
               //We accept input from the 1st player
               try {
                   re_input = in1.readLine();
               } catch (IOException e) {
                   e.printStackTrace();
               }
               //We set up the new column we're going to drop the token in
               int newColumn = Integer.parseInt(re_input) - 1;

               //We need to restart the i we are starting at
               Max_I = 6;
               drop_Check(Max_I, newColumn, in1,in2,out1,out2);


           }

           //We let player 2 try again
           if (Turn == 2)
           {
               //We tell 2nd player that they must try again
               out2.println("Invalid Entry: Please try another column that isn't full");
               //We accept input from the 1st player
               try {
                   re_input = in2.readLine();
               } catch (IOException e) {
                   e.printStackTrace();
               }
               //We set up the new column we're going to drop the token in
               int newColumn = Integer.parseInt(re_input) - 1;

               //We need to restart the i we are starting at
               Max_I = 6;
               drop_Check(Max_I, newColumn, in1,in2,out1,out2);

           }

       }

   }

    /**
     * We iterate through the 2D array to display the full board to the user after their turn
     * and tell the appropriate user that it's their turn
     */
   public void display_board(PrintWriter out1, PrintWriter out2)
   {
       int count = 1;
       for (int i = 0; i < TwoD_Arr.length; i ++)
       {

           out1.println('\n');
           out2.println('\n');
           for (int j = 0; j < TwoD_Arr[i].length; j ++){
               if (i == 0)
               {
                   //For player 1 to see
                   out1.print(count);
                   out1.print("      ");

                   //For player 2 to see
                   out2.print(count);
                   out2.print("      ");
                   count++;

               }

               else {
                   //TwoD_Arr[i][j] = '-';

                   //For player 1 to see
                   out1.print(TwoD_Arr[i][j] + "      ");

                   //For player 2 to see
                   out2.print(TwoD_Arr[i][j] + "      ");
               }

           }
       }
   }

    /**
     * There are 4 methods to check to see if there is a winner by checking...
     * if there are 4 in a row...
     * vertically (checkBelow)
     * horizontally (checkHorizontal)
     * diagonally btm left - top right (checkDiagonalOne)
     * diagonally top left - btm right (checkDiagonalTwo)
     */

   public void checkBelow(int i, int j)
   {
       //We are dividing this method into two separate outcomes.
       //checking that player 1 has won
       //checking that player 2 has won

       //The if and else statement have the same code, just for different symbols being checked in the char array

       //Player 1
       if (Turn == 1)
       {

           // if we are not out of bounds yet
           if (i < 6)
           {

               if (TwoD_Arr[i+1][j] == 'X')
               {
                   //We update the count
                   fourCounter++;

                   // If count reaches 4, then we have obviously gotten 4 in a row (NOT PART OF RECURSION)
                   if (fourCounter >= 4)
                   {
                       winner = true;
                   }

                   //We go further below
                   checkBelow(i + 1, j);
               }

           }

       }

       //Player 2
       else
       {

           // if we are not out of bounds yet
           if (i < 6)
           {

               if (TwoD_Arr[i+1][j] == 'O')
               {
                   //We update the count
                   fourCounter++;

                   // If count reaches 4, then we have obviously gotten 4 in a row (NOT PART OF RECURSION)
                   if (fourCounter >= 4)
                   {
                       winner = true;
                   }

                   //We go further below
                   checkBelow(i + 1, j);
               }
           }
       }
   }

    /**
     * Used to add the total of the right and the left tokens to see if the global variable 'fourcounter' equals 4
     * @param i
     * @param j
     */
   public void checkHorizontal(int i, int j)
   {
       checkLeft(i,j);
       checkRight(i,j);
   }


    /**
     * Used to check how many tokens of the same type are in a row on the left side
     * @param i
     * @param j
     */
   public void checkLeft(int i, int j)
   {//Player 1
       if (Turn == 1)
       {

           // if we are not out of bounds yet
           if (j > 0)
           {

               if (TwoD_Arr[i][j-1] == 'X')
               {
                   //We update the count
                   fourCounter++;

                   // If count reaches 4, then we have obviously gotten 4 in a row (NOT PART OF RECURSION)
                   if (fourCounter >= 4)
                   {
                       winner = true;
                   }

                   //We go further below
                   checkLeft(i , j-1);
               }

           }

       }

       //Player 2
       else
       {
           // if we are not out of bounds yet
           if (j > 0)
           {

               if (TwoD_Arr[i][j-1] == 'O')
               {
                   //We update the count
                   fourCounter++;

                   // If count reaches 4, then we have obviously gotten 4 in a row (NOT PART OF RECURSION)
                   if (fourCounter >= 4)
                   {
                       winner = true;
                   }

                   //We go further below
                   checkLeft(i , j-1);
               }

           }


       }
   }


    /**
     * Checking right of each token to see how many are in a row
     * @param i
     * @param j
     */
   public void checkRight(int i, int j)
   {
       //Player 1
       if (Turn == 1)
       {

           // if we are not out of bounds yet
           if (j < 6)
           {

               if (TwoD_Arr[i][j + 1] == 'X')
               {
                   //We update the count
                   fourCounter++;

                   // If count reaches 4, then we have obviously gotten 4 in a row (NOT PART OF RECURSION)
                   if (fourCounter >= 4)
                   {
                       winner = true;
                   }

                   //We go further below
                   checkRight(i, j + 1);
               }

           }

       }

       //Player 2
       else
       {

           // if we are not out of bounds yet
           if (j < 6)
           {

               if (TwoD_Arr[i][j+1] == 'O')
               {
                   //We update the count
                   fourCounter++;

                   // If count reaches 4, then we have obviously gotten 4 in a row (NOT PART OF RECURSION)
                   if (fourCounter >= 4)
                   {
                       winner = true;
                   }

                   //We go further below
                   checkRight(i , j + 1);
               }
           }
       }
   }


    /**
     * Checks diagonal connect 4 from the bottom left to top right of the token we have dropped
     * @param i
     * @param j
     */

   public void checkDiagonalOne(int i, int j)
   {checkBottomLeft(i,j);   checkTopRight(i,j);}

    /**
     * Checking each token from the dropped token to the bottom left most token
     * @param i
     * @param j
     */
    public void checkBottomLeft(int i, int j)
    {
        //Player 1
        if (Turn == 1)
        {

            // if we are not out of bounds yet
            if (j > 0 && i < 6)
            {

                if (TwoD_Arr[i + 1][j - 1] == 'X')
                {
                    //We update the count
                    fourCounter++;

                    // If count reaches 4, then we have obviously gotten 4 in a row (NOT PART OF RECURSION)
                    if (fourCounter >= 4)
                    {
                        winner = true;
                    }

                    //We go further below
                    checkBottomLeft(i + 1, j - 1);
                }

            }

        }

        //Player 2
        else
        {

            // if we are not out of bounds yet
            if (j > 0 && i < 6)
            {

                if (TwoD_Arr[i + 1][j - 1] == 'O')
                {
                    //We update the count
                    fourCounter++;

                    // If count reaches 4, then we have obviously gotten 4 in a row (NOT PART OF RECURSION)
                    if (fourCounter >= 4)
                    {
                        winner = true;
                    }

                    //We go further below
                    checkBottomLeft(i +  1 , j - 1);
                }
            }
        }
    }

    /**
     * Check Diagonally to the top right to see if there are any in a row
     * @param i
     * @param j
     */
    public void checkTopRight(int i, int j)
    {
        //Player 1
        if (Turn == 1)
        {

            // if we are not out of bounds yet
            if (j < 6 && i > 0)
            {

                if (TwoD_Arr[i - 1][j + 1] == 'X')
                {
                    //We update the count
                    fourCounter++;

                    // If count reaches 4, then we have obviously gotten 4 in a row (NOT PART OF RECURSION)
                    if (fourCounter >= 4)
                    {
                        winner = true;
                    }

                    //We go further below
                    checkTopRight(i - 1, j + 1);
                }

            }

        }

        //Player 2
        else
        {

            // if we are not out of bounds yet
            if (j < 6 && i > 0)
            {

                if (TwoD_Arr[i - 1][j + 1] == 'O')
                {
                    //We update the count
                    fourCounter++;

                    // If count reaches 4, then we have obviously gotten 4 in a row (NOT PART OF RECURSION)
                    if (fourCounter >= 4)
                    {
                        winner = true;
                    }

                    //We go further below
                    checkTopRight(i - 1 , j + 1);
                }
            }
        }
    }



    /**
     * Checks diagonal connect 4 from the top left to bottom right of the token we have dropped
     * @param i
     * @param j
     */

    public void checkDiagonalTwo(int i, int j)
    {checkTopLeft(i,j); checkBottomRight(i,j);

    }

    /**
     * Check from the token to the top left of it to see if there are any in a row
     * @param i
     * @param j
     */
    public void checkTopLeft(int i, int j)
    {
        //Player 1
        if (Turn == 1)
        {

            // if we are not out of bounds yet
            if (j > 0 && i > 0)
            {

                if (TwoD_Arr[i - 1][j - 1] == 'X')
                {
                    //We update the count
                    fourCounter++;

                    // If count reaches 4, then we have obviously gotten 4 in a row (NOT PART OF RECURSION)
                    if (fourCounter >= 4)
                    {
                        winner = true;
                    }

                    //We go further below
                    checkTopLeft(i - 1, j - 1);
                }

            }

        }

        //Player 2
        else
        {

            // if we are not out of bounds yet
            if (j > 0 && i > 0)
            {

                if (TwoD_Arr[i - 1][j - 1] == 'O')
                {
                    //We update the count
                    fourCounter++;

                    // If count reaches 4, then we have obviously gotten 4 in a row (NOT PART OF RECURSION)
                    if (fourCounter >= 4)
                    {
                        winner = true;
                    }

                    //We go further below
                    checkTopLeft(i -  1 , j - 1);
                }
            }
        }
    }

    /**
     * Checking if any of the tokens to the bottom right of the dropped token are in a row
     * @param i
     * @param j
     */
    public void checkBottomRight(int i, int j)
    {
        //Player 1
        if (Turn == 1)
        {

            // if we are not out of bounds yet
            if (j < 6 && i < 6)
            {

                if (TwoD_Arr[i + 1][j + 1] == 'X')
                {
                    //We update the count
                    fourCounter++;

                    // If count reaches 4, then we have obviously gotten 4 in a row (NOT PART OF RECURSION)
                    if (fourCounter >= 4)
                    {
                        winner = true;
                    }

                    //We go further below
                    checkBottomRight(i + 1, j + 1);
                }

            }

        }

        //Player 2
        else
        {

            // if we are not out of bounds yet
            if (j < 6 && i < 6)
            {

                if (TwoD_Arr[i + 1][j + 1] == 'O')
                {
                    //We update the count
                    fourCounter++;

                    // If count reaches 4, then we have obviously gotten 4 in a row (NOT PART OF RECURSION)
                    if (fourCounter >= 4)
                    {
                        winner = true;
                    }

                    //We go further below
                    checkBottomRight(i +  1 , j + 1);
                }
            }
        }
    }

}

