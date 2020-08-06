package com.core.example.songLyricSearchApp.myFavLyrics.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.core.example.R;

public class DetailsFragment extends Fragment {

    private boolean isTablet;
    private Bundle dataFromActivity;
    private long db_id;
    private int id;

    public void setTablet(boolean tablet) { isTablet = tablet; }


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dataFromActivity = getArguments();
        db_id = dataFromActivity.getLong("db_id" );
        id = dataFromActivity.getInt("id" );
        // Inflate the layout for this fragment
        View result =  inflater.inflate(R.layout.lyric_my_far_lyrics, container, false);

        //show the message
//        TextView message = (TextView)result.findViewById(R.id.messfragment);
//        message.setText("Message Here "+ dataFromActivity.getString("item"));

        //show the id:
//        TextView idView = (TextView) result.findViewById(R.id.IDtext);
//        idView.setText("Listview ID=" + id);

//        TextView ID_position = (TextView) result.findViewById(R.id.IDtext);
//        ID_position.setText("ID="+db_id);

        // get the delete button, and add a click listener:
//        Button hidebtn = (Button)result.findViewById(R.id.hideButton);
//        hidebtn.setOnClickListener( clk -> {
//
//            if(isTablet) { //both the list and details are on the screen:
//                ChatRoomActivity parent = (ChatRoomActivity)getActivity();
//                parent.deleteMessageId((int)db_id); //this deletes the item and updates the list
//
//
//                //now remove the fragment since you deleted it from the database:
//                // this is the object to be removed, so remove(this):
//                parent.getSupportFragmentManager().beginTransaction().remove(this).commit();
//            }
//            //for Phone:
//            else {
//                EmptyActivity parent = (EmptyActivity) getActivity();
//                Intent backToFragmentExample = new Intent();
//                backToFragmentExample.putExtra("db_id", dataFromActivity.getLong("db_id"));
//
//                parent.setResult(Activity.RESULT_OK, backToFragmentExample); //send data back to FragmentExample in onActivityResult()
//                parent.finish(); //go back
//            }
//
//        });
        return result;
    }
}
