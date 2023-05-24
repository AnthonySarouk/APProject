import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class starter{
    public static void main(String[] args){
        JFrame frame = new JFrame("Snake");
        ImageIcon image = new ImageIcon("snake.png");
        frame.setSize(1000, 700);
        frame.getContentPane().setBackground(Color.darkGray);
        frame.setIconImage(image.getImage());
        frame.setVisible(true);

    }
}