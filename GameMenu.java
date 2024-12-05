
import javax.swing.table.DefaultTableCellRenderer;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;


import java.awt.*;


public class GameMenu extends JPanel {
    private SnakeMath mainFrame;
    private ScoreManager scoreManager;

    public GameMenu(SnakeMath frame) {
        mainFrame = frame;
        scoreManager = new ScoreManager();
        initMenu();
    }

    private void initMenu() {
        setPreferredSize(new Dimension(600, 400));
        setBackground(GameStyles.BACKGROUND_COLOR);
        setLayout(null);

        JLabel titleLabel = new JLabel("SnakeMath");
        titleLabel.setFont(GameStyles.TITLE_FONT);
        titleLabel.setForeground(GameStyles.PRIMARY_COLOR);
        titleLabel.setBounds(150, 50, 300, 60);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        JButton startButton = new JButton("Iniciar Juego");
        GameStyles.styleButton(startButton);
        startButton.setBounds(200, 200, 200, 50);
        startButton.addActionListener(e -> mainFrame.startGame());

        JButton highScoresButton = new JButton("Mejores Puntajes");
        GameStyles.styleButton(highScoresButton);
        highScoresButton.setBounds(200, 270, 200, 50);
        highScoresButton.addActionListener(e -> showHighScores());

        JButton exitButton = new JButton("Salir");
        GameStyles.styleButton(exitButton);
        exitButton.setBounds(200, 340, 200, 50);
        exitButton.addActionListener(e -> System.exit(0));

        add(titleLabel);
        add(startButton);
        add(highScoresButton);
        add(exitButton);

        // Agregar decoración: "serpiente" estilizada
        addSnakeDecoration();
    }

private void showHighScores() {
    List<Player> topPlayers = new ArrayList<Player>(scoreManager.getTopPlayers(5));
    
    // Crear un panel personalizado para el diálogo de puntuaciones
    JPanel highScoresPanel = new JPanel(new BorderLayout(10, 10));
    highScoresPanel.setBackground(GameStyles.BACKGROUND_COLOR);
    highScoresPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
    // Título de puntuaciones altas
    JLabel titleLabel = new JLabel("🏆 Mejores Puntajes 🏆");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
    titleLabel.setForeground(GameStyles.PRIMARY_COLOR);
    titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    highScoresPanel.add(titleLabel, BorderLayout.NORTH);
    
    // Crear tabla de puntuaciones
    String[] columnNames = {"Posición", "Jugador", "Puntuación", "Fecha"};
    Object[][] data = new Object[topPlayers.size()][4];
    
    for (int i = 0; i < topPlayers.size(); i++) {
        Player player = topPlayers.get(i);
        data[i][0] = i + 1;
        data[i][1] = player.getUsername();
        data[i][2] = player.getHighScore();
        data[i][3] = player.getHighScoreDate() != null 
            ? player.getHighScoreDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) 
            : "Sin fecha";
    }
    
    JTable scoresTable = new JTable(data, columnNames) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    
    // Personalizar estilo de la tabla
    scoresTable.setBackground(new Color(40, 40, 40));
    scoresTable.setForeground(GameStyles.TEXT_COLOR);
    scoresTable.setSelectionBackground(GameStyles.PRIMARY_COLOR);
    scoresTable.setSelectionForeground(GameStyles.BACKGROUND_COLOR);
    scoresTable.setFont(new Font("Arial", Font.PLAIN, 14));
    scoresTable.getTableHeader().setBackground(GameStyles.SECONDARY_COLOR);
    scoresTable.getTableHeader().setForeground(GameStyles.BACKGROUND_COLOR);
    scoresTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
    
    // Centrar el contenido de las celdas
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
    for (int i = 0; i < scoresTable.getColumnCount(); i++) {
        scoresTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }
    
    // Ajustar tamaño de columnas
    scoresTable.getColumnModel().getColumn(0).setMaxWidth(80);
    scoresTable.getColumnModel().getColumn(1).setPreferredWidth(150);
    scoresTable.getColumnModel().getColumn(2).setPreferredWidth(100);
    scoresTable.getColumnModel().getColumn(3).setPreferredWidth(100);
    
    // Añadir tabla a un scroll pane
    JScrollPane scrollPane = new JScrollPane(scoresTable);
    scrollPane.getViewport().setBackground(GameStyles.BACKGROUND_COLOR);
    scrollPane.setBorder(BorderFactory.createLineBorder(GameStyles.SECONDARY_COLOR, 2));
    
    highScoresPanel.add(scrollPane, BorderLayout.CENTER);
    
    // Mensaje si no hay puntuaciones
    if (topPlayers.isEmpty()) {
        JLabel noScoresLabel = new JLabel("Aún no hay puntuaciones registradas");
        noScoresLabel.setForeground(GameStyles.TEXT_COLOR);
        noScoresLabel.setHorizontalAlignment(SwingConstants.CENTER);
        highScoresPanel.add(noScoresLabel, BorderLayout.CENTER);

        
    }
    
    Object[] options = {"Volver "};
    // Mostrar diálogo
    JOptionPane.showOptionDialog(
        this, 
        highScoresPanel, 
        "Mejores Puntajes", 
        JOptionPane.OK_CANCEL_OPTION, 
        JOptionPane.PLAIN_MESSAGE,
        null,
        options,
        options[0]

    );




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