package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.view.View;


public interface FlavorsInterface  {

    String INTERSTITIAL_CLOSED = "com.fnp.backend.intent.interstitial.closed";

    void loadAds(View view);
    void initInterstitialAd(Context context);
    void showInterstitialAd(Context context);
}
