import java.awt.*;

/**
 * This class is used to have general information about the invader.
 * This includes information on the version the invader is and location
 */
public class Invader extends GameObject
{
    public enum Version {SMALL, MEDIUM, LARGE}

    private Version version;
    private int current_sprite;
    private boolean hittable = true;

    //stores values of how big we want each invader to be
    public static final int HEIGHT  = 15;
    public static final int WIDTH   = 25;
    public static final int HEIGHT_PAD  = 10;
    public static final int WIDTH_PAD   = 10;
    public static final int TOTAL_HEIGHT  = HEIGHT + HEIGHT_PAD * 2;
    public static final int TOTAL_WIDTH  = WIDTH + WIDTH_PAD * 2;


    Invader(Version version, int x, int y)
    {
        super(GameObject.Type.ENEMY,
                version == Version.SMALL ? Sprites.SMALL_INVADER_0 :
                (version == Version.MEDIUM ? Sprites.MEDIUM_INVADER_0 : Sprites.LARGE_INVADER_0));
        current_sprite = 0;

        setLocation(x, y);
        setSize(WIDTH, HEIGHT);
        this.version = version;
    }

    //to be drawn gets the image from base class
    public void draw(Graphics g) {
        g.drawImage(super.getSprite().getImage(), getX(), getY(), WIDTH, HEIGHT, null);
    }

    public void toggleSprite() {
        current_sprite = current_sprite == 0 ? 1 : 0;
        switch (version) {
            case SMALL:
                if (current_sprite == 0)
                    super.setSprite(Sprites.SMALL_INVADER_0);
                else
                    super.setSprite(Sprites.SMALL_INVADER_1);
                break;
            case MEDIUM:
                if (current_sprite == 0)
                    super.setSprite(Sprites.MEDIUM_INVADER_0);
                else
                    super.setSprite(Sprites.MEDIUM_INVADER_1);
                break;
            case LARGE:
                if (current_sprite == 0)
                    super.setSprite(Sprites.LARGE_INVADER_0);
                else
                    super.setSprite(Sprites.LARGE_INVADER_1);
                break;
        }
    }

    public int getPoints() {
        int result = 0;
        switch (version) {
            case SMALL: result = 30; break;
            case MEDIUM: result = 20; break;
            case LARGE: result = 10; break;
        }
        return result;
    }


    public Bullet getBullet() {
        return new Bullet(getX() + WIDTH / 2 - 2, getY() + HEIGHT / 2, 4, 8, Bullet.Source.ENEMY);
    }
    public boolean hitByBullet(Bullet bullet) {
        boolean hit = bullet.getSource() == Bullet.Source.PLAYER && getBounds().intersects(bullet.getBounds()) && hittable;
        if (hit) {
            super.setSprite(Sprites.DESTROYED);
            Sounds.INVADER_KILLED.play(-15);
            hittable = false;
        }
        return hit;
    }

}
