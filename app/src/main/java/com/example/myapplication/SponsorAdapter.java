package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SponsorAdapter extends RecyclerView.Adapter<SponsorAdapter.SponsorHolder>
{
    ArrayList<Sponsors> sponsors;
    Context context;
    ViewGroup parent;

    public SponsorAdapter(ArrayList<Sponsors> sponsors,Context context)
    {
        this.sponsors = sponsors;
        this.context = context;
    }

    @NonNull
    @Override
    public SponsorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.parent = parent;
        View view = LayoutInflater.from(context).inflate(R.layout.sponsor_holder,parent,false);
        return new SponsorHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SponsorHolder holder, int position) {
        holder.logo.setImageDrawable(sponsors.get(position).getSponsorLogo());
        holder.sponsorName.setText(sponsors.get(position).getSponsorName());
        holder.sponsorType.setText(sponsors.get(position).getSponsorType());
    }

    @Override
    public int getItemCount() {
        return sponsors.size();
    }

    class SponsorHolder extends RecyclerView.ViewHolder{
        ImageView logo;
        TextView sponsorName,sponsorType;
        public SponsorHolder(@NonNull View itemView) {
            super(itemView);
            logo = itemView.findViewById(R.id.sponsorImage);
            sponsorName = itemView.findViewById(R.id.companyName);
            sponsorType = itemView.findViewById(R.id.companyType);
        }
    }
}
