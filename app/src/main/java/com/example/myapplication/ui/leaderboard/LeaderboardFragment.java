package com.example.myapplication.ui.leaderboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class LeaderboardFragment extends Fragment {
    ConnectionHelper con;
    static Connection connect;
    public static ArrayList<String> userNames = new ArrayList<>();
    public static ArrayList<Double> points = new ArrayList<>();
    RecyclerView usersLeaderBoard;
    static UserAdapter adapter;
    public static ArrayList<Users> users = new ArrayList<>();

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
        usersLeaderBoard = requireView().findViewById(R.id.userLeaderBoardView);
        usersLeaderBoard.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new UserAdapter(users,requireContext());
        usersLeaderBoard.setAdapter(adapter);
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
            for (int i=0;i<userNames.size();i++)
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