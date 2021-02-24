package com.example.myapplication;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.ui.home.HomeFragment;
import com.example.myapplication.ui.newsfeed.newsFeedFragment;
import com.example.myapplication.ui.powercard.PowerCardFragment;

import java.util.ArrayList;

public class News {
    static int newscolorflag=0;
    public static ArrayList<String> setNewsText(){
        ArrayList<String> setNews = new ArrayList<>();
        switch(HomeFragment.roundNo){
            case 1:
                setNews.add("Scientist discover 119th element <name> and that has super strength and is light weighted and as malleable as steel and is environment friendly. To get this functional scientist need heavy use of Chemical refineries.");
                setNews.add("Government has decided to privatise the Airline Sector, which had bidders from various major leagues of Utopia.");
                setNews.add("Pharma Giant Dr. Utopia receives overseas funding and looks forward to expand their business to different domains.");
                setNews.add("World’s largest economy has an election coming up and there is a massive support for Xi Zie  from conservative party.");
                if(PowerCardFragment.pc2flag==1){
                    setNews.add(0,"Union of IT workers plans to declare strike due to long working hours and increasing deadline pressure.");
                    PowerCardFragment.pc2flag=0;
                }
                break;
            case 2:
                setNews.clear();
                setNews.add("Government awards tendor to Dr. Utopia for privatisation of Air Utopia.");
                setNews.add("Election Results are OUT , Xi Zie wins the throne with an overwhelming majority. His first action plan is to revise the policy on Immigration of Utopia Workers and reduce investment into Utopian MSE.");
                setNews.add("Students from SPIT are developing a game changing AI to smoothen the cryptocurrency transactions.");
                if(PowerCardFragment.pc2flag==1){
                    setNews.add(0,"Friction between board member of Utopia Bank regarding their upcoming policy <name> to reduce the repo rate by 2%.");
                    PowerCardFragment.pc2flag=0;
                    newscolorflag=1;
                }
                break;
            case 3:
                setNews.clear();
                setNews.add("Researchers found that name is hazardous to human life in the longer run and chemical research is required to control the radiation caused by existing element.");
                setNews.add("Dr. Utopia conducts a survey and devices a plan to neutralize humans of the radiations and gets huge sums of money for the research.");
                setNews.add("Utopia Banks policy name to reduce the repo rate gets consensus.");
                setNews.add("UTOPIA backs out of Paris Agreement due to Xi Zie’s conservative approach.");
                if(PowerCardFragment.pc2flag==1){
                    setNews.add(0,"UTOPIA Investments planning to increase their mutual funds share in Air Utopia to 29.33% from 21%.");
                    PowerCardFragment.pc2flag=0;
                    newscolorflag=1;
                }
                break;
            case 4:
                setNews.clear();
                setNews.add("SPITian’s make UTOPIA proud after successfully creating the greatest ever AI technology single handedly.");
                setNews.add("In the latest audit, Dr. Utopia gets accused of allegedly involved in diverting funds received for research in subsidiary companies.");
                setNews.add("Government incentivises the use of Solar Panels.");
                setNews.add("Banks witness in significant increase in number of NPA(non-performing assets).");
                if(PowerCardFragment.pc2flag==1){
                    setNews.add(0,"UTOPIA plans to sign Paris Agreement again due to backlash by other governments.");
                    PowerCardFragment.pc2flag=0;
                    newscolorflag=1;
                }
                break;
            case 5:
                if(PowerCardFragment.pc2flag==1){
                    PowerCardFragment.pc2flag=0;
                }
                break;
            default:
                break;
        }

        newsFeedFragment.newsroundnumber.setText("News: Round "+String.valueOf(HomeFragment.roundNo));
        //newsFeedFragment.news.setText(setNews);
        return setNews;
    }
}
