package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class SoccerEmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soccer_empty);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle dataToPass = getIntent().getExtras(); //get the data that was passed from FragmentExample

        SoccerDetailsFragment dFragment = new SoccerDetailsFragment(); //add a DetailFragment
        dFragment.setArguments(dataToPass); //pass it a bundle for information
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentLocation, dFragment) //Add the fragment in FrameLayout
                .commit(); //actually load the fragment. Calls onCreate() in DetailFragment
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //Look at your menu XML file. Put a case for every id in that file:
        switch (item.getItemId()) {
            //what to do when the menu item is selected:
            case R.id.GeoData:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case R.id.SongLyrics:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case R.id.DeezerSong:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case R.id.MyProject:
                Toast.makeText(this, (R.string.sComment), Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

}