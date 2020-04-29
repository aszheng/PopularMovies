package com.example.popularmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private String mMovieDetails;
    private TextView mDetailDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mDetailDisplay = (TextView) findViewById(R.id.tv_display_movie_detail);

        Intent intentTrigger = getIntent();

        if (intentTrigger != null) {
            if (intentTrigger.hasExtra(Intent.EXTRA_TEXT)) {
                mMovieDetails = intentTrigger.getStringExtra(Intent.EXTRA_TEXT);
                mDetailDisplay.setText(mMovieDetails + " details shall appear here");
            }
        }
    }
}
