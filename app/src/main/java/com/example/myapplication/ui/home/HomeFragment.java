package com.example.myapplication.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.ConnectionHelper;
import com.example.myapplication.ItemClicked;
import com.example.myapplication.R;
import com.example.myapplication.SelectorActivity;
import com.example.myapplication.StockAdapter;
import com.example.myapplication.Stocks;

import java.nio.channels.Selector;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import static com.example.myapplication.MainActivity.MyPREFERENCES;

public class HomeFragment extends Fragment
{
    SharedPreferences sharedPreferences;
    CountDownTimer countDownTimer;
    TextView timer,userAmount;
    RecyclerView stockList;
    ArrayList<Stocks> stocks = new ArrayList<>();
    ArrayList<Double> stockPrice = new ArrayList<>();
    ArrayList<String> stockName = new ArrayList<>();
    ArrayList<Integer> shareOwned = new ArrayList<>();
    ViewPager mViewPager;
    StockAdapter adapter;
    String number;
    private int millisecValue;
    ConnectionHelper con;
    Connection connect;
    int roundNo = 1;
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sharedPreferences = this.getActivity().getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE);
        try {
            con = new ConnectionHelper();
            connect = ConnectionHelper.CONN();
        } catch (Exception e) {
            e.printStackTrace();
        }
        millisecValue = 30100;
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userAmount = requireView().findViewById(R.id.userAmount);
        number = sharedPreferences.getString("number","");
        getData();
        stockList = requireView().findViewById(R.id.stockView);
        stockList.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new StockAdapter(stocks, requireContext(), new ItemClicked() {
            @Override
            public void onClickBuy(int position, View view) {
                Log.d("Buy","Lets Buy");
            }

            @Override
            public void onClickSell(int position, View view) {
                Log.d("Sell","Lets Sell");
            }
        });
        stockList.setAdapter(adapter);
        timer  = requireView().findViewById(R.id.timer);
        startContinueTimer();
    }
    private void getData()
    {
        String queryCompanyDetails = "Select * from company ";
        String userPortfolioDetails = "Select * from valuation where phoneID= "+number;
        try {
            Statement st = connect.createStatement();
            ResultSet rs = st.executeQuery(queryCompanyDetails);
            Statement userSt = connect.createStatement();
            ResultSet userRs = userSt.executeQuery(userPortfolioDetails);
            String columnPrice = "r"+roundNo+"_price";
            while (rs.next())
            {
                stockName.add(rs.getString("company_name"));
                stockPrice.add(rs.getDouble(columnPrice));
            }
            while (userRs.next())
            {
                userAmount.setText(userRs.getString("cash"));
                shareOwned.add(userRs.getInt("A_shares"));
                shareOwned.add(userRs.getInt("B_shares"));
                shareOwned.add(userRs.getInt("C_shares"));
                shareOwned.add(userRs.getInt("D_shares"));
                shareOwned.add(userRs.getInt("E_shares"));
                shareOwned.add(userRs.getInt("F_shares"));
                shareOwned.add(userRs.getInt("G_shares"));
                shareOwned.add(userRs.getInt("H_shares"));
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        for (int i=0;i<stockName.size();i++)
        {
            Stocks stockInstance = new Stocks(stockName.get(i),stockPrice.get(i),shareOwned.get(i));
            stocks.add(stockInstance);
        }
        if (roundNo!=1)
            adapter.resetData(stocks);
    }
    private void startContinueTimer()
    {
        countDownTimer = new CountDownTimer(30100, 1000)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                timer.setText(String.valueOf(millisUntilFinished / 1000));
            }
            @Override
            public void onFinish()
            {
                Toast.makeText(requireContext(), "Round Finished proceed to next Round", Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(requireContext())
                        .setPositiveButton("Proceed to Next Round", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                stocks.clear();
                                stockName.clear();
                                stockPrice.clear();
                                shareOwned.clear();
                                roundNo++;
                                if(roundNo==5)
                                    Log.d("GAME OVER","Game Over");
                                else{
                                    getData();
                                    startContinueTimer();
                                }
                            }
                        }).show();
            }
        }.start();
    }
}