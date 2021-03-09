package com.oculus.vsm;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.oculus.vsm.R;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder>
{
    ArrayList<Users> user;
    Context context;
    ViewGroup parent;
    int playerposition;
    int type;

    public UserAdapter(ArrayList<Users> user,Context context,int playerposition,int type)
    {
        this.user = user;
        this.context = context;
        this.playerposition = playerposition;
        this.type = type;
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
        if(type==0) {
            if (position == 0) {
                holder.cardView.setCardBackgroundColor(Color.parseColor("#FFD700"));
                holder.ranks.setBackgroundColor(Color.parseColor("#FFD700"));
            } else if (position == 1) {
                holder.cardView.setCardBackgroundColor(Color.parseColor("#C0C0C0"));
                holder.ranks.setBackgroundColor(Color.parseColor("#C0C0C0"));
            } else if (position == 2) {
                holder.cardView.setCardBackgroundColor(Color.parseColor("#CD7F32"));
                holder.ranks.setBackgroundColor(Color.parseColor("#CD7F32"));
            }
            holder.ranks.setText(String.valueOf(position + 1) + ".");
        }else if (type==1){
            if (position+3==playerposition) {
                Log.d("Tag",playerposition+position+user.get(position).getName());
                holder.cardView.setCardBackgroundColor(Color.parseColor("#228BFF"));
                holder.ranks.setBackgroundColor(Color.parseColor("#228BFF"));
            }else {
                holder.cardView.setCardBackgroundColor(Color.parseColor("#6200EE"));
                holder.ranks.setBackgroundColor(Color.parseColor("#6200EE"));
            }
            holder.ranks.setText(String.valueOf(position + 4) + ".");
        }
        holder.name.setText(user.get(position).getName());
        holder.points.setText(user.get(position).getPoints().toString());
    }

    @Override
    public int getItemCount() {
        return user.size();
    }

    public void resetData(ArrayList<Users> user,int playerposition) {
        this.user = user;
        this.playerposition = playerposition;
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