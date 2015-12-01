import javax.swing.*;
import java.awt.*;
import java.awt.Component;
import java.awt.event.*;
import java.util.ArrayList;

import java.util.Random;


public class GamePanel extends JPanel implements ActionListener, KeyListener
{
    private Timer paint_timer;
    private Timer invader_shoot_timer;
    private Timer invader_move_timer;
    private BoxLayout boxlayout;

    private InvaderGrid invaders;
    private ArrayList<Bullet> bullets;
    private Player player;
    private int score;

    private boolean key_left_down = false;
    private boolean key_right_down = false;
    private boolean key_space_down = false;


    public GamePanel(Dimension size) {
        setSize(size);
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        invaders = new InvaderGrid();
        bullets = new ArrayList<>();
        player = new Player(getWidth() / 2 - Player.WIDTH / 2, getHeight() * 8 / 10);
        score = 0;

        // start the timer
        paint_timer = new Timer(10, this);
        paint_timer.start();
        invader_shoot_timer = new Timer(1000, this);
        invader_shoot_timer.start();
        invader_move_timer = new Timer(557, this);
        invader_move_timer.start();
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
            case KeyEvent.VK_R: getParent().add(new GamePanel(getSize())); getParent().remove(this); break;
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
        invaders.draw(g);
        player.draw(g);

        moveAndDrawBullets(g);
        checkCollision();

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
            // Invaders bullet collisions
            points_added += invaders.runBulletCollision(bullets.get(i));
            if (points_added > 0) {
                score += points_added;
                invader_move_timer.setDelay(invader_move_timer.getDelay() - 10);
                System.out.println("HIT! Points awarded: " + points_added + "   Score: " + score);
                bullets.remove(i);
                break;
            }

            // Player bullet collisions
            if (player.hitByBullet(bullets.get(i))) {
                System.out.printf("OUCH!\n");
                bullets.remove(i);
            }
        }
    }


    public int getScore() {
        return score;
    }

}