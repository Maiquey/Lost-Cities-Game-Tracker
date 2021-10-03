package ca.cmpt276.as2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import ca.cmpt276.as2.databinding.ActivityNewGameBinding;

public class NewGame extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityNewGameBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        //Enable "up" on toolbar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        int ActivityState = intent.getIntExtra("state", 1);
        Toast.makeText(this,"" + ActivityState, Toast.LENGTH_SHORT).show();

        //determine if New game or Edit game
        switch(ActivityState){
            case 1:
                setTitle(getString(R.string.new_title));
                //code for making new game
                break;

            case 2:
                setTitle(getString(R.string.edit_title));
                //code for editting existing game
                int index = intent.getIntExtra("index", 0);
                break;
        }

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