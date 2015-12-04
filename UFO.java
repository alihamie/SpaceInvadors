import java.awt.*;
import java.util.Random;

public class UFO extends GameObject{
    private static Random random = new Random();
    public static final int HEIGHT  = 30;
    public static final int WIDTH   = 50;
    public static final int HEIGHT_PAD  = 5;
    public static final int WIDTH_PAD   = 5;
    public static final int TOTAL_HEIGHT  = HEIGHT + HEIGHT_PAD * 2;
    public static final int TOTAL_WIDTH  = WIDTH + WIDTH_PAD * 2;

    public static final int POINTS_1 = 100, POINTS_2 = 300;

    UFO(int x, int y) {
        super(GameObject.Type.ENEMY, Sprites.UFO);

        setLocation(x, y);
        setSize(WIDTH, HEIGHT);

        Sounds.UFO_LOOP.loop(-10);
    }

    //to be drawn gets the image from base class
    public void draw(Graphics g) {
        g.drawImage(super.getSprite().getImage(), getX(), getY(), WIDTH, HEIGHT, null);
    }


    public void stopSoundLoop() {
        Sounds.UFO_LOOP.stop();
    }

    public int getPoints() {
        int points = 0;
        switch (random.nextInt(2)) {
            case 0: points = POINTS_1; break;
            case 1: points = POINTS_2; break;
        }
        return points;
    }
    public boolean hitByBullet(Bullet bullet) {
        boolean hit = bullet.getSource() == Bullet.Source.PLAYER &&  getBounds().intersects(bullet.getBounds());
        if (hit) {
            Sounds.UFO_LOOP.stop();
            Sounds.UFO_DESTROYED.play(-5);
        }
        return hit;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        Sounds.UFO_LOOP.stop();
    }
}
