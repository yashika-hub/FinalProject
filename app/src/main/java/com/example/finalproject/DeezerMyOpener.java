package com.example.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DeezerMyOpener extends SQLiteOpenHelper {

    public final static String TABLE_NAME = "DeezerSong";
    public final static String ID = "_id";
    public final static String TITLE = "Title";
    public final static String DURATION = "Duration";
    public final static String ALBUMNAME = "AlbumName";
    public final static String ALBUMCOVER = "AlbumCover";
    protected final static String DB_NAME = "MessageDB";
    protected final static int VERSION_NUM = 1;

    public DeezerMyOpener(Context ctx) {
        super(ctx, DB_NAME, null, VERSION_NUM);
    }


    //This function gets called if no database file exists.
    //Look on your device in the /data/data/package-name/database directory.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TITLE + " TEXT, "
                + DURATION + " TEXT, "
                + ALBUMNAME + " TEXT, "
                + ALBUMCOVER + " TEXT);"
        );  // add or remove columns
    }


    //this function gets called if the database version on your device is lower than VERSION_NUM
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {   //Drop the old table:
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create the new table:
        onCreate(db);
    }

    //this function gets called if the database version on your device is higher than VERSION_NUM
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {   //Drop the old table:
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create the new table:
        onCreate(db);
    }
}
