package ca.cmpt276.as2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ca.cmpt276.as2.databinding.ActivityNewGameBinding;
import ca.cmpt276.as2.model.Game;
import ca.cmpt276.as2.model.GameManager;
import ca.cmpt276.as2.model.PlayerScore;

public class NewGame extends AppCompatActivity {

    private ActivityNewGameBinding binding;
    private Game game;
    private Game originalGame;
    private PlayerScore Player1;
    private PlayerScore Player2;
    private TextView Player1Score;
    private TextView Player2Score;
    private EditText Player1Cards;
    private EditText Player1Points;
    private EditText Player1Wagers;
    private EditText Player2Cards;
    private EditText Player2Points;
    private EditText Player2Wagers;
    private TextView Winner;
    private GameManager gameManager;

    private int ActivityState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        //Enable "up" on toolbar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        //initialize working variables
        Winner = (TextView) findViewById(R.id.winner);
        Player1Score = (TextView) findViewById(R.id.Player1Score);
        Player2Score = (TextView) findViewById(R.id.Player2Score);
        Player1Cards = (EditText) findViewById(R.id.Player1Cards);
        Player1Points = (EditText) findViewById(R.id.Player1Points);
        Player1Wagers = (EditText) findViewById(R.id.Player1Wagers);
        Player2Cards = (EditText) findViewById(R.id.Player2Cards);
        Player2Points = (EditText) findViewById(R.id.Player2Points);
        Player2Wagers = (EditText) findViewById(R.id.Player2Wagers);

        //singleton instance
        gameManager = GameManager.getInstance();

        Intent intent = getIntent();
        ActivityState = intent.getIntExtra("state", 1);


