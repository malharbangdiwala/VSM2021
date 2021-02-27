package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

import static com.example.myapplication.MainActivity.MyPREFERENCES;

public class GameOverActivity extends AppCompatActivity {

    private Button next;
    private RecyclerView leaderBoardFinal,toppersLeaderBoard;
    ConnectionHelper con;
    Connection connect;
    ArrayList<Users> users = new ArrayList<>();
    ArrayList<Users> toppers = new ArrayList<>();
    ArrayList<String> userNames = new ArrayList<>();
    ArrayList<Double> points = new ArrayList<>();
    UserAdapter adapter,adapterTopper;
    ArrayList<Double> stockPrice = new ArrayList<>();
    String name;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        next = findViewById(R.id.overButton);
        int playerPosition=0;

        leaderBoardFinal = findViewById(R.id.userLeaderBoardViewFinal);
        toppersLeaderBoard = findViewById(R.id.winner);

        leaderBoardFinal.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        toppersLeaderBoard.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        name = sharedPreferences.getString("name","");

        Intent intent = getIntent();
        String roundType = intent.getStringExtra("roundType");
        if (roundType.equals("TRIAL_ROUND"))
        {
            leaderBoardFinal.setVisibility(View.INVISIBLE);
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
            String getLeaderBoard ="Select nameID,cash+A_shares*"+priceA+"+B_shares*"+priceB+"+C_shares*"+priceC+"+D_shares*"+priceD+"+E_shares*"+priceE+"+F_shares*"+priceF+" as points from login,valuation where login.phoneID = valuation.phoneID and day=1 order by points desc,nameID;";
            try {
                Statement st = connect.createStatement();
                ResultSet rs = st.executeQuery(getLeaderBoard);
                while (rs.next()){
                    userNames.add(rs.getString("nameID"));
                    points.add(rs.getDouble("points"));
                }

                for (int i=0;i<userNames.size();i++)
                {
                    if (userNames.get(i).equals(name))
                        playerPosition = i;
                    Users userInstance = new Users(userNames.get(i),points.get(i));
                    if(i>=3)
                        users.add(userInstance);
                    else
                        toppers.add(userInstance);
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            adapter = new UserAdapter(users,getApplicationContext(),playerPosition,1);
            adapterTopper =new UserAdapter(toppers,getApplicationContext(),playerPosition,0);
            if (!(playerPosition>=3))
                leaderBoardFinal.scrollToPosition(playerPosition);
            leaderBoardFinal.setAdapter(adapter);
            toppersLeaderBoard.setAdapter(adapterTopper);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
class BoardLinearLayoutManager extends LinearLayoutManager{

    public BoardLinearLayoutManager(Context context) {
        super(context);
    }

    @Override
    public boolean isAutoMeasureEnabled() {
        return false;
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        }catch (IndexOutOfBoundsException e)
        {
            Log.d("Tag",""+e.getLocalizedMessage());
        }
    }
}