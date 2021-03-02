package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.myapplication.ui.home.HomeFragment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;

import static com.example.myapplication.MainActivity.MyPREFERENCES;

public class powercard3 {
    public static Double deduction;
    public static double addcash(){
        Double cash=Double.parseDouble(HomeFragment.userAmount.getText().toString());
        deduction=cash*0.5;
        cash=cash*1.4;
        BigDecimal bd = BigDecimal.valueOf(cash);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
