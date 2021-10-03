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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        //Enable "up" on toolbar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        Player1Score = (TextView) findViewById(R.id.Player1Score);
        Player2Score = (TextView) findViewById(R.id.Player2Score);
        Player1Cards = (EditText) findViewById(R.id.Player1Cards);
        Player1Points = (EditText) findViewById(R.id.Player1Points);
        Player1Wagers = (EditText) findViewById(R.id.Player1Wagers);
        Player2Cards = (EditText) findViewById(R.id.Player2Cards);
        Player2Points = (EditText) findViewById(R.id.Player2Points);
        Player2Wagers = (EditText) findViewById(R.id.Player2Wagers);

        Player1 = new PlayerScore(0,0,0);
        Player2 = new PlayerScore(0,0,0);

        GameManager gameManager = GameManager.getInstance();

        Intent intent = getIntent();
        int ActivityState = intent.getIntExtra("state", 1);
        Toast.makeText(this,"" + ActivityState, Toast.LENGTH_SHORT).show();

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
                editGame(gameManager, intent);

                break;
        }

    }

    private void newGame() {
        game = new Game();

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
            Toast.makeText(NewGame.this, "SCORE!", Toast.LENGTH_SHORT).show();

        }else{
            PlayerScore.setText("-");
            Toast.makeText(NewGame.this, "NOPE!", Toast.LENGTH_SHORT).show();
        }
    }

    private void editGame(GameManager gameManager, Intent intent) {
        int index = intent.getIntExtra("index", 0);
        Game game = gameManager.retrieveGame(index);
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
                Toast.makeText(this, "Now saving!", Toast.LENGTH_SHORT).show();
                finish();
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