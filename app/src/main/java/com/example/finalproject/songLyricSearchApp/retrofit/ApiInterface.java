package com.core.example.songLyricSearchApp.retrofit;

import com.core.example.songLyricSearchApp.model.common.LyricDatabaseLyrics;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("{artist}/{title}")


    Call<LyricDatabaseLyrics> groupList(@Path("artist")
                            String artist, @Path("title") String title);
}
