package com.example.myapplication.ui.leaderboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.ConnectionHelper;
import com.example.myapplication.R;
import com.example.myapplication.ui.home.HomeFragment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LeaderboardFragment extends Fragment {
    ConnectionHelper con;
    Connection connect;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            con = new ConnectionHelper();
            connect = ConnectionHelper.CONN();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String getLeaderBoard ="Select name,cash+A_shares+B_shares+C_shares+D_shares+E_shares+F_shares+G_shares+H_shares*r"+HomeFragment.roundNo+"_price as points from login,valuation where login.phoneID = valuation.phoneID order by points";
        Log.d("Query",getLeaderBoard);
        try {
            Statement st = connect.createStatement();
            ResultSet rs = st.executeQuery(getLeaderBoard);
            Log.d("Query",String.valueOf(rs.next()));
            while (rs.next())
            {
                Log.d("Query",rs.getString("points"));
            }
        }catch (Exception e)
        {
            Log.d("Error",e.getMessage());
        }
    }
}