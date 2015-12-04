import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
public class MainMenuPanel extends JPanel {


    private FlowLayout flow;

    private JLabel space   = new JLabel("S P A C E");
    private JLabel invaders = new JLabel("INVADERS!");
    private JLabel ten_points = new JLabel("= 10 POINTS");
    private JLabel twenty_points = new JLabel("= 20 POINTS");
    private JLabel thirty_points = new JLabel("= 30 POINTS");
    private JLabel ufo_points = new JLabel("= ???");

    private JLabel start_button = new JLabel("Play Space Invaders");
    private JLabel options_button = new JLabel("Options");
    private JLabel exit_button = new JLabel("Exit!");

    private JLabel start_cursor = new JLabel("");
    private JLabel options_cursor = new JLabel("");
    private JLabel exit_cursor = new JLabel("");

    ImageIcon small_invader = Sprites.SMALL_INVADER_0;
    ImageIcon medium_invader = Sprites.MEDIUM_INVADER_0;
    ImageIcon large_invader = Sprites.LARGE_INVADER_0;
    ImageIcon ufo = Sprites.UFO;

    private GridBagLayout layout;
    private GridBagConstraints c;

    private int visible = 0;

    MainMenuPanel (int width) {
        setSize(90, 100);
        setBackground(Color.BLACK);
        makeKeyBindings();

        layout = new GridBagLayout();
        c = new GridBagConstraints();
        setLayout(layout);
        space.setForeground(new Color(252, 252, 252));
        invaders.setForeground(new Color(252, 252, 252));
        ten_points.setForeground(new Color(252, 252, 252));
        twenty_points.setForeground(new Color(252, 252, 252));
        thirty_points.setForeground(new Color(252, 252, 252));
        ufo_points.setForeground(new Color(252, 252, 252));
        start_button.setForeground(new Color(252, 252, 252));
        options_button.setForeground(new Color(252, 252, 252));
        exit_button.setForeground(new Color(252, 252, 252));

        space.setFont(SpaceInvaders.FONT.deriveFont(50f));
        invaders.setFont(SpaceInvaders.FONT.deriveFont(30f));
        ten_points.setFont(SpaceInvaders.FONT.deriveFont(15f));
        twenty_points.setFont(SpaceInvaders.FONT.deriveFont(15f));
        thirty_points.setFont(SpaceInvaders.FONT.deriveFont(15f));
        ufo_points.setFont(SpaceInvaders.FONT.deriveFont(15f));
        start_button.setFont(SpaceInvaders.FONT.deriveFont(17f));
        options_button.setFont(SpaceInvaders.FONT.deriveFont(17f));
        exit_button.setFont(SpaceInvaders.FONT.deriveFont(17f));

        Image small = getScaledImage( small_invader.getImage(),20,20 );
        Image medium = getScaledImage( medium_invader.getImage(),20,20 );
        Image large = getScaledImage( large_invader.getImage(),20,20 );
        Image ufo_image =  getScaledImage( ufo.getImage(),20,20 );

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        c.fill = GridBagConstraints.VERTICAL;
        add(space);


        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0;
        c.insets = new Insets(5,0,0,0);  //top padding
        add(invaders,c);



        c.gridx =0;
        c.gridy = 2;
        c.weightx = 0;
        c.insets = new Insets(30,0,0,0);  //top padding
        ten_points.setIcon ( (Icon) new ImageIcon(small) );
        add(ten_points,c);

        c.gridx =0;
        c.gridy = 3;
        c.weightx = 0;
        c.insets = new Insets(10,0,0,0);  //top padding
        twenty_points.setIcon ( (Icon) new ImageIcon(medium) );
        add(twenty_points,c);


        c.gridx =0;
        c.gridy = 4;
        c.weightx = 0;
        c.insets = new Insets(10,0,0,0);  //top padding
        thirty_points.setIcon ( (Icon) new ImageIcon(large) );
        add(thirty_points,c);


        c.gridx = 0;
        c.gridy = 5;
        c.weightx = 0;
        c.insets = new Insets(10,0,0,0);  //top padding
        c.ipadx = 60;
        ufo_points.setIcon ( (Icon) new ImageIcon(ufo_image) );
        add(ufo_points,c);




        c.gridx = 0;
        c.gridy = 6;
        c.weightx = 0;
        start_cursor.setIcon ( (Icon) new ImageIcon(ufo_image) );
        c.insets = new Insets(10,0,0,0);  //top padding
        JPanel p = new JPanel();
        flow = new FlowLayout ();
        p.setLayout(flow);
        p.setBackground(Color.BLACK);
        p.add(start_cursor);
        p.add(start_button);
        //start_cursor.setVisible(false);
        add(p,c);


        /*
        c.gridx =0;
        c.gridy = 7;
        c.weightx = 0;
        //c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(10,0,0,0);  //top padding
        options_cursor.setIcon ( (Icon) new ImageIcon(ufo_image) );
        JPanel k = new JPanel();
        FlowLayout flow1 = new FlowLayout ();
        k.setLayout(flow1);
        k.setBackground(Color.BLACK);
        k.add(options_cursor);
        k.add(options_button);
        options_cursor.setVisible(false);
        add(k,c);
        */


        c.gridx =0;
        c.gridy = 8;
        c.weightx = 0;
        //c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(10,0,0,0);  //top padding
        exit_cursor.setIcon ( (Icon) new ImageIcon(ufo_image) );
        JPanel v = new JPanel();
        FlowLayout flow2 = new FlowLayout ();
        v.setLayout(flow2);
        v.setBackground(Color.BLACK);
        v.add(exit_cursor);
        v.add(exit_button);
        exit_cursor.setVisible(false);
        add(v,c);


    }


