package com.zetcode;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Timer;
import java.util.ArrayList;

public class ScoredGameBoard extends GameComponent implements Scoreable, ActionListener {
    private static final int INITIAL_SCORE = 0;
    private static final int HIGH_SCORE_THRESHOLD = 100;
    private static final int NUMBER_COUNT = 5;
    private static final int DELAY = 140;
    private static final int DOT_SIZE = 10;
    
    private Snake snake;
    private java.util.List<Number> numbers;
    private int targetSum;
    private int currentSum;
    private int score;
    private Timer gameTimer;
    private SnakeMath mainFrame;
    private boolean inGame;

    public ScoredGameBoard(SnakeMath frame) {
        super(500, 500, GameStyles.BACKGROUND_COLOR);
        this.mainFrame = frame;
        initializeComponent();
    }

    @Override
    protected void initializeComponent() {
        setBackground(backgroundColor);
        setFocusable(true);
        setPreferredSize(new Dimension(width, height));
        
        // Inicializar variables del juego
        inGame = true;
        score = INITIAL_SCORE;
        currentSum = 0;
        
        // Crear serpiente y números
        snake = new Snake();
        numbers = new ArrayList<>();
        
        // Configurar el KeyListener
        addKeyListener(new TAdapter());
        
        // Generar números iniciales y objetivo
        generateNewTarget();
        generateNumbers();
        
        // Iniciar el timer
        gameTimer = new Timer(DELAY, this);
        gameTimer.start();
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            
            if (inGame) {
                snake.setDirection(key);
            }
        }
    }

    @Override
    protected void updateComponent() {
        if (inGame) {
            checkNumbers();
            snake.move();
            checkCollisions();
        }
    }

    private void checkCollisions() {
        if (snake.checkCollisionWithWall(width, height) || snake.checkCollisionWithSelf()) {
            inGame = false;
        }
    }

    @Override
    protected void renderComponent(Graphics g) {
        if (inGame) {
            // Dibujar borde
            g.setColor(GameStyles.SECONDARY_COLOR);
            g.drawRect(1, 1, width - 2, height - 2);

            // Dibujar números
            for (Number number : numbers) {
                number.draw(g);
            }

            // Dibujar serpiente
            snake.draw(g);

            // Dibujar información del juego
            drawGameInfo(g);
        } else {
            drawGameOver(g);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        updateComponent();
        repaint();
    }

    // Implementación de Scoreable
    @Override
    public void updateScore(int points) {
        score += points;
    }

    @Override
    public int getCurrentScore() {
        return score;
    }

    @Override
    public void resetScore() {
        score = INITIAL_SCORE;
    }

    @Override
    public boolean isHighScore() {
        return score > HIGH_SCORE_THRESHOLD;
    }

    private void generateNewTarget() {
        targetSum = (int) (Math.random() * 30) + 10;
    }

    private void generateNumbers() {
        numbers.clear();
        for (int i = 0; i < NUMBER_COUNT; i++) {
            numbers.add(new Number(width, height, DOT_SIZE));
        }
    }

    private void checkNumbers() {
        java.awt.Point snakeHead = snake.getHeadPosition();
        for (Number number : numbers) {
            if (number.collidesWith(snakeHead)) {
                currentSum += number.getValue();
                
                if (currentSum == targetSum) {
                    updateScore(10);
                    currentSum = 0;
                    generateNewTarget();
                    generateNumbers();
                } else if (currentSum > targetSum) {
                    inGame = false;
                    return;
                }
                
                snake.grow();
                number.relocate();
                break;
            }
        }
    }

    private void drawGameInfo(Graphics g) {
        g.setColor(GameStyles.TEXT_COLOR);
        g.setFont(GameStyles.INFO_FONT);
        g.drawString("Objetivo: " + targetSum, 10, 20);
        g.drawString("Suma actual: " + currentSum, 150, 20);
        g.drawString("Puntuación: " + getCurrentScore(), 300, 20);
    }

    private void drawGameOver(Graphics g) {
        String msg = "Game Over - Puntuación: " + getCurrentScore();
        if (isHighScore()) {
            msg += " - ¡NUEVA PUNTUACIÓN MÁXIMA!";
        }
        
        g.setColor(GameStyles.PRIMARY_COLOR);
        g.setFont(GameStyles.TITLE_FONT);
        java.awt.FontMetrics metr = getFontMetrics(g.getFont());
        g.drawString(msg, (width - metr.stringWidth(msg)) / 2, height / 2);
        
        // Agregar botón de retorno al menú
        if (!isActive) {
            javax.swing.JButton menuButton = new javax.swing.JButton("Volver al Menú");
            GameStyles.styleButton(menuButton);
            menuButton.setBounds((width - 200) / 2, height / 2 + 50, 200, 50);
            menuButton.addActionListener(e -> mainFrame.showMenu());
            add(menuButton);
        }
    }

    @Override
    public void addNotify() {
        super.addNotify();
        requestFocus();
    }
}