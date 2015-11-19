/*************************************

	This is basic invader class
	its the regular space invader enemy

**************************************/



import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;
public class BasicInvader extends GameObject
{

	//stores values of how big we want each invader to be
	public static final int HEIGHT  = 30;
	public static final int WIDTH   = 30;
	private int x;
	private int y;
	

	//calls base class and sets with its type and the sprite
	public BasicInvader(int x, int y)
	{
		super("basic",Sprites.ENEMY);
		this.x = x;
		this.y = y;
		

	}

	public int getX()
	{
		return this.x;

	}
	//to be drawn gets the image from base class
	public void draw(Graphics g)
	{

		g.drawImage( super.getSprite().getImage(),x,y,WIDTH,HEIGHT,null );

	}

	public ImageIcon getSprite()
	{
		return super.getSprite();

	}

	public void move(int dx)
	{
		
		x+= dx;	


	}

	public void moveDown(int dy)
	{
		y+= dy;

	}


	public Bullet getBullet()
	{

		return new Bullet(x,y,3,6);

	}

	public Rectangle getBounds()
	{
		return new Rectangle(x,y,WIDTH,HEIGHT);

	}


	



}