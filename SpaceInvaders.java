/**********************************

	This is the main class
	

***********************************/
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SpaceInvaders
{
    private static GamePanel game;
    private static InfoPanelTop top;

    private static Timer update_panels;

    public static void main(String[] args)
    {
        //regular JFrame procedure
        JFrame frame = new JFrame("Space Invaders");
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setLayout(new BorderLayout());

        frame.setSize(900, 500); // set frame size

        top = new InfoPanelTop(frame.getWidth());
        frame.add(top, BorderLayout.PAGE_START);

        game = new GamePanel(frame.getSize());
        frame.add(game, BorderLayout.CENTER);


        update_panels = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                top.updateScore(game.getScore());
            }
        });
        update_panels.start();


        frame.setVisible( true ); // display frame
    }

}