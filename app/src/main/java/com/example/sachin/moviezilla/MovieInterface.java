package com.example.sachin.moviezilla;

import com.example.sachin.moviezilla.model.Movie;
import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by Tan on 8/10/2015.
 */
public interface MovieInterface {



    @GET("/movie?vote_average.gte=7&vote_average.lte=10&sort_by=vote_count.desc&api_key=b67ba58a9ec094a58f2a82340e134ba1")
    public void fetchPopularMovies(Callback<Movie> movie);

 @GET("/movie?primary_release_year=2015&sort_by=popularity.desc&api_key=b67ba58a9ec094a58f2a82340e134ba1")
 public void fetchMovies(Callback<Movie> movie);
}
