package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class FlavorsFeatures implements FlavorsInterface{

    private InterstitialAd mInterstitialAd;

    @Override
    public void loadAds(View view) {
        AdView mAdView = (AdView) view.findViewById(R.id.adView);
        if (mAdView != null) { //Only free version
            // Create an ad request. Check logcat output for the hashed device ID to
            // get test ads on a physical device. e.g.
            // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .build();
            mAdView.loadAd(adRequest);
        }
    }

    @Override
    public void initInterstitialAd(final Context context) {
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712"); //Test add

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                interstitialFinished(context);
            }
        });

        requestNewInterstitial();
    }

    @Override
    public void showInterstitialAd(Context context) {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }else{
            interstitialFinished(context);
        }
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    private void interstitialFinished (Context context){
        Intent intent = new Intent(FlavorsInterface.INTERSTITIAL_CLOSED);
        context.sendBroadcast(intent);
    }
}
