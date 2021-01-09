package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectorActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector);
        getSupportActionBar().hide();
        Button gameRound = findViewById(R.id.gameRound);
        gameRound.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View v) {
            Intent intent = new Intent(SelectorActivity.this, GamesActivity.class);
            startActivity(intent);
        }
        });
        Button trialRound = findViewById(R.id.trialRound);
        trialRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectorActivity.this,TrialActivity.class);
                startActivity(intent);
            }
        });
    }
}