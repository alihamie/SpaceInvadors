import javax.swing.*;
import java.awt.*;
<<<<<<< HEAD
import java.awt.Component;
=======
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
>>>>>>> f52cfaee35eabf68729decac7067d4cd012af010

public class GamePanel extends JPanel implements ActionListener, KeyListener
{
    private Timer paint_timer;
    private Timer invader_shoot_timer;

<<<<<<< HEAD
	 private int delay = 10;
  	 protected Timer timer;
	 protected Timer shoot_timer; //timer to shoot
	 private int dx = 2;
	 private Container container;
	 private Player player;
	 private ArrayList<Bullet> bullets ;
	 ArrayList<ArrayList<GameObject>>  enemies;
	 boolean initialize = false;
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
		if(!initialize)
		init_player();

		drawInvaders(g);
		move();
		moveBullets(g);
		checkCollision();
		player.draw(g);

	}


	public void init_player()
	{
		player = new Player(getWidth()/2 , getHeight() - Player.HEIGHT );
		initialize = true;

	}

	/**
	
			public void keyPressed(KeyEvent key)
			{
				System.out.printf("hello");

			}

			public void keyReleased(KeyEvent key)
			{
				System.out.printf("released");
			}

			public void keyTyped(KeyEvent key)
			{

				System.out.printf("typed");
			}


		**/

	


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
		int i = rand.nextInt(enemies.size() );
		
		if(enemies.size() > 1 )
		{
			
			int j = rand.nextInt(enemies.get(i).size() );
			bullets.add( enemies.get(i).get(j).getBullet() );
		}
		else {
			
			int j = rand.nextInt(enemies.get(0).size() );
			bullets.add( enemies.get(0).get(j).getBullet() );
		     }

	}

	public void moveBullets(Graphics g)
	{
		for(int i = 0; i < bullets.size() ; ++i)
		{	
			bullets.get(i).moveDown(3);
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
		
		if( enemies.get(0).get(size-1).getX() > getWidth() - Invader.TOTAL_WIDTH )
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

				enemies.get(i).get(j).moveX(dx);

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

=======
    private InvaderGrid invaders;
    private ArrayList<Bullet> bullets;
    private Player player;
>>>>>>> f52cfaee35eabf68729decac7067d4cd012af010


    private boolean key_left_down = false;
    private boolean key_right_down = false;
    private boolean key_space_down = false;


    public GamePanel(Dimension size) {
        setSize(size);
        setFocusable(true);
        addKeyListener(this);

        invaders = new InvaderGrid();
        bullets = new ArrayList<Bullet>();
        player = new Player(getWidth() / 2 - Player.WIDTH / 2, getHeight() * 8 / 10);

        // start the timer
        paint_timer = new Timer(10, this);
        paint_timer.start();
        invader_shoot_timer = new Timer(1000, this);
        invader_shoot_timer.start();
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

         if(source == invader_shoot_timer) {
            bullets.add(invaders.getRandomBullet());
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
            case KeyEvent.VK_R: getParent().add(new GamePanel(getSize())); getParent().remove(this); break;
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
        destroyBullets();
        invaders.draw(g);
        player.draw(g);

        moveAndDrawBullets(g);
        checkCollision();

        movePlayer();
        invaders.performMovement(0, getWidth());
        fireBulletPlayer();

        if (invaders.isEmpty()) { // no more enemies left
            paint_timer.stop();
            invader_shoot_timer.stop();
            System.out.println("\nNo More Enemies!");
        }
    }



    private void destroyBullets() {
        for (int i = 0; i < bullets.size() ; ++i) {
            if(bullets.get(i).getY() > this.getHeight() || bullets.get(i).getY() < 0) {
                bullets.remove(i);
            }
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
        for (int i = 0; i < bullets.size() ; i++ ) {
            // Invaders bullet collisions
            if (invaders.runBulletCollision(bullets.get(i))) {
                System.out.print("HIT! ");
                bullets.remove(i);
                break;
            }

            // Player bullet collisions
            if (player.hitByBullet(bullets.get(i))) {
                System.out.printf("OUCH!\n");
                bullets.remove(i);
            }
        }
    }



}