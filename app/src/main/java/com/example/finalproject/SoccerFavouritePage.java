package com.example.finalproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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

import java.util.ArrayList;

public class SoccerFavouritePage extends AppCompatActivity {
    static ArrayList<SoccerMatch> soccerMatchArrayList = new ArrayList<>();
    MyListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soccer_favourite_page);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView FavPageListView = findViewById(R.id.sFavPageListView);

        loadDataFromDatabase();
        adapter = new MyListAdapter();
        FavPageListView.setAdapter(adapter);

        Button sFavPageBackButton = findViewById(R.id.sFavPageBackButton);
        sFavPageBackButton.setOnClickListener( v -> {
            finish();
        });

        FavPageListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent goToDetail = new Intent(SoccerFavouritePage.this, SoccerDetailPage.class);
            SoccerMatch selectedMatch = soccerMatchArrayList.get(position);

            goToDetail.putExtra("POSITION", position);
            goToDetail.putExtra("ID", selectedMatch.getId());
            goToDetail.putExtra("TITLE", selectedMatch.getTitle());
            goToDetail.putExtra("DATE", selectedMatch.getDate());
            goToDetail.putExtra("URL", selectedMatch.getURL());

            startActivityForResult(goToDetail, 123);
        });

        FavPageListView.setOnItemLongClickListener((parent, view, position, id) -> {
            SoccerMatch selectedMatch = soccerMatchArrayList.get(position);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.sFavPageQuestion)
                    .setPositiveButton(R.string.sFavPagePositive, (click, b) -> {
                        soccerMatchArrayList.remove(position);
                        FavPageListView.setAdapter(new MyListAdapter());
                       final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.sFavPageRefresher);
                        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh() {
                                FavPageListView.setAdapter(new MyListAdapter());
                            }
                        });
                        Toast.makeText(SoccerFavouritePage.this,getResources().getString(R.string.sFavPageToastMsg), Toast.LENGTH_LONG).show();
                    })
                    .setNegativeButton(R.string.sFavPageNegative, (click, b) -> {
                        Toast.makeText(SoccerFavouritePage.this, getResources().getString(R.string.sFavPageToastMsg2), Toast.LENGTH_LONG).show();
                    }).create().show();
            return true;
        });
    }


    private void loadDataFromDatabase() {

        soccerMatchArrayList.clear();

        //get a database connection:
        SoccerMyOpener dbOpener = new SoccerMyOpener(this);
        SoccerMainPage.db = dbOpener.getReadableDatabase(); //This calls onCreate() if you've never built the table before, or onUpgrade if the version here is newer

        // We want to get all of the columns. Look at MyOpener.java for the definitions:
        String[] columns = {SoccerMyOpener.ID, SoccerMyOpener.TITLE, SoccerMyOpener.DATE, SoccerMyOpener.URL};
        //query all the results from the database:
        Cursor results = SoccerMainPage.db.query(false, SoccerMyOpener.TABLE_NAME, columns, null, null, null, null, null, null);

        //Now the results object has rows of results that match the query.
        //find the column indices:
        int SoccerIDIndex = results.getColumnIndex(SoccerMyOpener.ID);
        int SoccerTitleIndex = results.getColumnIndex(SoccerMyOpener.TITLE);
        int SoccerDateIndex = results.getColumnIndex(SoccerMyOpener.DATE);
        int SoccerURLIndex = results.getColumnIndex(SoccerMyOpener.URL);

        //iterate over the results, return true if there is a next item:
        while (results.moveToNext()) {
            long SoccerId = results.getLong(SoccerIDIndex);
            String SoccerTitle = results.getString(SoccerTitleIndex);
            String SoccerDate = results.getString(SoccerDateIndex);
            String SoccerURL = results.getString(SoccerURLIndex);

            //add the new Contact to the array list:
            soccerMatchArrayList.add(new SoccerMatch(SoccerId,SoccerTitle,SoccerDate,SoccerURL));
        }
    }


    private class MyListAdapter extends BaseAdapter {

        public int getCount() {
            return soccerMatchArrayList.size();
        }

        public Object getItem(int position) {
            return soccerMatchArrayList.get(position);
        }

        public long getItemId(int position) {
            return ((SoccerMatch) getItem(position)).getId();
        }

        public View getView(int position, View old, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            SoccerMatch match = (SoccerMatch) getItem(position);

            View newView = inflater.inflate(R.layout.activity_soccer_list_view, null);
            //set what the text should be for this row:
            TextView tView = newView.findViewById(R.id.sListView);
            tView.setText(match.getTitle());
            //return it to be put in the table
            return newView;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 123) {
            loadDataFromDatabase();
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //Look at your menu XML file. Put a case for every id in that file:
        switch (item.getItemId()) {
            //what to do when the menu item is selected:
            case R.id.GeoData:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case R.id.SongLyrics:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case R.id.DeezerSong:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case R.id.MyProject:
                Toast.makeText(this, (R.string.sComment), Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}