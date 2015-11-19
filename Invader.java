import java.awt.*;

/**
 * This class is used to have general information about the invader.
 * This includes information on the version the invader is and location
 */
public class Invader extends GameObject
{
    public enum Version {SMALL, MEDIUM, LARGE};

    private Version version;

    //stores values of how big we want each invader to be
    public static final int HEIGHT  = 30;
    public static final int WIDTH   = 30;
    private int x;
    private int y;


    Invader(Version version, int x, int y)
    {
        super(GameObject.Type.ENEMY,
                version == Version.SMALL ? Sprites.SMALL_INVADER :
                (version == Version.MEDIUM ? Sprites.MEDIUM_INVADER : Sprites.LARGE_INVADER));
        this.x = x;
        this.y = y;
        this.version = version;
    }


    public int getX() {
        return this.x;
    }

    public int getY()
    {
        return this.y;
    }


    //to be drawn gets the image from base class
    public void draw(Graphics g)
    {
        g.drawImage(super.getSprite().getImage(), x, y, WIDTH, HEIGHT, null);
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

    public void move(int dx)
    {
        moveX(dx);
    }
    public void moveDown(int dy)
    {
        moveY(dy);
    }

    public Bullet getBullet()
    {
        return new Bullet(x,y,3,6);
    }

    public Rectangle getBounds()
    {
        return new Rectangle(x,y,WIDTH,HEIGHT);
    }

}
