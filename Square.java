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
/*
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
import javax.swing.*;
public class Square
{
    public static final String NONE = "NONE";
    public static final String WHITE = "WHITE";
    public static final String RED = "RED";

    private boolean highlighted;
    private String pieceType;
    private int arrayPosition;
    private int yPosition = arrayPosition/8;
    private int xPosition = (arrayPosition%8)*8;
    JButton button;
    private int[] canMoveTo = new int[2];
    /**
    * Square constructor
    * @param color is the color of the square
    * @param x is the x position of the square
    * @param y is the y position of the square
    */
    public Square(ImageIcon color, int x, int y,int arrayPosition, String pieceType)
    {
        button = new JButton(color);
        button.setIcon(color);
        xPosition = x;
        yPosition = y;
        this.arrayPosition = arrayPosition;
        this.pieceType = pieceType;
    }
    /**
     * @return the xPosition
     */
    public int getxPosition() 
    {
        return xPosition;
    }
    /**
     * @return the yPosition
     */
    public int getyPosition() 
    {
        return yPosition;
    }
    /**
     * @return the arrayPosition
     */
    public int getArrayPosition() 
    {
        return arrayPosition;
    }
     /**
     * @return the pieceType
     */
    public String getPieceType()
    {
        return pieceType;
    }
    public int getcanMoveTo(int n)
    {
        return canMoveTo[n];
    }
    public void setHighlighted(boolean highlighted)
    {
        this.highlighted = highlighted;
    }
    public void setPiece(String pieceType)
    {
        this.pieceType = pieceType;
    }
    public boolean gethighlighted()
    {
        return highlighted;
    }
    void moveTo(Square square,Board board)
    {
        ImageIcon white = new ImageIcon("img/empty.png"); // Create white ImageIcon
        square.pieceType = pieceType;
        square.button.setIcon(button.getIcon());
        pieceType = NONE;
        board.setToWhite(arrayPosition);
        if(Math.abs(xPosition-square.getxPosition()) > 1)
        {
            int xpos;
            int ypos;
            xpos = square.getxPosition();
            ypos = square.getyPosition();
            xpos = (xpos+xPosition)/2;
            ypos = (ypos+yPosition)/2;
            int piecePosition = ypos*8+xpos;
            board.setToWhite(piecePosition);
            board.getSquare(piecePosition).setPiece("NONE");
        }
    }
    void SetPosibleMoves(Board board)
    {
        int y = 1; //set for red piece
        int x = -1;
        if(pieceType==WHITE)
        {
            y = -1;
        }
        if(xPosition == 7 || xPosition == 0)
        {
            if(xPosition == 7)
                x = -1;
            else if(xPosition == 0)
                x = 1;
            canMoveTo[0] = (yPosition+y)*8+xPosition+x;
            canMoveTo[1] = canMoveTo[0];
        }
        else
        {
            canMoveTo[0] = (yPosition+y)*8+xPosition+1;
            canMoveTo[1] = (yPosition+y)*8+xPosition-1;
        }
        boolean change = false;
        for(int i = 0;i<2;i++)
        {
            if(board.getSquare(canMoveTo[i]).getPieceType() !=NONE)
            {
                int xpos;
                int ypos;
                xpos = board.getSquare(canMoveTo[i]).getxPosition();
                ypos = board.getSquare(canMoveTo[i]).getyPosition();
                xpos = xpos - (xPosition - xpos);
                ypos = ypos - (yPosition - ypos);
                canMoveTo[i] = (ypos)*8+xpos;
                if(xpos>7 || xpos<0 || ypos>7 || xpos<0)
                if(i==1)
                    canMoveTo[1] = canMoveTo[0];
                else
                    change = true;
            }
        }
        if(change == true)
            canMoveTo[0] = canMoveTo[1];
    }

    boolean canMoveTo(Square square)
    {
        if(square.getArrayPosition() == canMoveTo[0] || square.getArrayPosition() == canMoveTo[1]) 
        {
            if(square.getPieceType() == NONE)
                return true;
            else
                return false;
        }
        else 
            return false;
    }

    boolean canMove()
    {
        int y;
        if(pieceType==WHITE)
            {
                y=0;
            }
        else if(pieceType==RED)
            {
                y=7;
            }
        else 
            return false;

        if(xPosition == 7 || xPosition == 0)
        {
            if (yPosition == y)
                return false;
        }
        return true;
    }
}