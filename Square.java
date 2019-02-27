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
public class Square
{
    private int xPosition;
    private int yPosition;
    JButton button;
    /**
    * Square constructor
    * @param color is the color of the square
    * @param x is the x position of the square
    * @param y is the y position of the square
    */
    public Square(ImageIcon color, int x, int y)
    {
        button = new JButton(color);
        button.setIcon(color);
        xPosition = x;
        yPosition = y;
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
}