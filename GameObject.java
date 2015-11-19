/*******************************

	This would be the base class of our 
	GameObjects such as different kind of enemies
	and different players

*******************************/
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Rectangle;
public abstract class GameObject
{

	//so far only have sprite and a name 
	//would be better to use ENUM for different
	//types of enemies

	private ImageIcon sprite;
	private String name;
	


	public abstract Rectangle getBounds();
	public abstract void draw(Graphics g);
	public abstract void move(int dx);
	public abstract void moveDown(int dy);
	//public abstract void shoot(Graphics g);
	public abstract int getX();
	public abstract Bullet getBullet();

	public GameObject(String name,ImageIcon sprite)
	{

		this.sprite =sprite;
		this.name = name;


	}


	public ImageIcon getSprite()
	{

		return this.sprite;

	}








}