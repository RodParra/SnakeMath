

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Player {
    private String username;
    private int highScore;
    private LocalDateTime highScoreDate;
    private int totalGames;

    public Player(String username) {
        this.username = username;
        this.highScore = 0;
        this.totalGames = 0;
    }

    public void updateScore(int newScore) {
        totalGames++;
        if (newScore > highScore) {
            highScore = newScore;
            highScoreDate = LocalDateTime.now();
        }
    }

    // Getters
    public String getUsername() { return username; }
    public int getHighScore() { return highScore; }
    public LocalDateTime getHighScoreDate() { return highScoreDate; }
    public int getTotalGames() { return totalGames; }

    // Método para convertir a cadena para guardar en archivo
    public String toFileString() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return String.format("%s,%d,%s,%d", 
            username, 
            highScore, 
            highScoreDate != null ? highScoreDate.format(formatter) : "N/A", 
            totalGames);
    }

    // Método estático para crear Player desde cadena de archivo
    public static Player fromFileString(String line) {
        String[] parts = line.split(",");
        Player player = new Player(parts[0]);
        player.highScore = Integer.parseInt(parts[1]);
        player.highScoreDate = parts[2].equals("N/A") ? 
            null : LocalDateTime.parse(parts[2], DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        player.totalGames = Integer.parseInt(parts[3]);
        return player;
    }
}