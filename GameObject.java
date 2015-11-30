/*******************************

	This would be the base class of our 
	GameObjects such as different kind of enemies
	and different players

*******************************/
//import com.sun.javafx.collections.MappingChange;

import javax.swing.ImageIcon;
import java.awt.*;

public abstract class GameObject extends Component
{
	public enum Type {PLAYER, ENEMY, BARRIER, BULLET}

	private ImageIcon sprite;
	private Type type;
	

	public abstract void draw(Graphics g);


	public GameObject(Type type, ImageIcon sprite) {
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


	public void setX(int x) {
		setLocation(x, getY());
	}
	public void setY(int y) {
		setLocation(getX(), y);
	}

	public int moveX(int dx) {
		setLocation(getX() + dx, getY());
		return getX();
	}
	public int moveY(int dy) {
		setLocation(getX(), getY() + dy);
		return getY();
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