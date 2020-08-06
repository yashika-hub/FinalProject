package com.core.example.songLyricSearchApp.myFavLyrics.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.core.example.R;
import com.core.example.songLyricSearchApp.model.LyricDataBase;
import com.core.example.songLyricSearchApp.model.LyricsOpener;
import com.core.example.songLyricSearchApp.myFavLyrics.adapter.LyricsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Fragment_Recycler extends Fragment {


    @BindView(R.id.recycler_letters)
    RecyclerView recyclerView;
    Unbinder unbinder;
    private LyricsAdapter lyricsAdapter;
    private boolean isTablet;

    public void setTablet(boolean tablet) { isTablet = tablet; }

    private List<LyricsOpener> lyricsSong = new ArrayList<>();
    private LyricDataBase lyricDataBase;

    public Fragment_Recycler() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.lyrics_fragment__recycler, container, false);
        unbinder = ButterKnife.bind(this, view);

        // Inflate the layout for this fragment
        lyricDataBase = new LyricDataBase(getContext());
        lyricsSong.addAll(lyricDataBase.getAllNotes());

        lyricsAdapter = new LyricsAdapter(getContext(), lyricsSong);

        recyclerView.hasFixedSize();
        lyricsAdapter.notifyDataSetChanged();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());


        lyricsAdapter.notifyItemRemoved(lyricsAdapter.getItemCount());
        lyricsAdapter.notifyItemRangeChanged(lyricsAdapter.getItemCount(), lyricsSong.size());


        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(lyricsAdapter);


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

