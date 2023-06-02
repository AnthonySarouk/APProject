import javax.swing.JFrame;
import javax.swing.*;


public class GameFrame extends JFrame  {
    JButton reset;
    GamePanel game;

    GameFrame() {
        game = new GamePanel();
        ImageIcon image = new ImageIcon("snake.png");
        this.setIconImage(image.getImage());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.add(game);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

       
    }

    

}
