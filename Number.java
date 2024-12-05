

import java.awt.*;
import java.util.Random;

public class Number {
    private int x, y;
    private int value;
    private final int DOT_SIZE;
    private final int B_WIDTH;
    private final int B_HEIGHT;
    private static Random random = new Random();

    public Number(int boardWidth, int boardHeight, int dotSize) {
        B_WIDTH = boardWidth;
        B_HEIGHT = boardHeight;
        DOT_SIZE = dotSize;
        relocate();
    }

    public void relocate() {
        value = random.nextInt(10);
        
        // Ajustar generación para evitar bordes
        int maxX = (B_WIDTH / DOT_SIZE) - 2;
        int maxY = (B_HEIGHT / DOT_SIZE) - 2;
        
        x = ((random.nextInt(maxX) + 1) * DOT_SIZE);
        y = ((random.nextInt(maxY) + 1) * DOT_SIZE);
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g; // Convertimos a Graphics2D para mayor control
    
        // Dibujar el número con el color y fuente definidos en GameStyles
        g2d.setColor(GameStyles.PRIMARY_COLOR);
        g2d.setFont(GameStyles.INFO_FONT);
    
        // Centrar el número dentro del espacio del DOT_SIZE
        FontMetrics fm = g2d.getFontMetrics();
        int stringWidth = fm.stringWidth(String.valueOf(value));
        int stringHeight = fm.getAscent(); // Altura desde la línea base hacia arriba
    
        g2d.drawString(
            String.valueOf(value),
            x + (DOT_SIZE - stringWidth) / 2, // Centrar horizontalmente
            y + ((DOT_SIZE + stringHeight) / 2) // Centrar verticalmente
        );
    }
    

    public int getValue() {
        return value;
    }

    public Point getPosition() {
        return new Point(x, y);
    }

    public boolean collidesWith(Point p) {
        return p.x == x && p.y == y;
    }

    public void setValue(int value) {
        this.value = value;
    }
}



