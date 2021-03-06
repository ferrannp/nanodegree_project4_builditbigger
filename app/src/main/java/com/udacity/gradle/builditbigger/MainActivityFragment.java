package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private FlavorsFeatures flavorsFeatures;

    public MainActivityFragment() {
        flavorsFeatures = new FlavorsFeatures();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        flavorsFeatures.loadAds(root);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        flavorsFeatures.initInterstitialAd(getActivity());
        super.onActivityCreated(savedInstanceState);
    }

    public void showInterstitialAd(){
        flavorsFeatures.showInterstitialAd(getActivity());
    }
}
