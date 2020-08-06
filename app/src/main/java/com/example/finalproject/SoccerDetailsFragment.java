package com.example.finalproject;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class SoccerDetailsFragment extends Fragment {

    long id;

    public SoccerDetailsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_soccer_details, container, false);
        Bundle fromMain = getArguments();

        Button sDetailPageBackButton = result.findViewById(R.id.sDetailPageBackButton);
        sDetailPageBackButton.setOnClickListener( v -> {
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .remove(this)
                    .commit();
        });

        TextView sDetailPageTitle = (TextView) result.findViewById(R.id.sDetailPageTitle);
        String title = fromMain.getString("TITLE");
        sDetailPageTitle.setText("Two Teams that are Playing: " + title);

        TextView sDetailPageDate = (TextView) result.findViewById(R.id.sDetailPageDate);
        String date = fromMain.getString("DATE");
        sDetailPageDate.setText("Today's Date: " + date);

        TextView sDetailPageURL = (TextView) result.findViewById(R.id.sDetailPageURL);
        String URL = fromMain.getString("URL");
        sDetailPageURL.setText("URL: "+ URL);


        // get the delete button, and add a click listener:
        Button sDetailPageFavButton = (Button) result.findViewById(R.id.sDetailPageFavButton);
        id = fromMain.getLong("ID", -1);
        int position = fromMain.getInt("POSITION", 0);


        if(id!=-1)
            sDetailPageFavButton.setText("REMOVE FROM FAVOURITE");

        SoccerMyOpener dbOpener = new SoccerMyOpener(getActivity());
        SQLiteDatabase db = dbOpener.getWritableDatabase();


        sDetailPageFavButton.setOnClickListener( clk -> {
            SoccerMatch sm = SoccerMainPage.soccerMatchArrayList.get(position);
            if(sDetailPageFavButton.getText().equals("REMOVE FROM FAVOURITE")){ // Remove from DB
                sDetailPageFavButton.setText("ADD TO FAVOURITE");
                Snackbar.make(sDetailPageFavButton, getResources().getString(R.string.sDetailPageToastMsg), Snackbar.LENGTH_LONG).show();

                db.delete(SoccerMyOpener.TABLE_NAME, SoccerMyOpener.ID + " =?", new String[]{Long.toString(id)});

            } else{ // Adding to DB
                sDetailPageFavButton.setText("REMOVE FROM FAVOURITE");
                Snackbar.make(sDetailPageFavButton, getResources().getString(R.string.sDetailPageToastMsg2), Snackbar.LENGTH_LONG).show();

                ContentValues newRowValues = new ContentValues();
                newRowValues.put(SoccerMyOpener.TITLE, title);
                newRowValues.put(SoccerMyOpener.DATE, date);
                newRowValues.put(SoccerMyOpener.URL, URL);
                id = db.insert(SoccerMyOpener.TABLE_NAME, null, newRowValues);
            }
        });

        Button sDetailPageMatchHighlights = result.findViewById(R.id.sDetailPageMatchHighlights);
        if(URL == null || URL.isEmpty()){
            sDetailPageMatchHighlights.setVisibility(View.GONE);
        }
        sDetailPageMatchHighlights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(URL));
                startActivity(i);
            }
        });
        return result;

    }

}
