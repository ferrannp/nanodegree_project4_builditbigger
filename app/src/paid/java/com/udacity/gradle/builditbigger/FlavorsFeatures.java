package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.view.View;

public class FlavorsFeatures implements FlavorsInterface{

    @Override
    public void loadAds(View view) {
        //No ads
    }

    @Override
    public void initInterstitialAd(Context context) {
        //Nothing to do
    }

    @Override
    public void showInterstitialAd(Context context) {
        //Paid version directly starts
        Intent intent = new Intent(FlavorsInterface.INTERSTITIAL_CLOSED);
        context.sendBroadcast(intent);
    }
}
