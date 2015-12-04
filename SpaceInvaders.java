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
    private static MainMenuPanel mainMenu;
    private static ContinuePanel cont;

    public static Font FONT;

    public static void main(String[] args) {
        setMainFont();
        Sounds.init();

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


        initMenu();
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
        game.addPropertyChangeListener("player_won", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                frame.remove(game);
                game.resetAndWait();
                initContinue("YOU WIN!");
                frame.add(cont, BorderLayout.CENTER);
                frame.revalidate();
                cont.requestFocus();
            }
        });


        game.addPropertyChangeListener("player_lose", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                frame.remove(game);
                game.resetAndWait();
                initContinue("YOU LOSE! Earth has been destroyed!");
                frame.add(cont, BorderLayout.CENTER);
                frame.revalidate();
                cont.requestFocus();

            }
        });
    }

    private static void initMenu() {
        mainMenu = new MainMenuPanel(50);
        mainMenu.addPropertyChangeListener("start_game_selected", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println("Starting Game");
                frame.remove(mainMenu);
                if (game == null) {
                    initGame();
                } else {
                    game.init();
                }
                frame.add(game, BorderLayout.CENTER);
                // Makes sure game has focus
                frame.revalidate();
                game.requestFocus();
            }
        });
        mainMenu.addPropertyChangeListener("options_selected", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println("Starting Options");
            }
        });
    }


    private static void initContinue(String msg){
        cont = new ContinuePanel(msg);
        cont.addPropertyChangeListener("continue_game_selected", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println("Starting Game");
                frame.remove(cont);
                cont = null;
                frame.add(game, BorderLayout.CENTER);
                game.init();
                top.setScore(0);
                bottom.setLives(3);
                // Makes sure game has focus
                frame.revalidate();
                game.requestFocus();
            }
        });

        cont.addPropertyChangeListener("exit_game_selected", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println("exiting Game");
                frame.remove(cont);
                cont = null;
                initMenu();
                frame.add(mainMenu, BorderLayout.CENTER);
                top.setScore(0);
                bottom.setLives(3);
                // Makes sure game has focus
                frame.revalidate();
                mainMenu.requestFocus();
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
}