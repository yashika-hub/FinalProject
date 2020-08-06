package com.core.example.songLyricSearchApp.myFavLyrics.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.core.example.R;
import com.core.example.songLyricSearchApp.model.LyricsOpener;
import com.core.example.songLyricSearchApp.myFavLyrics.My_fav_lyrics;
import com.core.example.songLyricSearchApp.myFavLyrics.fragment.lyrics_fragment;

import java.util.List;

public class LyricsAdapter extends RecyclerView.Adapter<LyricsAdapter.MyViewHolder> {


    private Context context;
    private List<LyricsOpener> lyricsData;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView artists;
        public TextView lyricss;
        public CardView cardView;


        public MyViewHolder(View view) {

            super(view);

            artists = view.findViewById(R.id.text_Artist);
            lyricss = view.findViewById(R.id.text_letter);
            cardView= view.findViewById(R.id.cardView);
            }

    }

    public LyricsAdapter(Context context, List<LyricsOpener> letrasData) {
        this.context = context;
        this.lyricsData = letrasData;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lyric_cardview_letter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
       final LyricsOpener lyricsOpener = lyricsData.get(position);

        holder.artists.setText(lyricsOpener.getArtist());
        holder.lyricss.setText(lyricsOpener.getLyric());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, lyrics_fragment.class);

                //card de show trong my fav
                i.putExtra("id", lyricsOpener.getId());
                i.putExtra("artista", lyricsOpener.getArtist());
                i.putExtra("letra",lyricsOpener.getLyric());

                context.startActivity(i);
                ((Activity)context).finish();

            }
        });


    }

    @Override
    public int getItemCount() {
        return lyricsData.size();
    }

    public void switchContent(int id, Fragment fragment) {
        if (context == null)
            return;
        if (context instanceof My_fav_lyrics) {
            My_fav_lyrics mainActivity = (My_fav_lyrics) context;
            Fragment frag = fragment;

        }

    }

}
