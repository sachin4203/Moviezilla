package com.example.sachin.moviezilla;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.sachin.moviezilla.model.Movie;
import com.twotoasters.jazzylistview.JazzyGridView;
import com.twotoasters.jazzylistview.JazzyHelper;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }
    RestClient restClient;
    private static final String TAG = MainActivityFragment.class.getSimpleName();
    private JazzyGridView mGridView;
    private ProgressBar mProgressBar;
    private GridViewAdapter mGridAdapter;
    private ArrayList<GridItem> mGridData;
    private RelativeLayout mRelativeLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
       // setHasOptionsMenu(true);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);


        mGridView = (JazzyGridView) view.findViewById(R.id.gridView);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mRelativeLayout= (RelativeLayout)view.findViewById(R.id.container);
          mGridView.setTransitionEffect(JazzyHelper.HELIX);
        mProgressBar.setVisibility(View.VISIBLE);
        mRelativeLayout.setVisibility(View.GONE);
        //Initialize with empty data
        mGridData = new ArrayList<>();
        mGridAdapter = new GridViewAdapter(getActivity(), R.layout.grid_item_layout, mGridData);
        mGridView.setAdapter(mGridAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent intent = new Intent(getActivity(), MovieDetail.class);
                intent.putExtra("title", mGridData.get(position).getOriginal_title());
                intent.putExtra("poster", mGridData.get(position).getPoster_path());
                intent.putExtra("release", mGridData.get(position).getRelease_date());
                intent.putExtra("overview", mGridData.get(position).getOverview());
                intent.putExtra("vote", mGridData.get(position).getVote_average());
                intent.putExtra("backdrop", mGridData.get(position).getBackdrop_path());

                startActivity(intent);
            }
        });

        return view;

    }

    public void fetchMov(Movie movie){
        mProgressBar.setVisibility(View.GONE);
        GridItem item;
        if (movie.results != null && movie.results.size() > 0) {
            for (int i = 0; i < movie.results.size(); i++) {
                        /*result += "Title: " + movie.results.get(i).original_title +'\n' +
                                "Overveiw :" + movie.results.get(i).overview +'\n';*/
                item = new GridItem();
                String poster = "http://image.tmdb.org/t/p/w300" + movie.results.get(i).poster_path;
                item.setPoster_path(poster);
                String backdrop = "http://image.tmdb.org/t/p/w500" + movie.results.get(i).backdrop_path;
                item.setBackdrop_path(backdrop);
                String title = movie.results.get(i).original_title;
                item.setOriginal_title(title);
                String overview = movie.results.get(i).overview;
                item.setOverview(overview);
                double voteAverage = movie.results.get(i).vote_average;
                item.setVote_average(voteAverage);
                String releaseDate = movie.results.get(i).release_date;
                item.setRelease_date(releaseDate);
                mGridData.add(item);

            }
        }

        mGridAdapter.setGridData(mGridData);
    }



    @Override
    public void onResume(){
        super.onResume();
        restClient = new RestClient();
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getActivity());

        String downloadType = SP.getString("movieType","Popular");
        if(downloadType!=null && !downloadType.equalsIgnoreCase("Popular"))
        {mGridData.clear();
            restClient.getService().fetchMovies(new Callback<Movie>() {
                @Override
                public void success(Movie movie, Response response) {
                /*String result = "";*/

                    mProgressBar.setVisibility(View.GONE);
                    mRelativeLayout.setVisibility(View.VISIBLE);
                    fetchMov(movie);

                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(getActivity(), error.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });

        }
        else {
            mGridData.clear();
            restClient.getService().fetchPopularMovies(new Callback<Movie>() {
                @Override
                public void success(Movie movie, Response response) {
                /*String result = "";*/
                    mProgressBar.setVisibility(View.GONE);
                    mRelativeLayout.setVisibility(View.VISIBLE);
                    fetchMov(movie);

                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(getActivity(), error.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });

        }






    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {

           /* Intent i = new Intent(getActivity(),Mysettings.class);
            startActivity(i);*/
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
