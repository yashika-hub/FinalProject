package com.core.example.songLyricSearchApp;

import com.core.example.songLyricSearchApp.model.LyricAPILyric;
import com.core.example.songLyricSearchApp.model.LyricMainInteract;

public class Presenter implements MainPresenter {

    private MainView vista;
    private LyricMainInteract interact;

    public Presenter(MainView vista) {
        this.vista = vista;
        interact = new LyricAPILyric(this);

    }

    @Override
    public void showResult(String result) {

        if(vista!=null){

            vista.show(result);
        }
    }

    @Override
    public void sendData(String artist, String title) {

        if(vista!= null){

            interact.sendData(artist,title);
        }

    }

    @Override
    public void showError(String error) {
        if(vista!= null){

            vista.showError(error);

        }


    }






}
