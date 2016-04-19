package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.gr3ymatter.jokedisplaymodule.JokeDisplayActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public InterstitialAd mInterstitialAd;
    private ProgressBar spinner;


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_main, container, false);

        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);



        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                new EndpointsAsyncTask().setListener(new EndpointsAsyncTask.CallbackListener() {
                    @Override
                    public void onComplete(String result) {
                        spinner.setVisibility(View.INVISIBLE);
                        Intent launchIntent = new Intent(getContext(), JokeDisplayActivity.class);
                        launchIntent.putExtra(JokeDisplayActivity.JOKEKEY, result);
                        getContext().startActivity(launchIntent);
                    }
                }).execute(new Pair<Context, String>(getContext(), null));
            }
        });

        requestNewInterstitial();
        spinner = (ProgressBar)root.findViewById(R.id.progressBar);
        spinner.setVisibility(View.INVISIBLE);

        Button jokeButton = (Button)root.findViewById(R.id.jokeButton);
        jokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setVisibility(View.VISIBLE);
                if (mInterstitialAd.isLoaded())
                    mInterstitialAd.show();
                else {
                    new EndpointsAsyncTask().setListener(new EndpointsAsyncTask.CallbackListener() {
                        @Override
                        public void onComplete(String result) {
                            spinner.setVisibility(View.INVISIBLE);
                            Intent launchIntent = new Intent(getContext(), JokeDisplayActivity.class);
                            launchIntent.putExtra(JokeDisplayActivity.JOKEKEY, result);
                            getContext().startActivity(launchIntent);
                        }
                    }).execute(new Pair<Context, String>(getContext(), null));
                }
            }
        });

        return root;
    }


    public void requestNewInterstitial(){
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);

    }

}
