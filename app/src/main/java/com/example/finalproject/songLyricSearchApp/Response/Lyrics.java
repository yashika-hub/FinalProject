package com.core.example.songLyricSearchApp.Response;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.core.example.R;
import com.core.example.songLyricSearchApp.LyricsSearchActivity;
import com.core.example.songLyricSearchApp.model.LyricDataBase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Lyrics extends AppCompatActivity {

    @BindView(R.id.lyric)
    TextView lyric;

    private String lyrics;
    private String artist;
    private String title;

    private LyricDataBase lyricDataBase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyric_activity_lyrics);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lyricDataBase = new LyricDataBase(this);


        lyrics = getIntent().getStringExtra("lyric");
        artist = getIntent().getStringExtra("artist");
        title = getIntent().getStringExtra("title");

        lyric.setText(lyrics);

        toolbar.setTitle(artist);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ResponseLyric( artist +" - "+ title, lyrics,view);
            }
        });
    }

    private void ResponseLyric(String artist, String lyrics, View view) {


        lyricDataBase.insertNote(artist,lyrics );

        Snackbar.make(view, R.string.success, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        Intent activity = new Intent(this, LyricsSearchActivity.class);
        startActivity(activity);
    }
}
