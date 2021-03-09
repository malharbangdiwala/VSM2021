package com.oculus.vsm;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oculus.vsm.R;

import java.util.ArrayList;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.StockHolder>
{

    ArrayList<Stocks> stocks;
    Context context;
    ItemClicked itemClicked;
    ViewGroup parent;
    ArrayList<Integer> IncDec;

    public StockAdapter(ArrayList<Stocks> stocks, Context context,ItemClicked itemClicked,ArrayList<Integer> IncDec) {
        this.stocks = stocks;
        this.context = context;
        this.itemClicked = itemClicked;
        this.IncDec = IncDec;
    }
    @NonNull
    @Override
    public StockHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.parent = parent;
        View view = LayoutInflater.from(context).inflate(R.layout.stock_holder,parent,false);
        return new StockHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StockHolder holder, int position) {
        holder.shareN.setText(stocks.get(position).getShareName());
        holder.shareP.setText(stocks.get(position).getSharePrice().toString());
        holder.shareOwned.setText(stocks.get(position).getShareOwned().toString());
        if (IncDec.get(position)==1) {
            holder.shareP.setTextColor(Color.GREEN);
            //holder.shareOwned.setTextColor(Color.GREEN);
        }
        else if (IncDec.get(position)==0) {
            holder.shareP.setTextColor(Color.RED);
            //holder.shareOwned.setTextColor(Color.RED);
        }else
        {
            holder.shareP.setTextColor(Color.WHITE);
            //holder.shareOwned.setTextColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return stocks.size();
    }

    public void resetData(ArrayList<Stocks> stocks,ArrayList<Integer> IncDec) {
        this.stocks = stocks;
        this.IncDec = IncDec;
        this.notifyDataSetChanged();
    }

    class StockHolder extends RecyclerView.ViewHolder
    {
        TextView shareP,shareN,shareOwned;
        Button buy,sell;
        public StockHolder(@NonNull View itemView) {
            super(itemView);
            shareP = itemView.findViewById(R.id.share_price);
            shareN = itemView.findViewById(R.id.share_name);
            buy = itemView.findViewById(R.id.buy);
            sell = itemView.findViewById(R.id.sell);
            shareOwned = itemView.findViewById(R.id.owned);
            buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClicked.onClickBuy(getAdapterPosition(),v);
                }
            });
            sell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClicked.onClickSell(getAdapterPosition(),v);
                }
            });
        }
    }
}
