package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GameOverActivity extends AppCompatActivity {

    private Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        next = findViewById(R.id.overButton);
        final Intent intent = getIntent();
        final String roundType = intent.getStringExtra("roundType");
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (roundType.equals("TRIAL_ROUND"))
                {
                    Intent intentS = new Intent(GameOverActivity.this,SelectorActivity.class);
                    startActivity(intentS);
                    finish();
                }else {
                    Toast.makeText(GameOverActivity.this, "Wait for Instructions", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}