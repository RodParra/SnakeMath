package com.zetcode;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

public abstract class GameComponent extends JPanel {
    protected int width;
    protected int height;
    protected Color backgroundColor;
    protected boolean isActive;

    public GameComponent(int width, int height, Color backgroundColor) {
        this.width = width;
        this.height = height;
        this.backgroundColor = backgroundColor;
        this.isActive = true;
    }

    // Métodos abstractos que deben implementar las clases hijas
    protected abstract void initializeComponent();
    protected abstract void updateComponent();
    protected abstract void renderComponent(Graphics g);

    // Métodos comunes para todos los componentes
    public void setActive(boolean active) {
        this.isActive = active;
    }

    public boolean isActive() {
        return isActive;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (isActive) {
            renderComponent(g);
        }
    }
}
