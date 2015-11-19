
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;

public class Bullet
{


	private int width;
	private int height;
	private int x;
	private int y;





	public Bullet(int x,int y,int width,int height)
	{

		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;


	}



	public void draw(Graphics g)
	{
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