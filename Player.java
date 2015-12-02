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
        setLocation(0, 0);
        setSize(WIDTH, HEIGHT);
    }

    Player(int x, int y) {
        super(GameObject.Type.PLAYER, Sprites.PLAYER);
        setLocation(x, y);
        setSize(WIDTH, HEIGHT);

    }

    //to be drawn gets the image from base class
    public void draw(Graphics g)
    {
        g.drawImage(super.getSprite().getImage(), getX(), getY(), WIDTH, HEIGHT, null);
    }


    public Bullet getBullet() {
        Sounds.SHOOT.play(-15);
        return new Bullet(getX() + WIDTH / 2 - 2, getY(), 4, 8, Bullet.Source.PLAYER);
    }
    public boolean hitByBullet(Bullet bullet) {
        return bullet.getSource() == Bullet.Source.ENEMY && getBounds().intersects(bullet.getBounds());
    }

}
