import java.awt.*;

/**
 * This class is used to have general information about the invader.
 * This includes information on the version the invader is and location
 */
public class Invader extends GameObject
{
    public enum Version {SMALL, MEDIUM, LARGE}

    private Version version;

    //stores values of how big we want each invader to be
    public static final int HEIGHT  = 30;
    public static final int WIDTH   = 30;
    public static final int HEIGHT_PAD  = 5;
    public static final int WIDTH_PAD   = 5;
    public static final int TOTAL_HEIGHT  = HEIGHT + HEIGHT_PAD * 2;
    public static final int TOTAL_WIDTH  = WIDTH + WIDTH_PAD * 2;


    Invader(Version version, int x, int y)
    {
        super(GameObject.Type.ENEMY,
                version == Version.SMALL ? Sprites.SMALL_INVADER :
                (version == Version.MEDIUM ? Sprites.MEDIUM_INVADER : Sprites.LARGE_INVADER));
        this.x = x;
        this.y = y;
        this.version = version;
    }

    //to be drawn gets the image from base class
    public void draw(Graphics g)
    {
        g.drawImage(super.getSprite().getImage(), x, y, WIDTH, HEIGHT, null);
    }


    public Bullet getBullet()
    {
        return new Bullet(x + WIDTH / 2, y + HEIGHT / 2, 3, 6, Bullet.Source.ENEMY);
    }
    public boolean hitByBullet(Bullet bullet)
    {
        return bullet.getSource() == Bullet.Source.PLAYER && getBounds().intersects(bullet.getBounds());
    }

    public Rectangle getBounds()
    {
        return new Rectangle(x,y,WIDTH,HEIGHT);
    }

}
