
import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.util.Random;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.Container;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Random;
import java.lang.Math;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class GamePanel extends JPanel
{

	//list of enemies to be drawn
	//okay so I think this should be a double arraylist
	//with columns and rows for the enemies that will act as a
	//Grid or we can make our own GRID class
	ArrayList<GameObject> enemies;

	public GamePanel(ArrayList<GameObject> enemies)
	{
		//this works same way as clone() 
		this.enemies= new ArrayList<GameObject>(enemies) ;

	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	
		//calls the draw function of the gameObjects
		for(int i =0; i < enemies.size(); ++i)
		enemies.get(i).draw(g,i*10 *4,10+50);
		


	}




}