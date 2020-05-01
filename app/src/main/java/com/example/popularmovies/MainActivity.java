package com.example.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.net.URL;

import com.example.popularmovies.utilities.NetworkUtils;
import com.example.popularmovies.utilities.TMDB_jsonUtils;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;

    private TextView mErrorMessageDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movies);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        mRecyclerView.setLayoutManager(gridLayoutManager);
        mMovieAdapter = new MovieAdapter(this);

        mRecyclerView.setAdapter(mMovieAdapter);

        loadMovieData();
    }

    //Set movie data list
    public void showMovieDataView () {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility((View.VISIBLE));
    }

    public void showErrorMessage () {
        mRecyclerView.setVisibility((View.INVISIBLE));
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }


    // onClick function to handle click events and send to DetailActivity
    @Override
    public void onClick(String movieSelected) {
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, movieSelected);
        startActivity(intentToStartDetailActivity);

        Log.d(TAG, movieSelected + " clicked");
    }

    private void loadMovieData() {
        showMovieDataView();

        new FetchMovieDataTask().execute();
    }


    //class to FetchMovieDataTask in its own thread.
    //converts HTTP response JSON for only details needed array for display
    public class FetchMovieDataTask extends AsyncTask <String, Void, String[]>  {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String[] doInBackground(String... params) {

            URL callingUrl = NetworkUtils.buildUrl();

            try {
                String returnData = NetworkUtils.getResponseFromHttpUrl(callingUrl);

                String[] arr = TMDB_jsonUtils.getPopularMovies(MainActivity.this, returnData);

                return arr;
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] popularMovie) {
            if (popularMovie != null) {
                showMovieDataView();

                mMovieAdapter.setMovieData(popularMovie);
            } else {
                showErrorMessage();
            }
        }

    }
}
