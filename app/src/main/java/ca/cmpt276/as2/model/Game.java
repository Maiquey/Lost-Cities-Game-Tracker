package ca.cmpt276.as2.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * represents one game played by 1 to 4 players
 */

public class Game {

    private static final int MAX_NUM_OF_PLAYERS = 2;
    private LocalDateTime creation_time;
    private ArrayList<PlayerScore> players;
    private int winner;

    public Game() {
        this.creation_time = LocalDateTime.now();
        this.players = new ArrayList<>();
        this.winner = 0;
    }

    public void addScore(PlayerScore data){
        players.add(data);
        if (players.size() == MAX_NUM_OF_PLAYERS){
            findWinners();
        }
    }

    public void findWinners() {

        if ( players.get(0).getScore() == players.get(1).getScore()){
            return;
        }
        if ( players.get(0).getScore() > players.get(1).getScore()){
            winner = 1;
            return;
        }
        else{
            winner = 2;
            return;
        }
    }


    public LocalDateTime getCreation_time() {
        return creation_time;
    }

    public PlayerScore getPlayer(int index){
        return players.get(index);
    }

    public ArrayList<PlayerScore> getPlayers() {
        return players;
    }

    public int getWinner() {
        return winner;
    }
}
