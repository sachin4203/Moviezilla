package com.example.sachin.moviezilla;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sachin.moviezilla.model.Reviews;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ReviewsActivity extends AppCompatActivity {

  String mId;
    private String author[];
    private String reviews[];
    ListView listView;
    private String names[] = {
            "HTML",
            "CSS",
            "Java Script",
            "Wordpress"
    };

    private String desc[] = {
            "The Powerful Hypter Text Markup Language 5",
            "Cascading Style Sheets",
            "Code with Java Script",
            "Manage your content with Wordpress"
    };


    private List<ReviewModel> mReviewsData = new ArrayList<>();
    private CustomList reviewDataAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

  Bundle extras = getIntent().getExtras();

        if (extras != null) {
            mId = extras.getString(getString(R.string.in_rev_id));

            Toast.makeText(getApplicationContext(), mId, Toast.LENGTH_LONG).show();

        }


        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3/")
                .build();

        MovieInterface methods = restAdapter.create(MovieInterface.class);
        Callback callback = new Callback<Reviews>() {
            @Override
            public void success(Reviews rev , Response response) {



                if (rev.results != null && rev.results.size() > 0){
                    Toast.makeText(getApplicationContext(), "Sucesssss!!", Toast.LENGTH_LONG).show();
                    for (int i = 0; i < rev.results.size(); i++) {
                        // key = vid.results.get(i).key;
                        ReviewModel revObj = new ReviewModel();
                            revObj.setAuthor(rev.results.get(i).author);
                            revObj.setReview(rev.results.get(i).content);
                        mReviewsData.add(revObj);
                       // Toast.makeText(getApplicationContext(), tAuthor.get(i), Toast.LENGTH_LONG).show();

                    }
                }





            }

            @Override
            public void failure(RetrofitError retrofitError) {

                Toast.makeText(getApplicationContext(),retrofitError.toString(),Toast.LENGTH_LONG).show();
            }
        };
        methods.fetchReviews(mId, callback);

        listView = (ListView) findViewById(R.id.reviewsListView);
        //Custom ListAdapter for our android custom listview
        reviewDataAdapter = new CustomList(this, mReviewsData);
        //Setting ListView's adapter to the custom list adapter we've created
        listView.setAdapter(reviewDataAdapter);




/*

        listView = (ListView) findViewById(R.id.reviewsListView);
        CustomList customList = new CustomList(this, tAuthor, tReviews);


        listView.setAdapter(customList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "You Clicked " + author[i], Toast.LENGTH_SHORT).show();
            }
        });
*/


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



    @Override
    public void onResume() {

        super.onResume();
       /* Log.d(LOG_TAG, "resume called");*/






       /* listView = (ListView) findViewById(R.id.reviewsListView);
        CustomList customList = new CustomList(this, author, reviews);


        listView.setAdapter(customList);*/
    }

    }
