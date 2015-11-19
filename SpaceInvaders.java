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
	public static int ROW = 3;
	public static int COL = 10;

	public static void main(String[] args)
	{

		ArrayList<ArrayList< GameObject > > enemies = new ArrayList<ArrayList< GameObject>> ();

		init_invaders(enemies);
	
		//regular JFrame procedure
		JFrame frame = new JFrame("Space Invaders");
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

		frame.setSize(900, 500); // set frame size
		frame.setBackground(Color.BLACK);

		GamePanel game = new GamePanel(enemies);
		frame.add(game);

		frame.setVisible( true ); // display frame

	}

	public static void init_invaders(ArrayList<ArrayList<GameObject>>  enemies ) {

		//initializes them "Invader" is a type of enemy
		int v_distance = Invader.TOTAL_HEIGHT;
		for (int i = 0; i < ROW; ++i) {
			enemies.add(new ArrayList<GameObject>());
			int h_distance = Invader.TOTAL_WIDTH;
			for (int j = 0; j < COL; ++j) {
				enemies.get(i).add(new Invader(Invader.Version.MEDIUM, h_distance, v_distance));

				h_distance += Invader.TOTAL_WIDTH;
			}
			v_distance += Invader.TOTAL_HEIGHT;
		}

	}


}