
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
import java.awt.Image;
import javax.swing.Timer;
import java.awt.event.*;

public class GamePanel extends JPanel implements ActionListener
{

	 private int delay = 10;
  	 protected Timer timer;
	 protected Timer shoot_timer; //timer to shoot
	 private int dx = 2;
	 private Container container;
	 private ArrayList<Bullet> bullets ;
	ArrayList<ArrayList<GameObject>>  enemies;

	public GamePanel(ArrayList<ArrayList<GameObject>> enemies)
	{
		//this works same way as clone() 
		container = this;
		this.bullets = new ArrayList<Bullet>();
		this.enemies= new ArrayList<ArrayList<GameObject>>(enemies) ;
		timer = new Timer(delay, this);
		shoot_timer = new Timer(1000,this);
		shoot_timer.start();
		timer.start();		// start the timer
	}



	 public void actionPerformed(ActionEvent e)
   	{
		if(e.getSource() == shoot_timer )
		{	
			//bullets.clear();
			randomShoot(enemies);
			
		}
		else
		repaint();
   	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	
		//calls the draw function of the gameObjects
		drawInvaders(g);
		
		
		move();
		
		moveBullets(g);

	}


	public void destroyBullets()
	{
		for(int i = 0; i < bullets.size() ; ++i)
		{
			if(bullets.get(i).getY() > container.getHeight() )
			{
				bullets.remove(i);
			}

		}


	}

	public void randomShoot(ArrayList<ArrayList<GameObject>> enemies)
	{
		Random rand = new Random();
		int i = rand.nextInt(enemies.size() -1);
		int j = rand.nextInt(enemies.get(i).size() -1);

		bullets.add( enemies.get(3).get(j).getBullet() );


	}

	public void moveBullets(Graphics g)
	{
		for(int i = 0; i < bullets.size() ; ++i)
		{	
			bullets.get(i).move(3);
		}

		for(int i = 0; i < bullets.size() ; ++i)
		{	bullets.get(i).draw(g);
			
		}

	}
	

	public void drawInvaders(Graphics g)
	{

		for(int i =0; i < enemies.size() ; ++i)
		{	
			
			for(int j = 0; j < enemies.get(i).size() ; ++j)
			{
				
				enemies.get(i).get(j).draw(g);
				
			}
			
		}


	}

	//decides if we move right or left
	public void move()
	{

		int size = enemies.get(0).size();

		if( enemies.get(0).get(size-1).getX() > getWidth() - Sprites.WIDTH ) 
		{	
			dx = -2;
			moveDown(enemies,15);
		}

		if( enemies.get(0).get(0).getX() < 30 )
		{
			dx = 2;
			moveDown(enemies,15);

		}		
		moveInvaders(enemies,dx);

	}

	//used to move right and left
	public void moveInvaders(ArrayList<ArrayList<GameObject> > enemies,int dx)
	{

		for(int i = 0; i < enemies.size(); ++i)
		{

			for(int j = 0; j < enemies.get(i).size() ; ++j )
			{

				enemies.get(i).get(j).move(dx);

			}

		}	

		

	}

	//used to move down
	public void moveDown(ArrayList<ArrayList<GameObject> > enemies, int dy)
	{

		
		for(int i = 0; i < enemies.size(); ++i)
		{

			for(int j = 0; j < enemies.get(i).size() ; ++j )
			{

				enemies.get(i).get(j).moveDown(dy);

			}

		}
		

	}
}