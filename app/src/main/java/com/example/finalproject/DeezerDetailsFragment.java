package com.example.finalproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;


/**
 * A simple {@link Fragment} subclass.
 */
public class DeezerDetailsFragment extends Fragment {
    long id;
    private boolean ifTablet;
    private AppCompatActivity parentActivity;

    public void setTablet(boolean tablet) {ifTablet = tablet;}

    public DeezerDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View result = inflater.inflate(R.layout.fragment_deezer_details, container, false);
        Bundle fromMain = getArguments();

        Button dBackButton = result.findViewById(R.id.dDetailsPageButtonFavFrag);
        dBackButton.setOnClickListener( v -> {
            if(ifTablet){
                DeezerSearchPage parent = (DeezerSearchPage) getActivity();
                parent.getSupportFragmentManager().beginTransaction().remove(this).commit();
            } else {

                DeezerEmptyActivity parent = (DeezerEmptyActivity) getActivity();
                Intent back = new Intent();
                parent.setResult(Activity.RESULT_OK, back);
                parent.finish();
            }
        }); TextView dTitle = result.findViewById(R.id.dDetailsPageTextView1Frag);
        String title = fromMain.getString("TITLE");
        dTitle.setText("Title of the song is: " + title);

        TextView dDuration = result.findViewById(R.id.dDetailsPageTextView2Frag);
        String duration = fromMain.getString("DURATION");
        dDuration.setText("Duration of song: " + duration);

        TextView dAlbumName = result.findViewById(R.id.dDetailsPageTextView3Frag);
        String AlbumName = fromMain.getString("Album Name");
        dAlbumName.setText("Album Name: " + AlbumName);

        TextView dAlbumCover = result.findViewById(R.id.dDetailsPageImageViewFrag);
        String AlbumCover = fromMain.getString("Album Cover");
        dAlbumCover.setText("Album Cover:  " + AlbumCover);

        // get the delete button, and add a click listener:
        Button dFavButton = (Button) result.findViewById(R.id.dDetailsPageButtonFavFrag);
        id = fromMain.getLong("ID", 0);
        int position = fromMain.getInt("POSITION", 0);


        dFavButton.setOnClickListener( clk -> {
            DeezerSong song = DeezerMainPage.DeezerSongArrayList.get(position);
            if(dFavButton.getText().equals("REMOVE FROM FAVOURITE")){
                dFavButton.setText("ADD TO FAVOURITE");
                Snackbar.make(dFavButton, "Removed from favourite list", Snackbar.LENGTH_LONG).show();
            } else{
                dFavButton.setText("REMOVE FROM FAVOURITE");
                Snackbar.make(dFavButton, "Added to favourite list", Snackbar.LENGTH_LONG).show();
            }
        });
        return result;

    }

    public void onAttach(Context context){
        super.onAttach(context);
        parentActivity = (AppCompatActivity)context;
    }
}
