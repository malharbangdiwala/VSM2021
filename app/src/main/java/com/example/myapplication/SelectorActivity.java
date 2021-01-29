package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SelectorActivity extends AppCompatActivity {
    ConnectionHelper con;
    Connection connect;
    int roundNo = 1;
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector);
        Button gameRound = findViewById(R.id.gameRound);

        try {
            con = new ConnectionHelper();
            connect = ConnectionHelper.CONN();
        } catch (Exception e) {
            e.printStackTrace();
        }


        gameRound.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View v) {
            String whichRounds = "Select * from rounds";
            try {
                Statement st = connect.createStatement();
                ResultSet rs = st.executeQuery(whichRounds);
                if (rs.next())
                {
                    if (rs.getInt("r1")==1)
                    {
                        flag = 1;
                        roundNo = 1;
                    }else if (rs.getInt("r2")==1)
                    {
                        roundNo = 2;
                    }else if (rs.getInt("r3")==1)
                    {
                        roundNo = 3;
                    }else if (rs.getInt("r4")==1)
                    {
                        roundNo = 4;
                    }else if (rs.getInt("r5")==1){
                        roundNo = 5;
                    }
                }
            }catch (SQLException e)
            {

            }
            Log.d("Round No",""+roundNo);
            if (flag==0&& roundNo==1) {
                Toast.makeText(SelectorActivity.this, "Next round hasn't started yet!", Toast.LENGTH_SHORT).show();
            }else {
            Intent intent = new Intent(SelectorActivity.this, GamesActivity.class);
            intent.putExtra("roundNoLive",roundNo);
            startActivity(intent);
            }
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