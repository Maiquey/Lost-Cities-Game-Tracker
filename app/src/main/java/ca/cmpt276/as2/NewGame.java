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

import androidx.navigation.ui.AppBarConfiguration;

import ca.cmpt276.as2.databinding.ActivityNewGameBinding;
import ca.cmpt276.as2.model.Game;
import ca.cmpt276.as2.model.GameManager;
import ca.cmpt276.as2.model.PlayerScore;

public class NewGame extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityNewGameBinding binding;
    private Game game;
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

        Winner = (TextView) findViewById(R.id.winner);
        Player1Score = (TextView) findViewById(R.id.Player1Score);
        Player2Score = (TextView) findViewById(R.id.Player2Score);
        Player1Cards = (EditText) findViewById(R.id.Player1Cards);
        Player1Points = (EditText) findViewById(R.id.Player1Points);
        Player1Wagers = (EditText) findViewById(R.id.Player1Wagers);
        Player2Cards = (EditText) findViewById(R.id.Player2Cards);
        Player2Points = (EditText) findViewById(R.id.Player2Points);
        Player2Wagers = (EditText) findViewById(R.id.Player2Wagers);



        gameManager = GameManager.getInstance();

        Intent intent = getIntent();
        ActivityState = intent.getIntExtra("state", 1);


        //determine if New game or Edit game
        switch(ActivityState){
            case 1:
                setTitle(getString(R.string.new_title));
                //code for making new game
                newGame();
                break;

            case 2:
                setTitle(getString(R.string.edit_title));
                //code for editting existing game
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
        dateTime.setText("" + game.getCreation_time());

        watchText1(Player1Cards);
        watchText1(Player1Points);
        watchText1(Player1Wagers);
        watchText2(Player2Cards);
        watchText2(Player2Points);
        watchText2(Player2Wagers);
    }

    private void watchText1(EditText TextBox) {
        TextBox.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateScore(Player1Score, Player1, Player1Cards, Player1Points, Player1Wagers);
//                updateScore(Player2Score, Player2, Player2Cards, Player2Points, Player2Wagers);
                //Toast.makeText(NewGame.this, "update!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void watchText2(EditText TextBox) {
        TextBox.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                updateScore(Player1Score, Player1, Player1Cards, Player1Points, Player1Wagers);
                updateScore(Player2Score, Player2, Player2Cards, Player2Points, Player2Wagers);
                //Toast.makeText(NewGame.this, "update!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void updateScore(TextView PlayerScore, PlayerScore player, EditText CardText, EditText PointsText, EditText WagersText) {
        String CardsStr = CardText.getText().toString();
        String PointsStr = PointsText.getText().toString();
        String WagersStr = WagersText.getText().toString();
        if (!CardsStr.equals("")
                &&  !PointsStr.equals("")
                &&  !WagersStr.equals("")){

            player.setNum_of_cards(Integer.parseInt(CardsStr));
            player.setSum_of_points(Integer.parseInt(PointsStr));
            player.setNum_of_wager_cards(Integer.parseInt(WagersStr));

            PlayerScore.setText("" + player.getScore());
            //Toast.makeText(NewGame.this, "SCORE!", Toast.LENGTH_SHORT).show();
            changeWinnerMessage();
        }else{
            PlayerScore.setText("-");
            Winner.setText("");
            //Toast.makeText(NewGame.this, "NOPE!", Toast.LENGTH_SHORT).show();
        }
    }

    private void changeWinnerMessage() {
        if (!Player1Score.getText().toString().equals(" - ") && !Player2Score.getText().toString().equals(" - ")){
            if (Integer.parseInt(Player1Score.getText().toString()) == Integer.parseInt(Player2Score.getText().toString())){
                Winner.setText("Tie");
            }
            else if (Integer.parseInt(Player1Score.getText().toString()) > Integer.parseInt(Player2Score.getText().toString())){
                Winner.setText("Winner is Player 1");
            }
            else{
                Winner.setText("Winner is Player 2");
            }
        }
    }

    private void editGame(int gameIndex) {

        game = gameManager.retrieveGame(gameIndex);
        Player1 = game.getPlayer(0);
        Player2 = game.getPlayer(1);

        TextView dateTime = (TextView) findViewById(R.id.DateTime);
        dateTime.setText("" + game.getCreation_time());

        populateView();

        watchText1(Player1Cards);
        watchText1(Player1Points);
        watchText1(Player1Wagers);
        watchText2(Player2Cards);
        watchText2(Player2Points);
        watchText2(Player2Wagers);
    }

    private void populateView() {
        Player1Cards.setText("" + Player1.getNum_of_cards());
        Player1Points.setText("" + Player1.getSum_of_points());
        Player1Wagers.setText("" + Player1.getNum_of_wager_cards());
        Player1Score.setText("" + Player1.getScore());
        Player2Cards.setText("" + Player2.getNum_of_cards());
        Player2Points.setText("" + Player2.getSum_of_points());
        Player2Wagers.setText("" + Player2.getNum_of_wager_cards());
        Player2Score.setText("" + Player2.getScore());
        changeWinnerMessage();
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, NewGame.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate menu:
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        getMenuInflater().inflate(R.menu.menu_new_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_save:

                boolean missingScore = (Player1Score.getText().toString().equals("-") || Player2Score.getText().toString().equals("-"));
                boolean player1ZeroError = ((Integer.parseInt(Player1Cards.getText().toString()) == 0)
                                            && (Integer.parseInt(Player1Points.getText().toString()) != 0
                                            || Integer.parseInt(Player1Wagers.getText().toString()) != 0));
                boolean player2ZeroError = ((Integer.parseInt(Player2Cards.getText().toString()) == 0)
                        && (Integer.parseInt(Player2Points.getText().toString()) != 0
                        || Integer.parseInt(Player2Wagers.getText().toString()) != 0));

                if(missingScore){
                    Toast.makeText(this, "Input fields incomplete", Toast.LENGTH_LONG).show();
                }
                else if(player1ZeroError || player2ZeroError){
                    Toast.makeText(this, "Point and Wager fields must be 0 if number of cards is 0", Toast.LENGTH_LONG).show();
                }
                else{
                    if (ActivityState == 1){
                        Toast.makeText(this, "Now saving!", Toast.LENGTH_SHORT).show();
                        game.addScore(Player1);
                        game.addScore(Player2);
                        game.findWinners();
                        gameManager.addGame(game);
                    }
                    else{
                        Toast.makeText(this, "Now Re-saving!", Toast.LENGTH_SHORT).show();
                        game.findWinners();
                    }
                    finish();

                }
                break;

            case android.R.id.home:
                //Toast.makeText(this, "Going Up!", Toast.LENGTH_SHORT).show();
                finish();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }
}