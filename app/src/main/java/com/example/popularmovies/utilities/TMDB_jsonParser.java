package com.example.popularmovies.utilities;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TMDB_jsonParser {

    private static final String TAG = TMDB_jsonParser.class.getSimpleName();

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
}
