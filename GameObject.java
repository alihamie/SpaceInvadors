/*******************************

	This would be the base class of our 
	GameObjects such as different kind of enemies
	and different players

*******************************/
//import com.sun.javafx.collections.MappingChange;

import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject
{
	public enum Type {SHOOTER, ENEMY, BARRIER, BULLET};

	//so far only have sprite and a name 
	//would be better to use ENUM for different
	//types of enemies

	private ImageIcon sprite;
	private Type type;
	protected int x;
	protected int y;
	

	public abstract Rectangle getBounds();
	public abstract void draw(Graphics g);
	//public abstract void shoot(Graphics g);

	// Quick fix for abstracting bullet and GameObject
	public Bullet getBullet() {
		return null;
	};

	public GameObject(Type type, ImageIcon sprite)
	{
		this.sprite = sprite;
		this.type = type;
	}

	public ImageIcon getSprite()
	{
		return sprite;
	}

	public Type getType()
	{
		return type;
	}



	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public int moveX(int dx)
	{
		x += dx;
		return x;
	}

	public int moveY(int dy)
	{
		y += dy;
		return y;
	}


	public void moveUp(int dy)
	{
		moveY(-dy);
	}

	public void moveDown(int dy)
	{
		moveY(dy);
	}

	public void moveLeft(int dx)
	{
		moveX(-dx);
	}

	public void moveRight(int dx)
	{
		moveX(dx);
	}

}