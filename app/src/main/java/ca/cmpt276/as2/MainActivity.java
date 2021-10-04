package ca.cmpt276.as2;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.ui.AppBarConfiguration;

import ca.cmpt276.as2.databinding.ActivityMainBinding;
import ca.cmpt276.as2.model.GameManager;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private GameManager gameManager;

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
        //create list of items
        String[] myItems = gameManager.getStringArr();

        //build adapter
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(
                this,            //context for activity
                R.layout.list_games,    //layout to use (create)
                myItems);               //Items to be displayed

        //configure listview
        ListView list = (ListView) findViewById(R.id.GameList);
        list.setAdapter(adapter);
    }

    //register callback on ListView
    private void registerClickCallback() {
        ListView list = (ListView) findViewById(R.id.GameList);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                TextView textView = (TextView) viewClicked;
                Intent intent = NewGame.makeIntent(MainActivity.this, EDIT_GAME_STATE, position);
                startActivity(intent);
            }
        });
    }

}

