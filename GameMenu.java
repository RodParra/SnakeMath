package com.zetcode;

import javax.swing.*;
import java.awt.*;

public class GameMenu extends JPanel {

    private SnakeMath mainFrame;

    public GameMenu(SnakeMath frame) {
        mainFrame = frame;
        initMenu();
    }

    private void initMenu() {
        setPreferredSize(new Dimension(600, 400));
        setBackground(GameStyles.BACKGROUND_COLOR);
        setLayout(null); // Usamos layout nulo para posicionar los componentes manualmente

        JLabel titleLabel = new JLabel("SnakeMath");
        titleLabel.setFont(GameStyles.TITLE_FONT);
        titleLabel.setForeground(GameStyles.PRIMARY_COLOR);
        titleLabel.setBounds(150, 50, 300, 60);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        JButton startButton = new JButton("Iniciar Juego");
        GameStyles.styleButton(startButton);
        startButton.setBounds(200, 200, 200, 50);
        startButton.addActionListener(e -> mainFrame.startGame());

        JButton exitButton = new JButton("Salir");
        GameStyles.styleButton(exitButton);
        exitButton.setBounds(200, 270, 200, 50);
        exitButton.addActionListener(e -> System.exit(0));

        add(titleLabel);
        add(startButton);
        add(exitButton);

        // Agregar decoración: "serpiente" estilizada
        addSnakeDecoration();
    }

    private void addSnakeDecoration() {
        JPanel snakeBody = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(GameStyles.SECONDARY_COLOR);
                g2d.setStroke(new BasicStroke(5));
                g2d.drawLine(0, 10, 100, 10);
                g2d.drawLine(100, 10, 100, 50);
                g2d.drawLine(100, 50, 50, 50);
                g2d.fillOval(40, 40, 20, 20); // "ojo" de la serpiente
            }
        };
        snakeBody.setOpaque(false);
        snakeBody.setBounds(450, 300, 150, 100);
        add(snakeBody);
    }
}