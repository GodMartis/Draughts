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

    private String pieceType;
    private int xPosition;
    private int yPosition;
    private int arrayPosition;
    JButton button;
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
    public int getxPosition() {
        return xPosition;
    }
    /**
     * @return the yPosition
     */
    public int getyPosition() {
        return yPosition;
    }
    /**
     * @return the arrayPosition
     */
    public int getArrayPosition() {
        return arrayPosition;
    }
    void moveTo(Square square)
    {
        ImageIcon white = new ImageIcon("img/empty.png"); // Create white ImageIcon
        ImageIcon black = new ImageIcon("img/empty2.png"); // Create black ImageIcon
        ImageIcon redPiece = new ImageIcon("img/red.png");
        ImageIcon whitePiece = new ImageIcon("img/white.png");
        square.pieceType = pieceType;
        square.button.setIcon(button.getIcon());
        pieceType = NONE;
        button.setIcon(white);
    }
    boolean canMoveTo(Square square, Board board)
    {
        int canArrayLocation1, canArrayLocation2;

        if(pieceType==WHITE)
        {
            canArrayLocation1 = getArrayPosition()-7;
            canArrayLocation2 = getArrayPosition()-9;
        }
        else if(pieceType==RED)
        {
            canArrayLocation1 = getArrayPosition()+7;
            canArrayLocation2 = getArrayPosition()+9;
        }
        else return false;
        if(this==square) 
        {
            board.highlight(canArrayLocation1);
            board.highlight(canArrayLocation2);
            System.out.println("HIGHLIGHT");
            return true;
        }
        else
        {
            if(square.getArrayPosition() == canArrayLocation1 || square.getArrayPosition() == canArrayLocation2) 
                {
                    board.setToWhite(canArrayLocation1);
                    board.setToWhite(canArrayLocation2);
                    return true;
                }
            else 
                return false;
        }


    }
}