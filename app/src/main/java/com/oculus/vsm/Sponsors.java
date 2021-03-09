package com.oculus.vsm;

import android.graphics.drawable.Drawable;

public class Sponsors
{
    Drawable sponsorLogo;
    String sponsorName;
    String sponsorType;

    public Sponsors(Drawable sponsorLogo, String sponsorName, String sponsorType) {
        this.sponsorLogo = sponsorLogo;
        this.sponsorName = sponsorName;
        this.sponsorType = sponsorType;
    }

    public Drawable getSponsorLogo() {
        return sponsorLogo;
    }

    public void setSponsorLogo(Drawable sponsorLogo) {
        this.sponsorLogo = sponsorLogo;
    }

    public String getSponsorName() {
        return sponsorName;
    }

    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName;
    }

    public String getSponsorType() {
        return sponsorType;
    }

    public void setSponsorType(String sponsorType) {
        this.sponsorType = sponsorType;
    }
}
