/**********************************

	This is the main class
	

***********************************/
import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.net.URL;


public class SpaceInvaders {
    private static JFrame frame;
    private static GamePanel game;
    private static InfoPanelTop top;
    private static InfoPanelBottom bottom;

    public static Font FONT;

    public static void main(String[] args) {
        setMainFont();

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

        initGame();
        frame.add(game, BorderLayout.CENTER);

        frame.setVisible( true ); // display frame
    }

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
    }

    private static void setMainFont() {
        try {
            URL input = SpaceInvaders.class.getResource("space_invaders.ttf");
            FONT = Font.createFont(Font.TRUETYPE_FONT, new File(input.toURI()));
        } catch (Exception e) {
            System.err.println("Unable to load font: " + e);
            FONT = new Font("Space Invaders", Font.PLAIN, 16);
        }
    }

}