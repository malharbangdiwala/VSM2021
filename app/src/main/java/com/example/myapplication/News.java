package com.example.myapplication;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.ui.home.HomeFragment;
import com.example.myapplication.ui.newsfeed.newsFeedFragment;
import com.example.myapplication.ui.powercard.PowerCardFragment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.xml.transform.Result;

public class News {
    static int newscolorflag=0;
    public static int day = 0;
    public static ArrayList<String> setNewsText(){
        ArrayList<String> setNews = new ArrayList<>();
        String query="Select newsday from rounds;";
        ConnectionHelper con;
        Connection connect = null;
        try {
            con = new ConnectionHelper();
            connect = ConnectionHelper.CONN();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Statement st = connect.createStatement();
            ResultSet rs = st.executeQuery(query);
            if(rs.next()) {
                day = rs.getInt("newsday");
                Log.i("Newsday:",""+day);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (HomeFragment.status==1) {
            if (day == 1) {
                switch (HomeFragment.roundNo) {
                    case 1:
                        setNews.add("The 127th element of the periodic table is equally strong and malleable but requires high end chemicals refineries.");
                        setNews.add("Airline Sector may soon be privatized.");
                        setNews.add("Dr.Pharma is looking for foreign investors.");
                        setNews.add("Chances for the conservative party to win the elections seems pretty high.");
                        if (PowerCardFragment.pc2flag == 1) {
                            setNews.add(0, "Finology enters the high competition battle zone “FINTECH” ");
                            PowerCardFragment.pc2flag = 0;
                        }
                        break;
                    case 2:
                        setNews.clear();
                        setNews.add("Dr.Pharma owns a majority stake in Air Utopia after they win the tender.");
                        setNews.add("Xi Zie wins the elections with an overwhelming majority.");
                        setNews.add("Xi Zie plans is to revise the policy on Immigration of Utopia Workers and reduce investment into Utopian MSE.");
                        setNews.add("AI to smoothen the cryptocurrency transactions is being developed.");
                        if (PowerCardFragment.pc2flag == 1) {
                            setNews.add(0, "Friction between board members of Utopia Bank regarding their upcoming policy “Ease of Loan” to reduce the repo rate by 2%.");
                            PowerCardFragment.pc2flag = 0;
                            newscolorflag = 1;
                        }
                        break;
                    case 3:
                        setNews.clear();
                        setNews.add("Ununbaktoniom is highly radioactive and causes damage to human life.");
                        setNews.add("Dr. Pharma devises a plan to neutralize humans of these radiations.");
                        setNews.add("Dr. Pharma gets a huge grant for research regarding neutralizing these radiations.");
                        setNews.add("Bank’s policy “Ease of Loan” to reduce the repo rate gets consensus.");
                        setNews.add("Utopia backs out of the Paris Agreement due to Xi Zie’s conservative approach.");
                        if (PowerCardFragment.pc2flag == 1) {
                            setNews.add(0, "Utopia Investmentor Pvt Limited planning to increase their mutual funds share in Air Utopia to 29.33% from 21%.");
                            PowerCardFragment.pc2flag = 0;
                            newscolorflag = 1;
                        }
                        break;
                    case 4:
                        setNews.clear();
                        setNews.add("AI to smooth cryptocurrency is ready!!");
                        setNews.add("Dr. Pharma accused of being involved in diverting funds in subsidiary companies.");
                        setNews.add("Government incentivises the use of Solar Panels.");
                        setNews.add("Increase in the number of NPA(non-performing assets) for Banks.");
                        if (PowerCardFragment.pc2flag == 1) {
                            setNews.add(0, "The Fintech industry is about to revolutionize.");
                            PowerCardFragment.pc2flag = 0;
                            newscolorflag = 1;
                        }
                        break;
                    case 5:
                        setNews.clear();
                        setNews.add("Finology works with SEBI, NSE and The Bank to change the Fintech space forever.");
                        setNews.add("Finology has become the most popular brand to learn and invest in the share market.");
                        setNews.add("Utopians are free from all radiations caused by Ununbaktoniom.");
                        setNews.add("Xi Zie signs a peace treaty with Utopia.");
                        setNews.add("Another steel mine discovered in Utopia.");
                        if (PowerCardFragment.pc2flag == 1) {
                            setNews.add(0, "Utopia plans to sign Paris Agreement again due to backlash by other governments.");
                            PowerCardFragment.pc2flag = 0;
                        }
                        break;
                    default:
                        break;
                }
            } else if (day == 2) {
                //TODO: Add news for day 2
            }
        }else{
            setNews.add("ABC");
        }
        newsFeedFragment.newsroundnumber.setText("News: Round "+String.valueOf(HomeFragment.roundNo));
        //newsFeedFragment.news.setText(setNews);
        return setNews;
    }
}