    private void makeKeyBindings() {
        final JPanel self = this;
        getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "select");
        getActionMap().put("select", new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                /*
                if (options_cursor.isVisible()) {
                    self.firePropertyChange("options_selected", false, true);
                    //System.out.printf("Clicked options\n");
                } else
                */
                if(start_cursor.isVisible() ){
                    self.firePropertyChange("start_game_selected", false, true);
                    //System.out.printf("Clicked start game\n");
                }
                else if(exit_cursor.isVisible() ){
                    System.exit(1);

                }
            }
        });

        getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "down");
        getActionMap().put("down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(visible == 0 )
                    visible++;
                /*else if(visible == 1 )
                {
                    visible++;
                }*/
                else visible = 0;

                if (visible == 0 )
                {
                    start_cursor.setVisible(true);
                    //options_cursor.setVisible(false);
                    exit_cursor.setVisible(false);
                }
                /*
                else if(visible == 1 )
                {
                    start_cursor.setVisible(false);
                    options_cursor.setVisible(true);
                    exit_cursor.setVisible(false);
                }*/
                else
                {
                    start_cursor.setVisible(false);
                    //options_cursor.setVisible(false);
                    exit_cursor.setVisible(true);
                }

            }
        });

        getInputMap().put(KeyStroke.getKeyStroke("UP"), "up");
        getActionMap().put("up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(visible == 0 )
                    visible = 1;
                /*else if(visible == 1 )
                {
                    visible--;
                }*/
                else visible--;

                if(visible == 0 )
                {
                    start_cursor.setVisible(true);
                    //options_cursor.setVisible(false);
                    exit_cursor.setVisible(false);
                }
                /*else if(visible == 1 )
                {
                    start_cursor.setVisible(false);
                    options_cursor.setVisible(true);
                    exit_cursor.setVisible(false);
                }*/
                else
                {
                    start_cursor.setVisible(false);
                    //options_cursor.setVisible(false);
                    exit_cursor.setVisible(true);
                }


            }
        });
    }



    private Image getScaledImage(Image srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }

}
