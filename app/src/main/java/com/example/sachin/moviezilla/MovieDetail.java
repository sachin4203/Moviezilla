package com.example.sachin.moviezilla;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetail extends AppCompatActivity {


    TextView title;
    TextView overview;
    TextView releaseDate;
    TextView voteAverage;
    ImageView imageView,backdrop;
    String mtitle,mPoster,mOverView ,mReleaseDate,mBackdrop;
    Double mVoteAverage;


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

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
             mtitle = extras.getString("title");
             mPoster = extras.getString("poster");
             mOverView = extras.getString("overview");
             mReleaseDate= extras.getString("release");
             mVoteAverage= extras.getDouble("vote");
            mBackdrop =extras.getString("backdrop");

        }
        title.setText(mtitle);
        getSupportActionBar().setTitle(mtitle);
        overview.setText(mOverView);
        releaseDate.setText(mReleaseDate);
        voteAverage.setText(mVoteAverage.toString()+"/10");
        Picasso.with(this).load(mPoster).into(backdrop);
        Picasso.with(this).load(mBackdrop).into(imageView);


    }

}
