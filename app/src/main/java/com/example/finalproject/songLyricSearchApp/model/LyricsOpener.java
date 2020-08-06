package com.core.example.songLyricSearchApp.model;

public class LyricsOpener {
    public static final String TABLE_NAME = "lyrics";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ARTIST = "artist";
    public static final String COLUMN_LYRIC = "lyric";

    private int id;
    private String artist;
    private String lyric;


    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_ARTIST + " TEXT,"
                    + COLUMN_LYRIC + " TEXT"
                    + ")";


    public LyricsOpener() {
    }

    public LyricsOpener(int id, String artist, String lyric) {
        this.id = id;
        this.artist = artist;
        this.lyric = lyric;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getLyric() {
        return lyric;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }
}



