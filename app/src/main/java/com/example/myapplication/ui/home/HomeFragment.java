package com.example.myapplication.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.myapplication.ConnectionHelper;
import com.example.myapplication.GameOverActivity;
import com.example.myapplication.ItemClicked;
import com.example.myapplication.News;
import com.example.myapplication.R;
import com.example.myapplication.SelectorActivity;
import com.example.myapplication.StockAdapter;
import com.example.myapplication.Stocks;
import com.example.myapplication.powercard3;
import com.example.myapplication.ui.leaderboard.LeaderboardFragment;
import com.example.myapplication.ui.newsfeed.newsFeedFragment;
import com.example.myapplication.ui.newsfeed.newsFeedFragment;
import com.example.myapplication.ui.powercard.PowerCardFragment;

import org.w3c.dom.Text;

import java.nio.channels.Selector;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import static com.example.myapplication.MainActivity.MyPREFERENCES;

public class HomeFragment extends Fragment
{
    SharedPreferences sharedPreferences;
    CountDownTimer countDownTimer;
    TextView timer;
    public static TextView userAmount;
    RecyclerView stockList;
    ArrayList<Stocks> stocks = new ArrayList<>();
    ArrayList<String> stockName = new ArrayList<>();
    ArrayList<Integer> shareOwned = new ArrayList<>();
    public static ArrayList<Double> stockPrice = new ArrayList<>();
    StockAdapter adapter;
    String number;
    int status;
    TextView totalSum;
    private int millisecValue;
    ConnectionHelper con;
    Connection connect;
    public static int roundNo = 1;
    TextView homeroundno;
    Random rand = new Random();

    ArrayList<Drawable> memes = new ArrayList<>();
    DecimalFormat df = new DecimalFormat();

