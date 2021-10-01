package ca.cmpt276.as2.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * represents one game played by 1 to 4 players
 */

public class Game {

    private LocalDateTime creation_time;
    private ArrayList<Integer> players;
    private ArrayList<Integer> winners;

    public Game() {
        this.creation_time = LocalDateTime.now();
        this.players = new ArrayList<>();
        this.winners = new ArrayList<>();
    }

    public void addScore(int score){
        players.add(score);
    }

    public void findWinners() {

        int size = players.size();
        int highestScore = players.get(0);

        for (int i = 1; i < size; i++){
            if (players.get(i) > highestScore) {
                highestScore = players.get(i);
            }
        }

        for (int i = 0; i < size; i++){
            if (players.get(i) == highestScore){
                winners.add(i + 1);
            }
        }
    }


    public LocalDateTime getCreation_time() {
        return creation_time;
    }

    public ArrayList<Integer> getPlayers() {
        return players;
    }

    public ArrayList<Integer> getWinners() {
        return winners;
    }
}
