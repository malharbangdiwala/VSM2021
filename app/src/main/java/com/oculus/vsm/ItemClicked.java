package com.oculus.vsm;

import android.view.View;

public interface ItemClicked{
    void onClickBuy(int position, View view);
    void onClickSell(int position, View view);
}
