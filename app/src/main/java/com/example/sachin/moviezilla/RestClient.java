package com.example.sachin.moviezilla;

/**
 * Created by Sachin on 8/10/2016.
 */
public class RestClient {

    private static final String URL = "http://api.themoviedb.org/3/";

    private retrofit.RestAdapter restAdapter;
    private MovieInterface movieInterface;


    public RestClient()
    {

        restAdapter = new retrofit.RestAdapter.Builder()
                .setEndpoint(URL)
                .setLogLevel(retrofit.RestAdapter.LogLevel.FULL)
                .build();

        movieInterface = restAdapter.create(MovieInterface.class);

    }

    public MovieInterface getService()
    {
        return movieInterface;
    }

    public void getdata(String result){

    }
}
