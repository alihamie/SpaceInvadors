
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;

public class Bullet extends GameObject
{
	enum Source {PLAYER, ENEMY} // Enumeration of sources for bullet (allows for enemy bullets to pass by other enemies)


	private Color color = new Color(252, 252, 252); // Almost pure white (as used in the arcade game)
	private Source source;


	public Bullet(int x, int y, int width, int height, Source source) {
		super(Type.BULLET, Sprites.BULLET);
		setLocation(x, y);
		setSize(width, height);
		this.source = source;
	}


	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(getX(), getY(), getWidth(), getHeight());
	}

	public Source getSource() {
		return source;
	}
	public boolean hitByBullet(Bullet bullet) {
		return getBounds().intersects(bullet.getBounds());
	}


}