package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import java.util.ArrayList;

public class Sponsor extends AppCompatActivity {

    ArrayList<Sponsors> sponsorsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor);
        sponsorsList = new ArrayList<>();

        RecyclerView sponsors = findViewById(R.id.sponsorRV);
        sponsors.setLayoutManager(new GridLayoutManager(getApplicationContext(),1));

        sponsorsList.add(new Sponsors(getResources().getDrawable(R.drawable.logo),"VSM","Title Sponsor"));
        sponsorsList.add(new Sponsors(getResources().getDrawable(R.drawable.logo),"VSM","Title Sponsor"));
        sponsorsList.add(new Sponsors(getResources().getDrawable(R.drawable.logo),"VSM","Title Sponsor"));
        sponsorsList.add(new Sponsors(getResources().getDrawable(R.drawable.logo),"VSM","Title Sponsor"));

        SponsorAdapter adapter = new SponsorAdapter(sponsorsList,getApplicationContext());
        sponsors.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
