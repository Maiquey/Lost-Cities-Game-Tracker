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
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, NewGame.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate menu:
        getMenuInflater().inflate(R.menu.menu_new_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_save:
                Toast.makeText(this, "Now saving!", Toast.LENGTH_SHORT).show();
                finish();

            case android.R.id.home:
                //Toast.makeText(this, "Going Up!", Toast.LENGTH_SHORT).show();
                finish();

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}