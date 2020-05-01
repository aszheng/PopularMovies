package com.example.popularmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popularmovies.utilities.TMDB_jsonUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private String mMovieDetailStr;

    private TextView mDetailTitle;
    private TextView mDetailPlot;
    private TextView mDetailUserRating;
    private TextView mDetailReleaseDate;
    private ImageView mDetailPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mDetailPlot = (TextView) findViewById(R.id.tv_detail_plot);
        mDetailUserRating = (TextView) findViewById(R.id.tv_detail_user_rating);
        mDetailReleaseDate = (TextView) findViewById(R.id.tv_detail_release_date);
        mDetailTitle = (TextView) findViewById(R.id.tv_detail_title);
        mDetailPoster = (ImageView) findViewById(R.id.iv_detail_poster_image);

        Intent intentTrigger = getIntent();

        if (intentTrigger != null) {
            if (intentTrigger.hasExtra(Intent.EXTRA_TEXT)) {
                mMovieDetailStr = intentTrigger.getStringExtra(Intent.EXTRA_TEXT);

                JSONObject mMovieDetailObj = null;

                try {
                    mMovieDetailObj = new JSONObject(mMovieDetailStr);
                    String title = mMovieDetailObj.getString("title");
                    String path = mMovieDetailObj.getString("poster_path");
                    String overview = mMovieDetailObj.getString("overview");
                    String releaseDate = mMovieDetailObj.getString("release_date");
                    String userRating = mMovieDetailObj.getString("vote_average");

                    String pathToDisplay = TMDB_jsonUtils.posterUrl(path);

                    Picasso.get()
                            .load(pathToDisplay)
                            .into(mDetailPoster);
                    mDetailTitle.setText(title);
                    mDetailUserRating.setText(userRating);
                    mDetailReleaseDate.setText(releaseDate);
                    mDetailPlot.setText(overview);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
