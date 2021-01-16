package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder>
{
    ArrayList<Users> user;
    Context context;
    ViewGroup parent;

    public UserAdapter(ArrayList<Users> user,Context context)
    {
        this.user = user;
        this.context = context;
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
        holder.name.setText(user.get(position).getName());
        holder.points.setText(user.get(position).getPoints().toString());
        holder.ranks.setText(String.valueOf(position+4));
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
        public UserHolder(@NonNull View itemView) {
            super(itemView);
            ranks = itemView.findViewById(R.id.rank);
            name = itemView.findViewById(R.id.userName);
            points = itemView.findViewById(R.id.pointsUser);
        }
    }
}