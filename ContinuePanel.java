import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;

public class ContinuePanel extends JPanel {


    private FlowLayout flow;

    private JLabel message   = new JLabel("");
    private JLabel continue_button = new JLabel("CONTINUE?");
    private JLabel exit_button = new JLabel("EXIT (QUITTER!)");

    private JLabel continue_cursor = new JLabel("");
    private JLabel exit_cursor = new JLabel("");
  
    ImageIcon ufo = Sprites.UFO;

    private GridBagLayout layout;
    private GridBagConstraints c;

    private boolean visible = true;

   ContinuePanel (String msg) {
        setSize(90, 100);
        setBackground(Color.BLACK);
        makeKeyBindings();
	this.message.setText(msg);

        layout = new GridBagLayout();
        c = new GridBagConstraints();
        setLayout(layout);

        message.setForeground(new Color(252, 252, 252));
        continue_button.setForeground(new Color(252, 252, 252));
        exit_button.setForeground(new Color(252, 252, 252));
      
       	message.setFont(SpaceInvaders.FONT.deriveFont(30f));
        continue_button.setFont(SpaceInvaders.FONT.deriveFont(15f));
	exit_button.setFont(SpaceInvaders.FONT.deriveFont(15f));


 
        Image ufo_image =  getScaledImage( ufo.getImage(),20,20 );

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        c.fill = GridBagConstraints.VERTICAL;
        add(message,c);


        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0;
        continue_cursor.setIcon ( (Icon) new ImageIcon(ufo_image) );
        c.insets = new Insets(10,0,0,0);  //top padding
        JPanel p = new JPanel();
        flow = new FlowLayout ();
        p.setLayout(flow);
        p.setBackground(Color.BLACK);
        p.add(continue_cursor);
        p.add(continue_button);
        add(p,c);


        c.gridx =0;
        c.gridy = 2;
        c.weightx = 0;
        c.insets = new Insets(10,0,0,0);  //top padding
        exit_cursor.setIcon ( (Icon) new ImageIcon(ufo_image) );
        JPanel k = new JPanel();
        FlowLayout flow1 = new FlowLayout ();
        k.setLayout(flow1);
        k.setBackground(Color.BLACK);
        k.add(exit_cursor);
        k.add(exit_button);
        exit_cursor.setVisible(false);
        add(k,c);

    }


    private void makeKeyBindings() {
        final JPanel  self = this;
        getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "select");
        getActionMap().put("select", new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (exit_cursor.isVisible()) {
                    self.firePropertyChange("exit_game_selected", false, true);
                    //System.out.printf("C options\n");
                } else {
                    self.firePropertyChange("continue_game_selected", false, true);
                    //System.out.printf("Clicked start game\n");
                }
            }
        });

        getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "down");
        getActionMap().put("down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exit_cursor.setVisible(visible);
                visible = !visible;
                continue_cursor.setVisible(visible);
            }
        });

        getInputMap().put(KeyStroke.getKeyStroke("UP"), "up");
        getActionMap().put("up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exit_cursor.setVisible(visible);
                visible = !visible;
                continue_cursor.setVisible(visible);
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
