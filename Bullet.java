
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;

public class Bullet
{
	enum SOURCE {SHOOTER, ENEMY}; // Enumeration of sources for bullet (allows for enemy bullets to pass by other enemies)


	private int width;
	private int height;
	private int x;
	private int y;
	private Color color = new Color(252, 252, 252); // Almost pure white (as used in the arcade game)
	private SOURCE source;


	public Bullet(int x,int y,int width,int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

	}


	public void draw(Graphics g)
	{
		// g.setColor(color);
		g.fillRect(x,y,width,height);

	}

	public int getY()
	{
		return this.y;

	}

	public int getX()
	{
		return this.x;

	}

	public void move(int dy)
	{
		y+=dy;

	}

	public Rectangle getBounds()
	{
		return new Rectangle(x,y,width,height);

	}


}