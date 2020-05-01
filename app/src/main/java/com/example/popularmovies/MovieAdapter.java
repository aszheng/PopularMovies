package com.example.popularmovies;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

public class MovieAdapter extends RecyclerView.Adapter <MovieAdapter.MovieAdapterViewHolder> {

    private static final String TAG = MovieAdapter.class.getSimpleName();

    private String [] mMovieData;

    private final MovieAdapterOnClickHandler mClickHandler;

    public interface MovieAdapterOnClickHandler {
        void onClick(String movieSelected);
    }

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mMovieTextView;

        public MovieAdapterViewHolder (View view) {
            super(view);
            mMovieTextView = (TextView) view.findViewById(R.id.tv_movie_data);
            view.setOnClickListener(this);
        }

        //Past entire movie object string back up to MainActivity (to be passed to DetailActivity)
        @Override
        public void onClick(View v) {
          int adapterPosition = getAdapterPosition();
          String movieSelected = mMovieData[adapterPosition];
          Log.d(TAG, movieSelected + " clicked");

          //pass back up to MainActivity.java the movie clicked
          mClickHandler.onClick(movieSelected);
        }
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MovieAdapterViewHolder(view);
    }

    //Set Movie Poster Here:
    @Override
    public void onBindViewHolder(MovieAdapterViewHolder movieAdapterViewHolder, int position) {
        String movieSelected = mMovieData[position];

        try {
            JSONObject movieJsonObj = new JSONObject(movieSelected);

            String title = movieJsonObj.getString("title");
            movieAdapterViewHolder.mMovieTextView.setText(position + " :" + title);
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    @Override
    public int getItemCount() {
        if (null == mMovieData) return 0;
        return mMovieData.length;
    }

    public void setMovieData(String[] movieData) {
        mMovieData = movieData;
        notifyDataSetChanged();
    }

}
