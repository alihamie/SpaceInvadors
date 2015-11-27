
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
import java.awt.*;

public class GamePanel extends JPanel implements ActionListener
{
	//how many invaders in the grid
	public int ROW = 3;
	public int COL = 10;
	private ArrayList<ArrayList<Invader>> enemies;

	private int delay = 10;
  	protected Timer timer;
	protected Timer shoot_timer; //timer to shoot
	private int dx = 2;
	private ArrayList<Bullet> bullets ;



	public GamePanel()
	{
		//this works same way as clone()
		bullets = new ArrayList<Bullet>();
		enemies = new ArrayList<ArrayList<Invader>>() ;
		init_invaders();

		// start the timer
		timer = new Timer(delay, this);
		timer.start();
		shoot_timer = new Timer(100, this);
		shoot_timer.start();
		
	}

	 public void actionPerformed(ActionEvent e)
   	{
		if(e.getSource() == shoot_timer ) {
			//bullets.clear();
			randomShoot(enemies);
		}
		repaint();

   	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		//calls the draw function of the gameObjects
		drawInvaders(g);
		moveInvaders();
		moveAndDrawBullets(g);
		destroyBullets();
		checkCollision();
		if (enemies.size() == 0) { // no more enemies left
			timer.stop();
			shoot_timer.stop();
			System.out.println("\nNo More Enemies!");
		}
	}

	// Initializes them "Invader" is a type of enemy
	public void init_invaders() {
		int v_distance = 0;
		for (int i = 0; i < ROW; ++i) {
			enemies.add(new ArrayList<Invader>());
			int h_distance = 0;

			v_distance += Invader.HEIGHT_PAD;
			for (int j = 0; j < COL; ++j) {
				h_distance += Invader.WIDTH_PAD;
				enemies.get(i).add(new Invader(Invader.Version.MEDIUM, h_distance, v_distance));
				h_distance += Invader.WIDTH + Invader.WIDTH_PAD;
			}
			v_distance += Invader.HEIGHT + Invader.HEIGHT_PAD;
		}

	}



	public void destroyBullets() {
		for (int i = 0; i < bullets.size() ; ++i) {
			if(bullets.get(i).getY() > this.getHeight() ) {
				bullets.remove(i);
			}
		}
	}

	public void randomShoot(ArrayList<ArrayList<Invader>> enemies) {
		Random rand = new Random();
		int i = rand.nextInt(enemies.size());
		
		if (enemies.size() >= 1 ) {
			int j = rand.nextInt(enemies.get(i).size());
			bullets.add(enemies.get(i).get(j).getBullet());
		} else {
			int j = rand.nextInt(enemies.get(0).size());
			bullets.add(enemies.get(0).get(j).getBullet());
		}

	}

	public void moveAndDrawBullets(Graphics g) {
		for (int i = 0; i < bullets.size(); ++i) {
			bullets.get(i).moveDown(3);
			bullets.get(i).draw(g);
		}
	}
	

	public void drawInvaders(Graphics g) {
		for (int i = 0; i < enemies.size(); ++i)  {
			for (int j = 0; j < enemies.get(i).size(); ++j) {
				enemies.get(i).get(j).draw(g);
			}
		}
	}

	// Used to move invaders
	public void moveInvaders() {
		int size = enemies.get(0).size();
		boolean move_down = false;
		int left_bound = getWidth();
		int right_bound = 0;

		// Get left and right bound of invader table
		// Goes through all the the elements and finds the leftmost and rightmost location (with padding)
		for (int i = 0; i < enemies.size(); i++) {
			for (int j = 0; j < enemies.get(i).size(); j++) {
				if (enemies.get(i).get(j).getX() - Invader.WIDTH_PAD < left_bound) {
					left_bound = enemies.get(i).get(j).getX() - Invader.WIDTH_PAD;

				} else if (enemies.get(i).get(j).getX() + Invader.WIDTH + Invader.WIDTH_PAD > right_bound) {
					right_bound = enemies.get(i).get(j).getX() + Invader.WIDTH + Invader.WIDTH_PAD;
				}
			}
		}

		// Decide to move left or right
		if ( right_bound >= getWidth() ) {
			dx = -2; // move left
			move_down = true;
		} else if ( left_bound <= 0 ) {
			dx = 2; // move right
			move_down = true;
		}

		// Move invaders
		for (int i = 0; i < enemies.size(); ++i) {
			for (int j = 0; j < enemies.get(i).size(); ++j) {
				if (move_down) { // Move invaders down
					enemies.get(i).get(j).moveDown(15);
				}
				enemies.get(i).get(j).moveX(dx); // move invaders left or right
			}
		}
	}


	public void checkCollision() {
		for (int k = 0; k < bullets.size() ; ++k ) {
			for (int i = 0; i < enemies.size(); ++i) {
				for (int j = 0; j < enemies.get(i).size(); ++j) {
					if (enemies.get(i).get(j).hitByBullet(bullets.get(k))) {
						System.out.printf("HIT!");
						enemies.get(i).remove(j);
					}
				}
				if (enemies.get(i).size() == 0) {
					enemies.remove(i);
				}
			}
		}
	}


}