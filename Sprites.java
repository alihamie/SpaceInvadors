/****

This class will have all the sprites so we can access them from
anywhere.

****/

import javax.swing.ImageIcon;

public abstract class Sprites {
	public static final ImageIcon SMALL_INVADER_0 = new ImageIcon(Sprites.class.getResource("/sprites/small_invader_0.png"));
	public static final ImageIcon SMALL_INVADER_1 = new ImageIcon(Sprites.class.getResource("/sprites/small_invader_1.png"));
	public static final ImageIcon MEDIUM_INVADER_0 = new ImageIcon(Sprites.class.getResource("/sprites/medium_invader_0.png"));
	public static final ImageIcon MEDIUM_INVADER_1 = new ImageIcon(Sprites.class.getResource("/sprites/medium_invader_1.png"));
	public static final ImageIcon LARGE_INVADER_0 = new ImageIcon(Sprites.class.getResource("/sprites/large_invader_0.png"));
	public static final ImageIcon LARGE_INVADER_1 = new ImageIcon(Sprites.class.getResource("/sprites/large_invader_1.png"));
	public static final ImageIcon UFO = new ImageIcon(Sprites.class.getResource("/sprites/ufo.png"));
	public static final ImageIcon PLAYER = new ImageIcon(Sprites.class.getResource("/sprites/shooter.png"));
	public static final ImageIcon BULLET = new ImageIcon(Sprites.class.getResource("/sprites/bullet.png"));
	public static final ImageIcon DESTROYED = new ImageIcon(Sprites.class.getResource("/sprites/destroyed.png"));
}