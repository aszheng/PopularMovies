package com.example.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.net.URL;

import com.example.popularmovies.utilities.NetworkUtils;
import com.example.popularmovies.utilities.TMDB_jsonParser;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;

    private TextView mErrorMessageDisplay;

    String[] movieFakeData = new String[] { "Movie A", "Movie B", "Movie C", "Movie D", "Movie E" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movies);

        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(this);
        mRecyclerView.setAdapter(mMovieAdapter);

//        mMovieAdapter.setMovieData(movieFakeData);
//        new FetchMovieDataTask().execute();
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
                Log.d(TAG, "!@!@!@!@!" + returnData);

                String[] arr = TMDB_jsonParser.getPopularMovies(MainActivity.this, returnData);

                Log.d(TAG, "!@!@!@!@! doIn bg" + arr[0]);

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
                Log.d(TAG, "!@!@!@!@! POST EXECUTE" + popularMovie[0]);

                mMovieAdapter.setMovieData(popularMovie);
            } else {
                showErrorMessage();
            }
        }

    }
}
