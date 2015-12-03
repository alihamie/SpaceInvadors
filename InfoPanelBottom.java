import javax.swing.*;
import java.awt.*;

public class InfoPanelBottom extends JPanel{
    private JLabel lives_label = new JLabel("Lives: 3");

    InfoPanelBottom (int width) {
        setPreferredSize(new Dimension(width, 50));
        setSize(new Dimension(width, 50));
         setBackground(Color.BLACK);

        lives_label.setForeground(new Color(252, 252, 252));
        lives_label.setFont(SpaceInvaders.FONT.deriveFont(16f));
        add(lives_label);
    }

    public void setLives(Integer lives_count) {
        lives_label.setText("Lives: " + (lives_count));
    }
}
