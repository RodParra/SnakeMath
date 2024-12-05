import javax.swing.*;
import java.awt.*;

public class GameInfoPanel extends JPanel {
    private int targetSum;
    private int currentSum;
    private int score;

    public GameInfoPanel() {
        setPreferredSize(new Dimension(500, 40));
        setBackground(GameStyles.GAME_INFO_BACKGROUND);
        setLayout(new FlowLayout(FlowLayout.LEFT));
    }

    public void updateInfo(int targetSum, int currentSum, int score) {
        this.targetSum = targetSum;
        this.currentSum = currentSum;
        this.score = score;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.setColor(GameStyles.GAME_INFO_TEXT);
        g.setFont(GameStyles.INFO_FONT);
        
        g.drawString("Objetivo: " + targetSum, 10, 25);
        g.drawString("Suma actual: " + currentSum, 200, 25);
        g.drawString("Puntuación: " + score, 400, 25);
    }
}