package com.example.myapplication.ui.newsfeed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.News;
import com.example.myapplication.NewsAdapter;
import com.example.myapplication.R;
import com.example.myapplication.ui.home.HomeFragment;

import java.util.ArrayList;

public class newsFeedFragment extends Fragment
{
    public static TextView newsroundnumber;
    public static RecyclerView news;
    public static TextView timer;
    public static ArrayList<String> newList = new ArrayList<>();
    public static NewsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_newsfeed, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        news=requireView().findViewById(R.id.newsText);
        timer = requireView().findViewById(R.id.timer1);
        newsroundnumber=requireView().findViewById(R.id.newsRoundNo);

        newList = News.setNewsText();
        news.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter = new NewsAdapter(newList,requireContext());
        news.setAdapter(adapter);
    }
}
