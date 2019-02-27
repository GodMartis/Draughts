/*
----board layout----
-   y
- x 0 1 2 3 4 5 6 7
-   1 * * * * * * *
-   2 * * * * * * *
-   3 * * * * * * *
-   4 * * * * * * *
-   5 * * * * * * *
-   6 * * * * * * *
-   7 * * * * * * *
*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Board
{
    JFrame a = new JFrame(); // Create a blank window
    JPanel panel = new JPanel(); // Create a panel
    GridLayout layout = new GridLayout(8,8); // create an 8x8 grid layout
    public Board()
    {
        a.setContentPane(panel); // Use panel on Window
        panel.setLayout(layout); // Set layout
        a.setVisible(true); // Make it visible
        a.setTitle("Draughts"); // Change window title
        a.setSize(800,800); // Change window size
        a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit program on close
        a.setResizable(false); // Make the window non-resizable
    }
    /**
    * Creates 64 squares, sets their colour and arranges them in an 8x8 grid.
    */
    void loadSquares()
    {

        Square[] square = new Square[64]; // Init 64 squares
        ImageIcon white = new ImageIcon("img/empty.png"); // Create white ImageIcon
        ImageIcon black = new ImageIcon("img/empty2.png"); // Create black ImageIcon
        int x = 0; // Create x coordinate
        int y = 0; // Create y coordinate
        for(int i = 0;i<64;i=i+8)
        {
            for(int a = i;a<i+8;a+=2)
            {
                square[a] = new Square(black,x,y); // Add a black square
                panel.add(square[a].button); // Add square to Jpanel
                square[a+1] = new Square(white,x+1,y); // Add a white square
                panel.add(square[a+1].button); // Add square to Jpanel
                x+=2; // Added 2 squares, so x=x+2
            }
            i+=8; // Added a row of squares, so i=i+8
            for(int a = i;a<i+8;a+=2)
            {
                square[a] = new Square(white,x,y+1); // Add a white square
                panel.add(square[a].button); // Add square to Jpanel
                square[a+1] = new Square(black,x+1,y+1); // Add a black square
                panel.add(square[a+1].button); // Add square to Jpanel
                x+=2; // Added 2 squares, so x=x+2
            }
            y+=2; // Added two rows, so y=y+2
        } 
        a.setVisible(true); // Make layout visible
    }

}