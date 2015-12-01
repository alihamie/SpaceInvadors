import javax.swing.*;
import java.awt.*;

public class InfoPanelTop extends JPanel {
    private JLabel score1 = new JLabel("Score: 0000");

    InfoPanelTop (int width) {
        setSize(width, 50);
        setBackground(Color.BLACK);

        score1.setForeground(new Color(252, 252, 252));
        add(score1);
    }

    public void updateScore(int score) {
        score1.setText("Score: " + score);
    }
}
