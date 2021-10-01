package ca.cmpt276.as2.model;

import java.util.ArrayList;

/**
 * Manager for ArrayList of stored games
 */
public class GameManager {

    private ArrayList<Game> games;

    public GameManager() {
        this.games = new ArrayList<>();
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
