package com.core.example.songLyricSearchApp.myFavLyrics;

public class Interactor_Letter implements Interactor {



    private Show_Letters showLetters;
    private Interactor_Letter interactor_letter;



    public Interactor_Letter(Show_Letters showLetters) {
        this.showLetters = showLetters;

    }

    @Override
    public void RecyclerShow() {


        showLetters.onShow();
    }
}
