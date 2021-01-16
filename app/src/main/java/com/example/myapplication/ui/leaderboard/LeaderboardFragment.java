package com.example.myapplication.ui.leaderboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ConnectionHelper;
import com.example.myapplication.R;
import com.example.myapplication.UserAdapter;
import com.example.myapplication.Users;
import com.example.myapplication.ui.home.HomeFragment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static com.example.myapplication.ui.home.HomeFragment.roundNo;
import static com.example.myapplication.ui.home.HomeFragment.stockPrice;

public class LeaderboardFragment extends Fragment {
    ConnectionHelper con;
    static Connection connect;
    public static ArrayList<String> userNames = new ArrayList<>();
    public static ArrayList<Double> points = new ArrayList<>();
    RecyclerView usersLeaderBoard;
    static UserAdapter adapter;
    public static ArrayList<Users> users = new ArrayList<>();
    public static TextView leaderboardroundnumber;
    public static ImageView podium;
    static TextView podium1;
    static TextView podium2;
    static TextView podium3;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        leaderboardroundnumber=requireView().findViewById(R.id.leaderboardRoundNo);
        podium=requireView().findViewById(R.id.leaderboardPodium);
        podium1= requireView().findViewById(R.id.podium1);
        podium2= requireView().findViewById(R.id.podium2);
        podium3= requireView().findViewById(R.id.podium3);
        try {
            con = new ConnectionHelper();
            connect = ConnectionHelper.CONN();
        } catch (Exception e) {
            e.printStackTrace();
        }
        usersLeaderBoard = requireView().findViewById(R.id.userLeaderBoardView);
        usersLeaderBoard.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new UserAdapter(users,requireContext());
        usersLeaderBoard.setAdapter(adapter);
        if (roundNo!=1){
        leaderboardroundnumber.setVisibility(View.VISIBLE);
        leaderboardroundnumber.setText("Leaderboard: Round "+String.valueOf(roundNo));
        refreshLeaderBoard(stockPrice.get(0), stockPrice.get(1), stockPrice.get(2), stockPrice.get(3), stockPrice.get(4), stockPrice.get(5), stockPrice.get(6), stockPrice.get(7));
        LeaderboardFragment.podium.setVisibility(View.VISIBLE);}
    }
    public static void refreshLeaderBoard(double priceA,double priceB,double priceC,double priceD,double priceE,double priceF,double priceG,double priceH)
    {
        String getLeaderBoard ="Select nameID,cash+A_shares*"+priceA+"+B_shares*"+priceB+"+C_shares*"+priceC+"+D_shares*"+priceD+"+E_shares*"+priceE+"+F_shares*"+priceF+"+G_shares*"+priceG+"+H_shares*"+priceH+" as points from login,valuation where login.phoneID = valuation.phoneID and day=1 order by points desc,nameID;";
        try {
            Statement st = connect.createStatement();
            ResultSet rs = st.executeQuery(getLeaderBoard);
            while (rs.next())
            {
                userNames.add(rs.getString("nameID"));
                points.add(rs.getDouble("points"));
            }
            Log.d("TAG",userNames.toString()+points.toString());
            podium1.setText(userNames.get(0)+"\n"+points.get(0));
            podium2.setText(userNames.get(1)+"\n"+points.get(1));
            podium3.setText(userNames.get(2)+"\n"+points.get(2));
            for (int i=3;i<userNames.size();i++)
            {
                Users userInstance = new Users(userNames.get(i),points.get(i));
                users.add(userInstance);
            }
            Log.d("TAG",users.toString());
            adapter.resetData(users);
        }catch (Exception e)
        {
            Log.d("Error",e.getLocalizedMessage());
        }
    }
}