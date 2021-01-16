package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class GameOverActivity extends AppCompatActivity {

    private Button next;
    private RecyclerView leaderBoardFinal;
    ImageView podiumF;
    TextView podium_one_f,podium_two_f,podium_three_f;
    ConnectionHelper con;
    Connection connect;
    ArrayList<Users> users = new ArrayList<>();
    ArrayList<String> userNames = new ArrayList<>();
    ArrayList<Double> points = new ArrayList<>();
    UserAdapter adapter;
    ArrayList<Double> stockPrice = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        next = findViewById(R.id.overButton);
        leaderBoardFinal = findViewById(R.id.userLeaderBoardViewFinal);
        leaderBoardFinal.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        podiumF = findViewById(R.id.leaderboardPodiumFinal);
        podium_one_f = findViewById(R.id.podium1Final);
        podium_two_f = findViewById(R.id.podium2Final);
        podium_three_f = findViewById(R.id.podium3Final);

        Intent intent = getIntent();
        String roundType = intent.getStringExtra("roundType");
        if (roundType.equals("TRIAL_ROUND"))
        {
            leaderBoardFinal.setVisibility(View.INVISIBLE);
            podiumF.setVisibility(View.INVISIBLE);
            podium_one_f.setVisibility(View.INVISIBLE);
            podium_two_f.setVisibility(View.INVISIBLE);
            podium_three_f.setVisibility(View.INVISIBLE);
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentS = new Intent(GameOverActivity.this,SelectorActivity.class);
                    startActivity(intentS);
                    finish();
                }
            });
        }else {
            next.setVisibility(View.INVISIBLE);
            try {
                con = new ConnectionHelper();
                connect = ConnectionHelper.CONN();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            String queryCompanyDetails = "Select * from company ";
            try {
                Statement st = connect.createStatement();
                ResultSet rs = st.executeQuery(queryCompanyDetails);
                while (rs.next()){
                    stockPrice.add(rs.getDouble("r6_price"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Double priceA = stockPrice.get(0);
            Double priceB = stockPrice.get(1);
            Double priceC = stockPrice.get(2);
            Double priceD = stockPrice.get(3);
            Double priceE = stockPrice.get(4);
            Double priceF = stockPrice.get(5);
            Double priceG = stockPrice.get(6);
            Double priceH = stockPrice.get(7);
            String getLeaderBoard ="Select nameID,cash+A_shares*"+priceA+"+B_shares*"+priceB+"+C_shares*"+priceC+"+D_shares*"+priceD+"+E_shares*"+priceE+"+F_shares*"+priceF+"+G_shares*"+priceG+"+H_shares*"+priceH+" as points from login,valuation where login.phoneID = valuation.phoneID and day=1 order by points desc,nameID;";
            try {
                Statement st = connect.createStatement();
                ResultSet rs = st.executeQuery(getLeaderBoard);
                while (rs.next()){
                    userNames.add(rs.getString("nameID"));
                    points.add(rs.getDouble("points"));
                }
                podium_one_f.setText(userNames.get(0)+"\n"+points.get(0));
                podium_two_f.setText(userNames.get(1)+"\n"+points.get(1));
                podium_three_f.setText(userNames.get(2)+"\n"+points.get(2));
                for (int i=3;i<userNames.size();i++)
                {
                    Users userInstance = new Users(userNames.get(i),points.get(i));
                    users.add(userInstance);
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            adapter = new UserAdapter(users,getApplicationContext());
            leaderBoardFinal.setAdapter(adapter);
        }
    }
}