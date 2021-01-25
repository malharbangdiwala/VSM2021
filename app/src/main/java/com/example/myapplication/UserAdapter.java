package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static com.example.myapplication.MainActivity.MyPREFERENCES;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder>
{
    ArrayList<Users> user;
    Context context;
    ViewGroup parent;
    String name;

    public UserAdapter(ArrayList<Users> user,Context context,String name)
    {
        this.user = user;
        this.context = context;
        this.name = name;
    }
    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.parent = parent;
        View view = LayoutInflater.from(context).inflate(R.layout.users_holder,parent,false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        if(position==0){
            holder.cardView.setCardBackgroundColor(Color.parseColor("#FFD700"));
            holder.ranks.setBackgroundColor(Color.parseColor("#FFD700"));
        }
        else if(position==1){
            holder.cardView.setCardBackgroundColor(Color.parseColor("#C0C0C0"));
            holder.ranks.setBackgroundColor(Color.parseColor("#C0C0C0"));
        }
        else if(position==2){
            holder.cardView.setCardBackgroundColor(Color.parseColor("#CD7F32"));
            holder.ranks.setBackgroundColor(Color.parseColor("#CD7F32"));
        }
        if (user.get(position).getName().equals(name)){
            holder.cardView.setCardBackgroundColor(Color.parseColor("#228BFF"));
            holder.ranks.setBackgroundColor(Color.parseColor("#228BFF"));
        }
        holder.name.setText(user.get(position).getName());
        holder.points.setText(user.get(position).getPoints().toString());
        holder.ranks.setText(String.valueOf(position+1)+".");
    }

    @Override
    public int getItemCount() {
        return user.size();
    }

    public void resetData(ArrayList<Users> user) {
        this.user = user;
        this.notifyDataSetChanged();
    }
    class UserHolder extends RecyclerView.ViewHolder {
        TextView name,points,ranks;
        CardView cardView;
        public UserHolder(@NonNull View itemView) {
            super(itemView);
            ranks = itemView.findViewById(R.id.rank);
            name = itemView.findViewById(R.id.userName);
            points = itemView.findViewById(R.id.pointsUser);
            cardView = itemView.findViewById(R.id.cardViewUser);
        }
    }
}