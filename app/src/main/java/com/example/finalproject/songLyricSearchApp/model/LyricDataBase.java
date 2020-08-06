package com.core.example.songLyricSearchApp.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class LyricDataBase extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "lyrics_db";


    public LyricDataBase(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }




    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(LyricsOpener.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + LyricsOpener.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }


    public long insertNote(String artista,String lyrics) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(LyricsOpener.COLUMN_ARTIST, artista);
        values.put(LyricsOpener.COLUMN_LYRIC, lyrics);

        // insert row
        long id = db.insert(LyricsOpener.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }
    public LyricsOpener getNote(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(LyricsOpener.TABLE_NAME,
                         new String[]{LyricsOpener.COLUMN_ID, LyricsOpener.COLUMN_ARTIST, LyricsOpener.COLUMN_LYRIC},
                LyricsOpener.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        LyricsOpener note = new LyricsOpener(
                cursor.getInt(cursor.getColumnIndex(LyricsOpener.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(LyricsOpener.COLUMN_ARTIST)),
                cursor.getString(cursor.getColumnIndex(LyricsOpener.COLUMN_LYRIC)));

        cursor.close();

        return note;
    }


    public List<LyricsOpener> getAllNotes() {
        List<LyricsOpener> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + LyricsOpener.TABLE_NAME + " ORDER BY " +
                LyricsOpener.COLUMN_ARTIST + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                LyricsOpener letras = new LyricsOpener();
                letras.setId(cursor.getInt(cursor.getColumnIndex(LyricsOpener.COLUMN_ID)));
                letras.setArtist(cursor.getString(cursor.getColumnIndex(LyricsOpener.COLUMN_ARTIST)));
                letras.setLyric(cursor.getString(cursor.getColumnIndex(LyricsOpener.COLUMN_LYRIC)));

                notes.add(letras);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }

    public int getLyrics() {
        String countQuery = "SELECT  * FROM " + LyricsOpener.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public void deleteNote(LyricsOpener letras) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(LyricsOpener.TABLE_NAME, LyricsOpener.COLUMN_ID + " = ?",
                new String[]{String.valueOf(letras.getId())});
        db.close();
    }


}
