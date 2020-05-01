package com.example.popularmovies.utilities;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TMDB_jsonUtils {

    private static final String TAG = TMDB_jsonUtils.class.getSimpleName();
    private static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/";
    private static final String POSTER_SIZE = "w185";

    public static String[] getPopularMovies (Context context, String fullJsonStr) throws JSONException {
       
        //recreate JSON object
        JSONObject popularMovieJson = new JSONObject(fullJsonStr);

        JSONArray resultsArr = popularMovieJson.getJSONArray("results");

        String[] finalPopularMovieData = new String[resultsArr.length()];

        for (int i=0; i< resultsArr.length(); i++) {
            JSONObject movieDetail = resultsArr.getJSONObject(i);
            String strMovieDetail = movieDetail.toString();
            finalPopularMovieData[i] = strMovieDetail;
        }
        
        return finalPopularMovieData;
    }

    public static String posterUrl (String path) {
        //remove "\" from first item of path
        String moviePath = path.substring(0);
        String finalPosterUrl = POSTER_BASE_URL + POSTER_SIZE + moviePath;

        return finalPosterUrl;
    }
}