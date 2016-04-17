package com.gr3ymatter.jokedisplaymodule;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.gr3ymatter.jokedisplayactivity.R;

public class JokeDisplayActivity extends AppCompatActivity {

    public static String JOKEKEY = "JOKEKEY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_display);
        String joke = getIntent().getStringExtra(JOKEKEY);

        if(joke == null)
            joke = getString(R.string.no_joke_string);

        TextView jokeTExtView = (TextView)findViewById(R.id.jokeText);

        jokeTExtView.setText(joke);

    }
}
