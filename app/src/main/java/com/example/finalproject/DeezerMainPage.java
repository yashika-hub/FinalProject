package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class DeezerMainPage extends AppCompatActivity {

    static ArrayList<DeezerSong> DeezerSongArrayList = new ArrayList<>();
    static SQLiteDatabase db;
    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deezer_main_page);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        EditText editTxt = findViewById(R.id.dMainPageEditText);
        Button buttonFav = findViewById(R.id.dMainPageButtonFav);
        Button buttonSrch = findViewById(R.id.dMainPageButtonSrch);

        prefs = getSharedPreferences("SearchKeyword", Context.MODE_PRIVATE);
        String savedString = prefs.getString("ReserveName", null);
        editTxt.setText(savedString);

        buttonSrch.setOnClickListener((click) -> {
            System.out.println(editTxt + "!!!");
            Intent toSrch = new Intent(DeezerMainPage.this, DeezerSearchPage.class);
            toSrch.putExtra("keyForName", editTxt.getText().toString());
            startActivity(toSrch);
        });
        buttonFav.setOnClickListener((click) -> {
            Intent toFav = new Intent(DeezerMainPage.this, DeezerFavouritePage.class);
            startActivity(toFav);
        });

        //For NavigationDrawer:
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, toolbar, R.string.dNavigationBarOpen, R.string.dNavigationBarClose);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.itemInstructions:

                        AlertDialog.Builder builder  = new AlertDialog.Builder(DeezerMainPage.this);
                        builder.setTitle(R.string.dInstructions)
                                .setMessage(R.string.dInstructionsDetails)
                                .setPositiveButton("Ok", null)
                                .create().show();

                        break;
                    case R.id.itemAboutAPI:

                        // https://stackoverflow.com/questions/3004515/sending-an-intent-to-browser-to-open-specific-url
                        String url = "https://developers.deezer.com/guidelines";
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);

                        break;
                    case R.id.itemDonate:

                        // https://stackoverflow.com/questions/18799216/how-to-make-a-edittext-box-in-a-dialog
                        final EditText input = new EditText(DeezerMainPage.this);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT);
                        input.setLayoutParams(lp);
                        input.setHint("$$$");

                        AlertDialog.Builder builderDonate  = new AlertDialog.Builder(DeezerMainPage.this);
                        builderDonate.setTitle("Please give generou$ly")
                                .setMessage(R.string.dDonationQues)
                                .setView(input)
                                .setPositiveButton("Thank you", null)
                                .setNegativeButton("Cancel", null)
                                .create().show();

                        break;
                }

                DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = getSharedPreferences("SearchKeyword", Context.MODE_PRIVATE);
        EditText editText = findViewById(R.id.dMainPageEditText);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("ReserveName", editText.getText().toString()); //putInt(String key, int i)  putFloat()
        editor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //Look at your menu XML file. Put a case for every id in that file:
        switch (item.getItemId()) {
            //what to do when the menu item is selected:
            case R.id.itemGeo:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case R.id.itemSoccer:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case R.id.itemLyrics:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case R.id.itemAboutTheProject:
                Toast.makeText(this, "This is the Deezer activity, written by Yashika", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}
