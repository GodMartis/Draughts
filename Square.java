import javax.swing.*;
/**
 * This is the Square class. 
 * It contains all functions used for piece movement over squares.
 * All square properties are defined here.
 * @author Martynas Dabravalskis
 */
public class Square
{
    private boolean highlighted;
    private String pieceType;
    private int arrayPosition;
    private int yPosition = arrayPosition/8;
    private int xPosition = (arrayPosition%8)*8;
    JButton button;
    private boolean[] jump = new boolean[2];
    private int[] canMoveTo = new int[2];

    /**
     * Square constructor
     * @param color is the color of the square
     * @param xPosition is the x position of the square
     * @param yPosition is the y position of the square
     * @param arrayPosition position of square in the square array
     * @param pieceType piece type of piece on square
     */
    public Square(ImageIcon color, int xPosition, int yPosition,int arrayPosition, String pieceType)
    {
        button = new JButton(color);
        button.setIcon(color);
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.arrayPosition = arrayPosition;
        this.pieceType = pieceType;
    }
    /**
     * Move piece to a selected square and determine whether piece can perform multiple jumps. Force player to make a second jump if multiple jumps are available.
     * @param square square object which piece moves to
     * @param board board object
     * @param turnPieceType piece type of piece which moves next
     */
    public void moveTo(Square square,Board board,String turnPieceType)
    {
        board.setTurnPieceType(pieceType);
        square.pieceType = pieceType;
        square.button.setIcon(button.getIcon());
        pieceType = "NONE";
        board.setToWhite(arrayPosition);
        square.setJump(false, 0);
        square.setJump(false, 1);
        // perform jump
        if(Math.abs(xPosition-square.getxPosition()) > 1)
        {
            int xPos;
            int ypos;
            xPos = square.getxPosition();
            ypos = square.getyPosition();
            xPos = (xPos+xPosition)/2;
            ypos = (ypos+yPosition)/2;
            int piecePosition = ypos*8+xPos;
            board.setToWhite(piecePosition);
            board.getSquare(piecePosition).setPieceType("NONE");
            // check if piece can move after this move
            if(square.canMove("NULL",board)==true && square.getyPosition() !=0 && square.getyPosition() != 7)
                square.SetPosibleMoves(board);
        }
        // check if square can jump after this jump. Force player to jump if possible.
        if(square.getJump(0) == true && square.getJump(1) == true && square.canMoveTo(board.getSquare(square.getcanMoveTo(0))) && square.canMoveTo(board.getSquare(square.getcanMoveTo(1))))
        {
            board.setArrayPosition(square.getArrayPosition());
            board.setLock(true);
            board.highlight(square.getcanMoveTo(0));
            board.highlight(square.getcanMoveTo(1));
        }
        else if(square.getJump(0) == true && square.canMoveTo(board.getSquare(square.getcanMoveTo(0))))
        {
            board.setArrayPosition(square.getArrayPosition());
            board.highlight(square.getcanMoveTo(0));
            board.setLock(true);
        }
        else if(square.getJump(1) == true && square.canMoveTo(board.getSquare(square.getcanMoveTo(1))))
        {
            board.setArrayPosition(square.getArrayPosition());
            board.highlight(square.getcanMoveTo(1));
            board.setLock(true);
        }
        else
        {
            board.setLock(false);
            board.setIsPressed(false);
        }
    }
    /**
     * Set possible move locations from piece on this square
     * @param board board object
     */
    public void SetPosibleMoves(Board board)
    {
        int y = 1; //set for red piece
        int x = 1; //-1 if xPosition is 0
        int disableY = 6;
        // wites move up, reds move down
        if(pieceType=="WHITE")
        {
            y = -1;
            disableY = 1;
        }
        // only 1 possible move if piece is on the left or right edge of the board
        if(xPosition == 7 || xPosition == 0)
        {
            if(xPosition == 7)
                x = -1;
            canMoveTo[0] = (yPosition+y)*8+xPosition+x;
            canMoveTo[1] = canMoveTo[0];
        }
        else
        {
            canMoveTo[0] = (yPosition+y)*8+xPosition+1;
            canMoveTo[1] = (yPosition+y)*8+xPosition-1;
        }
        boolean change = false;
        // determine whether possible move location contains a square, if it does, calculate possible jump
        for(int i=0;i<2;i++)
        {

            if(board.getSquare(canMoveTo[i]).getPieceType() !="NONE" && board.getSquare(canMoveTo[i]).getPieceType() != pieceType && yPosition != disableY)
            {
                int xpos;
                int yPos;
                xpos = board.getSquare(canMoveTo[i]).getxPosition();
                yPos = board.getSquare(canMoveTo[i]).getyPosition();
                xpos = xpos - (xPosition - xpos);
                yPos = yPos - (yPosition - yPos);
                canMoveTo[i] = (yPos)*8+xpos;
                jump[i] = true;
                if(xpos>7 || xpos<0 || yPos>7 || xpos<0)
                    {
                    if(i==1)
                        canMoveTo[1] = canMoveTo[0];
                    else
                        change = true;
                    jump[i] = false;
                    }

            }
        }
        if(change == true)
            canMoveTo[0] = canMoveTo[1];
    }
    /**
     * Return true if piece can move to a selected square
     * @param square square object
     * @return true if piece can move to a selected square
     */
    public boolean canMoveTo(Square square)
    {
        if(square.getArrayPosition() == canMoveTo[0] || square.getArrayPosition() == canMoveTo[1]) 
        {
            if(square.getPieceType() == "NONE")
                return true;
            else
                return false;
        }
        else 
            return false;
    }
    /**
     * Return true if square contains piece which can move
     * @param turn_pieceType piece type of piece which moves next
     * @param board board object
     * @return true if square contains piece which can move
     */
    public boolean canMove(String turn_pieceType, Board board)
    {
        int y;
        if(pieceType == turn_pieceType)
            return false;
        if(pieceType=="WHITE")
            {
                y=0;
            }
        else if(pieceType=="RED")
            {
                y=7;
            }
        else 
            return false;
        if (yPosition == y)
                return false;
        return true;
    }

        //-- Accessors --//

    /**
     * @return the xPosition of the square
     */
    public int getxPosition() 
    {
        return xPosition;
    }
    /**
     * @return the yPosition of the square
     */
    public int getyPosition() 
    {
        return yPosition;
    }
    /**
     * @return the arrayPosition in square array
     */
    public int getArrayPosition() 
    {
        return arrayPosition;
    }
    /**
     * @param n which possible move to return (2 possible)
     * @return possible move array location (position in the square array)
     */
    public int getcanMoveTo(int n)
    {
        return canMoveTo[n];
    }
    /**
     * @return the pieceType
     */
    public String getPieceType()
    {
        return pieceType;
    }
    /**
     * @param n possible move (1,2)
     * @return true if jump is performed on move
     */
    public boolean getJump(int n)
    {
        return jump[n];
    }
    /**
     * @return is square highlighted
     */
    public boolean gethighlighted()
    {
        return highlighted;
    }

        //-- Mutators --//

    /**
     * Set which piece is on square
     * @param pieceType type of piece (WHITE, RED, NONE)
     */
    public void setPieceType(String pieceType)
    {
        this.pieceType = pieceType;
    }
    /**
     * Set true if square highlighted
     * @param highlighted true if square highlighted
     */
    public void setHighlighted(boolean highlighted)
    {
        this.highlighted = highlighted;
    }
    /**
     * Set if piece jumps on possible move
     * @param jump true if jump
     * @param n possible move (1,2)
     */
    public void setJump(boolean jump,int n)
    {
        this.jump[n] = jump;
    }
}
