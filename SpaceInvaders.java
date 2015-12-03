/**********************************

	This is the main class
	

***********************************/
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.net.URL;



public class SpaceInvaders implements KeyListener {

    private static JFrame frame;
    private static GamePanel game;
    private static InfoPanelTop top;
    private static InfoPanelBottom bottom;
    private static MainMenuPanel mainMenu;

    public static Font FONT;

    public static void main(String[] args) {
        setMainFont();
        //Sounds.init();

        //regular JFrame procedure
        frame = new JFrame("Space Invaders");
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setLayout(new BorderLayout());

        frame.setSize(800, 600); // set frame size
        frame.setResizable(false);

        top = new InfoPanelTop(frame.getWidth());
        frame.add(top, BorderLayout.PAGE_START);
        bottom = new InfoPanelBottom(frame.getWidth());
        frame.add(bottom, BorderLayout.PAGE_END);

	
	mainMenu = new MainMenuPanel(50);


        initGame();
        //frame.add(game, BorderLayout.CENTER);
	frame.add(mainMenu, BorderLayout.CENTER);

        frame.setVisible( true ); // display frame
    }

    /**
     * Initialize the game
     */
    private static void initGame() {
        game = new GamePanel(new Dimension(frame.getWidth(), frame.getHeight() - top.getHeight() - bottom.getHeight()));
        game.addPropertyChangeListener("score_update", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                top.setScore((Integer) evt.getNewValue());
            }
        });
        game.addPropertyChangeListener("lives_update", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                bottom.setLives((Integer) evt.getNewValue());
            }
        });
        game.addPropertyChangeListener("game_reset", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                bottom.setLives(3);
                top.setScore(0);
            }
        });
    }

    /**
     * Load the font to be used throughout the game and initialize it as SpaceInvader.FONT
     */
    private static void setMainFont() {
        try {
            URL input = SpaceInvaders.class.getResource("space_invaders.ttf");
            FONT = Font.createFont(Font.TRUETYPE_FONT, new File(input.toURI()));
        } catch (Exception e) {
            System.err.println("Unable to load font: " + e);
            FONT = new Font("Space Invaders", Font.PLAIN, 16);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_M: Sounds.toggleMuteAll(); break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}