import javax.swing.*;
import java.util.Random;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.awt.event.*;
import java.awt.*;

public class GamePanel extends JPanel implements ActionListener, KeyListener
{
	//how many invaders in the grid
	public int ROW = 5;
	public int COL = 10;

	private ArrayList<ArrayList<Invader>> enemies;
	private Player player;

	private int delay = 10;
  	protected Timer timer;
	protected Timer shoot_timer; //timer to shoot
	private int dx = 2;
	private ArrayList<Bullet> bullets;

	private boolean key_left_down = false;
	private boolean key_right_down = false;
	private boolean key_space_down = false;


	public GamePanel(Dimension size) {
		setSize(size);

		bullets = new ArrayList<Bullet>();
		enemies = new ArrayList<ArrayList<Invader>>() ;
		init_invaders();
		player = new Player(getWidth() / 2 - Player.WIDTH / 2, getHeight() * 8 / 10);

		// start the timer
		timer = new Timer(delay, this);
		timer.start();
		shoot_timer = new Timer(1000, this);
		shoot_timer.start();

		setFocusable(true);
		this.addKeyListener(this);
	}

	 public void actionPerformed(ActionEvent e) {
		if(e.getSource() == shoot_timer) {
			randomShoot(enemies);
		} else {
			repaint();
		}
   	}




	public void keyTyped(KeyEvent e) {

	}

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:  key_left_down  = true; break;
			case KeyEvent.VK_RIGHT: key_right_down = true; break;
			case KeyEvent.VK_SPACE: key_space_down = true; break;
		}
	}

	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:  key_left_down  = false; break;
			case KeyEvent.VK_RIGHT: key_right_down = false; break;
			case KeyEvent.VK_SPACE: key_space_down = false; break;
		}
	}



	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		//calls the draw function of the gameObjects
		movePlayer();
		player.draw(g);
		drawInvaders(g);
		moveInvaders();
		fireBulletPlayer();
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
	private void init_invaders() {
		int v_distance = 0;
		for (int i = 0; i < ROW; ++i) {
			enemies.add(new ArrayList<Invader>());
			int h_distance = 0;

			v_distance += Invader.HEIGHT_PAD;
			for (int j = 0; j < COL; ++j) {
				h_distance += Invader.WIDTH_PAD;
				Invader.Version version;
				if (i < 1) {
					version = Invader.Version.LARGE;
				} else if (i < 3) {
					version = Invader.Version.MEDIUM;
				} else {
					version = Invader.Version.SMALL;
				}
				enemies.get(i).add(new Invader(version, h_distance, v_distance));
				h_distance += Invader.WIDTH + Invader.WIDTH_PAD;
			}
			v_distance += Invader.HEIGHT + Invader.HEIGHT_PAD;
		}

	}



	private void destroyBullets() {
		for (int i = 0; i < bullets.size() ; ++i) {
			if(bullets.get(i).getY() > this.getHeight() || bullets.get(i).getY() < 0) {
				bullets.remove(i);
			}
		}
	}

	private void randomShoot(ArrayList<ArrayList<Invader>> enemies) {
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

	private void moveAndDrawBullets(Graphics g) {
		for (int i = 0; i < bullets.size(); ++i) {
			if (bullets.get(i).getSource() == Bullet.Source.ENEMY) {
				bullets.get(i).moveDown(3);
			} else if  (bullets.get(i).getSource() == Bullet.Source.PLAYER) {
				bullets.get(i).moveUp(8);
			}
			bullets.get(i).draw(g);
		}
	}
	

	private void drawInvaders(Graphics g) {
		for (int i = 0; i < enemies.size(); ++i) {
			for (int j = 0; j < enemies.get(i).size(); ++j) {
				enemies.get(i).get(j).draw(g);
			}
		}
	}

	// Used to move invaders
	private void moveInvaders() {
		int size = enemies.get(0).size();
		boolean move_down = false;
		int left_bound = this.getWidth();
		int right_bound = 0;

		// Get left and right bound of invader table
		// Goes through all the the elements and finds the leftmost and rightmost location (with padding)
		for (int i = 0; i < enemies.size(); i++) {
			for (int j = 0; j < enemies.get(i).size(); j++) {
				if (enemies.get(i).get(j).getX() - Invader.WIDTH_PAD < left_bound) {
					left_bound = enemies.get(i).get(j).getX() - Invader.WIDTH_PAD;
				}
				if (enemies.get(i).get(j).getX() + Invader.WIDTH + Invader.WIDTH_PAD > right_bound) {
					right_bound = enemies.get(i).get(j).getX() + Invader.WIDTH + Invader.WIDTH_PAD;
				}
			}
		}

		// Decide to move left or right
		if ( right_bound >= this.getWidth() ) {
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
					enemies.get(i).get(j).moveDown(Invader.HEIGHT);
				}
				enemies.get(i).get(j).moveX(dx); // move invaders left or right
			}
		}
	}


	// Move the Player
	private void movePlayer () {
		if (key_left_down && player.getX() > Player.WIDTH_PAD) {
			player.moveLeft(4);
		}
		if (key_right_down && player.getX() + Player.WIDTH + Player.WIDTH_PAD < getWidth()) {
			player.moveRight(4);
		}
	}

	private void fireBulletPlayer () {
		int bullets_in_play = 0;
		for (int i = 0; i < bullets.size(); i++) {
			if (bullets.get(i).getSource() == Bullet.Source.PLAYER) {
				bullets_in_play++;
			}
		}

		if (key_space_down && bullets_in_play < 1) {
			bullets.add(player.getBullet());
		}
	}


	private void checkCollision() {
		for (int k = 0; k < bullets.size() ; ++k ) {
			// Enemy bullet collisions
			boolean removed = false;
			for (int i = 0; i < enemies.size(); ++i) {
				for (int j = 0; j < enemies.get(i).size(); ++j) {
					if (enemies.get(i).get(j).hitByBullet(bullets.get(k))) {
						System.out.printf("HIT!");
						enemies.get(i).remove(j);
						bullets.remove(k);
						removed = true;
						break;
					}
				}
				if (enemies.get(i).size() == 0) {
					enemies.remove(i);
				}
				if (removed) break;
			}
			if (removed) break;


			// Player bullet collisions
			if (player.hitByBullet(bullets.get(k))) {
				System.out.printf("OUCH!\n");
				bullets.remove(k);
			}
		}
	}



}