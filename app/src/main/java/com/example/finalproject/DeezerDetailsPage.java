package com.example.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class DeezerDetailsPage extends AppCompatActivity {

    long id;
    private ImageView dAlbumCover;
    private boolean comingFromDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deezer_details_page);

        setSupportActionBar(findViewById(R.id.toolbar));

        DeezerMyOpener dbOpener = new DeezerMyOpener(this);
        SQLiteDatabase db = dbOpener.getWritableDatabase();


        Intent mainIntent = getIntent();

        Button dBackButton = findViewById(R.id.dDetailsPageButtonBack);
        dBackButton.setOnClickListener(v -> {
            finish();
        });

        comingFromDB = mainIntent.getBooleanExtra("comingFromDB", false);

        TextView dTitle = findViewById(R.id.dDetailsPageTextView1);
        String title = mainIntent.getStringExtra("TITLE");
        dTitle.setText("Title of the song is: " + title);

        TextView dDuration = findViewById(R.id.dDetailsPageTextView2);
        long duration = mainIntent.getLongExtra("DURATION", 0);
        dDuration.setText("Duration of song: " + duration);

        TextView dAlbumName = findViewById(R.id.dDetailsPageTextView3);
        String AlbumName = mainIntent.getStringExtra("ALBUM NAME");
        dAlbumName.setText("Album Name: " + AlbumName);

        String AlbumCover = mainIntent.getStringExtra("ALBUM COVER");
        id = mainIntent.getLongExtra("ID", 0);
        dAlbumCover = findViewById(R.id.dDetailsPageImageView);
        if (isFileExists(Long.toString(id))) {
            FileInputStream f = null;
            Bitmap image = null;
            try {
                f = openFileInput(Long.toString(id));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            image = BitmapFactory.decodeStream(f);
            dAlbumCover.setImageBitmap(image);
        } else {
            new CoverImageDownloader().execute(AlbumCover, Long.toString(id));
        }

        // get the delete button, and add a click listener:
        Button dFavButton = (Button) findViewById(R.id.dDetailsPageButtonFav);

        int position = mainIntent.getIntExtra("POSITION", 0);

        if (comingFromDB)
            dFavButton.setText("REMOVE FROM FAVOURITE");

        dFavButton.setOnClickListener(clk -> {
            if (dFavButton.getText().equals("REMOVE FROM FAVOURITE")) {
                dFavButton.setText("ADD TO FAVOURITE");
                Snackbar.make(dFavButton, "Removed from favourites", Snackbar.LENGTH_LONG).show();
                db.delete(DeezerMyOpener.TABLE_NAME, DeezerMyOpener.ID + " =?", new String[]{Long.toString(id)});
            } else {
                dFavButton.setText("REMOVE FROM FAVOURITE");
                Snackbar.make(dFavButton, "Added to favourites", Snackbar.LENGTH_LONG).show();
                ContentValues newRowValues = new ContentValues();
                newRowValues.put(DeezerMyOpener.ID, id);
                newRowValues.put(DeezerMyOpener.TITLE, title);
                newRowValues.put(DeezerMyOpener.DURATION, duration);
                newRowValues.put(DeezerMyOpener.ALBUMNAME, AlbumName);
                newRowValues.put(DeezerMyOpener.ALBUMCOVER, AlbumCover);
                db.insert(DeezerMyOpener.TABLE_NAME, null, newRowValues);

            }
        });
    }

    public boolean isFileExists(String fname) {
        File f = getBaseContext().getFileStreamPath(fname);
        return f.exists();
    }

    private class CoverImageDownloader extends AsyncTask<String, Integer, String> {

        Bitmap image = null;

        public String doInBackground(String... args) {

            try {
                URL urlImg = new URL(args[0]);
                HttpURLConnection connection = null;
                connection = (HttpURLConnection) urlImg.openConnection();

                connection.connect();
                int respCode = connection.getResponseCode();
                if (respCode == 200) {
                    image = BitmapFactory.decodeStream(connection.getInputStream());
                }
                FileOutputStream outputStream = openFileOutput(args[1], Context.MODE_PRIVATE);
                image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "Done";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            dAlbumCover.setImageBitmap(image);
        }
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
                Toast.makeText(this, "This is the Deezer Song Search activity, written by Yashika", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}
