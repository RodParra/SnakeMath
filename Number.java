package com.zetcode;

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
        value = random.nextInt(9) + 1;  // Genera un número entre 1 y 9
        x = ((random.nextInt(B_WIDTH / DOT_SIZE)) * DOT_SIZE);
        y = ((random.nextInt(B_HEIGHT / DOT_SIZE)) * DOT_SIZE);
    }

    public void draw(Graphics g) {
        g.setColor(GameStyles.PRIMARY_COLOR);
        g.setFont(GameStyles.INFO_FONT);
        g.drawString(String.valueOf(value), x, y + DOT_SIZE);
        
        // Dibujar un círculo alrededor del número para destacarlo
        g.drawOval(x - 5, y - 5, DOT_SIZE + 10, DOT_SIZE + 10);
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
}