package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class SoccerDetailPage extends AppCompatActivity {

    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soccer_detail_page);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent fromMain = getIntent();

        Button sDetailPageBackButton = findViewById(R.id.sDetailPageBackButton);
        sDetailPageBackButton.setOnClickListener( v -> {
        finish();
        });

        TextView sDetailPageTitle = (TextView) findViewById(R.id.sDetailPageTitle);
        String title = fromMain.getStringExtra("TITLE");
        sDetailPageTitle.setText("Two Teams that are Playing: "  + title);

        TextView sDetailPageDate = (TextView) findViewById(R.id.sDetailPageDate);
        String date = fromMain.getStringExtra("DATE");
        sDetailPageDate.setText("Today's Date: " + date);

        TextView sDetailPageURL = (TextView) findViewById(R.id.sDetailPageURL);
        String URL = fromMain.getStringExtra("URL");
        sDetailPageURL.setText("URL: " + URL);

        // get the delete button, and add a click listener:
        Button sDetailPageFavButton = (Button) findViewById(R.id.sDetailPageFavButton);
        id = fromMain.getLongExtra("ID", -1);
        int position = fromMain.getIntExtra("POSITION", 0);


        if(id!=-1)
            sDetailPageFavButton.setText("REMOVE FROM FAVOURITE");

        SoccerMyOpener dbOpener = new SoccerMyOpener(this);
        SQLiteDatabase db = dbOpener.getWritableDatabase();


        sDetailPageFavButton.setOnClickListener( clk -> {
            SoccerMatch sm = SoccerMainPage.soccerMatchArrayList.get(position);
            if(sDetailPageFavButton.getText().equals("REMOVE FROM FAVOURITE")){ // Remove from DB
                sDetailPageFavButton.setText(R.string.sDetailPageFavButton);
                Snackbar.make(sDetailPageFavButton, getResources().getString(R.string.sDetailPageToastMsg), Snackbar.LENGTH_LONG).show();

                db.delete(SoccerMyOpener.TABLE_NAME, SoccerMyOpener.ID + " =?", new String[]{Long.toString(id)});

            } else{ // Adding to DB
                sDetailPageFavButton.setText(R.string.sDetailPageRemove);
                Snackbar.make(sDetailPageFavButton, getResources().getString(R.string.sDetailPageToastMsg2), Snackbar.LENGTH_LONG).show();

                ContentValues newRowValues = new ContentValues();
                newRowValues.put(SoccerMyOpener.TITLE, title);
                newRowValues.put(SoccerMyOpener.DATE, date);
                newRowValues.put(SoccerMyOpener.URL, URL);
                id = db.insert(SoccerMyOpener.TABLE_NAME, null, newRowValues);
            }
        });

        Button sDetailPageMatchHighlights = findViewById(R.id.sDetailPageMatchHighlights);
        if(URL == null || URL.isEmpty()){
            sDetailPageMatchHighlights.setVisibility(View.GONE);
        }
        sDetailPageMatchHighlights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(URL));
                startActivity(i);
            }
        });

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