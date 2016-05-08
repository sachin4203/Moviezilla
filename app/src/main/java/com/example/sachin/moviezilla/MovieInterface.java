package com.example.sachin.moviezilla;

import com.example.sachin.moviezilla.model.Movie;
import com.example.sachin.moviezilla.model.Video;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Tan on 8/10/2015.
 */
public interface MovieInterface {



    @GET("/discover/movie?vote_average.gte=7&vote_average.lte=10&sort_by=vote_count.desc&api_key=b67ba58a9ec094a58f2a82340e134ba1")
    public void fetchPopularMovies(Callback<Movie> movie);

 @GET("/discover/movie?primary_release_year=2015&sort_by=popularity.desc&api_key=b67ba58a9ec094a58f2a82340e134ba1")
 public void fetchMovies(Callback<Movie> movie);

   // /movie/281957/videos?api_key=b67ba58a9ec094a58f2a82340e134ba1

    @GET("/movie/{id}/videos?api_key=b67ba58a9ec094a58f2a82340e134ba1")
    public void fetchVideos(@Path("id") int id, Callback<Video> video);




}
