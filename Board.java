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
import java.awt.event.*;
import java.awt.event.ActionListener;

public class Board implements ActionListener
{
    JFrame a = new JFrame(); // Create a blank window
    JPanel panel = new JPanel(); // Create a panel
    GridLayout layout = new GridLayout(8,8); // create an 8x8 grid layout
    boolean isPressed = false;
    int arrayPosition;
    Square[] square = new Square[64]; // Init 64 squares
    public Board()
    {
        a.setContentPane(panel); // Use panel on Window
        panel.setLayout(layout); // Set layout
        a.setVisible(true); // Make it visible
        a.setTitle("Draughts"); // Change window title
        a.setSize(800,800); // Change window size
        a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit program on close
        a.setResizable(false); // Make the window non-resizable
        ImageIcon white = new ImageIcon("img/empty.png"); // Create white ImageIcon
        ImageIcon black = new ImageIcon("img/empty2.png"); // Create black ImageIcon
        ImageIcon redPiece = new ImageIcon("img/red.png");
        ImageIcon whitePiece = new ImageIcon("img/white.png");
    }
    /**
    * Creates 64 squares, sets their colour and arranges them in an 8x8 grid.
    */
    void loadSquares()
    {

        //Square[] square = new Square[64]; // Init 64 squares
        ImageIcon white = new ImageIcon("img/empty.png"); // Create white ImageIcon
        ImageIcon black = new ImageIcon("img/empty2.png"); // Create black ImageIcon
        ImageIcon white_init;
        ImageIcon redPiece = new ImageIcon("img/red.png");
        ImageIcon whitePiece = new ImageIcon("img/white.png");
        int x = 0; // Create x coordinate
        int y = 0; // Create y coordinate
        String pieceType = Square.RED;
        white_init = redPiece;
        for(int i = 0;i<64;i=i+8)
        {
            for(int a = i;a<i+8;a+=2)
            {
                square[a] = new Square(black,x,y,a,Square.NONE); // Add a black square
                panel.add(square[a].button); // Add square to Jpanel
                square[a+1] = new Square(white_init,x+1,y,a+1,pieceType); // Add a white square
                panel.add(square[a+1].button); // Add square to Jpanel
                x+=2; // Added 2 squares, so x=x+2
            }
            x = 0;
            i+=8; // Added a row of squares, so i=i+8

            if(i>=8*5) 
            {
                pieceType = Square.WHITE;
                white_init = whitePiece;
            }
            else if(i>=8*3) 
            {
                pieceType = Square.NONE; 
                white_init = white;
            } 

            for(int a = i;a<i+8;a+=2)
            {
                square[a] = new Square(white_init,x,y+1,a,pieceType); // Add a white square
                panel.add(square[a].button); // Add square to Jpanel
                square[a+1] = new Square(black,x+1,y+1,a+1,Square.NONE); // Add a black square
                panel.add(square[a+1].button); // Add square to Jpanel
                x+=2; // Added 2 squares, so x=x+2
            }
            x = 0;
            y+=2; // Added two rows, so y=y+2
        }
        for(int i = 0;i<64;i++)
        {
            square[i].button.addActionListener(this);
            System.out.println(i);
            System.out.println(square[i].getxPosition());
            System.out.println(square[i].getyPosition());
            System.out.println(" ");

        }
        a.setVisible(true); // Make layout visible
    }
    public void actionPerformed(ActionEvent e)
    {
        System.out.println("REEE");
        for(int i = 0;i<64;i++)
        {
            if (e.getSource() == square[i].button)
            {
                if(isPressed == false)
                    {
                        if(square[i].canMoveTo(square[i], this) == true)
                        {
                            isPressed = true;
                            arrayPosition = square[i].getArrayPosition();
                        }
                    }
                else 
                {
                    System.out.println(square[arrayPosition].getArrayPosition());
                    System.out.println(square[i].getArrayPosition());
                    if(square[arrayPosition].canMoveTo(square[i],this) == true)
                    {
                        System.out.println(isPressed);
                        System.out.println(arrayPosition);
                        square[arrayPosition].moveTo(square[i]);
                        isPressed = false;
                        updateScreen();
                    }
                }
            }
            
        }

    }
    public void highlight(int arrayLocation)
    {
        ImageIcon selected = new ImageIcon("img/selected.png");
        square[arrayLocation].button.setIcon(selected);
    }
    public void setToWhite(int arrayLocation)
    {
        ImageIcon white = new ImageIcon("img/empty.png");
        square[arrayLocation].button.setIcon(white);
    }
    public void updateScreen()
    {
        a.setVisible(true); // Make layout visible
    }

}