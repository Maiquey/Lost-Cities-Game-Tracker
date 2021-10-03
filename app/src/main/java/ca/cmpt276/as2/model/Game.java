package ca.cmpt276.as2.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * represents one game played by 1 to 4 players
 */

public class Game {

    private LocalDateTime creation_time;
    private ArrayList<PlayerScore> players;
    private int winner;

    //constructor
    public Game() {
        this.creation_time = LocalDateTime.now();
        this.players = new ArrayList<>();
        this.winner = 0;
    }

    //add a score to the list
    public void addScore(PlayerScore data){
        players.add(data);
    }

    //player 1 or 2 wins, 0 for tie
    public void findWinners() {

        if ( players.get(0).getScore() == players.get(1).getScore()){
            winner = 0;
        }
        else if ( players.get(0).getScore() > players.get(1).getScore()){
            winner = 1;
        }
        else{
            winner = 2;
        }
    }

    //copies data from parameter game to object, preserves original time
    public void copyGame(Game game){
        players = new ArrayList<>(game.getPlayers());
        winner = game.getWinner();
    }

    //returns string used by game list
    public String getCreation_timeStr() {
        LocalDateTime time = creation_time;
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("MMM d");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("h:mma");
        String formatted = time.format(formatter1) + " @ " + time.format(formatter2);

        return formatted;
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
