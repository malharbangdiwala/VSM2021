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
        double priceA = HomeFragment.stockPrice.get(0);
        double priceB = HomeFragment.stockPrice.get(1);
        double priceC = HomeFragment.stockPrice.get(2);
        double priceD = HomeFragment.stockPrice.get(3);
        double priceE = HomeFragment.stockPrice.get(4);
        double priceF = HomeFragment.stockPrice.get(5);
        double priceG = HomeFragment.stockPrice.get(6);
        double priceH = HomeFragment.stockPrice.get(7);
        String getLeaderBoard ="Select nameID,cash+A_shares*"+priceA+"+B_shares*"+priceB+"+C_shares*"+priceC+"+D_shares+E_shares+F_shares+G_shares+H_shares as points from login,valuation where login.phoneID = valuation.phoneID order by points";
        try {
            Statement st = connect.createStatement();
            ResultSet rs = st.executeQuery(getLeaderBoard);
            while (rs.next())
            {
                Log.d("Query",rs.getString("nameID")+rs.getDouble("points"));
            }
        }catch (Exception e)
        {
            Log.d("Error",e.getMessage());
        }
    }
}