package com.example.myapplication.ui.leaderboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import static com.example.myapplication.MainActivity.MyPREFERENCES;
import static com.example.myapplication.ui.home.HomeFragment.roundNo;
import static com.example.myapplication.ui.home.HomeFragment.stockPrice;

public class LeaderboardFragment extends Fragment {
    ConnectionHelper con;
    static Connection connect;
    public static ArrayList<String> userNames = new ArrayList<>();
    public static ArrayList<Double> points = new ArrayList<>();
    static RecyclerView usersLeaderBoard,toppersLeaderBoard;
    static UserAdapter adapter;
    static UserAdapter adapterTopper;
    public static TextView timers;
    public static ArrayList<Users> users = new ArrayList<>();
    public static ArrayList<Users> toppers = new ArrayList<>();
    public static TextView leaderboardroundnumber;
    public static ImageView podium;
    static TextView podium1;
    static TextView podium2;
    static TextView podium3;
    SharedPreferences sharedPreferences;
    static String name;
    public static int playerPosition = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = requireActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        name = sharedPreferences.getString("name","");
        leaderboardroundnumber=requireView().findViewById(R.id.leaderboardRoundNo);
        try {
            con = new ConnectionHelper();
            connect = ConnectionHelper.CONN();
        } catch (Exception e) {
            e.printStackTrace();
        }
        timers = requireView().findViewById(R.id.timer2);
        usersLeaderBoard = requireView().findViewById(R.id.userLeaderBoardView);
        usersLeaderBoard.setLayoutManager(new LeaderBoardLinearLayoutManager(requireContext()));
        adapter = new UserAdapter(users,requireContext(),playerPosition,1);
        usersLeaderBoard.setAdapter(adapter);

        toppersLeaderBoard = requireView().findViewById(R.id.winner);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        toppersLeaderBoard.setLayoutManager(layoutManager);
        adapterTopper = new UserAdapter(users,requireContext(),playerPosition,0);
        toppersLeaderBoard.setAdapter(adapterTopper);

        if (!(playerPosition>=3))
            usersLeaderBoard.scrollToPosition(playerPosition);
        if (roundNo!=1){
        leaderboardroundnumber.setVisibility(View.VISIBLE);
        leaderboardroundnumber.setText("Leaderboard: Round "+String.valueOf(roundNo));
        refreshLeaderBoard(stockPrice.get(0), stockPrice.get(1), stockPrice.get(2), stockPrice.get(3), stockPrice.get(4), stockPrice.get(5));
        }
    }
    public static void refreshLeaderBoard(double priceA,double priceB,double priceC,double priceD,double priceE,double priceF)
    {
        String getLeaderBoard ="Select nameID,cash+A_shares*"+priceA+"+B_shares*"+priceB+"+C_shares*"+priceC+"+D_shares*"+priceD+"+E_shares*"+priceE+"+F_shares*"+priceF+" as points from login,valuation where login.phoneID = valuation.phoneID and day=1 order by points desc,nameID;";
        try {
            Statement st = connect.createStatement();
            ResultSet rs = st.executeQuery(getLeaderBoard);
            while (rs.next())
            {
                userNames.add(rs.getString("nameID"));
                points.add(rs.getDouble("points"));
            }

            for (int i=0;i<userNames.size();i++)
            {
                if (userNames.get(i).equals(name)) {
                    playerPosition = i;
                }
                Users userInstance = new Users(userNames.get(i),points.get(i));
                if(i>=3)
                    users.add(userInstance);
                else
                    toppers.add(userInstance);
            }
            if (!(playerPosition>=3))
                usersLeaderBoard.scrollToPosition(playerPosition);
            adapter.resetData(users,playerPosition);
            adapterTopper.resetData(toppers,playerPosition);
        }catch (Exception e)
        {
            Log.d("Error",e.getMessage());
        }
    }
}
class LeaderBoardLinearLayoutManager extends LinearLayoutManager{

    public LeaderBoardLinearLayoutManager(Context context) {
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