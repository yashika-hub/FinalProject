package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button ToSoccerMainPageButton = findViewById(R.id.ToSoccerMainPageButton);
        ToSoccerMainPageButton.setOnClickListener( (click) -> {
            Intent goToSoccerMainPage = new Intent(MainActivity.this, SoccerMainPage.class);
            startActivity(goToSoccerMainPage);
        });
    }
}

