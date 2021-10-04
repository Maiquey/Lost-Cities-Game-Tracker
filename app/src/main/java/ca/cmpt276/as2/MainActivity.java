package ca.cmpt276.as2;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import ca.cmpt276.as2.databinding.ActivityMainBinding;
import ca.cmpt276.as2.model.Game;
import ca.cmpt276.as2.model.GameManager;

import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private GameManager gameManager;
    private ArrayList<Game> games;

    private static final int NEW_GAME_STATE = 1;
    private static final int EDIT_GAME_STATE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        gameManager = GameManager.getInstance();

        setUpFAB();
        UpdateUI();
        registerClickCallback();
    }

    @Override
    protected void onResume() {
        super.onResume();
        UpdateUI();
    }

    private void UpdateUI() {
        TextView element1 = (TextView) findViewById(R.id.empty_element_1);
        TextView element2 = (TextView) findViewById(R.id.empty_element_2);
        ImageView element3 = (ImageView) findViewById(R.id.empty_element_3);
        ImageView element4 = (ImageView) findViewById(R.id.empty_element_4);
        ImageView element5 = (ImageView) findViewById(R.id.empty_element_5);

        if(gameManager.isEmpty()){
            element1.setVisibility(View.VISIBLE);
            element2.setVisibility(View.VISIBLE);
            element3.setVisibility(View.VISIBLE);
            element4.setVisibility(View.VISIBLE);
            element5.setVisibility(View.VISIBLE);
        }
        else{
            element1.setVisibility(View.INVISIBLE);
            element2.setVisibility(View.INVISIBLE);
            element3.setVisibility(View.INVISIBLE);
            element4.setVisibility(View.INVISIBLE);
            element5.setVisibility(View.INVISIBLE);
        }

        populateListView();
    }

    private void setUpFAB() {
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = NewGame.makeIntent(MainActivity.this, NEW_GAME_STATE, 0);
                startActivity(intent);

            }
        });
    }

    private void populateListView() {
        games = gameManager.getGames();

        ArrayAdapter<Game> adapter = new MyListAdapter();
        ListView list = (ListView)  findViewById(R.id.GameList);
        list.setAdapter(adapter);
    }

    //register callback on ListView
    private void registerClickCallback() {
        ListView list = (ListView) findViewById(R.id.GameList);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {

                Game clickedGame = games.get(position);
                Intent intent = NewGame.makeIntent(MainActivity.this, EDIT_GAME_STATE, position);
                startActivity(intent);
            }
        });
    }

    private class MyListAdapter extends ArrayAdapter<Game> {
        public MyListAdapter() {
            super(MainActivity.this, R.layout.item_view, games);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //makes sure we have a view to work with
            View itemView = convertView;
            if(itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.item_view, parent, false);
            }

            //Find game to work with
            Game game = games.get(position);

            //Fill the view
            ImageView image = (ImageView) itemView.findViewById(R.id.item_icon);

            //Fill winner
            TextView winner = (TextView) itemView.findViewById(R.id.item_who_won);

            switch (game.getWinner()) {
                case 0:
                    image.setImageResource(R.drawable.a2tie);
                    winner.setText(R.string.tie_game);
                    break;
                case 1:
                    image.setImageResource(R.drawable.win1);
                    winner.setText(R.string.winner_1);
                    break;
                case 2:
                    image.setImageResource(R.drawable.win2);
                    winner.setText(R.string.winner_2);
                    break;
            }

            TextView date = (TextView) itemView.findViewById(R.id.item_DateTime);
            date.setText("Date and time: " + game.getCreation_timeStr());

            TextView score = (TextView) itemView.findViewById(R.id.item_score);
            score.setText("Scores: " + game.getPlayer(0).getScore() + " vs " + game.getPlayer(1).getScore());

            return itemView;
        }
    }
}

