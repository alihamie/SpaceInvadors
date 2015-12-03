import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener
{
    private Random random = new Random();
    private Timer paint_timer;
    private Timer game_timer;
    private Timer invader_shoot_timer;
    private Timer invader_move_timer;
    private Timer heartbeat_timer;
    private int heartbeat_stepper;

    private InvaderGrid invaders;
    private ArrayList<Bullet> bullets;
    private Player player;
    private int score;
    private int lives;

    private boolean key_left_down = false;
    private boolean key_right_down = false;
    private boolean key_space_down = false;


    public GamePanel(Dimension size) {
        setPreferredSize(size);
        setSize(size);
        setBackground(Color.BLACK);
        makeKeyBindings();

        // Create Timers for the first time
        game_timer = new Timer(10, this);
        paint_timer = new Timer(10, this);
        invader_shoot_timer = new Timer(1000, this);
        invader_move_timer = new Timer(557, this);
        heartbeat_timer = new Timer(invader_move_timer.getInitialDelay(), this);

        init();
    }

    private void reset() {
        invader_move_timer.stop();
        invader_shoot_timer.stop();
        heartbeat_timer.stop();
        game_timer.stop();
        paint_timer.stop();

        paint_timer.setDelay(paint_timer.getInitialDelay());
        game_timer.setDelay(game_timer.getInitialDelay());
        invader_move_timer.setDelay(invader_move_timer.getInitialDelay());
        invader_shoot_timer.setDelay(invader_shoot_timer.getInitialDelay());
        heartbeat_timer.setDelay(heartbeat_timer.getInitialDelay());

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
        System.out.println(player.getY());
        score = 0;
        lives = 3;
        heartbeat_stepper = 0;

//        paint_timer.start();
        game_timer.start();
        invader_move_timer.start();
        invader_shoot_timer.start();
        heartbeat_timer.start();
    }

    private void makeKeyBindings() {
        getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "fireBullet");
        getActionMap().put("fireBullet", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                key_space_down = true;
            }
        });
        getInputMap().put(KeyStroke.getKeyStroke("released SPACE"), "fireBullet_release");
        getActionMap().put("fireBullet_release", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                key_space_down = false;
            }
        });

        getInputMap().put(KeyStroke.getKeyStroke("pressed LEFT"), "moveLeft");
        getActionMap().put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                key_left_down = true;
            }
        });
        getInputMap().put(KeyStroke.getKeyStroke("released LEFT"), "moveLeft_release");
        getActionMap().put("moveLeft_release", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                key_left_down = false;
            }
        });

        getInputMap().put(KeyStroke.getKeyStroke("pressed RIGHT"), "moveRight");
        getActionMap().put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                key_right_down = true;
            }
        });
        getInputMap().put(KeyStroke.getKeyStroke("released RIGHT"), "moveRight_release");
        getActionMap().put("moveRight_release", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                key_right_down = false;
            }
        });

        getInputMap().put(KeyStroke.getKeyStroke("R"), "reset");
        getActionMap().put("reset", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
                firePropertyChange("game_reset", false, true);
            }
        });

        getInputMap().put(KeyStroke.getKeyStroke("M"), "mute");
        getActionMap().put("mute", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sounds.toggleMuteAll();
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if(source == invader_shoot_timer) {
            int counter = 0;
            for (int i = 0; i < bullets.size() && counter < 3; i++) {
                if (bullets.get(i).getSource() == Bullet.Source.ENEMY) {
                    counter++;
                }
            }
            if (counter < 3) {
                bullets.add(invaders.getRandomBullet());
            }
            invader_shoot_timer.setDelay(random.nextInt(750) + 50);

        } else if (source == invader_move_timer) {
            invaders.performMovement(0, getWidth());

        } else if (source == heartbeat_timer) {
            switch (heartbeat_stepper) {
                case 0: Sounds.INVADERS_0.play(); heartbeat_stepper = 1; break;
                case 1: Sounds.INVADERS_1.play(); heartbeat_stepper = 2; break;
                case 2: Sounds.INVADERS_2.play(); heartbeat_stepper = 3; break;
                case 3: Sounds.INVADERS_3.play(); heartbeat_stepper = 0; break;
            }
        } else if (source == game_timer){
            playGame();
        } else {
            repaint();
        }
    }



    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBullets(g);
        invaders.draw(g);
        player.draw(g);
    }

    private void playGame() {
        destroyBullets();
        checkCollision();

        repaint();

        moveBullets();
        movePlayer();
        fireBulletPlayer();

        if (invaders.isEmpty()) { // no more enemies left
            heartbeat_timer.stop();
            invader_shoot_timer.stop();
            invader_move_timer.stop();
            game_timer.stop();
            paint_timer.stop();
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

    private void drawBullets(Graphics g) {
        for (int i = 0; i < bullets.size(); ++i) {
            bullets.get(i).draw(g);
        }
    }

    private void moveBullets() {
        for (int i = 0; i < bullets.size(); ++i) {
            if (bullets.get(i).getSource() == Bullet.Source.ENEMY) {
                bullets.get(i).moveDown(5);
            } else if  (bullets.get(i).getSource() == Bullet.Source.PLAYER) {
                bullets.get(i).moveUp(8);
            }
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
                accelerateHeartbeat();
                System.out.println("HIT! Points awarded: " + points_added + "   Score: " + score);
                bullets.remove(i);
                if (invaders.numInvadersNow() == 0) {
                    firePropertyChange("player_won", false, true);
                }
                break;
            }

            // Player bullet collisions
            if (player.hitByBullet(current)) {
                lives--;
                System.out.printf("OUCH! Lives: " + lives + "\n");
                firePropertyChange("lives_update", lives + 1, lives);
                bullets.clear();
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

    private void accelerateHeartbeat() {
        if (invaders.numInvadersNow() % 5 == 0) {
            heartbeat_timer.setDelay(heartbeat_timer.getDelay() - 45);
        }
    }

}