import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
/**
 * This is the Board class.
 * This is the main UI class, which makes calls to the square class when an action is performed.
 * @author Martynas Dabravalskis
 */
public class Board implements ActionListener
{
    private JFrame frame = new JFrame();
    private JPanel panel = new JPanel();
    private GridLayout layout = new GridLayout(8,8);
    private boolean isPressed = false;
    private int arrayPosition; // array position of the selected square
    private Square[] square = new Square[64];
    private String turnPieceType = "RED"; // which piece moves next (Whites start, so reds move next)
    private boolean lock = false; // Is movement locked to a selected square

    /**
     * Board constructor. Sets window, layout.
     */
    public Board()
    {
        frame.setContentPane(panel);
        panel.setLayout(layout); 
        frame.setTitle("Draughts");
        frame.setSize(800,800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }
    /**
     * Creates 64 squares, sets their colour and arranges them in an 8x8 grid.
     */
    public void loadSquares()
    {
        ImageIcon white = new ImageIcon("img/empty.png");
        ImageIcon black = new ImageIcon("img/empty2.png"); 
        ImageIcon redPiece = new ImageIcon("img/red.png");
        ImageIcon whitePiece = new ImageIcon("img/white.png");
        ImageIcon piece;

        int x = 0;
        int y = 0;
        String pieceType = "RED";
        piece = redPiece;
        // add all squares to the board
        for(int i=0;i<64;i=i+8)
        {
            // add a row of squares
            for(int a=i;a<i+8;a+=2)
            {
                square[a] = new Square(black,x,y,a,"NONE"); 
                panel.add(square[a].button); 
                square[a+1] = new Square(piece,x+1,y,a+1,pieceType); 
                panel.add(square[a+1].button); 
                x+=2; 
            }
            x=0;
            i+=8;
            if(i>=8*5) 
            {
                pieceType = "WHITE";
                piece = whitePiece;
            }
            else if(i>=8*3) 
            {
                pieceType = "NONE"; 
                piece = white;
            } 
            // add a row of squares
            for(int a=i;a<i+8;a+=2)
            {
                square[a] = new Square(piece,x,y+1,a,pieceType); 
                panel.add(square[a].button);
                square[a+1] = new Square(black,x+1,y+1,a+1,"NONE");
                panel.add(square[a+1].button); 
                x+=2; 
            }
            x=0;
            y+=2;
        }
        // add an ActionListener to each square
        for(int i=0;i<64;i++)
        {
            square[i].button.addActionListener(this);
        }
        frame.setVisible(true);
    }
    /**
     * Determines which square was clicked on and performs firstClick() or secondClick() depending on whether a piece is already selected
     */
    public void actionPerformed(ActionEvent e)
    {
        for(int i=0;i<64;i++)
        {
            if (e.getSource() == square[i].button)
            {
                if(isPressed == false)
                    {
                        firstClick(i);
                    }
                else 
                {
                    secondClick(i);
                }
            
            }

        }
    }
    /**
     * Highlight square
     * @param arrayLocation square position in array
     */
    public void highlight(int arrayLocation)
    {
        square[arrayLocation].setHighlighted(true);
        ImageIcon selected = new ImageIcon("img/selected.png");
        square[arrayLocation].button.setIcon(selected);
    }
    /**
     * Set square to white
     * @param arrayLocation square position in array
     */
    public void setToWhite(int arrayLocation)
    {
        square[arrayLocation].setHighlighted(false);
        ImageIcon white = new ImageIcon("img/empty.png");
        square[arrayLocation].button.setIcon(white);
    }
    /**
     * Selects piece to move(if movement is possible) and sets possible moves
     * @param i square position in array
     */
    public void firstClick(int i)
    {
        checkForJumps();
        isPressed=false;
        if(square[i].canMove(turnPieceType,this) == true && square[i].getMovementLocked() == false)
        {
            square[i].SetPosibleMoves(this);
            for(int a = 0;a<2;a++)
            {
                if(square[i].canMoveTo(getSquare(square[i].getcanMoveTo(a))) == true)
                {
                    isPressed = true;
                    arrayPosition = square[i].getArrayPosition();
                    highlight(square[i].getcanMoveTo(a));  
                }
            }
        }
    }
    /**
     * Moves piece to selected area(if possible)
     * @param i square position in array
     */
    public void secondClick(int i)
    {
        boolean isHighlighted = false;
        for(int a=0;a<2;a++)
            if(square[square[arrayPosition].getcanMoveTo(a)].gethighlighted() == true)
            {
                if(lock==false)
                {
                    setToWhite(square[arrayPosition].getcanMoveTo(a));
                    square[square[arrayPosition].getcanMoveTo(a)].setPieceType("NONE");
                }
                if(square[square[arrayPosition].getcanMoveTo(a)]==square[i])
                    isHighlighted = true;
            }
        if(square[arrayPosition].canMoveTo(square[i]) == true && isHighlighted == true)
        {
            if(lock == true)
                for(int a=0;a<2;a++)
                    if(square[square[arrayPosition].getcanMoveTo(a)].gethighlighted() == true)
                        setToWhite(square[arrayPosition].getcanMoveTo(a));
            square[arrayPosition].moveTo(square[i],this,turnPieceType);
        }
        else if(lock == false)
            isPressed = false;
    }
    /**
     * Checks if any jumps are available. If there are, force to jump.
     */
    public void checkForJumps()
    {
        boolean jumpsAvailable = false;
        for(int a=0;a<64;a++)
        {
            square[a].setMovementLocked(true);
        }
        for(int a=0;a<64;a++)
        {
            if(square[a].canMove(turnPieceType,this) == true && square[a].getPieceType()!=turnPieceType)
                {   
                    square[a].SetPosibleMoves(this);
                    if( ( square[a].getJump(0) == true && square[a].canMoveTo(square[square[a].getcanMoveTo(0)]) ) || ( square[a].getJump(1) == true && square[a].canMoveTo(square[square[a].getcanMoveTo(1)]) ) )
                    {
                        square[a].setMovementLocked(false);
                        jumpsAvailable = true;
                    }
                }
        }
        if(jumpsAvailable == false)
        {
            for(int a=0;a<64;a++)
            {
                square[a].setMovementLocked(false);
            }
        }
    }

        //-- Accessors --//

    /**
     * @param i square position in square array
     * @return square object
     */
    public Square getSquare(int i)
    {
        return square[i];
    }

        //-- Mutators --//

    /**
     * Set which player goes next
     * @param turnPieceType piece which goes next turn
     */
    public void setTurnPieceType(String turnPieceType)
    {
        this.turnPieceType = turnPieceType;
    }
    /**
     * Set if square is selected
     * @param isPressed is square selected
     */
    public void setIsPressed(boolean isPressed)
    {
        this.isPressed = isPressed;
    }
    /**
     * Set array position of pressed square
     * @param arrayPosition array position of pressed square
     */
    public void setArrayPosition(int arrayPosition)
    {
        this.arrayPosition = arrayPosition;
    }
    /**
     * Set to enable the movemenet of only the selected piece
     * @param lock is movement locked
     */
    public void setLock(boolean lock)
    {
        this.lock = lock;
    }
}
/*
    //-- board layout --//

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

    ----board layout----
    0  1  2  3  4  5  6  7
    8  9 10 11 12 13 14 15
    16 17 18 19 20 21 22 23
    24 25 26 27 28 29 30 31
    32 33 34 35 36 37 38 39
    40 41 42 43 44 45 46 47
    48 49 50 51 52 53 54 55
    56 57 58 59 60 61 62 63
*/