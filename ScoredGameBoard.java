import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.FontMetrics;

import javax.swing.JButton;
import javax.swing.Timer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;



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
    private GameInfoPanel infoPanel;
    private boolean showGameOver = false;
    private JButton returnToMenuButton;
    private static final int SCALE = 3;
    private boolean inGame;

    private Player currentPlayer;
    private ScoreManager scoreManager;
    private SnakeMath mainFrame;

    private SoundPlayer gameOverSound;
    private SoundPlayer gameMusic;
    private SoundPlayer eatNumberSound;
    private SoundPlayer levelCompleteSound;


    public ScoredGameBoard(SnakeMath frame, Player player, GameInfoPanel infoPanel) {
        
        super(220, 220, GameStyles.BACKGROUND_COLOR);
        this.mainFrame = frame;
        this.currentPlayer = player;
        this.scoreManager = new ScoreManager();
        this.infoPanel = infoPanel;
        
        gameOverSound = new SoundPlayer("sonidos/8-bit-video-game-fail-version-2-145478.wav");
        gameMusic = new SoundPlayer("sonidos/original-tetris-theme-tetris-soundtrack.wav");
        eatNumberSound = new SoundPlayer("sonidos/pacman_chomp.wav");
        levelCompleteSound = new SoundPlayer("sonidos/level-completed-230568.wav");

        gameMusic.loop(); // Reproduce la música en bucle al iniciar el juego
    
        // Configurar botón de volver al menú
        returnToMenuButton = new JButton("Volver al Menú");
        returnToMenuButton.setFont(GameStyles.BUTTON_FONT);
        returnToMenuButton.setForeground(GameStyles.BACKGROUND_COLOR);
        returnToMenuButton.setBackground(GameStyles.PRIMARY_COLOR);
        returnToMenuButton.setFocusPainted(false);
        returnToMenuButton.setBorderPainted(false);
        returnToMenuButton.setVisible(false);
        returnToMenuButton.addActionListener(e -> {
            mainFrame.showMenu();
        });
        
        // Establecer layout para poder agregar el botón
        setLayout(null);
        add(returnToMenuButton);
        
        initializeComponent();
    }

    @Override
    protected void initializeComponent() {
        setBackground(backgroundColor);
        setFocusable(true);
        setPreferredSize(new Dimension(width * SCALE, height * SCALE));

        
        // Inicializar variables del juego
        inGame = true;
        score = INITIAL_SCORE;
        currentSum = 0;
        
        // Crear serpiente y números
        snake = new Snake();
        numbers = new ArrayList<>();
        
        // Generar números iniciales y objetivo
        generateNewTarget();
        generateNumbers();
        
        // Actualizar panel de información inmediatamente
        infoPanel.updateInfo(targetSum, currentSum, score);
        
        // Configurar el KeyListener
        addKeyListener(new TAdapter());
        
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
            gameOver();
        }
    }

    private void drawGameOverScreen(Graphics g) {
        // Oscurecer la pantalla
        g.setColor(new Color(0, 0, 0, 150));  // Color con transparencia
        g.fillRect(0, 0, width, height);
    
        // Configurar fuente para el mensaje
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        
        // Dibujar mensaje de Game Over centrado
        String gameOverText = "Game Over";
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(gameOverText);
        g.drawString(gameOverText, 
            (width - textWidth) / 2, 
            height / 2); // Centrado en la pantalla
    }

    @Override
    protected void renderComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(SCALE, SCALE); // Escala la cuadrícula para mantener el tamaño visual
    
        if (inGame) {
            g2d.setColor(GameStyles.SECONDARY_COLOR);
            g2d.setStroke(new BasicStroke(4));
            g2d.drawRect(1, 1, width - 2, height - 2);
    
            // Dibujar números y la serpiente
            for (Number number : numbers) {
                number.draw(g2d);
            }
    
            snake.draw(g2d);
        } else if (showGameOver) {
            drawGameOverScreen(g2d);
        }
    }
    
    



    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            updateComponent();
            repaint();
        }
    }

    // Implementación de Scoreable
    @Override
    public void updateScore(int points) {
        // Incrementar exponencialmente la puntuación multiplicando por 1.10
        score = (int) (score * 1.10) + points;
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

//crear la referencia en Clase Number
private void generateNumbers() {
    numbers.clear(); // Limpiar la lista de números generados
    Random random = new Random();
    
    // Crear una lista de números que asegura una combinación para alcanzar el targetSum
    int remainingSum = targetSum; // Mantener un control del objetivo restante
    List<Number> tempNumbers = new ArrayList<>();
    
    // Generar números que sumen exactamente el objetivo
    while (remainingSum > 0 && tempNumbers.size() < NUMBER_COUNT) {
        // Generar un número entre 1 y el menor entre 9 y el objetivo restante
        int value = random.nextInt(Math.min(9, remainingSum)) + 1;
        Number num = new Number(width, height, DOT_SIZE);
        num.setValue(value);
        tempNumbers.add(num);
        remainingSum -= value;
    }
    
    // Añadir números adicionales al azar hasta completar NUMBER_COUNT
    while (tempNumbers.size() < NUMBER_COUNT) {
        Number num = new Number(width, height, DOT_SIZE);
        num.setValue(random.nextInt(9) + 1); // Generar valores entre 1 y 9
        tempNumbers.add(num);
    }
    
    // Barajar para que la solución no sea obvia
    Collections.shuffle(tempNumbers);
    
    // Asignar los números generados a la lista principal
    numbers.addAll(tempNumbers);
}


private void checkNumbers() {
    java.awt.Point snakeHead = snake.getHeadPosition();
    for (Number number : numbers) {
        if (number.collidesWith(snakeHead)) {
            currentSum += number.getValue();
            eatNumberSound.play(); // Reproducir sonido al comer

            // Actualizar información antes de validar el objetivo
            infoPanel.updateInfo(targetSum, currentSum, score);

            if (currentSum == targetSum) {
                levelCompleteSound.play(); // Reproducir sonido al completar el objetivo
                
                updateScore(100); // Incrementar el puntaje con la lógica exponencial
                currentSum = 0; // Reiniciar la suma actual
                generateNewTarget(); // Generar un nuevo objetivo
                generateNumbers(); // Generar nuevos números
            
                // Actualizar información inmediatamente después de completar el objetivo
                infoPanel.updateInfo(targetSum, currentSum, score);
            } else if (currentSum > targetSum) {
                gameOver(); // Finalizar el juego si se excede el objetivo
                return;
            }

            snake.grow(); // Hacer que la serpiente crezca
            number.relocate(); // Reubicar el número en el tablero
            break;
        }
    }
}


    private void gameOver() {
        inGame = false;
        showGameOver = true;
        gameTimer.stop();

        gameMusic.stop(); // Detener música de fondo
        gameOverSound.play(); // Reproducir sonido de game over
        
        
        returnToMenuButton.setBounds(
            (width * SCALE) / 2 - 100, // Centrado horizontalmente
            (height * SCALE) / 2 + 50, // Posicionado debajo del texto
            200, 
            50
        );
        returnToMenuButton.setVisible(true); // Hacer visible el botón solo al finalizar
        
        // Actualizar puntaje del jugador
        currentPlayer.updateScore(getCurrentScore());
        scoreManager.savePlayer(currentPlayer);
        
        // Forzar repintado
        repaint();
    }


    
    @Override
    public void addNotify() {
        super.addNotify();
        requestFocus();
    }


    

    
}