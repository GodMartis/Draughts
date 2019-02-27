import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Board
{
    JFrame a = new JFrame(); // Create a blank window
    JPanel panel = new JPanel(); // Create a panel
    GridLayout layout = new GridLayout(8,8);
    public Board()
    {
        a.setContentPane(panel); // Use panel on Window
        panel.setLayout(layout);
        a.setVisible(true); // Make it visible
        a.setTitle("Draughts"); // Change window title
        a.setSize(800,800); // Change window size
        a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        a.setResizable(false);
    }
    void loadSquares()
    {
        Square[] square = new Square[64];
        ImageIcon white = new ImageIcon("img/empty.png");
        ImageIcon black = new ImageIcon("img/empty2.png");
        for(int i = 0;i<64;i=i+8)
        {
            for(int a = i;a<i+8;a=a+2)
            {
                square[a] = new Square();
                square[a].button.setIcon(black);
                panel.add(square[a].button);
                square[a+1] = new Square();
                square[a+1].button.setIcon(white);
                panel.add(square[a+1].button);
                System.out.println("printed 2");
            }
            i=i+8;
            for(int a = i;a<i+8;a=a+2)
            {
                square[a] = new Square();
                square[a].button.setIcon(white);
                panel.add(square[a].button);
                square[a+1] = new Square();
                square[a+1].button.setIcon(black);
                panel.add(square[a+1].button);
            }
        } 
        a.setVisible(true); // Make it visible
    }

}