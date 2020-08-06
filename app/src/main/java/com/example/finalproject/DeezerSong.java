package com.example.finalproject;

public class DeezerSong {
    long id;
    String title;
    long duration;
    String albumName;
    String albumCover;

    public DeezerSong() {
    }

    public DeezerSong(Long id, String title, Long duration, String albumName, String albumCover) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.albumName = albumName;
        this.albumCover = albumCover;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumCover() {
        return albumCover;
    }

    public void setAlbumCover(String albumCover) {
        this.albumCover = albumCover;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
