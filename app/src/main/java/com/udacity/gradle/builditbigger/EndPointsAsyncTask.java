package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.util.Pair;

import com.example.afzal.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.gr3ymatter.jokedisplaymodule.JokeDisplayActivity;

import java.io.IOException;

public class EndpointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {

    private static MyApi myApiService = null;
    private Context context;
    private static String config;
    private TestingCallbackListener mlistener = null;


    @Override
    protected String doInBackground(Pair<Context, String>... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        context = params[0].first;
        config = params[0].second;

        try {
            return myApiService.tellJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if(mlistener != null) {
            this.mlistener.onComplete(result);
            return;
        }
        Intent launchIntent = new Intent(context, JokeDisplayActivity.class);
        launchIntent.putExtra(JokeDisplayActivity.JOKEKEY, result );
        context.startActivity(launchIntent);

    }

    @Override
    protected void onCancelled() {
        if(this.mlistener != null){
            mlistener.onComplete(null);
        }
    }

    public EndpointsAsyncTask setListener(TestingCallbackListener listener){
        this.mlistener = listener;
        return this;
    }

    public static interface TestingCallbackListener {
        public void onComplete(String result);
    }
}
