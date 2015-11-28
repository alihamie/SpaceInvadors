import java.awt.*;

/**
 * This class is used to have general information about the invader.
 * This includes information on the version the invader is and location
 */
public class Player extends GameObject
{
    //stores values of how big we want each invader to be
    public static final int HEIGHT  = 30;
    public static final int WIDTH   = 50;
    public static final int HEIGHT_PAD  = 5;
    public static final int WIDTH_PAD   = 5;
    public static final int TOTAL_HEIGHT  = HEIGHT + HEIGHT_PAD;
    public static final int TOTAL_WIDTH  = WIDTH + WIDTH_PAD;


    Player() {
        super(GameObject.Type.PLAYER, Sprites.PLAYER);
        this.x = 0;
        this.y = 0;
    }

    Player(int x, int y) {
        super(GameObject.Type.PLAYER, Sprites.PLAYER);
        this.x = x;
        this.y = y;
    }

    //to be drawn gets the image from base class
    public void draw(Graphics g)
    {
        g.drawImage(super.getSprite().getImage(), x, y, WIDTH, HEIGHT, null);
    }


    public Bullet getBullet()
    {
        return new Bullet(x + WIDTH / 2, y, 3, 6, Bullet.Source.PLAYER);
    }
    public boolean hitByBullet(Bullet bullet)
    {
        return bullet.getSource() == Bullet.Source.ENEMY && getBounds().intersects(bullet.getBounds());
    }

    public Rectangle getBounds()
    {
        return new Rectangle(x,y,WIDTH,HEIGHT);
    }

}
