package com.core.example.songLyricSearchApp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.core.example.LyricMainActivity;
import com.core.example.R;
import com.core.example.songLyricSearchApp.Response.Lyrics;
import com.core.example.songLyricSearchApp.myFavLyrics.My_fav_lyrics;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LyricsSearchActivity extends AppCompatActivity implements MainView {

    @BindView(R.id.artist)
    EditText artist;
    @BindView(R.id.title_)
    EditText title;
    @BindView(R.id.btn_search)
    AppCompatButton btnSearch;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.btn_mysongs)
    AppCompatButton btnMysongs;
    private MainPresenter presenter;

    private  String ARTIST ="SaveArtist";
    private String TITLE ="SaveTitle";
    private Bundle dataToPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyric_activity_songlyrics_search);
        ButterKnife.bind(this);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.ic_fav);
        actionBar.setTitle(R.string.app_name);


        showView();


//        LyricQuery lyricQuery = new LyricQuery();
//        lyricQuery.execute();

        if (savedInstanceState!=null){

            String ArtistSave = savedInstanceState.getString(ARTIST);
            String TitleSave = savedInstanceState.getString(TITLE);

            title.setText(ArtistSave);
            artist.setText(TitleSave);

        }else {



        }


         presenter = new Presenter(this);
        progressBar.setVisibility(View.GONE);

        // If it returns null then you are on a phone, otherwise it’s on a tablet. Store this in result in a Boolean variable
//    boolean onTablet = findViewById(R.id.fragmentLocation) != null;

//
//        if (onTablet){
//            Fragment_Recycler dFragment = new Fragment_Recycler(); //add a DetailFragment
//            dFragment.setArguments( dataToPass ); //pass it a bundle for information
//            dFragment.setTablet(true);  //tell the fragment if it's running on a tablet or not
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .add(R.id.fragmentLocation, dFragment) //Add the fragment in FrameLayout
//                    .addToBackStack("AnyName") //make the back button undo the transaction
//                    .commit(); //actually load the fragment.
//        }else {
//            Intent emptyActivity = new Intent(this, EmptyActivity.class);
//            emptyActivity.putExtras(dataToPass);
//            startActivityForResult(emptyActivity, 345);
//        }


    }




        //create menu tool bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_toolbar, menu);
        return true;
    }

    //menu tool bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Look at your menu XML file. Put a case for every id in that file:
        switch (item.getItemId()) {
            //what to do when the menu item is selected:
            case R.id.donate_toolbar:
                AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(this);
                alertDialogBuilder1.setTitle("Give me generously $$$");
                alertDialogBuilder1.setMessage("How much money do you want to donate?");
                final EditText input = new EditText(this);
                input.setInputType(InputType.TYPE_CLASS_TEXT );
                alertDialogBuilder1.setView(input);

                // add the buttons
                alertDialogBuilder1.setPositiveButton("Thank you", null);
                alertDialogBuilder1.setNegativeButton("Cancel", null);
                // create and show the alert dialog
                AlertDialog dialog = alertDialogBuilder1.create();
                dialog.show();
                break;
            case R.id.API_toolbar:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://lyricsovh.docs.apiary.io/#"));
                startActivity(intent);
                break;
            case R.id.instruction_toolbar:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Enter your artist and title lyric. Click Favourite with option note, to save a lyric to Favourites.");
                alertDialogBuilder.setNegativeButton("Exit", null);
                alertDialogBuilder.create().show();
                break;
        }
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);



        outState.putString(ARTIST, artist.getText().toString());
        outState.putString(TITLE,title.getText().toString());
    }


    @Override
    public void show(String result) {

        String artist= this.artist.getText().toString();
        String title = this.title.getText().toString();

        Intent activity = new Intent(this, Lyrics.class);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        activity.putExtra("artist", artist);
        activity.putExtra("title", title);
        activity.putExtra("lyric", result);
        startActivity(activity);

    }


    @Override
    public void showError(String error) {

        Toast.makeText(this, R.string.error_short_name, Toast.LENGTH_LONG).show();
        showView();
    }

    private boolean checkTyping() {


        if (artist.getText().toString().trim().isEmpty()) {

            artist.setError(getResources().getString(R.string.error_empty));
            btnSearch.setVisibility(View.GONE);
            return false;
        }
        if (artist.length()<3) {

            artist.setError(getResources().getString(R.string.error_short_name));
            btnSearch.setVisibility(View.GONE);
            return false;
        }


        if (title.getText().toString().trim().isEmpty()) {

            title.setError(getResources().getString(R.string.error_empty));
            btnSearch.setVisibility(View.GONE);
            return false;
        }
        if (title.length()<3) {

            title.setError(getResources().getString(R.string.error_short_name));
            btnSearch.setVisibility(View.GONE);
            return false;
        }
        btnSearch.setVisibility(View.VISIBLE);
        return true;
    }


    private void hideView() {

        linearLayout.setVisibility(View.GONE);
        btnMysongs.setVisibility(View.GONE);
        btnSearch.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

    }

    private void showView() {

        linearLayout.setVisibility(View.VISIBLE);
        btnMysongs.setVisibility(View.VISIBLE);
        btnSearch.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);


        artist.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkTyping();
            }
        });


        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkTyping();
            }
        });


    }

    @OnClick({R.id.btn_search, R.id.btn_mysongs})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_search:

                if (checkTyping()) {
                    hideView();
                    presenter.sendData(artist.getText().toString().trim(),
                            title.getText().toString().trim());
                }

                break;
            case R.id.btn_mysongs:
                startActivity(new Intent(this, My_fav_lyrics.class));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(this, LyricMainActivity.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
