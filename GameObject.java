/*******************************

	This would be the base class of our 
	GameObjects such as different kind of enemies
	and different players

*******************************/
import javax.swing.ImageIcon;
import java.awt.Graphics;

public abstract class GameObject
{

	//so far only have sprite and a name 
	//would be better to use ENUM for different
	//types of enemies

	private ImageIcon sprite;
	private String name;



	public abstract void draw(Graphics g,int x,int y);

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