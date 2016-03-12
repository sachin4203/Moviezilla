package com.example.sachin.moviezilla;

import com.example.sachin.moviezilla.model.Movie;
import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by Tan on 8/10/2015.
 */
public interface MovieInterface {

    //So these are the list available in our WEB API and the methods look straight forward

   /* @GET("/Array.txt")
    public void getArray(Callback<List<String>> items);

    @GET("/ArrayWithObjects.txt")
    public void getArrayWithObjects(Callback<List<Movie>> callback);

    @GET("/Object.txt")
    public void getObject(Callback<Movie> student);

    @GET("/ObjectWithNestedArray.txt")
    public void getObjectWithNestedArray(Callback<Movie> student);

    @GET("/ObjectWithNestedObject.txt")
    public void getObjectWithNestedObject(Callback<Movie> student);*/

    @GET("/movie?vote_average.gte=7&vote_average.lte=10&sort_by=vote_count.desc&api_key=b67ba58a9ec094a58f2a82340e134ba1")
    public void fetchPopularMovies(Callback<Movie> movie);

 @GET("/movie?primary_release_year=2015&sort_by=popularity.desc&api_key=b67ba58a9ec094a58f2a82340e134ba1")
 public void fetchMovies(Callback<Movie> movie);
}
