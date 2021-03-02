package com.example.myapplication.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

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
import java.sql.Array;
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
    ArrayList<Integer> IncDec = new ArrayList<>();
    ArrayList<String> nameStock = new ArrayList<>();
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




    MediaPlayer mediaPlayer;
    public HomeFragment(){}
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
        millisecValue = 30000;
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mediaPlayer=MediaPlayer.create(this.getActivity(),R.raw.timer);
        userAmount = requireView().findViewById(R.id.userAmount);
        homeroundno=requireView().findViewById(R.id.homeRoundNo);
        number = sharedPreferences.getString("number","");
        stockList = requireView().findViewById(R.id.stockView);
        stockList.setLayoutManager(new LinearLayoutManager(requireContext()));
        homeroundno.setText("Round "+String.valueOf(roundNo));
        IncDec.add(-1);
        IncDec.add(-1);
        IncDec.add(-1);
        IncDec.add(-1);
        IncDec.add(-1);
        IncDec.add(-1);

        nameStock.add("AIR UTOPIA");
        nameStock.add("DR. PHARMA");
        nameStock.add("STEEL WORKS");
        nameStock.add("THE BANK");
        nameStock.add("TECH GIANT");
        nameStock.add("CHEMICAL COMPANY");
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
                AlertDialog.Builder builder =new AlertDialog.Builder(requireContext(),R.style.AlertTheme);
                        builder.setView(v);
                        TextView title = new TextView(getContext());
                        title.setText("Buy "+nameStock.get(position));
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
                                            Stocks stockInstance = new Stocks(nameStock.get(position), stockPrice.get(position), shareOwned.get(position));
                                            stocks.set(position, stockInstance);
                                            adapter.resetData(stocks,IncDec);
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
                builder.setNeutralButton("Buy All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                        Integer stockB = (int)(Double.parseDouble(userAmount.getText().toString())/stockPrice.get(position));
                        Integer stockOwnedNow = Integer.parseInt(String.valueOf(shareOwned.get(position) + stockB));
                        Double cashOwnedNow = Double.parseDouble(userAmount.getText().toString()) - (stockPrice.get(position) * stockB);
                        if (cashOwnedNow < 0) {
                            Toast.makeText(requireContext(), "You don't have enough money!", Toast.LENGTH_SHORT).show();
                        } else {
                            shareOwned.set(position, stockOwnedNow);
                            userAmount.setText(String.valueOf((cashOwnedNow)));
                            String updateBuy = "Update valuation set " + stockName.get(position) + "_shares =" + stockOwnedNow + ",cash =" + cashOwnedNow + "where phoneID=" + number;
                            String insertBuy = "Insert into trade values(" + number + ",'" + stockName.get(position) + "'," + roundNo + "," + stockB + ",0);";
                            Stocks stockInstance = new Stocks(nameStock.get(position), stockPrice.get(position), shareOwned.get(position));
                            stocks.set(position, stockInstance);
                            adapter.resetData(stocks,IncDec);
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
                });
                        Rect displayRectangle = new Rect();
                        Window window = requireActivity().getWindow();
                        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);

                        AlertDialog alertDialog = builder.create();
                        alertDialog.getWindow().setLayout((int)(displayRectangle.width() *
                                0.9f),ViewGroup.LayoutParams.WRAP_CONTENT);
                        alertDialog.show();
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
                        totalSum.setText("Funds Gained: "+String.valueOf(Integer.parseInt(s.toString())*stockPrice.get(position)));
                        else
                            totalSum.setText("Funds Gained: 0");
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
                AlertDialog.Builder builder =new AlertDialog.Builder(requireContext(),R.style.AlertTheme);
                        builder.setView(v);
                        TextView title = new TextView(getContext());
                        title.setText("SELL "+nameStock.get(position));
                        title.setPadding(10,10,10,10);
                        title.setGravity(Gravity.CENTER);
                        title.setTextSize(20);
                        title.setTypeface(Typeface.DEFAULT_BOLD);
                        builder.setCustomTitle(title);
                        builder.setPositiveButton("Sell", new DialogInterface.OnClickListener() {
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
                                            Stocks stockInstance = new Stocks(nameStock.get(position), stockPrice.get(position), shareOwned.get(position));
                                            stocks.set(position, stockInstance);
                                            adapter.resetData(stocks,IncDec);
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
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.setNeutralButton("Sell all", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Integer stockB = Integer.parseInt(shareOwned.get(position).toString());
                        stockSell.setText(String.valueOf(shareOwned.get(position)));
                        Integer stockOwnedNow = Integer.parseInt(String.valueOf(shareOwned.get(position) - shareOwned.get(position)));
                        shareOwned.set(position, stockOwnedNow);
                        Double cashOwnedNow = Double.parseDouble(userAmount.getText().toString()) + (stockPrice.get(position) * stockB);
                        userAmount.setText(String.valueOf((cashOwnedNow)));
                        String updateSell = "Update valuation set " + stockName.get(position) + "_shares =" + stockOwnedNow + ",cash =" + cashOwnedNow + "where phoneID=" + number;
                        String insertSell = "Insert into trade values(" + number + ",'" + stockName.get(position) + "'," + roundNo + ",0," + stockB + ");";
                        Stocks stockInstance = new Stocks(nameStock.get(position), stockPrice.get(position), shareOwned.get(position));
                        stocks.set(position, stockInstance);
                        adapter.resetData(stocks, IncDec);
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
                    }
                });

                        Rect displayRectangle = new Rect();
                        Window window = requireActivity().getWindow();
                        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);

                        AlertDialog alertDialog = builder.create();
                        alertDialog.getWindow().setLayout((int)(displayRectangle.width() * 0.9f),ViewGroup.LayoutParams.WRAP_CONTENT);
                        alertDialog.show();
            }
        },IncDec);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        });
        thread.start();
        stockList.setAdapter(adapter);
        timer  = requireView().findViewById(R.id.timer);
        while (thread.isAlive())
        {
            Log.d("Wait","Waiting");
        }
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
            String columnPrevious = "";
            if(roundNo>1) {
                int prev = roundNo - 1;
                columnPrevious = "r" + prev + "_price";
            }
            int i = 0;
            while (rs.next())
            {
                stockName.add(rs.getString("company_name"));
                stockPrice.add(rs.getDouble(columnPrice));
                if (roundNo>1) {
                    if(rs.getDouble(columnPrevious)<stockPrice.get(i))
                        IncDec.add(1);
                    else if(rs.getDouble(columnPrevious)>stockPrice.get(0))
                        IncDec.add(0);
                    else
                        IncDec.add(-1);
                }
                i++;
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
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        for (int i=0;i<stockName.size();i++)
        {
            Stocks stockInstance = new Stocks(nameStock.get(i),stockPrice.get(i),shareOwned.get(i));
            stocks.add(stockInstance);
        }
        if (roundNo!=1)
            adapter.resetData(stocks,IncDec);
    }

    private void startContinueTimer() {
        countDownTimer = new CountDownTimer(millisecValue, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                if(millisUntilFinished<11000)
                {
                    mediaPlayer.start();
                }
                if(millisUntilFinished<=10000){
                    timer.setTextColor(Color.RED);
                    newsFeedFragment.timer.setTextColor(Color.RED);
                    LeaderboardFragment.timers.setTextColor(Color.RED);
                }
                if (millisUntilFinished<=1000)
                {
                    LeaderboardFragment.leaderboardRefreshText.setVisibility(View.VISIBLE);
                    LeaderboardFragment.usersLeaderBoard.setVisibility(View.GONE);
                    LeaderboardFragment.toppersLeaderBoard.setVisibility(View.GONE);
                }
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
                    ArrayList<String> news = News.setNewsText();
                    newsFeedFragment.adapter.resetData(news);
                    LeaderboardFragment.users.clear();
                    LeaderboardFragment.userNames.clear();
                    LeaderboardFragment.points.clear();
                    timer.setTextColor(Color.WHITE);
                    newsFeedFragment.timer.setTextColor(Color.WHITE);
                    LeaderboardFragment.timers.setTextColor(Color.WHITE);
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
                    Log.i("Video insert","Round:"+roundNo);
                    final Button roundChangeButton =  requireView().findViewById(R.id.roundChangeButton);
                    final TextView timertext=requireView().findViewById(R.id.timer);
                    final TextView fundstext=requireView().findViewById(R.id.textView3);
                    final RecyclerView stockviewtext=requireView().findViewById(R.id.stockView);
                    final VideoView videoView=(VideoView)requireView().findViewById(R.id.videoView);
                    final TextView useramounttext=requireView().findViewById(R.id.userAmount);

                    if(roundNo==2)
                        videoView.setVideoPath("android.resource://"+getActivity().getPackageName()+"/"+R.raw.afteronedayone);
                    else if (roundNo==3)
                        videoView.setVideoPath("android.resource://"+getActivity().getPackageName()+"/"+R.raw.aftertwodayone);
                    else if (roundNo==4)
                        videoView.setVideoPath("android.resource://"+getActivity().getPackageName()+"/"+R.raw.afterthreedayone);
                    else if (roundNo==5)
                        videoView.setVideoPath("android.resource://"+getActivity().getPackageName()+"/"+R.raw.afterfourdayone);
                    else if (roundNo==6)
                        videoView.setVideoPath("android.resource://"+getActivity().getPackageName()+"/"+R.raw.afteronedayone);

                    MediaController mediaController=new MediaController(getActivity());
                    mediaController.setAnchorView(videoView);
                    videoView.setMediaController(mediaController);
                    videoView.start();
                    videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        public void onPrepared(MediaPlayer mp) {
                            mp.setLooping(true);}
                    });
                    videoView.setVisibility(View.VISIBLE);
                    funds.setVisibility(View.INVISIBLE);
                    homeroundno.setVisibility(View.INVISIBLE);
                    timertext.setVisibility(View.INVISIBLE);
                    fundstext.setVisibility(View.INVISIBLE);
                    roundChangeButton.setVisibility(View.VISIBLE);
                    stockviewtext.setVisibility(View.INVISIBLE);
                    useramounttext.setVisibility(View.INVISIBLE);

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
                                    Button roundChangeButton =  requireView().findViewById(R.id.roundChangeButton);

                                    roundChangeButton.setVisibility(View.GONE);
                                    videoView.setVisibility(View.GONE);
                                    funds.setVisibility(View.VISIBLE);
                                    homeroundno.setVisibility(View.VISIBLE);
                                    stockviewtext.setVisibility(View.VISIBLE);
                                    timertext.setVisibility(View.VISIBLE);
                                    fundstext.setVisibility(View.VISIBLE);
                                    useramounttext.setVisibility(View.VISIBLE);
                                    LeaderboardFragment.leaderboardRefreshText.setVisibility(View.INVISIBLE);
                                    LeaderboardFragment.usersLeaderBoard.setVisibility(View.VISIBLE);
                                    LeaderboardFragment.toppersLeaderBoard.setVisibility(View.VISIBLE);

                                    stocks.clear();
                                    stockName.clear();
                                    stockPrice.clear();
                                    shareOwned.clear();
                                    IncDec.clear();
                                    getData();
                                    LeaderboardFragment.users.clear();
                                    LeaderboardFragment.toppers.clear();
                                    LeaderboardFragment.userNames.clear();
                                    LeaderboardFragment.points.clear();
                                    LeaderboardFragment.playerPosition = 0;
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
