/**********************************

	This is the main class
	

***********************************/
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.util.ArrayList;


public class SpaceInvaders
{

	//how many invaders in the grid
	public static int ROW = 1;
	public static int COL = 10;

	public static void main(String[] args)
	{

		ArrayList<ArrayList< GameObject > > enemies = new ArrayList<ArrayList< GameObject>> ();

		init_invaders(enemies);
	
		//regural JFrame procedure
		JFrame frame = new JFrame("Space Invaders");
		frame.setBackground(Color.BLACK);
		GamePanel game = new GamePanel(enemies);
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.setSize(900, 500 ); // set frame size
		frame.add(game);
      		frame.setVisible( true ); // display frame




	}

	public static void init_invaders(ArrayList<ArrayList<GameObject>>  enemies )
	{

	
		
		//initializes them "BasicInvader" is a type of enemy
		int v_distance = Sprites.ENEMY.getIconHeight() ;
		for(int i = 0; i < ROW ; ++i)
		{
			enemies.add(new ArrayList<GameObject> () );
			int h_distance = Sprites.ENEMY.getIconWidth();
			for(int j  =0 ; j < COL; ++j)
			{
				enemies.get(i).add(new BasicInvader(h_distance,v_distance) ) ;
				
				h_distance+= Sprites.ENEMY.getIconWidth();
			}
			v_distance += Sprites.ENEMY.getIconHeight();
		}


		
	}


}