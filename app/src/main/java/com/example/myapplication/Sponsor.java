package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import java.util.ArrayList;

public class Sponsor extends AppCompatActivity {

    ArrayList<Sponsors> sponsorsList;
    ArrayList<Sponsors> sponsorsOther;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor);
        sponsorsList = new ArrayList<>();
        sponsorsOther = new ArrayList<>();

        RecyclerView sponsors = findViewById(R.id.sponsorRV);
        sponsors.setLayoutManager(new GridLayoutManager(getApplicationContext(),1));

        RecyclerView sponsorOther = findViewById(R.id.sponsorKRV);
        sponsorOther.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));

        sponsorsList.add(new Sponsors(getResources().getDrawable(R.drawable.sponsorticker),"TICKER BY FINOLOGY","Title Sponsor"));
        sponsorsList.add(new Sponsors(getResources().getDrawable(R.drawable.sponsoredupeer),"EDUPEER","Associate Sponsor"));
        SponsorAdapter adapter = new SponsorAdapter(sponsorsList,getApplicationContext());
        sponsors.setAdapter(adapter);

        sponsorsOther.add(new Sponsors(getResources().getDrawable(R.drawable.sponsorelm),"ELEARN-MARKETS","Knowledge Partner"));
        sponsorsOther.add(new Sponsors(getResources().getDrawable(R.drawable.sponsordta),"DERIVATIVE TRADING ACADEMY","Channel Partner"));
        sponsorsOther.add(new Sponsors(getResources().getDrawable(R.drawable.sponsorstockedge),"STOCKEDGE","Knowledge Partner"));
        SponsorAdapter adapterOther = new SponsorAdapter(sponsorsOther,getApplicationContext());
        sponsorOther.setAdapter(adapterOther);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
