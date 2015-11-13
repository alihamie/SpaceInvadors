/*************************************

	This is basic invader class
	its the regular space invader enemy

**************************************/



import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Color;

public class BasicInvader extends GameObject
{

	//stores values of how big we want each invader to be
	public static int HEIGHT = 50;
	public static int WIDTH = 50;

	

	//calls base class and sets with its type and the sprite
	public BasicInvader()
	{
		super("basic",Sprites.ENEMY);
		
		

	}


	//to be drawn gets the image from base class
	public void draw(Graphics g,int x,int y)
	{

		g.drawImage( super.getSprite().getImage(),x,y,HEIGHT,WIDTH,null );

	}



}