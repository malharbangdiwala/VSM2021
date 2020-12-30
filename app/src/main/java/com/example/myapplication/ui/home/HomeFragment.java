package com.example.myapplication.ui.home;

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

import com.example.myapplication.ItemClicked;
import com.example.myapplication.R;
import com.example.myapplication.SelectorActivity;
import com.example.myapplication.StockAdapter;
import com.example.myapplication.Stocks;

import java.nio.channels.Selector;
import java.util.ArrayList;

public class HomeFragment extends Fragment
{
    CountDownTimer countDownTimer;
    TextView timer;
    RecyclerView stockList;
    ArrayList<Stocks> stocks = new ArrayList<>();
    ArrayList<Double> stockPrice = new ArrayList<>();
    ArrayList<String> stockName = new ArrayList<>();
    ArrayList<Integer> shareOwned = new ArrayList<>();
    StockAdapter adapter;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        stockName.add("A");
        stockName.add("B");
        stockName.add("C");
        stockName.add("D");
        stockName.add("E");
        stockPrice.add(100.0);
        stockPrice.add(200.0);
        stockPrice.add(300.0);
        stockPrice.add(400.0);
        stockPrice.add(500.0);
        shareOwned.add(10);
        shareOwned.add(20);
        shareOwned.add(30);
        shareOwned.add(40);
        shareOwned.add(50);
        for (int i=0;i<stockName.size();i++)
        {
            Stocks stockInstance = new Stocks(stockName.get(i),stockPrice.get(i),shareOwned.get(i));
            stocks.add(stockInstance);
        }
        stockList = requireView().findViewById(R.id.stockView);
        stockList.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new StockAdapter(stocks, requireContext(), new ItemClicked() {
            @Override
            public void onClick(int position, View view) {
                Log.i("TAG","Pressed");
            }
        });
        stockList.setAdapter(adapter);
        timer  = requireView().findViewById(R.id.timer);
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
                }
        }.start();
    }
}