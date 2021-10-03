package ca.cmpt276.as2.model;

import java.util.ArrayList;

/**
 * Manager for ArrayList of stored games
 */
public class GameManager {

    private ArrayList<Game> games;

    //Singleton support
    private static GameManager instance;
    private GameManager() {
        this.games = new ArrayList<>();
    }
    public static GameManager getInstance(){
        if (instance == null){
            instance = new GameManager();
        }
        return instance;
    }

    public String[] getStringArr(){
        String[] arr = new String[getSize()];
        for (int i = 0; i < getSize(); i++){

            String winner;
            if (games.get(i).getWinner() == 0){
                winner = "Tie Game: ";
            }
            else{
                winner = "Player " + games.get(i).getWinner() + " won: ";
            }

            String str = games.get(i).getCreation_timeStr()
                            + " - "
                            + winner
                            + games.get(i).getPlayer(0).getScore()
                            + " vs "
                            + games.get(i).getPlayer(1).getScore();

            arr[i] = str;
        }
        return arr;
    }

    public void addGame(Game game){
        games.add(game);
    }

    public Game retrieveGame(int index){
        return games.get(index);
    }

    public void removeGame(int index){
        games.remove(index);
    }

    public int getSize(){
        return games.size();
    }

    public boolean isEmpty(){
        return games.isEmpty();
    }
}
