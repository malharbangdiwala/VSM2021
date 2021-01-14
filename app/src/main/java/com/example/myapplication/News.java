package com.example.myapplication;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.ui.home.HomeFragment;
import com.example.myapplication.ui.newsfeed.newsFeedFragment;
import com.example.myapplication.ui.powercard.PowerCardFragment;

public class News {
    public static String setNewsText(){
        String setNews="";
        switch(HomeFragment.roundNo){
            case 1:
                setNews="Round1 news";
                if(PowerCardFragment.pc2flag==1){
                    setNews+="\nnews extra";
                    PowerCardFragment.pc2flag=0;
                }
                break;
            case 2:
                setNews="Round2 news";
                if(PowerCardFragment.pc2flag==1){
                    setNews+="\nnews extra";
                    PowerCardFragment.pc2flag=0;
                }
                break;
            case 3:
                setNews="Round3 news";
                if(PowerCardFragment.pc2flag==1){
                    setNews+="\nnews extra";
                    PowerCardFragment.pc2flag=0;
                }
                break;
            case 4:
                setNews="Round4 news";
                if(PowerCardFragment.pc2flag==1){
                    setNews+="\nnews extra";
                    PowerCardFragment.pc2flag=0;
                }
                break;
            case 5:
                setNews="Round5 news";
                if(PowerCardFragment.pc2flag==1){
                    setNews+="\nnews extra";
                    PowerCardFragment.pc2flag=0;
                }
                break;
            default:
                break;
        }
        Log.i("News",setNews);
        newsFeedFragment.newsroundnumber.setText("News: Round "+String.valueOf(HomeFragment.roundNo));
        newsFeedFragment.news.setText(setNews);
        return setNews;
    }
}
