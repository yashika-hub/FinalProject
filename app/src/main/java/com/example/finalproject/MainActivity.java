package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Button dataSource=findViewById(R.id.ToGeoDataSource);
//        dataSource.setOnClickListener((btn) ->{
//            Intent nextPage = new Intent(MainActivity.this, ProfileActivity.class);
//            startActivity(nextPage);
//        });
//
//        Button soccerMatch=findViewById(R.id.ToSoccerMatchHighlights);
//        soccerMatch.setOnClickListener((btn) ->{
//            Intent nextPage = new Intent(MainActivity.this, ProfileActivity.class);
//            startActivity(nextPage);
//        });
//
//        Button songLyrics=findViewById(R.id.ToSongLyricsSearch);
//        songLyrics.setOnClickListener((btn) ->{
//            Intent nextPage = new Intent(MainActivity.this, ProfileActivity.class);
//            startActivity(nextPage);
//        });

        Button DeezerSongSearch=findViewById(R.id.ToDeezerSongSearch);
        DeezerSongSearch.setOnClickListener((btn) ->{
            Intent nextPage = new Intent(MainActivity.this, DeezerMainPage.class);
            startActivity(nextPage);
        });
    }
}
