package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myapplication.ui.leaderboard.LeaderboardFragment;
import com.example.myapplication.ui.home.HomeFragment;
import com.example.myapplication.ui.newsfeed.newsFeedFragment;
import com.example.myapplication.ui.powercard.PowerCardFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter
{
    int status;
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior,int status) {
        super(fm, behavior);
        this.status = status;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
            switch (position)
            {
                case 0:
                     return new HomeFragment(status);
                case 1:
                    return new LeaderboardFragment();
                case 2:
                    return new PowerCardFragment(status);
                case 3:
                    return new newsFeedFragment();
                default:
                    return new HomeFragment(status);
            }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
