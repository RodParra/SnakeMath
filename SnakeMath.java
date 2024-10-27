package com.zetcode;
import javax.swing.*;

public class SnakeMath extends JFrame {

    public SnakeMath() {
        initUI();
    }

    private void initUI() {
        add(new GameMenu(this));
        
        setResizable(false);
        pack();
        
        setTitle("SnakeMath");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void startGame() {
        getContentPane().removeAll();
        ScoredGameBoard gameBoard = new ScoredGameBoard(this);
        add(gameBoard);
        pack();
        revalidate();
        repaint();
        gameBoard.requestFocus(); // Aseguramos que el tablero tenga el foco
    }

    public void showMenu() {
        getContentPane().removeAll();
        add(new GameMenu(this));
        pack();
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame ex = new SnakeMath();
            ex.setVisible(true);
        });
    }
}