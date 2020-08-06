package com.core.example.songLyricSearchApp.myFavLyrics;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.core.example.R;
import com.core.example.songLyricSearchApp.LyricsSearchActivity;
import com.core.example.songLyricSearchApp.myFavLyrics.fragment.Fragment_Recycler;

import butterknife.ButterKnife;

public class My_fav_lyrics extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyric_my_far_lyrics);
        ButterKnife.bind(this);


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame, new Fragment_Recycler());
        transaction.commit();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        startActivity(new Intent(getApplicationContext(), LyricsSearchActivity.class));

    }
}
