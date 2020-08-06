package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
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

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class SoccerSearchPage extends AppCompatActivity {

    ArrayList<SoccerMatch> list = new ArrayList<>();
    MyListAdapter adapter;
    String keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soccer_search_page);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView SearchPageListView = findViewById(R.id.sSearchPageListView);
        Intent fromSoccerMainPage = getIntent();
        keyword = fromSoccerMainPage.getStringExtra("KEYWORD");
        
        for (SoccerMatch soccerMatch : SoccerMainPage.soccerMatchArrayList) {
            if(soccerMatch.getTitle().toLowerCase().contains(keyword.toLowerCase())){
                list.add(soccerMatch);
            }
        }

        adapter = new MyListAdapter();
        SearchPageListView.setAdapter(adapter);

        if(list.size()==0){
            Snackbar.make(SearchPageListView, getResources().getString(R.string.sSearchPageSnackBarMsg), Snackbar.LENGTH_LONG).show();
        }

        Button sSearchPageBackButton = findViewById(R.id.sSearchPageBackButton);
        sSearchPageBackButton.setOnClickListener( v -> {
        finish();
        });

        boolean isTablet = findViewById(R.id.fragmentLocation) != null;

        SearchPageListView.setOnItemClickListener((parent, view, position, id) -> {
            Bundle goToSearch = new Bundle();
            SoccerMatch selectedMatch = list.get(position);

            goToSearch.putLong("ID", selectedMatch.getId());
            goToSearch.putString("TITLE", selectedMatch.getTitle());
            goToSearch.putString("DATE", selectedMatch.getDate());
            goToSearch.putString("URL", selectedMatch.getURL());

            if(findViewById(R.id.fragmentLocation) == null) {

                Intent nextActivity = new Intent(SoccerSearchPage.this, SoccerDetailPage.class);
                nextActivity.putExtras(goToSearch);
                startActivity(nextActivity);

            } else {

                SoccerDetailsFragment soccerDetailsFragment = new SoccerDetailsFragment();
                soccerDetailsFragment.setArguments(goToSearch);

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentLocation, soccerDetailsFragment)
                        .commit();

            }
        });
    }


    private class MyListAdapter extends BaseAdapter {

        public int getCount() {
            return list.size();
        }

        public Object getItem(int position) {
            return list.get(position);
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