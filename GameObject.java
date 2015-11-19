/*******************************

	This would be the base class of our 
	GameObjects such as different kind of enemies
	and different players

*******************************/
import com.sun.javafx.collections.MappingChange;

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
	

	public abstract Rectangle getBounds();
	public abstract void draw(Graphics g);
	public abstract void move(int dx);
	public abstract void moveDown(int dy);
	//public abstract void shoot(Graphics g);
	public abstract int getX();
	public abstract Bullet getBullet();

	public GameObject(Type type, ImageIcon sprite)
	{
		this.sprite = sprite;
		this.type = type;
	}


	public ImageIcon getSprite()
	{
		return this.sprite;

	}

}