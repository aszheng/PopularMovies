package com.example.popularmovies.utilities;

import android.net.Uri;
import android.util.Log;

import com.example.popularmovies.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String BASE_URL = "http://api.themoviedb.org/3/movie/";
    private static final String POPULAR = "popular";
    private static final String TOP_RATED = "top_rated";
    private static final String API_KEY = BuildConfig.API_KEY;

    //URI builder
    public static URL buildUrl(String searchType) {
        String finalUrl = null;

        if (searchType == POPULAR) {
            finalUrl = BASE_URL + POPULAR;
        } else if (searchType == TOP_RATED) {
            finalUrl = BASE_URL + TOP_RATED;
        }

        Uri builtUri = Uri.parse(finalUrl).buildUpon()
                .appendQueryParameter("api_key", API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Url to call: " + url);
        return url;
    }


    //boilerplate code for HTTP call
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }



}
