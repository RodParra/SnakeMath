
import javax.swing.*;
import java.awt.*;
import javax.swing.border.LineBorder;

public class SnakeMath extends JFrame {
    private Player currentPlayer;
    private ScoreManager scoreManager;
    private GameInfoPanel infoPanel;
    private SoundPlayer MusicMenu;

    public SnakeMath() {
        scoreManager = new ScoreManager();
        initUI();
    }

    private void initUI() {
        MusicMenu= new SoundPlayer("sonidos/menu-53679.wav");
        MusicMenu.loop();
        setLayout(new BorderLayout());
        
        // Agregar menú en la parte superior
        add(new GameMenu(this), BorderLayout.NORTH);
        
        setResizable(false);
        pack();
        
        setTitle("SnakeMath");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void startGame() {

        
        // Crear un panel personalizado para el diálogo
        JPanel dialogPanel = new JPanel(new BorderLayout(10, 10));
        dialogPanel.setBackground(GameStyles.BACKGROUND_COLOR);
        
        // Título del diálogo
        JLabel titleLabel = new JLabel("¡Bienvenido a SnakeMath!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(GameStyles.PRIMARY_COLOR);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dialogPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Panel para el campo de texto
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBackground(GameStyles.BACKGROUND_COLOR);
        
        JLabel promptLabel = new JLabel("Ingresa tu nombre de usuario:");
        promptLabel.setForeground(GameStyles.TEXT_COLOR);
        promptLabel.setFont(GameStyles.INFO_FONT);
        inputPanel.add(promptLabel, BorderLayout.NORTH);
        
        JTextField usernameField = new JTextField(20);
        usernameField.setBackground(new Color(50, 50, 50));
        usernameField.setForeground(GameStyles.TEXT_COLOR);
        usernameField.setBorder(new LineBorder(GameStyles.PRIMARY_COLOR, 2, true));
        usernameField.setCaretColor(GameStyles.PRIMARY_COLOR);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 16));
        inputPanel.add(usernameField, BorderLayout.CENTER);
        
        dialogPanel.add(inputPanel, BorderLayout.CENTER);
        

        // Estilo del botón de diálogo
        UIManager.put("OptionPane.background", GameStyles.BACKGROUND_COLOR);
        UIManager.put("Panel.background", GameStyles.BACKGROUND_COLOR);
        
        Object[] options = {"Confirmar", "Volver al Menú"};
        // Mostrar diálogo personalizado
        int result = JOptionPane.showOptionDialog(
            this, 
            dialogPanel, 
            "SnakeMath - Registro", 
            JOptionPane.OK_CANCEL_OPTION, 
            JOptionPane.PLAIN_MESSAGE,
            null,
            options,
            options[0]


        );

        if (result == 0) { // Confirmar
            String username = usernameField.getText().trim();
            
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(
                    this, 
                    "Por favor, ingrese un nombre de usuario", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE
                );
                startGame(); // Volver a mostrar el diálogo
                return;
            }
            
            // Cargar o crear jugador
        currentPlayer = scoreManager.loadPlayer(username);
           // Limpiar contenido anterior
        getContentPane().removeAll();
        
        // Crear panel de información
        infoPanel = new GameInfoPanel();
        add(infoPanel, BorderLayout.NORTH);
        
        // Crear tablero de juego
        MusicMenu.stop();
        ScoredGameBoard gameBoard = new ScoredGameBoard(this, currentPlayer, infoPanel);
        add(gameBoard, BorderLayout.CENTER);
        pack();  
        revalidate();
        repaint();
        gameBoard.requestFocus();
    }
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
