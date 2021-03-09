package com.oculus.vsm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.oculus.vsm.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class GamesActivity extends AppCompatActivity {

    private BottomNavigationView navigationView;
    private ViewPager mViewPager;
    public int roundNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);
        Intent intent = getIntent();
        roundNo = intent.getIntExtra("roundNoLive",1);
        navigationView = findViewById(R.id.bottom_nav);
        mViewPager = findViewById(R.id.view_pager);
        mViewPager.setOffscreenPageLimit(4);
        setUpViewPager();
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.navigation_leaderboard:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.navigation_powercards:
                        mViewPager.setCurrentItem(2);
                        break;
                    case R.id.navigation_news:
                        mViewPager.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });
    }

    private void setUpViewPager()
    {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,1,roundNo);
        mViewPager.setAdapter(viewPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position)
                {
                    case 0:
                        navigationView.getMenu().findItem(R.id.navigation_home);
                        break;
                    case 1:
                        navigationView.getMenu().findItem(R.id.navigation_leaderboard);
                        break;
                    case 2:
                        navigationView.getMenu().findItem(R.id.navigation_powercards);
                        break;
                    case 3:
                        navigationView.getMenu().findItem(R.id.navigation_news);
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}