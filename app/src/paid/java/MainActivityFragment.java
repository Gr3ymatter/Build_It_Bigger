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

import com.gr3ymatter.jokedisplaymodule.JokeDisplayActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment{

    public ProgressBar spinner;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        final ProgressBar spinner = (ProgressBar)root.findViewById(R.id.progressBar);
        spinner.setVisibility(View.INVISIBLE);

        Button jokeButton = (Button)root.findViewById(R.id.jokeButton);
        jokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setVisibility(View.VISIBLE);
                new EndpointsAsyncTask().setListener(new EndpointsAsyncTask.CallbackListener() {
                    @Override
                    public void onComplete(String result) {
                        spinner.setVisibility(View.INVISIBLE);
                        Intent launchIntent = new Intent(getContext(), JokeDisplayActivity.class);
                        launchIntent.putExtra(JokeDisplayActivity.JOKEKEY, result );
                        getContext().startActivity(launchIntent);
                    }
                }).execute(new Pair<Context, String>(getContext(), null));
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


}
