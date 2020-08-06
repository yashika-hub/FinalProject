package com.example.finalproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class DeezerFavouritePage extends AppCompatActivity {

    static ArrayList<DeezerSong> DeezerSongArrayList = new ArrayList<>();
    MyListAdapter adapter = new MyListAdapter();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deezer_favourite_page);

        setSupportActionBar(findViewById(R.id.toolbar));

        ListView fListView = findViewById(R.id.dFavouritePageListView);

        loadDataFromDatabase();
        fListView.setAdapter(adapter);

        Button favBackButton = findViewById(R.id.dFavouritePageButtonBack);
        favBackButton.setOnClickListener(v -> {
            finish();
        });

        fListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent goToDetail = new Intent(DeezerFavouritePage.this, DeezerDetailsPage.class);
            DeezerSong songSelect = DeezerSongArrayList.get(position);

            goToDetail.putExtra("ID", songSelect.getId());
            goToDetail.putExtra("TITLE", songSelect.getTitle());
            goToDetail.putExtra("DURATION", songSelect.getDuration());
            goToDetail.putExtra("ALBUM NAME", songSelect.getAlbumName());
            goToDetail.putExtra("ALBUM COVER", songSelect.getAlbumCover());
            goToDetail.putExtra("comingFromDB", true);

            startActivityForResult(goToDetail, 987);
        });

        fListView.setOnItemLongClickListener((parent, view, position, id) -> {
            DeezerSong songSelect = DeezerSongArrayList.get(position);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Do you want to remove this from favourite list?")
                    .setPositiveButton("Yes", (click, b) -> {
                        DeezerSongArrayList.remove(position);
                        fListView.setAdapter(new MyListAdapter());
                        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.dFavouritePageRefresher);
                        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh() {
                                fListView.setAdapter(new MyListAdapter());
                            }
                        });
                        Snackbar.make(fListView, "Removed from favourite list", Snackbar.LENGTH_LONG).show();
                    })
                    .setNegativeButton("No", (click, b) -> {
                        Snackbar.make(fListView, "Nothing changed", Snackbar.LENGTH_LONG).show();
                    }).create().show();
            return true;
        });

        }

    private void loadDataFromDatabase() {

        DeezerSongArrayList.clear();

        //get a database connection:
        DeezerMyOpener dbOpener = new DeezerMyOpener(this);
        DeezerMainPage.db = dbOpener.getReadableDatabase(); //This calls onCreate() if you've never built the table before, or onUpgrade if the version here is newer

        // We want to get all of the columns. Look at MyOpener.java for the definitions:
        String[] columns = {DeezerMyOpener.ID, DeezerMyOpener.TITLE, DeezerMyOpener.DURATION, DeezerMyOpener.ALBUMNAME, DeezerMyOpener.ALBUMCOVER};
        //query all the results from the database:
        Cursor results = DeezerMainPage.db.query(false, DeezerMyOpener.TABLE_NAME, columns, null, null, null, null, null, null);

        System.out.println(DeezerMyOpener.ALBUMCOVER);

        int SongID = results.getColumnIndex(DeezerMyOpener.ID);
        int SongTitle = results.getColumnIndex(DeezerMyOpener.TITLE);
        int SongDuration = results.getColumnIndex(DeezerMyOpener.DURATION);
        int SongAlbumName = results.getColumnIndex(DeezerMyOpener.ALBUMNAME);
        int SongAlbumCover = results.getColumnIndex(DeezerMyOpener.ALBUMCOVER);

        //iterate over the results, return true if there is a next item:
        while (results.moveToNext()) {
            long sId = results.getLong(SongID);
            String sTitle = results.getString(SongTitle);
            long sDuration = results.getLong(SongDuration);
            String sAlbumName = results.getString(SongAlbumName);
           String sAlbumCover = results.getString(SongAlbumCover);

            //add the new Contact to the array list:
            DeezerSongArrayList.add(new DeezerSong(sId,sTitle,sDuration,sAlbumName, sAlbumCover));
        }
    }

        private class MyListAdapter extends BaseAdapter {

        public long getItemId(int position) {
            return ((DeezerSong) getItem(position)).getId();
        }

        public int getCount() {
            return DeezerSongArrayList.size();
        }

        public Object getItem(int position) {
            return DeezerSongArrayList.get(position);
        }

        public View getView(int position, View old, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            DeezerSong song = (DeezerSong) getItem(position);

            View newView = inflater.inflate(R.layout.activity_deezer_list_view, null);
            //set what the text should be for this row:
            TextView tView = newView.findViewById(R.id.dListView);
            tView.setText(song.getTitle());
            //return it to be put in the table
            return newView;

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 987) {
            loadDataFromDatabase();
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //Look at your menu XML file. Put a case for every id in that file:
        switch (item.getItemId()) {
            //what to do when the menu item is selected:
            case R.id.itemGeo:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case R.id.itemSoccer:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case R.id.itemLyrics:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case R.id.itemAboutTheProject:
                Toast.makeText(this, "This is the Deezer activity, written by Yashika", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}
