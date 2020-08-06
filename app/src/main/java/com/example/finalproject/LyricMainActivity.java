package com.core.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.core.example.R;
import com.core.example.songLyricSearchApp.LyricsSearchActivity;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LyricMainActivity extends AppCompatActivity {

    @BindView(R.id.lyricsApp)
    LinearLayout linearLyric;



    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyric_drawer);
        ButterKnife.bind(this);
//        Objects.requireNonNull(this.getActionBar()).setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        (getSupportActionBar()).setHomeButtonEnabled(true);
//
//        drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer);
//        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name);
//        drawerLayout.addDrawerListener(drawerToggle);
        DrawerLayout drawerLayout = findViewById(R.id.activity_main_drawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.infor_app_drawer:
                    Toast.makeText(this, "Information button selected", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.help_drawer:
                    Toast.makeText(this, "Help button selected", Toast.LENGTH_SHORT).show();
                    break;
            }
                             return true;

        });
    }

    @OnClick({R.id.lyricsApp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lyricsApp:


                    startActivity(new Intent(this, LyricsSearchActivity.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                break;


        }
    }


//    @Override
//    protected void onPostCreate(Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//        // Sync the toggle state after onRestoreInstanceState has occurred.
//        drawerToggle.syncState();
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        drawerToggle.onConfigurationChanged(newConfig);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.app_drawer, menu);
//
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if(drawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }
//
//        switch (item.getItemId()) {
//            case R.id.search:
//                Toast.makeText(this, "Search button selected", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.about:
//                Toast.makeText(this, "About button selected", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.help:
//                Toast.makeText(this, "Help button selected", Toast.LENGTH_SHORT).show();
//                return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