        //determine if New game or Edit game
        switch(ActivityState){
            case 1:
                setTitle(getString(R.string.new_title));
                newGame();
                break;

            case 2:
                setTitle(getString(R.string.edit_title));
                int gameIndex = intent.getIntExtra("index", 0);
                Toast.makeText(this,"" + gameIndex, Toast.LENGTH_SHORT).show();
                editGame(gameIndex);

                break;
        }

    }

    private void newGame() {
        game = new Game();

        Player1 = new PlayerScore(0,0,0);
        Player2 = new PlayerScore(0,0,0);

        TextView dateTime = (TextView) findViewById(R.id.DateTime);
        dateTime.setText("" + game.getCreation_timeStr());

        Player1Score.setText("-");
        Player2Score.setText("-");

        watchText1(Player1Cards);
        watchText1(Player1Points);
        watchText1(Player1Wagers);
        watchText2(Player2Cards);
        watchText2(Player2Points);
        watchText2(Player2Wagers);
    }

    //make changes based on changes to fields related to player 1
    private void watchText1(EditText TextBox) {
        TextBox.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateScore(Player1Score, Player1, Player1Cards, Player1Points, Player1Wagers);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    //make changes based on changes to fields related to player 2
    private void watchText2(EditText TextBox) {
        TextBox.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateScore(Player2Score, Player2, Player2Cards, Player2Points, Player2Wagers);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    //method to change display of scores in real-time with manipulation of text fields
    private void updateScore(TextView PlayerScore, PlayerScore player, EditText CardText, EditText PointsText, EditText WagersText) {

        String CardsStr = CardText.getText().toString();
        String PointsStr = PointsText.getText().toString();
        String WagersStr = WagersText.getText().toString();

        //change score value if all 3 fields are filled
        if (!CardsStr.equals("")
                &&  !PointsStr.equals("")
                &&  !WagersStr.equals("")){

            player.setNum_of_cards(Integer.parseInt(CardsStr));
            player.setSum_of_points(Integer.parseInt(PointsStr));
            player.setNum_of_wager_cards(Integer.parseInt(WagersStr));

            PlayerScore.setText("" + player.getScore());
            changeWinnerMessage();
        }else{
            PlayerScore.setText("-");
            Winner.setText("");
        }
    }

    //sets the winner text if both player scores are non-empty
    private void changeWinnerMessage() {
        if (!Player1Score.getText().toString().equals("-") && !Player2Score.getText().toString().equals("-")){
            if (Integer.parseInt(Player1Score.getText().toString()) == Integer.parseInt(Player2Score.getText().toString())){
                Winner.setText(R.string.tie_game);
            }
            else if (Integer.parseInt(Player1Score.getText().toString()) > Integer.parseInt(Player2Score.getText().toString())){
                Winner.setText(R.string.winner_1);
            }
            else{
                Winner.setText(R.string.winner_2);
            }
        }
    }

    //similar to newGame, saves instance of original game to overwrite when saving
    //if editing is aborted, no changes are made to orignal game
    private void editGame(int gameIndex) {

        originalGame = gameManager.retrieveGame(gameIndex);
        game = new Game();
        Player1 = new PlayerScore(0,0,0);
        Player2 = new PlayerScore(0,0,0);

        populateView();

        watchText1(Player1Cards);
        watchText1(Player1Points);
        watchText1(Player1Wagers);
        watchText2(Player2Cards);
        watchText2(Player2Points);
        watchText2(Player2Wagers);
    }

    //populates view of activity with data of game to edit
    private void populateView() {
        TextView dateTime = (TextView) findViewById(R.id.DateTime);
        dateTime.setText("" + originalGame.getCreation_timeStr());
        Player1Cards.setText("" + originalGame.getPlayer(0).getNum_of_cards());
        Player1Points.setText("" + originalGame.getPlayer(0).getSum_of_points());
        Player1Wagers.setText("" + originalGame.getPlayer(0).getNum_of_wager_cards());
        Player1Score.setText("" + originalGame.getPlayer(0).getScore());
        Player2Cards.setText("" + originalGame.getPlayer(1).getNum_of_cards());
        Player2Points.setText("" + originalGame.getPlayer(1).getSum_of_points());
        Player2Wagers.setText("" + originalGame.getPlayer(1).getNum_of_wager_cards());
        Player2Score.setText("" + originalGame.getPlayer(1).getScore());
        changeWinnerMessage();
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, NewGame.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_game, menu);
        return true;
    }

    //switch case for up button and save button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_save:
                saveGame();
                break;

            case android.R.id.home:
                finish();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    //saving a game (new and edited)
    private void saveGame() {
        boolean missingScore = (Player1Score.getText().toString().equals("-") || Player2Score.getText().toString().equals("-"));

        if(missingScore){
            Toast.makeText(this, "Input fields incomplete", Toast.LENGTH_LONG).show();
        }
        else if(player1ZeroError() || player2ZeroError()){
            Toast.makeText(this, "Point and Wager fields must be 0 if number of cards is 0", Toast.LENGTH_LONG).show();
        }
        else{
            finalScoreCheck();
            game.addScore(Player1);
            game.addScore(Player2);
            game.findWinners();
            Toast.makeText(this, "Saving!", Toast.LENGTH_SHORT).show();
            if (ActivityState == 1){
                gameManager.addGame(game);
            }
            else{
                originalGame.copyGame(game);
            }
            finish();

        }
    }

    //only checked if missingScore is false
    private boolean player1ZeroError() {
        return (Integer.parseInt(Player1Cards.getText().toString()) == 0)
                && (Integer.parseInt(Player1Points.getText().toString()) != 0
                || Integer.parseInt(Player1Wagers.getText().toString()) != 0);
    }

    private boolean player2ZeroError() {
        return (Integer.parseInt(Player2Cards.getText().toString()) == 0)
                && (Integer.parseInt(Player2Points.getText().toString()) != 0
                || Integer.parseInt(Player2Wagers.getText().toString()) != 0);
    }

    //method to update any missed scores during editing
    private void finalScoreCheck(){
        updateScore(Player1Score, Player1, Player1Cards, Player1Points, Player1Wagers);
        updateScore(Player2Score, Player2, Player2Cards, Player2Points, Player2Wagers);
    }
}