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
import android.widget.EditText;
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
import com.example.myapplication.News;
import com.example.myapplication.R;
import com.example.myapplication.SelectorActivity;
import com.example.myapplication.StockAdapter;
import com.example.myapplication.Stocks;
import com.example.myapplication.ui.newsfeed.newsFeedFragment;

import java.nio.channels.Selector;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public static ArrayList<Double> stockPrice = new ArrayList<>();
    ArrayList<String> stockName = new ArrayList<>();
    ArrayList<Integer> shareOwned = new ArrayList<>();
    ViewPager mViewPager;
    StockAdapter adapter;
    String number;
    private int millisecValue;
    ConnectionHelper con;
    Connection connect;
    public static int roundNo = 1;
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
            public void onClickBuy(final int position, View view)
            {
                LayoutInflater inflater =(LayoutInflater) requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v = inflater.inflate(R.layout.buy_stocks,null,false);
                TextView stockNames = v.findViewById(R.id.stockName);
                final EditText stockBuy = v.findViewById(R.id.buy_id);
                stockNames.setText(stockName.get(position));
                new AlertDialog.Builder(requireContext())
                        .setView(v)
                        .setTitle("\t\t\t\t\t\tBUY\t\t\t\t\t")
                        .setPositiveButton("Buy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Integer stockB = Integer.parseInt(stockBuy.getText().toString());
                                Integer stockOwnedNow = Integer.parseInt(String.valueOf(shareOwned.get(position)+Integer.parseInt(stockBuy.getText().toString())));
                                shareOwned.set(position,stockOwnedNow);
                                Double cashOwnedNow = Double.parseDouble(userAmount.getText().toString())-(stockPrice.get(position)*stockB);
                                if (cashOwnedNow<0){
                                    Toast.makeText(requireContext(), "Not money", Toast.LENGTH_SHORT).show();
                                }else {
                                    userAmount.setText(String.valueOf(cashOwnedNow));

                                    String updateBuy = "Update valuation set " + stockName.get(position) + "_shares =" + stockOwnedNow + ",cash =" + cashOwnedNow + "where phoneID=" + number;
                                    String insertBuy = "Insert into trade values("+number+",'"+stockName.get(position)+"',"+roundNo+","+stockB+",0);";
                                    Stocks stockInstance = new Stocks(stockName.get(position),stockPrice.get(position),shareOwned.get(position));
                                    stocks.set(position,stockInstance);
                                    adapter.resetData(stocks);
                                    Log.d("Query",insertBuy);
                                    try {
                                        Statement st = connect.createStatement();
                                        st.executeQuery(updateBuy);
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        Statement statement = connect.createStatement();
                                        statement.executeQuery(insertBuy);
                                    }catch (Exception e)
                                    {

                                    }
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                            }
                        }).show();
            }
            @Override
            public void onClickSell(final int position, View view)
            {
                LayoutInflater inflater =(LayoutInflater) requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v = inflater.inflate(R.layout.buy_stocks,null,false);
                TextView stockNames = v.findViewById(R.id.stockName);
                final EditText stockSell = v.findViewById(R.id.buy_id);
                stockNames.setText(stockName.get(position));
                new AlertDialog.Builder(requireContext())
                        .setView(v)
                        .setTitle("\t\t\t\t\t\tSell\t\t\t\t\t")
                        .setPositiveButton("Sell", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Integer stockB = Integer.parseInt(stockSell.getText().toString());
                                if (Integer.parseInt(stockSell.getText().toString())<=shareOwned.get(position)) {
                                    Integer stockOwnedNow = Integer.parseInt(String.valueOf(shareOwned.get(position) - Integer.parseInt(stockSell.getText().toString())));
                                    shareOwned.set(position, stockOwnedNow);
                                    Double cashOwnedNow = Double.parseDouble(userAmount.getText().toString()) + (stockPrice.get(position) * stockB);
                                    userAmount.setText(String.valueOf(cashOwnedNow));
                                    String updateSell = "Update valuation set " + stockName.get(position) + "_shares =" + stockOwnedNow + ",cash =" + cashOwnedNow + "where phoneID=" + number;
                                    String insertSell = "Insert into trade values("+number+",'"+stockName.get(position)+"',"+roundNo+",0,"+stockB+");";
                                    Stocks stockInstance = new Stocks(stockName.get(position),stockPrice.get(position),shareOwned.get(position));
                                    stocks.set(position,stockInstance);
                                    adapter.resetData(stocks);
                                    try {
                                        Statement st = connect.createStatement();
                                        st.executeQuery(updateSell);
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        Statement statement = connect.createStatement();
                                        statement.executeQuery(insertSell);
                                    }catch (Exception e)
                                    {

                                    }
                                }else {
                                    Toast.makeText(requireContext(),"You dont have enough stocks",Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
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
        countDownTimer = new CountDownTimer(millisecValue, 1000)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                timer.setText(String.valueOf(millisUntilFinished / 1000));
            }
            @Override
            public void onFinish()
            {
                //TODO calculate the score and display it in the alertDialog
                /*
                score = A_shares*n_Ashares+...+H_shares*n_Hshares + Cash in hand
                 */
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
                                if(roundNo==6)
                                    Log.d("GAME OVER","Game Over");
                                else{
                                    getData();
                                    startContinueTimer();
                                    News.setNewsText();
                                }
                            }
                        }).show();
            }
        }.start();
    }
}