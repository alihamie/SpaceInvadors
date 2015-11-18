
import java.awt.Graphics;
import java.awt.Color;


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
		g.drawRect(x,y,width,height);


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


}