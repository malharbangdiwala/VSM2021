package com.oculus.vsm;

import com.oculus.vsm.ui.home.HomeFragment;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
