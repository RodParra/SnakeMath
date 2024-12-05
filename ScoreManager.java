

import java.io.*;
import java.util.*;

public class ScoreManager {
    private static final String SCORES_FILE = "snakemath_scores.txt";

    public void savePlayer(Player player) {
        try {
            // Primero, leemos los jugadores existentes
            List<Player> players = loadAllPlayers();
            
            // Buscamos si el jugador ya existe
            boolean found = false;
            for (int i = 0; i < players.size(); i++) {
                if (players.get(i).getUsername().equals(player.getUsername())) {
                    players.set(i, player);
                    found = true;
                    break;
                }
            }
            
            // Si no existe, lo añadimos
            if (!found) {
                players.add(player);
            }
            
            // Guardamos todos los jugadores
            try (PrintWriter writer = new PrintWriter(new FileWriter(SCORES_FILE))) {
                for (Player p : players) {
                    writer.println(p.toFileString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Player loadPlayer(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(SCORES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Player player = Player.fromFileString(line);
                if (player.getUsername().equals(username)) {
                    return player;
                }
            }
        } catch (IOException e) {
            // Si el archivo no existe, creamos un nuevo jugador
            return new Player(username);
        }
        
        // Si no se encuentra el jugador, creamos uno nuevo
        return new Player(username);
    }

    public List<Player> loadAllPlayers() {
        List<Player> players = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(SCORES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                players.add(Player.fromFileString(line));
            }
        } catch (IOException e) {
            // Si el archivo no existe o está vacío, devolvemos lista vacía
        }
        return players;
    }

    public ArrayList<Player> getTopPlayers(int limit) {
        List<Player> allPlayers = loadAllPlayers();
        //ordenar por puntaje mas alto
        allPlayers.sort((p1, p2) -> Integer.compare(p2.getHighScore(), p1.getHighScore()));
        
        // Crear un nuevo ArrayList con los primeros N jugadores
        return new ArrayList<>(allPlayers.subList(0, Math.min(limit, allPlayers.size())));
    }
}