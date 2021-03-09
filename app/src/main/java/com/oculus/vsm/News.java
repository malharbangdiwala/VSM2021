package com.oculus.vsm;

import android.util.Log;

import com.oculus.vsm.ui.home.HomeFragment;
import com.oculus.vsm.ui.newsfeed.newsFeedFragment;
import com.oculus.vsm.ui.powercard.PowerCardFragment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
                switch (HomeFragment.roundNo) {
                    case 1:
                        setNews.clear();
                        setNews.add("Auto parts to be made in UTOPIA.");
                        setNews.add("Government bans a controversial documentary.");
                        setNews.add("Infra might not win the bid to construct the tunnel.");
                        setNews.add("Scarce rainfall in UTOPIA.");
                        if (PowerCardFragment.pc2flag == 1) {
                            setNews.add(0, "Is FMCG making children work in their factories? ");
                            PowerCardFragment.pc2flag = 0;
                        }
                        break;
                    case 2:
                        setNews.clear();
                        setNews.add("Temporary suspension of leadership in FMCG.");
                        setNews.add("Protest against the government in support of freedom of speech.");
                        setNews.add("Farmers in a major debt crisis.");
                        setNews.add("Collaboration of schools with Finology.");
                        if (PowerCardFragment.pc2flag == 1) {
                            setNews.add(0, "Finology to raise 10M ?");
                            PowerCardFragment.pc2flag = 0;
                            newscolorflag = 1;
                        }
                        break;
                    case 3:
                        setNews.clear();
                        setNews.add("Automobile engine test fails.");
                        setNews.add("Import and traffic duties increased.");
                        setNews.add("Farmers beg banks to reduce interest rates.");
                        setNews.add("FMCG’s new CEO makes great claims for future growth.");
                        if (PowerCardFragment.pc2flag == 1) {
                            setNews.add(0, "New green metro project underway?");
                            PowerCardFragment.pc2flag = 0;
                            newscolorflag = 1;
                        }
                        break;
                    case 4:
                        setNews.clear();
                        setNews.add("Hybrid crop which survives on minimal water requirement synthesised successfully.");
                        setNews.add("Industrial belt destroyed after mega-earthquake.");
                        setNews.add("15M fundings received by Finology.");
                        setNews.add("Bingee shows interest to acquire Blockbuster.");
                        if (PowerCardFragment.pc2flag == 1) {
                            setNews.add(0, "Auto comes back into the game with all new electric self driving sedan?");
                            PowerCardFragment.pc2flag = 0;
                            newscolorflag = 1;
                        }
                        break;
                    case 5:
                        setNews.clear();
                        setNews.add("FMCG head faces backlash over his latest decision to expand to alcoholic beverages and smoking industry.");
                        setNews.add("Government un-bans the controversial documentary.");
                        setNews.add("Festive boom for auto sector.");
                        setNews.add("Re-construction of all factories completed.");
                        setNews.add("Another steel mine discovered in Utopia.");
                        if (PowerCardFragment.pc2flag == 1) {
                            setNews.add(0, "Infra hires brilliant engineers from SPCE to plan the tunnel project and finally wins the bid !");
                            PowerCardFragment.pc2flag = 0;
                        }
                        break;
                    default:
                        break;

                }
            }
        }
        else {
            setNews.clear();
            setNews.add("Airline Sector may soon be privatized.");
            setNews.add("Government un-bans the controversial documentary.");
            if (PowerCardFragment.pc2flag == 1) {
                setNews.add(0, "IPL to begin from 9th April.");
            }
        }
        newsFeedFragment.newsroundnumber.setText("News: Round "+String.valueOf(HomeFragment.roundNo));
        //newsFeedFragment.news.setText(setNews);
        return setNews;
    }
}
