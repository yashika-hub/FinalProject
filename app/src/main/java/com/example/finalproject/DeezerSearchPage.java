package com.example.finalproject;

import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DeezerSearchPage extends AppCompatActivity {

    ArrayList<DeezerSong> list = new ArrayList<>();
    MyListAdapter adapter = new MyListAdapter();
    String artistName;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deezer_search_page);

        setSupportActionBar(findViewById(R.id.toolbar));

        ListView searchPageListview = findViewById(R.id.dSearchPageListView);
        progressBar = findViewById(R.id.progressBar);
        Intent intent = getIntent();
        artistName = intent.getStringExtra("keyForName");

        MyHTTPRequest myReq = new MyHTTPRequest();
        String url = "https://api.deezer.com/search/artist/?q=" + artistName + "&output=json";
        myReq.execute(url);

        Button dButtonBack = findViewById(R.id.dSearchPageButtonBack);
        dButtonBack.setOnClickListener(v -> {
            finish();
        });

        boolean isTablet = findViewById(R.id.fragmentLocation) != null;


        searchPageListview.setAdapter(adapter);


        searchPageListview.setOnItemClickListener((parent, view, position, id) -> {
            Bundle goToDetail = new Bundle();

            DeezerSong songSelect = list.get(position);
            goToDetail.putLong("ID", songSelect.getId());
            goToDetail.putString("TITLE", songSelect.getTitle());
            goToDetail.putLong("DURATION", songSelect.getDuration());
            goToDetail.putString("ALBUM NAME", songSelect.getAlbumName());
            goToDetail.putString("ALBUM COVER", songSelect.getAlbumCover());

            Intent nextActivity = new Intent(DeezerSearchPage.this, DeezerDetailsPage.class);
            nextActivity.putExtras(goToDetail);
            startActivity(nextActivity);
        });
    }

    private class MyListAdapter extends BaseAdapter {

        public long getItemId(int position) {
            return list.get(position).getId();
        }

        public int getCount() {
            return list.size();
        }

        public Object getItem(int position) {
            return list.get(position);
        }

        public View getView(int position, View old, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            DeezerSong match = (DeezerSong) getItem(position);

            View newView = inflater.inflate(R.layout.activity_deezer_list_view, null);
            //set what the text should be for this row:
            TextView tView = newView.findViewById(R.id.dListView);
            tView.setText(match.getTitle());
            //return it to be put in the table
            return newView;

        }
    }

    private class MyHTTPRequest extends AsyncTask<String, Integer, String> {

        //Type3                Type1
        public String doInBackground(String... args) {
            try {
                //create a URL object of what server to contact:
                URL url = new URL(args[0]);

                //open the connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                //wait for data:
                InputStream response = urlConnection.getInputStream();

                //JSON reading:   Look at slide 26
                //Build the entire string response:
                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                String result = sb.toString(); //result is the whole string

                JSONObject jsonObject = new JSONObject(result);
                JSONArray data = jsonObject.getJSONArray("data");
                if(data.length() == 0){
                    return null;
                }
                String trackListURL = data.getJSONObject(0).getString("tracklist");

                //create a URL object of what server to contact:
                url = new URL(trackListURL);

                //open the connection
                urlConnection = (HttpURLConnection) url.openConnection();

                //wait for data:
                response = urlConnection.getInputStream();

                //JSON reading:   Look at slide 26
                //Build the entire string response:
                reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                sb = new StringBuilder();
                line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                result = sb.toString(); //result is the whole string

                jsonObject = new JSONObject(result);
                data = jsonObject.getJSONArray("data");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject songJsonObject = data.getJSONObject(i);
                    long id = songJsonObject.getLong("id");
                    String title = songJsonObject.getString("title");
                    long duration = songJsonObject.getLong("duration");
                    JSONObject albumJsonObject = songJsonObject.getJSONObject("album");
                    String albumName = albumJsonObject.getString("title");
                    String albumCover = albumJsonObject.getString("cover");
                    DeezerSong deezerSong = new DeezerSong(id, title, duration, albumName, albumCover);
                    list.add(deezerSong);
                }

            } catch (Exception e) {

            }

            return "Done";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            progressBar.setVisibility(View.GONE);

            if (list.size() == 0) {
                Toast.makeText(DeezerSearchPage.this, "No song found for the artist searched", Toast.LENGTH_LONG).show();
            } else {
                adapter.notifyDataSetChanged();
            }
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
