package com.core.example.songLyricSearchApp.retrofit;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import com.core.example.R;
import com.core.example.songLyricSearchApp.model.LyricsOpener;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LyricQuery extends AsyncTask<String, Integer, String> {


    private String lyrics;
        private String artist;
        private int id;
    @SuppressLint("StaticFieldLeak")
        private ProgressBar progressBar;

        @Override
        protected String doInBackground(String... strings) {
            try {
                //create URL object of server to contact
                URL url = new URL(strings[0]);
                //open connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                //wait for data
                InputStream response = urlConnection.getInputStream();
                //From part 3: slide 19
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(response, null);

                int eventType = xpp.getEventType(); //The parser is currently at START_DOCUMENT

                //create newsReader object
                LyricsOpener newsItem = new LyricsOpener();

                while (eventType != XmlPullParser.END_DOCUMENT){
                    switch (eventType){
                        case XmlPullParser.START_TAG:
                            //Thread.sleep(10);
                            if(xpp.getName().equalsIgnoreCase("artist")){
                                xpp.next();
                                //connects to website, searches tags in xml for strings required
                                String artist = xpp.getText();
                                //set artist of object
                                newsItem.setArtist(artist);
                                publishProgress(25);
                            }
                            if(xpp.getName().equalsIgnoreCase("title")){
                                xpp.next();
                                //connects to website, searches tags in xml for strings required
                                String title = xpp.getText();
                                //set title of object
                                newsItem.setArtist(title);
                                publishProgress(25);
                            }
                            else if(xpp.getName().equalsIgnoreCase("id")){
                                xpp.next();
                                id = xpp.getColumnNumber();
                                //set id of object
                                newsItem.setId(id);
                                publishProgress(50);
                            }
                            break;

                        case XmlPullParser.END_TAG:
                            break;
                    }
                    eventType = xpp.next();
                }

                //create the network connection:
                URL UVurl = new URL("https://api.lyrics.ovh/v1/");
                HttpURLConnection UVConnection = (HttpURLConnection) UVurl.openConnection();
                response = UVConnection.getInputStream();

                //create a JSON object from the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                String result = sb.toString();
            } catch (XmlPullParserException | IOException e) {
                e.printStackTrace();
            }
            return "done";
        }

        public void onProgressUpdate(Integer... args) {
            progressBar = progressBar.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
        }

        public void onPostExecute(String fromDoInBackground) {
            ArrayAdapter<Object> newsListAdapter = null;
            newsListAdapter.notifyDataSetChanged();
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