    public HomeFragment(int status,int roundNo) {
        this.status = status;
        this.roundNo = roundNo;
    }

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
        df.setMinimumFractionDigits(2);
        userAmount = requireView().findViewById(R.id.userAmount);
        homeroundno=requireView().findViewById(R.id.homeRoundNo);
        number = sharedPreferences.getString("number","");
        stockList = requireView().findViewById(R.id.stockView);
        stockList.setLayoutManager(new LinearLayoutManager(requireContext()));
        homeroundno.setText("Round "+String.valueOf(roundNo));
        adapter = new StockAdapter(stocks, requireContext(), new ItemClicked() {
            @Override
            public void onClickBuy(final int position, View view)
            {
                LayoutInflater inflater =(LayoutInflater) requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v = inflater.inflate(R.layout.buy_stocks,null,false);
                final EditText stockBuy = v.findViewById(R.id.buy_id);
                totalSum = v.findViewById(R.id.total);
                stockBuy.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(s.toString().length()>=10)
                        {
                          totalSum.setText("You don't have enough money!");
                        }
                        else if (!s.toString().equals(""))
                            totalSum.setText("Funds Required :"+String.valueOf(Integer.parseInt(s.toString())*stockPrice.get(position)));
                        else
                            totalSum.setText("0");
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
                AlertDialog.Builder builder =new AlertDialog.Builder(requireContext());
                        builder.setView(v);
                        TextView title = new TextView(getContext());
                        title.setText("BUY "+stockName.get(position));
                        title.setPadding(10,10,10,10);
                        title.setGravity(Gravity.CENTER);
                        title.setTextSize(20);
                        title.setTypeface(Typeface.DEFAULT_BOLD);
                        builder.setCustomTitle(title);
                        builder.setPositiveButton("Buy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!stockBuy.getText().toString().equals("")) {
                                    if (stockBuy.getText().toString().length() >= 10)
                                        Toast.makeText(requireContext(), "You don't have enough money!", Toast.LENGTH_SHORT).show();
                                    else {
                                        Integer stockB = Integer.parseInt(stockBuy.getText().toString());
                                        Integer stockOwnedNow = Integer.parseInt(String.valueOf(shareOwned.get(position) + Integer.parseInt(stockBuy.getText().toString())));
                                        Double cashOwnedNow = Double.parseDouble(userAmount.getText().toString()) - (stockPrice.get(position) * stockB);
                                        if (cashOwnedNow < 0) {
                                            Toast.makeText(requireContext(), "You don't have enough money!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            shareOwned.set(position, stockOwnedNow);
                                            userAmount.setText(String.valueOf((cashOwnedNow)));
                                            String updateBuy = "Update valuation set " + stockName.get(position) + "_shares =" + stockOwnedNow + ",cash =" + cashOwnedNow + "where phoneID=" + number;
                                            String insertBuy = "Insert into trade values(" + number + ",'" + stockName.get(position) + "'," + roundNo + "," + stockB + ",0);";
                                            Stocks stockInstance = new Stocks(stockName.get(position), stockPrice.get(position), shareOwned.get(position));
                                            stocks.set(position, stockInstance);
                                            adapter.resetData(stocks);
                                            if (status == 1) {
                                                try {
                                                    Statement st = connect.createStatement();
                                                    st.executeQuery(updateBuy);
                                                } catch (SQLException e) {
                                                    e.printStackTrace();
                                                }
                                                try {
                                                    Statement statement = connect.createStatement();
                                                    statement.executeQuery(insertBuy);
                                                } catch (Exception e) {

                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                            }
                        });
                        builder.show();
            }
            @Override
            public void onClickSell(final int position, View view)
            {
                LayoutInflater inflater =(LayoutInflater) requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v = inflater.inflate(R.layout.buy_stocks,null,false);
                final EditText stockSell = v.findViewById(R.id.buy_id);
                totalSum = v.findViewById(R.id.total);
                stockSell.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(s.toString().length()>=10)
                        {
                            totalSum.setText("You don't have enough stocks!");
                        }
                        else if (!s.toString().equals(""))
                        totalSum.setText(String.valueOf(Integer.parseInt(s.toString())*stockPrice.get(position)));
                        else
                            totalSum.setText("0");
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
                new AlertDialog.Builder(requireContext())
                        .setView(v)
                        .setTitle("\t\t\t\t\t\tSell\t\t\t\t\t")
                        .setPositiveButton("Sell", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!stockSell.getText().toString().equals("")) {
                                    if (stockSell.getText().toString().length() >= 10)
                                        Toast.makeText(requireContext(), "You don't have enough stocks!", Toast.LENGTH_SHORT).show();
                                    else {
                                        Integer stockB = Integer.parseInt(stockSell.getText().toString());
                                        if (Integer.parseInt(stockSell.getText().toString()) <= shareOwned.get(position)) {
                                            Integer stockOwnedNow = Integer.parseInt(String.valueOf(shareOwned.get(position) - Integer.parseInt(stockSell.getText().toString())));
                                            shareOwned.set(position, stockOwnedNow);
                                            Double cashOwnedNow = Double.parseDouble(userAmount.getText().toString()) + (stockPrice.get(position) * stockB);
                                            userAmount.setText(String.valueOf((cashOwnedNow)));
                                            String updateSell = "Update valuation set " + stockName.get(position) + "_shares =" + stockOwnedNow + ",cash =" + cashOwnedNow + "where phoneID=" + number;
                                            String insertSell = "Insert into trade values(" + number + ",'" + stockName.get(position) + "'," + roundNo + ",0," + stockB + ");";
                                            Stocks stockInstance = new Stocks(stockName.get(position), stockPrice.get(position), shareOwned.get(position));
                                            stocks.set(position, stockInstance);
                                            adapter.resetData(stocks);
                                            if (status == 1) {
                                                try {
                                                    Statement st = connect.createStatement();
                                                    st.executeQuery(updateSell);
                                                } catch (SQLException e) {
                                                    e.printStackTrace();
                                                }
                                                try {
                                                    Statement statement = connect.createStatement();
                                                    statement.executeQuery(insertSell);
                                                } catch (Exception e) {

                                                }
                                            }
                                        } else {
                                            Toast.makeText(requireContext(), "You don't have enough stocks!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
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
        getData();
        stockList.setAdapter(adapter);
        timer  = requireView().findViewById(R.id.timer);
        startContinueTimer();

        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Toast.makeText(requireContext(), "You cannot go back at this stage!", Toast.LENGTH_SHORT).show();
            }
        });

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

    private void startContinueTimer() {
        countDownTimer = new CountDownTimer(millisecValue, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(String.valueOf(millisUntilFinished / 1000));
                newsFeedFragment.timer.setText(String.valueOf(millisUntilFinished / 1000));
                LeaderboardFragment.timers.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                if (status==0){
                    Toast.makeText(requireContext(), "The Trial Round is Over!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(),GameOverActivity.class);
                    intent.putExtra("roundType","TRIAL_ROUND");
                    startActivity(intent);
                    getActivity().finish();
                }
                else {
                    roundNo++;
                    News.setNewsText();
                    LeaderboardFragment.users.clear();
                    LeaderboardFragment.userNames.clear();
                    LeaderboardFragment.points.clear();
                    homeroundno.setText("Round "+String.valueOf(roundNo));
                    if (PowerCardFragment.pc3flag == 1) {
                        if (status == 1) {
                            PowerCardFragment.pc3flag = 0;
                            String reducecash = "Update valuation set cash=cash-" + powercard3.deduction + " where phoneID=" + number + ";";
                            try {
                                Statement st = connect.createStatement();
                                st.executeQuery(reducecash);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    Toast.makeText(requireContext(), "Round Finished.Proceed to the next round!", Toast.LENGTH_SHORT).show();
                    final TextView funds = requireView().findViewById(R.id.textView3);

                    final ImageView roundChangeButton =  requireView().findViewById(R.id.roundChangeButton);
                    roundChangeButton.setVisibility(View.VISIBLE);
                    Glide.with(getContext()).load(R.drawable.loader).into(roundChangeButton);
                    funds.setVisibility(View.INVISIBLE);
                    homeroundno.setVisibility(View.INVISIBLE);
                    roundChangeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String queryNextRound = "Select r" + roundNo + " from rounds";
                            System.out.println(queryNextRound);
                            int nextRoundStart = 0;
                            try {
                                Statement st = connect.createStatement();
                                ResultSet rs = st.executeQuery(queryNextRound);
                                while (rs.next()) {
                                    Log.d("Tag", rs.getString("r" + roundNo));
                                    nextRoundStart = rs.getInt("r" + roundNo);
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            if (roundNo == 6 && nextRoundStart==1) {
                                Toast.makeText(requireContext(), "Game Over!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), GameOverActivity.class);
                                intent.putExtra("roundType","GAME_ROUND");
                                startActivity(intent);
                                getActivity().finish();
                            }else {
                                if (nextRoundStart == 1) {
                                    ImageView roundChangeButton =  requireView().findViewById(R.id.roundChangeButton);
                                    roundChangeButton.setVisibility(View.GONE);
                                    funds.setVisibility(View.VISIBLE);
                                    homeroundno.setVisibility(View.VISIBLE);
                                    stocks.clear();
                                    stockName.clear();
                                    stockPrice.clear();
                                    shareOwned.clear();
                                    getData();
                                    LeaderboardFragment.users.clear();
                                    LeaderboardFragment.userNames.clear();
                                    LeaderboardFragment.points.clear();
                                    if (roundNo!=6){
                                        LeaderboardFragment.leaderboardroundnumber.setVisibility(View.VISIBLE);
                                        LeaderboardFragment.leaderboardroundnumber.setText("Leaderboard: Round "+String.valueOf(roundNo));
                                        LeaderboardFragment.refreshLeaderBoard(stockPrice.get(0), stockPrice.get(1), stockPrice.get(2), stockPrice.get(3), stockPrice.get(4), stockPrice.get(5));
                                    }


                                    startContinueTimer();
                                } else {
                                    Toast.makeText(requireContext(), "Next Round hasn't started yet", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }
            }
        }.start();
    }
}
