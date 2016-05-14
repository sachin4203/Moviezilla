package com.example.sachin.moviezilla;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sachin.moviezilla.data.FavouriteMovieColumns;
import com.example.sachin.moviezilla.data.PlanetProvider;
import com.example.sachin.moviezilla.model.Video;
import com.squareup.picasso.Picasso;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MovieDetail extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = MovieDetail.class.getSimpleName();
    TextView title;
    TextView overview;
    TextView releaseDate;
    TextView voteAverage;
    ImageView imageView,backdrop;
    String mtitle,mPoster,mOverView ,mReleaseDate,mBackdrop;
    Double mVoteAverage;
    ImageButton Fav;
    ImageButton playButton;
    private static final int CURSOR_LOADER_ID = 0;
    long _id = 232;
    int mMovieId;
    String key;

   /* @Override
    public void onActivityCreated(Bundle savedInstanceState){
        Cursor c = this.getContentResolver().query(PlanetProvider.Planets.CONTENT_URI,
                null, null, null, null);
      //  Log.i(LOG_TAG, "cursor count: " + c.getCount());
        if (c == null || c.getCount() == 0){
            insertData();
        }


        *//*getLoaderManager().initLoader(CURSOR_LOADER_ID, null, this);
        super.onActivityCreated(savedInstanceState);*//*
    }*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(getApplicationContext() , ReviewsActivity.class);
                intent.putExtra(getString(R.string.in_rev_id), ""+mMovieId);
                startActivity(intent);

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = (TextView) findViewById(R.id.textmTitle);
        overview = (TextView) findViewById(R.id.textOverview);
        overview.setMovementMethod(new ScrollingMovementMethod());
        releaseDate = (TextView) findViewById(R.id.textRelease);
        voteAverage = (TextView) findViewById(R.id.textRatings);
        imageView = (ImageView) findViewById(R.id.imageView);
        backdrop = (ImageView) findViewById(R.id.posterBackdrop);
        Fav = (ImageButton) findViewById(R.id.button);
        playButton = (ImageButton) findViewById(R.id.playbutton);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            mtitle = extras.getString(getString(R.string.in_title));
            mPoster = extras.getString(getString(R.string.in_poster));
            mOverView = extras.getString(getString(R.string.in_overview));
            mReleaseDate = extras.getString(getString(R.string.in_release));
            mVoteAverage = extras.getDouble(getString(R.string.in_vote));
            mBackdrop = extras.getString(getString(R.string.in_backdrop));
            mMovieId= extras.getInt(getString(R.string.in_id));

        }
        title.setText(mtitle);
        getSupportActionBar().setTitle(mtitle);
        overview.setText(mOverView);
        releaseDate.setText(mReleaseDate);
        voteAverage.setText(mVoteAverage.toString() + "/10");
        Picasso.with(this).load(mPoster).into(backdrop);
        Picasso.with(this).load(mBackdrop).into(imageView);

        playButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Intent intent = new  Intent(Intent.ACTION_VIEW);

                intent.setPackage("com.google.android.youtube");
                intent.setData(Uri.parse("https://www.youtube.com/watch?v="+key));

                startActivity(intent);

            }

        } );

        Fav.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {





                    insertData();


               // Toast.makeText(getApplicationContext(), pref.getString("set_fav", "yes"), Toast.LENGTH_LONG).show();
               // Fav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                // Toast.makeText(MainActivity.this, "Button Clicked", Toast.LENGTH_SHORT).show();
                /*ContentValues[] flavorValuesArr = new ContentValues[1];
                // Loop through static array of Flavors, add each to an instance of ContentValues
                // in the array of ContentValues
                for(int i = 0; i < 2; i++){
                    flavorValuesArr[i] = new ContentValues();
                    flavorValuesArr[i].put(FavouriteContract.FavouriteEntry.COLUMN_TITLE, mtitle);
                    flavorValuesArr[i].put(FavouriteContract.FavouriteEntry.COLUMN_VERSION_NAME,
                            "gagag");
                    flavorValuesArr[i].put(FavouriteContract.FavouriteEntry.COLUMN_OVERVIEW,
                            mOverView);
                    flavorValuesArr[i].put(FavouriteContract.FavouriteEntry.COLUMN_RELEASE_DATE,
                            mReleaseDate);
                    flavorValuesArr[i].put(FavouriteContract.FavouriteEntry.COLUMN_VOTE_AVERAGE,
                            mVoteAverage);
                    flavorValuesArr[i].put(FavouriteContract.FavouriteEntry.COLUMN_BACKDROP,
                            mBackdrop);
                    flavorValuesArr[i].put(FavouriteContract.FavouriteEntry.COLUMN_POSTER,
                            mPoster);
            }
                // bulkInsert our ContentValues array
                getApplicationContext().getContentResolver().bulkInsert(FavouriteContract.FavouriteEntry.CONTENT_URI,
                        flavorValuesArr);/**/
            }

        });

    }



    public void insertData(){

        ContentValues cv = new ContentValues();


       // ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>(1);



        cv.put(FavouriteMovieColumns.TITLE, mtitle);
        cv.put(FavouriteMovieColumns.OVERVIEW, mOverView);
        cv.put(FavouriteMovieColumns.POSTER_PATH, mPoster);
        cv.put(FavouriteMovieColumns.VOTE_AVERAGE, mVoteAverage);
        cv.put(FavouriteMovieColumns.RELEASE_DATE, mReleaseDate);
        cv.put(FavouriteMovieColumns.BACK_DROP, mBackdrop);
        cv.put(FavouriteMovieColumns.MOVIE_ID, mMovieId);
        this.getContentResolver().insert(
                PlanetProvider.FavouriteMovies.withId(_id), cv);


        _id++;


    }

    @Override
    public void onResume() {

        super.onResume();
        Log.d(LOG_TAG, "resume called");


      /* /*//*//* getLoaderManager().restartLoader(CURSOR_LOADER_ID, null, this);
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
         favIcon = getResources().getString(R.string.icon_shared_fav);
        if(favIcon!=null && favIcon.equalsIgnoreCase("favourite"))))
        {

        }*/
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3/")
                .build();

        MovieInterface methods = restAdapter.create(MovieInterface.class);
        Callback callback = new Callback<Video>() {
            @Override
            public void success(Video vid , Response response) {

                if (vid.results != null && vid.results.size() > 0) {
                    for (int i = 0; i < 1; i++) {
                        key = vid.results.get(i).key;


                    }

                }

            }

            @Override
            public void failure(RetrofitError retrofitError) {

            }
        };
        methods.fetchVideos(mMovieId , callback);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args){
        return new CursorLoader(this, PlanetProvider.FavouriteMovies.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data){
       // mCursorAdapter.swapCursor(data);


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader){
      //  mCursorAdapter.swapCursor(null);
    }
}





