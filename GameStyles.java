package com.zetcode;

import java.awt.*;

import javax.swing.JButton;

public class GameStyles {
    public static final Color BACKGROUND_COLOR = new Color(25, 25, 25);
    public static final Color PRIMARY_COLOR = new Color(0, 255, 0);
    public static final Color SECONDARY_COLOR = new Color(0, 200, 0);
    public static final Color TEXT_COLOR = new Color(240, 240, 240);

    public static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 48);
    public static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 18);
    public static final Font INFO_FONT = new Font("Arial", Font.PLAIN, 14);

    public static void styleButton(JButton button) {
        button.setFont(BUTTON_FONT);
        button.setForeground(BACKGROUND_COLOR);
        button.setBackground(PRIMARY_COLOR);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
    }
}
