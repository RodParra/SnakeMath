package com.zetcode;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Snake {
    private final int DOT_SIZE = 10;
    private ArrayList<Point> body;
    private int direction;

    public Snake() {
        body = new ArrayList<>();
        body.add(new Point(50, 50));
        body.add(new Point(40, 50));
        body.add(new Point(30, 50));
        direction = KeyEvent.VK_RIGHT;
    }

    

    public void draw(Graphics g) {
        for (int i = 0; i < body.size(); i++) {
            if (i == 0) {
                g.setColor(GameStyles.PRIMARY_COLOR);
            } else {
                g.setColor(GameStyles.SECONDARY_COLOR);
            }
            Point p = body.get(i);
            g.fillRect(p.x, p.y, DOT_SIZE, DOT_SIZE);
        }
    }

    public void move() {
        for (int i = body.size() - 1; i > 0; i--) {
            body.set(i, new Point(body.get(i-1)));
        }

        Point head = body.get(0);
        switch (direction) {
            case KeyEvent.VK_LEFT:
                body.set(0, new Point(head.x - DOT_SIZE, head.y));
                break;
            case KeyEvent.VK_RIGHT:
                body.set(0, new Point(head.x + DOT_SIZE, head.y));
                break;
            case KeyEvent.VK_UP:
                body.set(0, new Point(head.x, head.y - DOT_SIZE));
                break;
            case KeyEvent.VK_DOWN:
                body.set(0, new Point(head.x, head.y + DOT_SIZE));
                break;
        }
    }

    public void grow() {
        body.add(new Point(body.get(body.size() - 1)));
    }

    public void setDirection(int key) {
        if ((key == KeyEvent.VK_LEFT) && (direction != KeyEvent.VK_RIGHT)) {
            direction = KeyEvent.VK_LEFT;
        } else if ((key == KeyEvent.VK_RIGHT) && (direction != KeyEvent.VK_LEFT)) {
            direction = KeyEvent.VK_RIGHT;
        } else if ((key == KeyEvent.VK_UP) && (direction != KeyEvent.VK_DOWN)) {
            direction = KeyEvent.VK_UP;
        } else if ((key == KeyEvent.VK_DOWN) && (direction != KeyEvent.VK_UP)) {
            direction = KeyEvent.VK_DOWN;
        }
    }

    public boolean checkCollision(Number number) {
        return body.get(0).equals(number.getPosition());
    }

    public boolean checkCollisionWithWall(int width, int height) {
        Point head = body.get(0);
        return head.x >= width || head.x < 0 || head.y >= height || head.y < 0;
    }

    public boolean checkCollisionWithSelf() {
        Point head = body.get(0);
        for (int i = 1; i < body.size(); i++) {
            if (head.equals(body.get(i))) {
                return true;
            }
        }
        return false;
    }

    public Point getHeadPosition() {
        return body.get(0);
    }


}

