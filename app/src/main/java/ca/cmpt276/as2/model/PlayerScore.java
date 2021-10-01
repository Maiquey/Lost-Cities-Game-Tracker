package ca.cmpt276.as2.model;

/**
 * represents one player's score
 */

public class PlayerScore {

    private int num_of_cards;
    private int sum_of_points;
    private int num_of_wager_cards;

    public PlayerScore(int num_of_cards, int sum_of_points, int num_of_wager_cards) {
        if (num_of_cards < 0){
            throw new IllegalArgumentException("Number of cards cannot be negative");
        }
        if (sum_of_points < 0){
            throw new IllegalArgumentException("Number of points cannot be negative");
        }
        if (num_of_wager_cards < 0){
            throw new IllegalArgumentException("Number of wager cards cannot be negative");
        }
        if (num_of_cards == 0){
            this.num_of_cards = 0;
            this.sum_of_points = 0;
            this.num_of_wager_cards = 0;
        }
        else{
            this.num_of_cards = num_of_cards;
            this.sum_of_points = sum_of_points;
            this.num_of_wager_cards = num_of_wager_cards;
        }
    }

    public int getScore(){
        int score = sum_of_points - 20;
        score *= (num_of_wager_cards + 1);
        if (num_of_cards >= 8){
            score += 20;
        }
        if (num_of_cards == 0){
            score = 0;
        }
        return score;
    }

    public int getNum_of_cards() {
        return num_of_cards;
    }

    public int getSum_of_points() {
        return sum_of_points;
    }

    public int getNum_of_wager_cards() {
        return num_of_wager_cards;
    }
}
