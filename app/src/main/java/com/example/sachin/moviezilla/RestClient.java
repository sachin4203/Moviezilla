package com.example.sachin.moviezilla;

/**
 * Created by Sachin on 8/10/2016.
 */
public class RestClient {
    //You need to change the IP if you testing environment is not local machine
    //or you may have different URL than we have here
   // private static final String URL = "http://instinctcoder.com/wp-content/uploads/2015/08/";
    private static final String URL = "http://api.themoviedb.org/3/discover/";

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
