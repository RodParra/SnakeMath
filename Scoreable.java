package com.zetcode;

public interface Scoreable {
    void updateScore(int points);
    int getCurrentScore();
    void resetScore();
    boolean isHighScore();
}