import javax.swing.*;
import java.awt.*;

public class InfoPanelTop extends JPanel {
    private JLabel score1 = new JLabel("Score: 0000");

    InfoPanelTop (int width) {
        setSize(width, 50);
        setBackground(Color.BLACK);

        score1.setForeground(new Color(252, 252, 252));
        score1.setFont(SpaceInvaders.FONT.deriveFont(16f));
        add(score1);
    }

    public void setScore(Integer score) {
        score1.setText(String.format("Score: %04d", score));
    }
}
