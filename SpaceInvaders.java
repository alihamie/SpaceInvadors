/**********************************

	This is the main class
	

***********************************/
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.util.ArrayList;


public class SpaceInvaders
{

	

	public static void main(String[] args)
	{

		//creates an array of enemies
		ArrayList<GameObject> enemies = new ArrayList<GameObject>();
		
		//initializes them "BasicInvader" is a type of enemy
		for(int i = 0; i < 10; ++i)
		enemies.add(new BasicInvader());
		
		//regural JFrame procedure
		JFrame frame = new JFrame("Space Invaders");
		frame.setBackground(Color.BLACK);
		GamePanel game = new GamePanel(enemies);
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.setSize(600, 400 ); // set frame size
		frame.add(game);
      		frame.setVisible( true ); // display frame




	}


}