/**********************************

	This is the main class
	

***********************************/
import javax.swing.*;
import java.awt.Color;


public class SpaceInvaders
{
	public static void main(String[] args)
	{
		//regular JFrame procedure
		JFrame frame = new JFrame("Space Invaders");
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

		frame.setSize(900, 500); // set frame size
		frame.setBackground(Color.BLACK);
		GamePanel game = new GamePanel(frame.getSize());
		frame.add(game);

		frame.setVisible( true ); // display frame
	}


}