package com.oculus.vsm;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.oculus.vsm.ui.leaderboard.LeaderboardFragment;
import com.oculus.vsm.ui.home.HomeFragment;
import com.oculus.vsm.ui.newsfeed.newsFeedFragment;
import com.oculus.vsm.ui.powercard.PowerCardFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter
{
    int status;
    int roundNo;
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior,int status,int roundNo) {
        super(fm, behavior);
        this.status = status;
        this.roundNo = roundNo;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
            switch (position)
            {
                case 0:
                     return new HomeFragment(status,roundNo);
                case 1:
                    return new LeaderboardFragment();
                case 2:
                    return new PowerCardFragment(status);
                case 3:
                    return new newsFeedFragment();
                default:
                    return new HomeFragment(status,roundNo);
            }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
