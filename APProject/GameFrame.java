import javax.swing.JFrame;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class GameFrame extends JFrame implements ActionListener{
	JButton reset;
	GamePanel game = new GamePanel();
	GameFrame(){
		this.add(game);
		this.setTitle("Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==reset){
			game.remove(game);
			game = new GamePanel();
			this.add(game);
			SwingUtilities.updateComponentTreeUI(this);
		}
	}
	
}