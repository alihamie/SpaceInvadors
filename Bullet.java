
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;

public class Bullet extends GameObject
{
	enum Source {SHOOTER, ENEMY}; // Enumeration of sources for bullet (allows for enemy bullets to pass by other enemies)


	private int width;
	private int height;
	private Color color = new Color(252, 252, 252); // Almost pure white (as used in the arcade game)
	private Source source;


	public Bullet(int x,int y,int width,int height, Source source)
	{
		super(Type.BULLET, Sprites.BULLET);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.source = source;

	}


	public void draw(Graphics g)
	{
		// g.setColor(color);
		g.fillRect(x,y,width,height);

	}


	public Rectangle getBounds()
	{
		return new Rectangle(x,y,width,height);

	}

	public Source getSource()
	{
		return source;
	
	}


}