package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder>
{
    ArrayList<String> news;
    Context context;
    ViewGroup parent;

    public NewsAdapter(ArrayList<String> news, Context context) {
        this.news = news;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.parent = parent;
        View view = LayoutInflater.from(context).inflate(R.layout.news,parent,false);
        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {

        if(position==0 && News.newscolorflag==1)
        {
            News.newscolorflag=0;
            //TODO Set background colour
            //holder.news.setBackgroundColor(Color.parseColor("FFFFFF"));
        }
        holder.news.setText(news.get(position));
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public void resetData(ArrayList<String> news)
    {
        this.news = news;
        this.notifyDataSetChanged();
    }

    class NewsHolder extends RecyclerView.ViewHolder{
        TextView news;
        public NewsHolder(@NonNull View itemView) {
            super(itemView);
            news = itemView.findViewById(R.id.newsText);
        }
    }
}
