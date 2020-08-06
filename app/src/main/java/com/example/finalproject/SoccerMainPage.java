package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.net.Uri;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class SoccerMainPage extends AppCompatActivity {

    /**
     *
     */
    static ArrayList<SoccerMatch> soccerMatchArrayList = new ArrayList<>();

    /**
     *
     */
    MyListAdapter adapter;
    static SQLiteDatabase db;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soccer_main_page);

        EditText sMainPageEditText = findViewById(R.id.sMainPageEditText);
        Button sMainPageFavButton = findViewById(R.id.sMainPageFavButton);
        Button sMainPageSearchButton = findViewById(R.id.sMainPageSearchButton);
        ListView MainPageListView = findViewById(R.id.sMainPageListView);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        prefs = getSharedPreferences("SearchKeyword", Context.MODE_PRIVATE);
        String savedString = prefs.getString("ReserveName", null);
        sMainPageEditText.setText(savedString);

        ProgressBar sMainPageProgressBar = findViewById(R.id.sMainPageProgressBar);
        sMainPageProgressBar.setVisibility(View.VISIBLE);

        MyHTTPRequest myRequest = new MyHTTPRequest();
        myRequest.execute("https://www.scorebat.com/video-api/v1/");

        adapter = new MyListAdapter();
        MainPageListView.setAdapter(adapter);

        sMainPageSearchButton.setOnClickListener((click) -> {
            Intent goToSearchPage = new Intent(SoccerMainPage.this, SoccerSearchPage.class);
            goToSearchPage.putExtra("KEYWORD", sMainPageEditText.getText().toString());
            startActivity(goToSearchPage);
        });

        sMainPageFavButton.setOnClickListener((click) -> {
            Intent goToFavPage = new Intent(SoccerMainPage.this, SoccerFavouritePage.class);
            startActivity(goToFavPage);
        });

        MainPageListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent goToDetail = new Intent(SoccerMainPage.this, SoccerDetailPage.class);
            SoccerMatch selectedMatch = soccerMatchArrayList.get(position);

            goToDetail.putExtra("ID", selectedMatch.getId());
            goToDetail.putExtra("TITLE", selectedMatch.getTitle());
            goToDetail.putExtra("DATE", selectedMatch.getDate());
            goToDetail.putExtra("URL", selectedMatch.getURL());

            startActivity(goToDetail);
        });

        //For NavigationDrawer:
        DrawerLayout drawer = findViewById(R.id.LayoutDrawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, toolbar, R.string.sNavigationOpen, R.string.sNavigationClose);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.Navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()) {
                    case R.id.itemInstructions:

                        AlertDialog.Builder builder  = new AlertDialog.Builder(SoccerMainPage.this);
                        builder.setTitle(R.string.sAppInstructions)
                                .setMessage(R.string.sAppDetailsInstructions)
                                .setPositiveButton(R.string.sPositiveButton, null).create().show();

                        break;
                    case R.id.itemAboutAPI:

                        // https://stackoverflow.com/questions/3004515/sending-an-intent-to-browser-to-open-specific-url
                        String url = "https://www.scorebat.com/video-api/";
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);

                        break;
                    case R.id.itemDonate:

                        // https://stackoverflow.com/questions/18799216/how-to-make-a-edittext-box-in-a-dialog
                        final EditText input = new EditText(SoccerMainPage.this);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT);
                        input.setLayoutParams(lp);
                        input.setHint("$$$");

                        AlertDialog.Builder builderDonate  = new AlertDialog.Builder(SoccerMainPage.this);
                        builderDonate.setTitle(R.string.sTitle)
                                .setMessage(R.string.sDonation)
                                .setView(input)
                                .setPositiveButton(R.string.sPositive, null)
                                .setNegativeButton(R.string.sNegative, null)
                                .create().show();
                        break;
                }

                DrawerLayout drawerLayout = findViewById(R.id.LayoutDrawer);
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    private class MyHTTPRequest extends AsyncTask<String, Integer, String> {

        //Type3                Type1
        public String doInBackground(String... args) {
            try {
                //create a URL object of what server to contact:
                URL Url = new URL(args[0]);

                //open the connection
                HttpURLConnection urlConnection = (HttpURLConnection) Url.openConnection();

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

                JSONArray SoccerJSONArray = new JSONArray(result);
                for (int i = 0; i < SoccerJSONArray.length(); i++) {
                    JSONObject SoccerJSONObj = SoccerJSONArray.getJSONObject(i);

                    SoccerMatch soccerMatch = new SoccerMatch();

                    soccerMatch.setTitle(SoccerJSONObj.getString("title"));
                    soccerMatch.setDate(SoccerJSONObj.getString("date"));

                    JSONArray videosJsonArray = SoccerJSONObj.getJSONArray("videos");
                    if (videosJsonArray.length() > 1) {
                        JSONObject videoJsonObject = videosJsonArray.getJSONObject(0);
                        String embed = videoJsonObject.getString("embed");
                        embed = embed.substring(embed.indexOf("src='") + 5);
                        embed = embed.substring(0, embed.indexOf("'"));
                        soccerMatch.setURL(embed);
                    }
                    soccerMatchArrayList.add(soccerMatch);
                }
            } catch (Exception e) {
            }
            return getString(R.string.sReturn);
        }

        //Type3
        public void onPostExecute(String fromDoInBackground) {

            ProgressBar pgBar = findViewById(R.id.sMainPageProgressBar);
            pgBar.setVisibility(View.INVISIBLE);
            adapter.notifyDataSetChanged();
        }
    }

    /**
     *
     */
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
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = getSharedPreferences("SearchKeyword", Context.MODE_PRIVATE);
        EditText sMainPageEditText = findViewById(R.id.sMainPageEditText);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("ReserveName", sMainPageEditText.getText().toString()); //putInt(String key, int i)  putFloat()
        editor.commit();
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