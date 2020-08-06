package com.core.example.songLyricSearchApp.model;

import android.content.Context;

import com.core.example.R;
import com.core.example.songLyricSearchApp.Presenter;
import com.core.example.songLyricSearchApp.retrofit.ApiInterface;
import com.core.example.songLyricSearchApp.model.common.LyricDatabaseLyrics;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LyricAPILyric implements LyricMainInteract {

    Presenter presenter;
    Context _context;


    public LyricAPILyric(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void sendData(final String artist, String title) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.lyrics.ovh/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiInterface request = retrofit.create(ApiInterface.class);


            Call<LyricDatabaseLyrics> call = request.groupList(artist,title);
            call.enqueue(new Callback<LyricDatabaseLyrics>() {
               @Override
               public void onResponse(Call<LyricDatabaseLyrics> call, Response<LyricDatabaseLyrics> response) {

                   LyricDatabaseLyrics lyricDatabaseLyrics = response.body();
                   try {
                       presenter.showResult(lyricDatabaseLyrics.getLyrics());
                   }catch (Exception e){
                            presenter.showError(e.getMessage());
                   }

               }

               @Override
               public void onFailure(Call<LyricDatabaseLyrics> call, Throwable t) {
                   presenter.showError(R.string.error_service +t.getMessage());


               }
           });




    }



}
