package com.core.example.songLyricSearchApp.myFavLyrics.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.core.example.R;
import com.core.example.songLyricSearchApp.model.LyricDataBase;
import com.core.example.songLyricSearchApp.myFavLyrics.My_fav_lyrics;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class lyrics_fragment extends AppCompatActivity {


    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.textView2)
    TextView textView2;


    AlertDialog.Builder builder;
    @BindView(R.id.fab_)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyric_activity_lyrics_fav);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

///////
        final int i = getIntent().getExtras().getInt("id");
        textView.setText(getIntent().getExtras().getString("artista"));
        textView2.setText(getIntent().getExtras().getString("letra"));


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, My_fav_lyrics.class));
        finish();
    }


    @OnClick(R.id.fab_)
    public void onViewClicked() {

        final int i = getIntent().getExtras().getInt("id");
        LyricDataBase lyricDataBase = new LyricDataBase(getApplicationContext());
                       lyricDataBase.deleteNote(lyricDataBase.getNote(i));
                       startActivity(new Intent(getApplicationContext(), My_fav_lyrics.class));
    }

    }