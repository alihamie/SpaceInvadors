import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener, KeyListener
{
    private Timer paint_timer;
    private Timer invader_shoot_timer;
    private Timer invader_move_timer;

    private InvaderGrid invaders;
    private ArrayList<Bullet> bullets;
    private Player player;
    private int score;
    private int lives;

    private boolean key_left_down = false;
    private boolean key_right_down = false;
    private boolean key_space_down = false;


    public GamePanel(Dimension size) {
        setSize(size);
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        // Create Timers for the first time
        paint_timer = new Timer(10, this);
        invader_shoot_timer = new Timer(1000, this);
        invader_move_timer = new Timer(560, this);

        init();
    }

    private void reset() {
        paint_timer.stop();
        invader_move_timer.stop();
        invader_shoot_timer.stop();

        paint_timer.setDelay(paint_timer.getInitialDelay());
        invader_move_timer.setDelay(invader_move_timer.getInitialDelay());
        invader_shoot_timer.setDelay(invader_shoot_timer.getInitialDelay());

        init();
    }

    /**
     * Sets the initial variables for the game and starts the timers
     * Timers MUST BE created or reset before this function is called
     */
    public void init() {
        invaders = new InvaderGrid();
        bullets = new ArrayList<>();
        player = new Player(getWidth() / 2 - Player.WIDTH / 2, getHeight() * 8 / 10);
        score = 0;
        lives = 3;

        invader_move_timer.start();
        invader_shoot_timer.start();
        paint_timer.start();
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
         if(source == invader_shoot_timer) {
             bullets.add(invaders.getRandomBullet());
        } else if (source == invader_move_timer) {
             invaders.performMovement(0, getWidth());
        } else {
             repaint();
        }
    }




    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:  key_left_down  = true; break;
            case KeyEvent.VK_RIGHT: key_right_down = true; break;
            case KeyEvent.VK_SPACE: key_space_down = true; break;
            case KeyEvent.VK_R: reset(); break;
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:  key_left_down  = false; break;
            case KeyEvent.VK_RIGHT: key_right_down = false; break;
            case KeyEvent.VK_SPACE: key_space_down = false; break;
        }
    }



    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        destroyBullets();

        checkCollision();
        moveAndDrawBullets(g);
        invaders.draw(g);
        player.draw(g);

        movePlayer();
        fireBulletPlayer();

        if (invaders.isEmpty()) { // no more enemies left
            paint_timer.stop();
            invader_shoot_timer.stop();
            invader_move_timer.stop();
            System.out.println("\nNo More Enemies!");
        }
    }



    private void destroyBullets() {
        for (int i = 0; i < bullets.size() ; ++i) {
            if(bullets.get(i).getY() > this.getHeight() || bullets.get(i).getY() < 0) {
                bullets.remove(i);
            }
        }
    }

    private void moveAndDrawBullets(Graphics g) {
        for (int i = 0; i < bullets.size(); ++i) {
            if (bullets.get(i).getSource() == Bullet.Source.ENEMY) {
                bullets.get(i).moveDown(3);
            } else if  (bullets.get(i).getSource() == Bullet.Source.PLAYER) {
                bullets.get(i).moveUp(8);
            }
            bullets.get(i).draw(g);
        }
    }


    // Move the Player
    private void movePlayer () {
        if (key_left_down && player.getX() > Player.WIDTH_PAD) {
            player.moveLeft(3);
        }
        if (key_right_down && player.getX() + Player.WIDTH + Player.WIDTH_PAD < getWidth()) {
            player.moveRight(3);
        }
    }

    private void fireBulletPlayer () {
        int bullets_in_play = 0;
        for (int i = 0; i < bullets.size(); i++) {
            if (bullets.get(i).getSource() == Bullet.Source.PLAYER) {
                bullets_in_play++;
            }
        }

        if (key_space_down && bullets_in_play < 1) {
            bullets.add(player.getBullet());
        }
    }


    private void checkCollision() {
        int points_added = 0;
        for (int i = 0; i < bullets.size() ; i++ ) {
            Bullet current = bullets.get(i);

            // Invaders bullet collisions
            points_added += invaders.runBulletCollision(current);
            if (points_added > 0) {
                score += points_added;
                firePropertyChange("score_update", score - points_added, score);
                invader_move_timer.setDelay(invader_move_timer.getDelay() - 10);
                System.out.println("HIT! Points awarded: " + points_added + "   Score: " + score);
                bullets.remove(i);
                break;
            }

            // Player bullet collisions
            if (player.hitByBullet(current)) {
                System.out.printf("OUCH! Lives: " + (lives - 1) + "\n");
                firePropertyChange("lives_update", lives, lives - 1);
                lives--;
                bullets.remove(i);
                break;
            }

            // Bullet to bullet collision
            boolean crash = false;
            for (int j = 0; j < bullets.size(); j++) {
                Bullet possible_crash = bullets.get(j);
                if (current != possible_crash){
                    if (current.hitByBullet(possible_crash)) {
                        System.out.println("Bullets crashed!");
                        bullets.remove(current);
                        bullets.remove(possible_crash);
                        crash = true;
                    }
                }
                if (crash) break;
            }
            if (crash) break;
        }



    }




    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        setFocusable(false);
    }
}